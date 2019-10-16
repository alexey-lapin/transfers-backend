package com.github.al.transfers.service;

import com.github.al.transfers.model.Account;
import com.github.al.transfers.model.AccountCreationRequest;
import com.github.al.transfers.repository.AccountRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

@Singleton
public class SimpleAccountService {

    private AccountRepository accountRepository;

    @Inject
    public SimpleAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(AccountCreationRequest request) {
        return accountRepository.create(request);
    }

    public Collection<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(long id) {
        return accountRepository.findById(id);
    }

    public void transfer(long from, long to, BigDecimal amount) {
        accountRepository.transfer(from, to, amount);
    }

}
