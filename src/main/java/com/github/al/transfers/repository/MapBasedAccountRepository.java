package com.github.al.transfers.repository;

import com.github.al.transfers.ServiceException;
import com.github.al.transfers.model.Account;
import com.github.al.transfers.model.AccountCreationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class MapBasedAccountRepository implements AccountRepository {

    private static final Logger log = LoggerFactory.getLogger(MapBasedAccountRepository.class);

    private final Map<Long, Account> data = new ConcurrentHashMap<>();

    private AtomicLong nextId;

    public MapBasedAccountRepository() {
        nextId = new AtomicLong(0);
    }

    @Override
    public Account create(AccountCreationRequest request) {
        Account account = new Account(nextId.incrementAndGet(), request.getAmount());
        data.put(account.getId(), account);
        return account;
    }

    @Override
    public Collection<Account> findAll() {
        return data.values();
    }

    @Override
    public Optional<Account> findById(long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void transfer(long from, long to, BigDecimal amount) {
        log.debug("transfer from {} to {} amount {}", from, to, amount);

        Account fromAccount = findById(from).orElseThrow(() -> new ServiceException(
                String.format("transfer failed: withdraw account [%s] doesn't exist", from)));
        Account toAccount = findById(to).orElseThrow(() -> new ServiceException(
                String.format("transfer failed: deposit account [%s] doesn't exist", to)));

        if (from == to) {
            throw new ServiceException("transfer failed: same account");
        }

        Account firstAccount = fromAccount.getId() < toAccount.getId() ? fromAccount : toAccount;
        Account secondAccount = fromAccount.getId() < toAccount.getId() ? toAccount : fromAccount;

        while (true) {
            if (firstAccount.getLock().tryLock()) {
                try {
                    if (secondAccount.getLock().tryLock()) {
                        try {
                            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                                throw new ServiceException(
                                        String.format("transfer failed: amount %s is invalid", amount));
                            }
                            if (fromAccount.getBalance().compareTo(amount) < 0) {
                                throw new ServiceException(
                                        String.format("transfer failed: account [%s] doesn't have enough money", from));
                            }
                            fromAccount.withdraw(amount);
                            toAccount.deposit(amount);
                            return;
                        } finally {
                            secondAccount.getLock().unlock();
                        }
                    }
                } finally {
                    firstAccount.getLock().unlock();
                }
            }
        }
    }
}
