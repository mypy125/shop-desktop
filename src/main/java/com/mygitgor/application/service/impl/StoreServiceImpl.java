package com.mygitgor.application.service.impl;

import com.mygitgor.domain.model.Product;
import com.mygitgor.domain.model.Stock;
import com.mygitgor.domain.model.Store;
import com.mygitgor.domain.model.StoreStock;
import com.mygitgor.domain.repository.ProductRepository;
import com.mygitgor.domain.repository.StockRepository;
import com.mygitgor.domain.repository.StoreRepository;
import com.mygitgor.domain.repository.StoreStockRepository;
import com.mygitgor.application.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final StoreStockRepository storeStockRepository;
    private final StockRepository stockRepository;

    @Transactional
    @Override
    public void addStore(Store store) {
        storeRepository.save(store);
    }

    @Transactional(readOnly = true)
    @Override
    public Store findByName(String name) {
        return storeRepository.findByName(name);
    }

    @Override
    public Store findById(UUID id) {
        return storeRepository.findById(id);
    }

    @Transactional
    @Override
    public void updateStore(Store store) {
        if (storeRepository.existsById(store.getId())) {
            storeRepository.save(store);
        } else {
            throw new IllegalArgumentException("Store not found");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteStore(String name) {
        Store store = storeRepository.findByName(name);
        if (store != null) {
            storeRepository.delete(store);
        } else {
            throw new IllegalArgumentException("Store not found");
        }
    }

    @Override
    public void deleteStore(UUID id) throws Exception {
        Store store = findById(id);
        storeRepository.delete(store);
    }

    @Transactional
    @Override
    public void returnProductToStock(String storeName, String productCode, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Store store = storeRepository.findByName(storeName);
        if (store == null) {
            throw new IllegalArgumentException("Store not found with name: " + storeName);
        }

        Optional<Product> product = productRepository.findByCode(productCode);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found with code: " + productCode);
        }

        Optional<Stock> stockByProduct = stockRepository.findByProduct(product.orElse(null));
        if (stockByProduct.isEmpty()) {
            stockByProduct = Optional.of(new Stock());
            stockByProduct.get().setProduct(product.get());
            stockByProduct.get().setQuantity(0);
            stockRepository.save(stockByProduct.orElse(null));
        }

        StoreStock storeStock = storeStockRepository.findByStoreAndStock(store, stockByProduct.orElse(null));

        if (storeStock == null) {
            storeStock = new StoreStock();
            storeStock.setStore(store);
            storeStock.setStock(stockByProduct.get());
            storeStock.setQuantity(0);
        }

        stockByProduct.get().setQuantity(stockByProduct.get().getQuantity() + quantity);
        stockRepository.save(stockByProduct.orElse(null));

        storeStock.setQuantity(storeStock.getQuantity() - quantity);
        storeStockRepository.save(storeStock);
    }


}
