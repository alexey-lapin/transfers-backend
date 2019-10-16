package com.github.al.transfers;

import com.github.al.transfers.repository.AccountRepository;
import com.github.al.transfers.repository.MapBasedAccountRepository;
import com.github.al.transfers.repository.MapBasedTransferRepository;
import com.github.al.transfers.repository.TransferRepository;
import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountRepository.class).to(MapBasedAccountRepository.class);
        bind(TransferRepository.class).to(MapBasedTransferRepository.class);
    }
}
