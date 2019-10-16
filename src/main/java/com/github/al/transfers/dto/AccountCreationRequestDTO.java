package com.github.al.transfers.dto;

import com.github.al.transfers.model.AccountCreationRequest;

public class AccountCreationRequestDTO {

    private AccountCreationRequest account;

    AccountCreationRequestDTO() {
    }

    public AccountCreationRequestDTO(AccountCreationRequest account) {
        this.account = account;
    }

    public AccountCreationRequest getAccount() {
        return account;
    }
}
