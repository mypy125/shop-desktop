package com.mygitgor.service;

import com.mygitgor.model.Store;

import java.util.List;

public interface StoreService {
    void addStore(Store store);
    Store findByName(String name);
    Store findById(Long id) throws Exception;
    void updateStore(Store store);
    List<Store> findAll();
    void deleteStore(String name);
    void deleteStore(Long id) throws Exception;

}
