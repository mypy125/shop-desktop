package com.mygitgor.repository;

import com.mygitgor.model.Store;

import java.util.List;

public interface StoreRepository {
    void addStore(Store store);
    void updateStore(Store store);
    void removeStore(String storeName);
    Store findStoreByName(String storeName);
    List<Store> findAllStores();
}
