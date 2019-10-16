package com.github.al.transfers.web;

import com.github.al.transfers.dto.TransferDTO;
import com.github.al.transfers.dto.TransferRequestDTO;
import com.github.al.transfers.dto.TransfersDTO;
import com.github.al.transfers.model.*;
import com.github.al.transfers.service.SimpleAccountService;
import com.github.al.transfers.service.SimpleTransferService;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Objects;

@Singleton
public class TransferController {

    private SimpleAccountService accountService;
    private SimpleTransferService transferService;

    @Inject
    public TransferController(SimpleAccountService accountService, SimpleTransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    public void findAll(Context ctx) {
        Collection<Transfer> transfers = transferService.findAll();
        ctx.json(new TransfersDTO(transfers));
    }

    public void findById(Context ctx) {
        long id = ctx.pathParam("id", Long.class)
                .check(Objects::nonNull, "id should not be empty")
                .get();
        Transfer transfer = transferService.findById(id).orElseThrow(NotFoundResponse::new);
        ctx.json(new TransferDTO(transfer));
    }

    public void findByAccountId(Context ctx) {
        long id = ctx.pathParam("id", Long.class)
                .check(Objects::nonNull, "id should not be empty")
                .get();
        Collection<Transfer> transfers = transferService.findByAccountId(id);
        ctx.json(new TransfersDTO(transfers));
    }

    public void transfer(Context ctx) {
        TransferRequestDTO transferRequestDTO = ctx.bodyValidator(TransferRequestDTO.class)
                .check(Objects::nonNull, "request body should not be empty")
                .check(r -> r.getTransfer() != null, "transfer should not be empty")
                .check(r -> r.getTransfer().getFromAccountId() != null, "fromAccountId should not be empty")
                .check(r -> r.getTransfer().getToAccountId() != null, "toAccountId account id should not be empty")
                .check(r -> r.getTransfer().getAmount() != null, "amount should not be empty")
                .get();

        TransferRequest transferRequest = transferRequestDTO.getTransfer();
        accountService.transfer(transferRequest.getFromAccountId(),
                transferRequest.getToAccountId(),
                transferRequest.getAmount());
        Transfer transfer = transferService.create(transferRequest);
        ctx.json(new TransferDTO(transfer));
    }
}
