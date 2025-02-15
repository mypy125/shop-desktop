package com.mygitgor.service;

import com.mygitgor.model.Store;
import com.mygitgor.repository.StoreRepository;

public class StoreService {
    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void addStore(Store store) {
        storeRepository.addStore(store);
    }
}
