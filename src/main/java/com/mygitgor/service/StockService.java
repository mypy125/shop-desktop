package com.mygitgor.service;

import com.mygitgor.domain.model.Stock;
import com.mygitgor.domain.model.StoreStock;

import java.util.List;

public interface StockService {
    void addStock(String productCode, int quantity);
    void removeStock(String productCode);
    List<Stock> findAllStocks();
    List<StoreStock> findAllStoreStocks();
    void moveStockToStore(String storeName, String productCode, int quantity);
}

