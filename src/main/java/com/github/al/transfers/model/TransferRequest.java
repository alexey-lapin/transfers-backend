package com.github.al.transfers.model;

import java.math.BigDecimal;

public class TransferRequest {

    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;

    TransferRequest() {
    }

    public TransferRequest(long fromAccountId, long toAccountId, BigDecimal amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
