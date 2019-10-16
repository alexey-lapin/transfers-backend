package com.github.al.transfers.service;

import com.github.al.transfers.model.Transfer;
import com.github.al.transfers.model.TransferRequest;
import com.github.al.transfers.repository.TransferRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Optional;

@Singleton
public class SimpleTransferService {

    private TransferRepository transferRepository;

    @Inject
    public SimpleTransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public Transfer create(TransferRequest transferRequest) {
        return transferRepository.create(transferRequest);
    }

    public Collection<Transfer> findAll() {
        return transferRepository.findAll();
    }

    public Optional<Transfer> findById(long id) {
        return transferRepository.findById(id);
    }

    public Collection<Transfer> findByAccountId(long id) {
        return transferRepository.findByAccountId(id);
    }
}
