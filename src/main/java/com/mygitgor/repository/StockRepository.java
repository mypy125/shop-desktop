package com.mygitgor.repository;

import com.mygitgor.model.Stock;

import java.util.List;

public interface StockRepository {
    void addStock(String productCode, int quantity);
    void updateStock(Stock stock);
    void removeStock(String productCode);
    Stock findStockByProductCode(String productCode);
    List<Stock> findAllStocks();
}
