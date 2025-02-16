package com.mygitgor.repository;

import com.mygitgor.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Store findByName(String name);
}
