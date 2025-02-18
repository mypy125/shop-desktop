package com.mygitgor.service.impl;

import com.mygitgor.model.Stock;
import com.mygitgor.model.StoreStock;
import com.mygitgor.model.Store;
import com.mygitgor.model.Product;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.StockRepository;
import com.mygitgor.repository.StoreRepository;
import com.mygitgor.repository.StoreStockRepository;
import com.mygitgor.service.StoreStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreStockServiceImpl implements StoreStockService {
    private final StoreStockRepository storeStockRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;


    @Transactional
    @Override
    public void addStoreStock(String storeName, String productCode, int quantity) {
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

    @Transactional
    @Override
    public void removeStoreStock(String storeName, String productCode, int quantity) {
        Store store = storeRepository.findByName(storeName);
        if (store == null) {
            throw new IllegalArgumentException("Store not found");
        }

        Product product = productRepository.findByCode(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        Stock stock = stockRepository.findByProduct(product);
        if (stock == null) {
            throw new IllegalArgumentException("Stock not found");
        }

        StoreStock storeStock = storeStockRepository.findByStoreAndStock(store, stock);
        if (storeStock == null || storeStock.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient store stock");
        }

        storeStock.setQuantity(storeStock.getQuantity() - quantity);
        storeStockRepository.save(storeStock);

        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StoreStock> findAllStoreStocks() {
        return storeStockRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<StoreStock> findStoreStocksByStore(String storeName) {
        Store store = storeRepository.findByName(storeName);
        if (store == null) {
            throw new IllegalArgumentException("Store not found");
        }
        return storeStockRepository.findByStore(store);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StoreStock> findStoreStocksByProduct(String productCode) {
        Product product = productRepository.findByCode(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        Stock stock = stockRepository.findByProduct(product);
        if (stock == null) {
            throw new IllegalArgumentException("Stock not found");
        }
        return storeStockRepository.findByStock(stock);
    }
}

