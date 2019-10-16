package com.github.al.transfers.dto;

import com.github.al.transfers.model.Account;

import java.util.Collection;

public class AccountsDTO {

    private Collection<Account> accounts;

    AccountsDTO() {
    }

    public AccountsDTO(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }
}
