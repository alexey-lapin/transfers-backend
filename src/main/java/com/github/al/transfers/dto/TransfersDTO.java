package com.github.al.transfers.dto;

import com.github.al.transfers.model.Transfer;

import java.util.Collection;

public class TransfersDTO {

    private Collection<Transfer> transfers;

    TransfersDTO() {
    }

    public TransfersDTO(Collection<Transfer> transfers) {
        this.transfers = transfers;
    }

    public Collection<Transfer> getTransfers() {
        return transfers;
    }
}
