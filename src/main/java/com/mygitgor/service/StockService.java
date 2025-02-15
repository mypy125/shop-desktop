package com.mygitgor.service;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.StockRepository;

import java.util.List;

public class StockService {
    private StockRepository stockRepository;
    private ProductRepository productRepository;

    public StockService(StockRepository stockRepository,
                        ProductRepository productRepository)
    {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    public void addStock(String productCode, int quantity)
    {
        Product product = productRepository.findById(productCode);
        if (product != null) {
            stockRepository.addStock(productCode, quantity);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
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

