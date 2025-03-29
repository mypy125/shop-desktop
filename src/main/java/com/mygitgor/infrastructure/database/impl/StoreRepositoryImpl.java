package com.mygitgor.infrastructure.database.impl;

import com.mygitgor.domain.model.Store;
import com.mygitgor.domain.repository.StoreRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StoreRepositoryImpl implements StoreRepository {
    @Override
    public Store findByName(String name) {
        return null;
    }
}
