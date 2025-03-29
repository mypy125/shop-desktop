package com.mygitgor.domain.repository;

import com.mygitgor.domain.model.Stock;
import com.mygitgor.domain.model.Store;
import com.mygitgor.domain.model.StoreStock;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StoreStockRepository {
    List<StoreStock> findByStore(Store store);
    List<StoreStock> findByStock(Stock stock);
    StoreStock findByStoreAndStock(Store store, Stock stock);
}
