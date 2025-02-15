package com.mygitgor.service;

import com.mygitgor.model.Store;
import com.mygitgor.repository.StoreRepository;

import java.util.List;

public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void addStore(Store store) {
        storeRepository.addStore(store);
    }

    public void updateStore(Store store) {
        storeRepository.updateStore(store);
    }

    public void removeStore(String storeName) {
        storeRepository.removeStore(storeName);
    }

    public Store findStoreByName(String storeName) {
        return storeRepository.findStoreByName(storeName);
    }

    public List<Store> findAllStores() {
        return storeRepository.findAllStores();
    }
}
