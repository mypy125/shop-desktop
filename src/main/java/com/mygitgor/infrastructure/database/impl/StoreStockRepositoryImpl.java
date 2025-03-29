package com.mygitgor.infrastructure.database.impl;

import com.mygitgor.domain.model.Stock;
import com.mygitgor.domain.model.Store;
import com.mygitgor.domain.model.StoreStock;
import com.mygitgor.domain.repository.StoreStockRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoreStockRepositoryImpl implements StoreStockRepository {
    @Override
    public List<StoreStock> findByStore(Store store) {
        return List.of();
    }

    @Override
    public List<StoreStock> findByStock(Stock stock) {
        return List.of();
    }

    @Override
    public StoreStock findByStoreAndStock(Store store, Stock stock) {
        return null;
    }
}
