package com.github.al.transfers.dto;

import com.github.al.transfers.model.Account;

public class AccountDTO {

    private Account account;

    AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
