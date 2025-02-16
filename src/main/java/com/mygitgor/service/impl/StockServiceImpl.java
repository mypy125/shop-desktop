package com.mygitgor.service.impl;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.StockRepository;
import com.mygitgor.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    @Override
    public void addStock(String productCode, int quantity) {
        Product product = productRepository.findByCode(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Product with code " + productCode + " not found.");
        }

        Stock stock = stockRepository.findByProductCode(productCode);
        if (stock == null) {
            stock = new Stock();
            stock.setProductCode(productCode);
            stock.setProduct(product);
            stock.setQuantity(quantity);
        } else {
            stock.setQuantity(stock.getQuantity() + quantity);
        }
        stockRepository.save(stock);
    }

    @Override
    public void updateStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public void removeStock(String productCode) {
        stockRepository.deleteByProductCode(productCode);
    }

    @Override
    public Stock findStockByProductCode(String productCode) {
        return stockRepository.findByProductCode(productCode);
    }

    @Override
    public List<Stock> findAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public void moveStockToStore(String storeName, String productCode, int quantity) {
        Stock stock = stockRepository.findByProductCode(productCode);
        if (stock != null && stock.getQuantity() >= quantity) {
            stock.setQuantity(stock.getQuantity() - quantity);
            stockRepository.save(stock);
        } else {
            throw new IllegalArgumentException("Not enough stock or stock not found.");
        }
    }
}
