package com.mygitgor.application.service.impl;

import com.mygitgor.domain.model.Stock;
import com.mygitgor.domain.model.StoreStock;
import com.mygitgor.domain.model.Store;
import com.mygitgor.domain.model.Product;
import com.mygitgor.domain.repository.ProductRepository;
import com.mygitgor.domain.repository.StockRepository;
import com.mygitgor.domain.repository.StoreRepository;
import com.mygitgor.domain.repository.StoreStockRepository;
import com.mygitgor.application.service.StoreStockService;
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

        Optional<Product> product = productRepository.findByCode(productCode);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Optional<Stock> stock = stockRepository.findByProduct(product.orElse(null));
        if (stock.isEmpty() || stock.get().getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        stock.get().setQuantity(stock.get().getQuantity() - quantity);
        stockRepository.save(stock.orElse(null));

        StoreStock storeStock = storeStockRepository.findByStoreAndStock(store, stock.orElse(null));
        if (storeStock == null) {
            storeStock = new StoreStock();
            storeStock.setStore(store);
            storeStock.setStock(stock.get());
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

        Optional<Product> product = productRepository.findByCode(productCode);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Optional<Stock> stock = stockRepository.findByProduct(product.orElse(null));
        if (stock.isEmpty()) {
            throw new IllegalArgumentException("Stock not found");
        }

        StoreStock storeStock = storeStockRepository.findByStoreAndStock(store, stock.orElse(null));
        if (storeStock == null || storeStock.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient store stock");
        }

        storeStock.setQuantity(storeStock.getQuantity() - quantity);
        storeStockRepository.save(storeStock);

        stock.get().setQuantity(stock.get().getQuantity() + quantity);
        stockRepository.save(stock.orElse(null));
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
        Optional<Product> product = productRepository.findByCode(productCode);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }
        Optional<Stock> stock = stockRepository.findByProduct(product.orElse(null));
        if (stock.isEmpty()) {
            throw new IllegalArgumentException("Stock not found");
        }
        return storeStockRepository.findByStock(stock.orElse(null));
    }
}

