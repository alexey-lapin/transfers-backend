package com.github.al.transfers.repository;

import com.github.al.transfers.model.Account;
import com.github.al.transfers.model.AccountCreationRequest;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface AccountRepository {

    Account create(AccountCreationRequest request);

    Collection<Account> findAll();

    Optional<Account> findById(long id);

    void transfer(long from, long to, BigDecimal amount);
}
