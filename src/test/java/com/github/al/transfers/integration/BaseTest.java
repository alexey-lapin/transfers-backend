package com.github.al.transfers.integration;

import com.github.al.transfers.App;
import com.github.al.transfers.dto.*;
import com.github.al.transfers.model.*;
import com.github.al.transfers.web.ErrorResponse;
import io.javalin.Javalin;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigDecimal;
import java.util.Collection;

import static io.restassured.RestAssured.given;

class BaseTest {

    private static final int DEFAULT_TEST_POST = 8000;

    private static final String PATH_ACCOUNTS = "/accounts";
    private static final String PATH_ACCOUNTS_ID = "/accounts/{id}";
    private static final String PATH_API = "/api";
    public static final String PATH_TRANSFER = "transfer";

    private static Javalin app;
    static RequestSpecification spec;

    @BeforeAll
    static void beforeAll() {
        app = App.start(DEFAULT_TEST_POST);
        spec = new RequestSpecBuilder()
                .setPort(DEFAULT_TEST_POST)
                .setBasePath(PATH_API)
                .setContentType(ContentType.JSON)
//                .addFilter(new RequestLoggingFilter())
//                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    @AfterAll
    static void afterAll() {
        app.stop();
    }

    Response createResource(String path, Object payload) {
        return given()
                .spec(spec)
                .body(payload)
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }

    Response createAccountResponse(String amount) {
        return given()
                .spec(spec)
                .body(new AccountCreationRequestDTO(new AccountCreationRequest().amount(new BigDecimal(amount))))
                .when()
                .post(PATH_ACCOUNTS)
                .then()
                .extract()
                .response();
    }

    Account createAccount(String amount) {
        return createAccountResponse(amount)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(AccountDTO.class)
                .getAccount();
    }

    Response getAccountResponse(long id) {
        return given()
                .spec(spec)
                .pathParam("id", id)
                .when()
                .get(PATH_ACCOUNTS_ID)
                .then()
                .extract()
                .response();
    }

    Account getAccount(long id) {
        return getAccountResponse(id)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(AccountDTO.class)
                .getAccount();
    }

    Collection<Account> getAccounts() {
        return given()
                .spec(spec)
                .when()
                .get(PATH_ACCOUNTS)
                .then()
                .extract()
                .body()
                .as(AccountsDTO.class)
                .getAccounts();
    }

    Response transferResponse(TransferRequest payload) {
        return given()
                .spec(spec)
                .body(new TransferRequestDTO(payload))
                .when()
                .post(PATH_TRANSFER);
    }

    Transfer transfer(long from, long to, String amount) {
        return transferResponse(new TransferRequest(from, to, new BigDecimal(amount)))
                .then()
                .extract()
                .body()
                .as(TransferDTO.class)
                .getTransfer();
    }

    ErrorResponse transferError(long from, long to, String amount) {
        return transferResponse(new TransferRequest(from, to, new BigDecimal(amount)))
                .then()
                .statusCode(400)
                .extract()
                .body()
                .as(ErrorResponse.class);
    }
}
