package com.mygitgor.service;


import com.mygitgor.model.Store;
import com.mygitgor.model.StoreStock;

import java.util.List;

public interface StoreStockService {
    void addStoreStock(String storeName, String productCode, int quantity);
    void removeStoreStock(String storeName, String productCode, int quantity);
    List<StoreStock> findAllStoreStocks();
    List<StoreStock> findStoreStocksByStore(String storeName);
    List<StoreStock> findStoreStocksByProduct(String productCode);
}

