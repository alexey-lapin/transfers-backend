package com.github.al.transfers.web;

import com.github.al.transfers.dto.AccountCreationRequestDTO;
import com.github.al.transfers.dto.AccountDTO;
import com.github.al.transfers.dto.AccountsDTO;
import com.github.al.transfers.model.*;
import com.github.al.transfers.service.SimpleAccountService;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Singleton
public class AccountController {

    private SimpleAccountService accountService;

    @Inject
    public AccountController(SimpleAccountService accountService) {
        this.accountService = accountService;
    }

    public void create(Context ctx) {
        AccountCreationRequestDTO request = ctx.bodyValidator(AccountCreationRequestDTO.class)
                .check(Objects::nonNull, "request body should not be empty")
                .check(r -> r.getAccount() != null, "account should not be empty")
                .check(r -> r.getAccount().getAmount().compareTo(BigDecimal.ZERO) >= 0,
                        "account balance should be >= 0")
                .get();
        Account account = accountService.create(request.getAccount());
        ctx.json(new AccountDTO(account));
    }

    public void findAll(Context ctx) {
        Collection<Account> accounts = accountService.findAll();
        ctx.json(new AccountsDTO(accounts));
    }

    public void findById(Context ctx) {
        long id = ctx.pathParam("id", Long.class)
                .check(Objects::nonNull, "id should not be empty")
                .get();
        Account account = accountService.findById(id).orElseThrow(NotFoundResponse::new);
        ctx.json(new AccountDTO(account));
    }
}
