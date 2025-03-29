package com.mygitgor.domain.repository;

import com.mygitgor.domain.model.Store;

import java.util.List;
import java.util.UUID;

public interface StoreRepository {
    Store findByName(String name);
    void save(Store store);
    Store findById(UUID id);
    boolean existsById(UUID id);
    List<Store> findAll();
    void delete(Store store);
}
