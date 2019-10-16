package com.github.al.transfers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.al.transfers.ServiceException;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    @JsonIgnore
    private ReentrantLock lock = new ReentrantLock();

    private long id;

    private BigDecimal balance;

    Account() {
    }

    public Account(long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public Lock getLock() {
        return lock;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void withdraw(BigDecimal amount) {
        if (lock.isLocked()) {
            if (balance.compareTo(amount) < 0) {
                throw new ServiceException(String.format("withdraw failed: account [%s] doesn't have enough money", id));
            }
            balance = balance.subtract(amount);
        } else {
            throw new ServiceException(String.format("withdraw failed: account [%s] isn't locked", id));
        }
    }

    public void deposit(BigDecimal amount) {
        if (lock.isLocked()) {
            balance = balance.add(amount);
        } else {
            throw new ServiceException(String.format("withdraw failed: account [%s] isn't locked", id));
        }
    }
}
