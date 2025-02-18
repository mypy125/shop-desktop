package com.mygitgor.repository;

import com.mygitgor.model.Stock;
import com.mygitgor.model.Store;
import com.mygitgor.model.StoreStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreStockRepository extends JpaRepository<StoreStock, Long> {
    List<StoreStock> findByStore(Store store);
    List<StoreStock> findByStock(Stock stock);
    StoreStock findByStoreAndStock(Store store, Stock stock);
}
