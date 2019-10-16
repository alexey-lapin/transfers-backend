package com.github.al.transfers.repository;

import com.github.al.transfers.model.Transfer;
import com.github.al.transfers.model.TransferRequest;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Singleton
public class MapBasedTransferRepository implements TransferRepository {

    private final Map<Long, Transfer> data = new ConcurrentHashMap<>();

    private AtomicLong nextId;

    public MapBasedTransferRepository() {
        this.nextId = new AtomicLong(0);
    }

    @Override
    public Transfer create(TransferRequest transferRequest) {
        Transfer transfer = new Transfer(nextId.incrementAndGet(),
                transferRequest.getFromAccountId(),
                transferRequest.getToAccountId(),
                transferRequest.getAmount());
        data.put(transfer.getId(), transfer);
        return transfer;
    }

    @Override
    public Collection<Transfer> findAll() {
        return data.values();
    }

    @Override
    public Optional<Transfer> findById(long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Collection<Transfer> findByAccountId(long id) {
        return data.values()
                .stream()
                .filter(t -> t.getFromAccountId() == id || t.getToAccountId() == id)
                .collect(Collectors.toList());
    }
}
