package com.github.al.transfers.repository;

import com.github.al.transfers.model.TransferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MapBasedTransferRepositoryTest {

    private MapBasedTransferRepository repo;

    @BeforeEach
    void beforeEach() {
        repo = new MapBasedTransferRepository();
    }

    @Test
    void should_returnEmptyCollection_when_repoIsEmpty() {
        assertThat(repo.findAll()).isEmpty();
    }

    @Test
    void should_createCorrectTransfer() {
        var transfer = repo.create(new TransferRequest(1, 2, new BigDecimal("1")));

        assertThat(transfer.getId()).isEqualTo(1);
        assertThat(transfer.getFromAccountId()).isEqualTo(1);
        assertThat(transfer.getToAccountId()).isEqualTo(2);
        assertThat(transfer.getAmount()).isEqualTo("1");
    }

    @Test
    void should_createCorrectTransferIds() {
        var transfer1 = repo.create(new TransferRequest(1, 2, new BigDecimal("1")));
        var transfer2 = repo.create(new TransferRequest(1, 2, new BigDecimal("2")));

        assertThat(transfer1.getId()).isEqualTo(1);
        assertThat(transfer2.getId()).isEqualTo(2);
    }

    @Test
    void should_returnEmptyOptional_when_idNotExists() {
        assertThat(repo.findById(1)).isEmpty();
    }

    @Test
    void should_returnFullOptional_when_idExists() {
        var transfer = repo.create(new TransferRequest(1, 2, new BigDecimal("1")));

        assertThat(repo.findById(1)).contains(transfer);
    }

    @Test
    void should_returnResult_when_accountIdExists() {
        var transfer = repo.create(new TransferRequest(1, 2, new BigDecimal("1")));

        assertThat(repo.findByAccountId(1)).contains(transfer);
        assertThat(repo.findByAccountId(2)).contains(transfer);
        assertThat(repo.findByAccountId(3)).isEmpty();
    }
}