package com.mygitgor.service.impl;

import com.mygitgor.model.Stock;
import com.mygitgor.model.StoreStock;
import com.mygitgor.model.Store;
import com.mygitgor.model.Product;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.StockRepository;
import com.mygitgor.repository.StoreStockRepository;
import com.mygitgor.service.StoreStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreStockServiceImpl implements StoreStockService {
    private final StoreStockRepository storeStockRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    @Override
    public StoreStock addStockToStore(Store store, String productCode, int quantity) {
        Product product = productRepository.findByCode(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Product not found: " + productCode);
        }

        Stock stock = stockRepository.findByProductCode(productCode);
        if (stock == null) {
            stock = new Stock();
            stock.setProduct(product);
            stock.setProductCode(productCode);
            stock.setQuantity(quantity);
            stockRepository.save(stock);
        }

        Optional<StoreStock> existingStock = storeStockRepository.findByStoreAndStock_Product(store, product);
        if (existingStock.isPresent()) {
            StoreStock storeStock = existingStock.get();
            storeStock.setQuantity(storeStock.getQuantity() + quantity);
            return storeStockRepository.save(storeStock);
        } else {
            StoreStock storeStock = new StoreStock();
            storeStock.setStore(store);
            storeStock.setStock(stock);
            storeStock.setQuantity(quantity);
            return storeStockRepository.save(storeStock);
        }
    }

    @Override
    public StoreStock findStoreStockByProductCode(Store store, String productCode) {
        return storeStockRepository.findByStoreAndStock_ProductCode(store, productCode)
                .orElseThrow(() -> new IllegalArgumentException("Product not found in store"));
    }

    @Override
    public void updateStockQuantity(StoreStock storeStock, int quantity) {
        storeStock.setQuantity(quantity);
        storeStockRepository.save(storeStock);
    }
}

