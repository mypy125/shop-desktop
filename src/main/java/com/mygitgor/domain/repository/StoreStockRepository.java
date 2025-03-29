package com.mygitgor.domain.repository;

import com.mygitgor.domain.model.Stock;
import com.mygitgor.domain.model.Store;
import com.mygitgor.domain.model.StoreStock;

import java.util.List;

public interface StoreStockRepository {
    List<StoreStock> findByStore(Store store);
    List<StoreStock> findByStock(Stock stock);
    StoreStock findByStoreAndStock(Store store, Stock stock);
    List<StoreStock> findAll();
    void save(StoreStock storeStock);
}
