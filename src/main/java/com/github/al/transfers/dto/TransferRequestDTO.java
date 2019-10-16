package com.github.al.transfers.dto;

import com.github.al.transfers.model.TransferRequest;

public class TransferRequestDTO {

    private TransferRequest transfer;

    TransferRequestDTO() {
    }

    public TransferRequestDTO(TransferRequest transfer) {
        this.transfer = transfer;
    }

    public TransferRequest getTransfer() {
        return transfer;
    }
}
