package com.mygitgor.service;

import com.mygitgor.model.Stock;
import com.mygitgor.repository.StockRepository;

import java.util.List;

public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void addStock(String productCode, int quantity) {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Product code cannot be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Stock stock = new Stock();
        stock.setProductCode(productCode);
        stock.setQuantity(quantity);

        stockRepository.addStock(stock);
    }

    public void updateStock(Stock stock) {
        stockRepository.updateStock(stock);
    }

    public void removeStock(String productCode) {
        stockRepository.removeStock(productCode);
    }

    public Stock findStockByProductCode(String productCode) {
        return stockRepository.findStockByProductCode(productCode);
    }

    public List<Stock> findAllStocks() {
        return stockRepository.findAllStocks();
    }
}

