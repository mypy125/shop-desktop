package com.mygitgor.repository.inMemory;

import com.mygitgor.model.Stock;
import com.mygitgor.repository.StockRepository;

import java.util.*;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStockRepository implements StockRepository {
    private final Map<String, Stock> stocks = new ConcurrentHashMap<>();

    @Override
    public void addStock(Stock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("Stock cannot be null.");
        }
        if (stock.getProductCode() == null || stock.getProductCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Product code cannot be null or empty.");
        }
        if (stock.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive number.");
        }

        stocks.put(stock.getProductCode(), stock);
    }

    @Override
    public void updateStock(Stock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("Stock cannot be null.");
        }
        if (stock.getProductCode() == null || stock.getProductCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Product code cannot be null or empty.");
        }
        if (stock.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive number.");
        }

        stocks.put(stock.getProductCode(), stock);
    }

    @Override
    public void removeStock(String productCode) {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Product code cannot be null or empty.");
        }
        stocks.remove(productCode);
    }

    @Override
    public Stock findStockByProductCode(String productCode) {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Product code cannot be null or empty.");
        }
        return stocks.get(productCode);
    }

    @Override
    public List<Stock> findAllStocks() {
        return new ArrayList<>(stocks.values());
    }
}