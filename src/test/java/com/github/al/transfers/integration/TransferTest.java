package com.github.al.transfers.integration;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class TransferTest extends BaseTest {

    @Test
    void should_success_when_payloadAmountIsCorrectInt() {
        var from = createAccount("20").getId();
        var to = createAccount("0").getId();

        var transfer = transfer(from, to, "10");

        assertThat(transfer.getId()).isGreaterThan(0);
        assertThat(transfer.getFromAccountId()).isEqualTo(from);
        assertThat(transfer.getToAccountId()).isEqualTo(to);
        assertThat(transfer.getAmount()).isEqualTo("10");

        assertThat(getAccount(from).getBalance()).isEqualTo("10");
        assertThat(getAccount(to).getBalance()).isEqualTo("10");
    }

    @Test
    void should_success_when_payloadAmountIsCorrectDecimal() {
        var from = createAccount("20.85").getId();
        var to = createAccount("11.30").getId();

        var transfer = transfer(from, to, "5.5");

        assertThat(transfer.getId()).isGreaterThan(0);
        assertThat(transfer.getFromAccountId()).isEqualTo(from);
        assertThat(transfer.getToAccountId()).isEqualTo(to);
        assertThat(transfer.getAmount()).isEqualTo("5.5");

        assertThat(getAccount(from).getBalance()).isEqualTo("15.35");
        assertThat(getAccount(to).getBalance()).isEqualTo("16.80");
    }

    @Test
    void should_fail400_when_payloadFromIdNotExists() {
        var error = transferError(1000000L, 1000001L, "10");

        var message = error.getError().getMessage();
        assertThat(message).endsWith("doesn't exist");
    }

    @Test
    void should_fail400_when_payloadToIdNotExists() {
        var from = createAccount("10").getId();

        var error = transferError(from, 1000001L, "10");

        var message = error.getError().getMessage();
        assertThat(message).endsWith("doesn't exist");
    }


    @Test
    void should_fail400_when_payloadAmountIsNegativeInt() {
        var from = createAccount("10").getId();
        var to = createAccount("10").getId();

        var error = transferError(from, to, "-10");

        var message = error.getError().getMessage();
        assertThat(message).endsWith("is invalid");
    }

    @Test
    void should_fail400_when_payloadAmountIsZeroInt() {
        var from = createAccount("10").getId();
        var to = createAccount("10").getId();

        var error = transferError(from, to, "0");

        var message = error.getError().getMessage();
        assertThat(message).endsWith("is invalid");
    }

    @Test
    void should_fail400_when_payloadAmountIsGreaterThanBalance() {
        var from = createAccount("10").getId();
        var to = createAccount("10").getId();

        var error = transferError(from, to, "20");

        var message = error.getError().getMessage();
        assertThat(message).endsWith("enough money");
    }

    @Test
    void should_fail400_when_payloadFromIdIsSameAsToId() {
        var from = createAccount("10").getId();

        var error = transferError(from, from, "10");

        var message = error.getError().getMessage();
        assertThat(message).endsWith("same account");
    }

    @Test
    void should_succeed_when_1000ParallelTransferBetween2Accounts() {
        var from = createAccount("1000").getId();
        var to = createAccount("2000").getId();

        IntStream.range(1, 1001).parallel().forEach(i -> {
            if (i % 2 == 0) {
                transfer(from, to, "5");
            } else {
                transfer(to, from, "5");
            }
        });

        assertThat(getAccount(from).getBalance()).isEqualTo("1000");
        assertThat(getAccount(to).getBalance()).isEqualTo("2000");
    }

    @Test
    void should_succeed_when_1000ParallelTransferBetween3Accounts() {
        var a1 = createAccount("1000").getId();
        var a2 = createAccount("2000").getId();
        var a3 = createAccount("0").getId();

        IntStream.range(1, 1001).parallel().forEach(i -> {
            if (i % 2 == 0) {
                transfer(a1, a3, "2");
            } else {
                transfer(a2, a3, "2");
            }
        });

        assertThat(getAccount(a1).getBalance()).isEqualTo("0");
        assertThat(getAccount(a2).getBalance()).isEqualTo("1000");
        assertThat(getAccount(a3).getBalance()).isEqualTo("2000");
    }

    @Test
    void should_succeed_when_1000ParallelTransferBetween4Accounts() {
        var a1 = createAccount("1000").getId();
        var a2 = createAccount("2000").getId();
        var a3 = createAccount("0").getId();
        var a4 = createAccount("0").getId();

        IntStream.range(1, 1001).parallel().forEach(i -> {
            if (i % 2 == 0) {
                transfer(a1, a3, "2");
            } else {
                transfer(a2, a4, "2");
            }
        });

        assertThat(getAccount(a1).getBalance()).isEqualTo("0");
        assertThat(getAccount(a2).getBalance()).isEqualTo("1000");
        assertThat(getAccount(a2).getBalance()).isEqualTo("1000");
        assertThat(getAccount(a4).getBalance()).isEqualTo("1000");
    }
}
