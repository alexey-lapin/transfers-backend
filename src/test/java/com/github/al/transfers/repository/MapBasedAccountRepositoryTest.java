package com.github.al.transfers.repository;

import com.github.al.transfers.model.AccountCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MapBasedAccountRepositoryTest {

    private MapBasedAccountRepository repo;

    @BeforeEach
    void beforeEach() {
        repo = new MapBasedAccountRepository();
    }

    @Test
    void should_returnEmptyCollection_when_repoIsEmpty() {
        assertThat(repo.findAll()).isEmpty();
    }

    @Test
    void should_createCorrectAccount() {
        var account = repo.create(new AccountCreationRequest().amount(new BigDecimal("1")));

        assertThat(account.getId()).isEqualTo(1);
        assertThat(account.getBalance()).isEqualTo("1");
    }

    @Test
    void should_createCorrectAccountIds() {
        var account1 = repo.create(new AccountCreationRequest().amount(new BigDecimal("1")));
        var account2 = repo.create(new AccountCreationRequest().amount(new BigDecimal("2")));

        assertThat(account1.getId()).isEqualTo(1);
        assertThat(account2.getId()).isEqualTo(2);
    }

    @Test
    void should_returnEmptyOptional_when_idNotExists() {
        assertThat(repo.findById(1)).isEmpty();
    }

    @Test
    void should_returnFullOptional_when_idExists() {
        var account = repo.create(new AccountCreationRequest().amount(new BigDecimal("1")));

        assertThat(repo.findById(1)).contains(account);
    }
}