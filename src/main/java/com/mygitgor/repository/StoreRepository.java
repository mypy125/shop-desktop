package com.mygitgor.repository;

import com.mygitgor.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Store findByName(String name);

    @Query("SELECT s FROM Store s JOIN FETCH s.stocks WHERE s.name = :storeName")
    Store findByNameWithStocks(@Param("storeName") String storeName);

}
