package com.github.al.transfers.repository;

import com.github.al.transfers.model.Transfer;
import com.github.al.transfers.model.TransferRequest;

import java.util.Collection;
import java.util.Optional;

public interface TransferRepository {

    Transfer create(TransferRequest transfer);

    Collection<Transfer> findAll();

    Optional<Transfer> findById(long id);

    Collection<Transfer> findByAccountId(long id);
}
