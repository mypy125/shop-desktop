package com.mygitgor.service;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;

import java.util.List;

public interface StockService {
    void addStock(String productCode, int quantity);
    void updateStock(Stock stock);
    void removeStock(String productCode);
    void moveStockToStore(String storeName, String productCode, int quantity);
    Stock findStockByProductCode(String productCode);
    List<Stock> findAllStocks();
}

