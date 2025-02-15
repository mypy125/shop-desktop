package com.mygitgor.service.impl;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import com.mygitgor.model.Store;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.StockRepository;
import com.mygitgor.repository.StoreRepository;
import com.mygitgor.service.StockService;

import java.util.Date;
import java.util.List;

public class StockServiceImpl implements StockService {
    private StockRepository stockRepository;
    private ProductRepository productRepository;

    public StockServiceImpl(StockRepository stockRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addStock(String productCode, int quantity) {
        Product product = productRepository.findById(productCode);
        if (product == null) {
            product = new Product();
            product.setCode(productCode);
            product.setPrice(0.0);
            product.setProductionDate(new Date());
            product.setExpirationDate(new Date());
            productRepository.save(product);
        }

        Stock stock = stockRepository.findStockByProductCode(productCode);
        if (stock == null) {
            stock = new Stock();
            stock.setProductCode(productCode);
            stock.setProduct(product);
            stock.setQuantity(quantity);
            stockRepository.addStock(stock);
        } else {
            // If the stock exists, update the quantity
            stock.setQuantity(stock.getQuantity() + quantity);
            stockRepository.updateStock(stock);
        }
    }

    @Override
    public void updateStock(Stock stock) {
        stockRepository.updateStock(stock);
    }

    @Override
    public void removeStock(String productCode) {
        stockRepository.removeStock(productCode);
    }

    @Override
    public Stock findStockByProductCode(String productCode) {
        return stockRepository.findStockByProductCode(productCode);
    }

    @Override
    public List<Stock> findAllStocks() {
        return stockRepository.findAllStocks();
    }

    @Override
    public void moveStockToStore(String storeName, String productCode, int quantity) {
        Stock stock = stockRepository.findStockByProductCode(productCode);
        if (stock != null && stock.getQuantity() >= quantity) {
            stock.setQuantity(stock.getQuantity() - quantity);
            stockRepository.updateStock(stock);
        } else {
            throw new IllegalArgumentException("Not enough stock or stock not found");
        }
    }
}
