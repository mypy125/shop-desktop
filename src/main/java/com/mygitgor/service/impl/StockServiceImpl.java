package com.mygitgor.service.impl;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import com.mygitgor.model.Store;
import com.mygitgor.model.StoreStock;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.StockRepository;
import com.mygitgor.repository.StoreRepository;
import com.mygitgor.repository.StoreStockRepository;
import com.mygitgor.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final StoreStockRepository storeStockRepository;
    private final StoreRepository storeRepository;

    @Transactional
    @Override
    public void addStock(String productCode, int quantity) {
        Product product = productRepository.findByCode(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        Stock stock = stockRepository.findByProduct(product);
        if (stock == null) {
            stock = new Stock();
            stock.setProduct(product);
            stock.setQuantity(quantity);
        } else {
            stock.setQuantity(stock.getQuantity() + quantity);
        }

        stockRepository.save(stock);
    }

    @Transactional
    @Override
    public void removeStock(String productCode) {
        Product product = productRepository.findByCode(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        Stock stock = stockRepository.findByProduct(product);
        if (stock != null) {
            stockRepository.delete(stock);
        } else {
            throw new IllegalArgumentException("Stock not found");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Stock> findAllStocks() {
        return stockRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<StoreStock> findAllStoreStocks() {
        return storeStockRepository.findAll();
    }

    @Transactional
    @Override
    public void moveStockToStore(String storeName, String productCode, int quantity) {
        Store store = storeRepository.findByName(storeName);
        if (store == null) {
            throw new IllegalArgumentException("Store not found");
        }

        Product product = productRepository.findByCode(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        Stock stock = stockRepository.findByProduct(product);
        if (stock == null || stock.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);

        StoreStock storeStock = storeStockRepository.findByStoreAndStock(store, stock);
        if (storeStock == null) {
            storeStock = new StoreStock();
            storeStock.setStore(store);
            storeStock.setStock(stock);
            storeStock.setQuantity(quantity);
        } else {
            storeStock.setQuantity(storeStock.getQuantity() + quantity);
        }

        storeStockRepository.save(storeStock);
    }
}
