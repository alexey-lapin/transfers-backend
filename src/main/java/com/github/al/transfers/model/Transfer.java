package com.github.al.transfers.model;

import java.math.BigDecimal;

public class Transfer {

    private long id;

    private long fromAccountId;

    private long toAccountId;

    private BigDecimal amount;

    Transfer() {
    }

    public Transfer(long id, long fromAccountId, long toAccountId, BigDecimal amount) {
        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public long getToAccountId() {
        return toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
