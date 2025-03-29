package com.mygitgor.domain.repository;

import com.mygitgor.domain.model.Store;
import org.springframework.stereotype.Repository;

public interface StoreRepository {
    Store findByName(String name);
}
