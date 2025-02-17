package com.mygitgor.repository;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import com.mygitgor.model.Store;
import com.mygitgor.model.StoreStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreStockRepository extends JpaRepository<StoreStock, Long> {
    Optional<StoreStock> findByStoreAndStock(Store store, Stock stock);
    Optional<StoreStock> findByStoreAndStock_Product(Store store, Product product);
    Optional<StoreStock> findByStoreAndStock_ProductCode(Store store, String productCode);
}
