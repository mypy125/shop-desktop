package com.mygitgor.domain.repository;

import com.mygitgor.domain.model.Store;

public interface StoreRepository {
    Store findByName(String name);
}
