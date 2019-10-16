package com.github.al.transfers.integration;

import com.github.al.transfers.dto.AccountCreationRequestDTO;
import com.github.al.transfers.web.ErrorResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest extends BaseTest {

    @Test
    void should_succeed200_when_payloadAmountIsCorrectInt() {
        var account = createAccount("100");

        assertThat(account.getId()).isGreaterThan(0);
        assertThat(account.getBalance()).isEqualTo("100");
    }

    @Test
    void should_succeed200_when_payloadAmountIsCorrectDecimal() {
        var account = createAccount("100.20");

        assertThat(account.getId()).isGreaterThan(0);
        assertThat(account.getBalance()).isEqualTo("100.20");
    }

    @Test
    void should_succeed200_when_payloadAmountIsZero() {
        var account = createAccount("0.00");

        assertThat(account.getId()).isGreaterThan(0);
        assertThat(account.getBalance()).isEqualTo("0.00");
    }

    @Test
    void should_fail400_when_payloadIsImproperObject() {
        var error = createResource("accounts", "")
                .then()
                .statusCode(400)
                .extract()
                .body()
                .as(ErrorResponse.class);

        var message = error.getError().getMessage();
        assertThat(message).startsWith("Couldn't deserialize body");
    }

    @Test
    void should_fail_when_payloadAmountIsInvalid() {
        var error = createAccountResponse("-100")
                .then()
                .statusCode(400)
                .extract()
                .body()
                .as(ErrorResponse.class);

        var message = error.getError().getMessage();
        assertThat(message).startsWith(
                String.format("Request body as %s invalid", AccountCreationRequestDTO.class.getSimpleName()));
    }

    @Test
    void should_create1000AccountsInParallel() {
        var ints = new ArrayList<Integer>();
        IntStream.range(0, 1000).parallel().forEach(i -> {
            ints.add(i);

            var balance = createAccount(String.valueOf(i)).getBalance().intValue();

            assertThat(balance).isEqualTo(i);
        });

        assertThat(getAccounts()).extracting(account -> account.getBalance().intValue()).containsAll(ints);
    }

    @Test
    void should_failed404_when_accountNotExists() {
        getAccountResponse(100000000)
                .then()
                .statusCode(404)
                .extract()
                .body()
                .as(ErrorResponse.class);
    }
}
