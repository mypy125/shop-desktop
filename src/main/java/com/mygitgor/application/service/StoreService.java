package com.mygitgor.application.service;

import com.mygitgor.domain.model.Store;

import java.util.List;
import java.util.UUID;

public interface StoreService {
    void addStore(Store store);
    Store findByName(String name);
    Store findById(UUID id) throws Exception;
    void updateStore(Store store);
    List<Store> findAll();
    void deleteStore(String name);
    void deleteStore(UUID id) throws Exception;
    void returnProductToStock(String storeName, String productName, int quantity);
}
