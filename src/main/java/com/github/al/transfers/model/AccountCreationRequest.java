package com.github.al.transfers.model;

import java.math.BigDecimal;

public class AccountCreationRequest {

    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public AccountCreationRequest amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
