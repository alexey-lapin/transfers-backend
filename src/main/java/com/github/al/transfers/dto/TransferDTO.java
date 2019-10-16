package com.github.al.transfers.dto;

import com.github.al.transfers.model.Transfer;

public class TransferDTO {

    private Transfer transfer;

    TransferDTO() {
    }

    public TransferDTO(Transfer transfer) {
        this.transfer = transfer;
    }

    public Transfer getTransfer() {
        return transfer;
    }
}
