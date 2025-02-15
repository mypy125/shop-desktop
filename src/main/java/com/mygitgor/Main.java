package com.mygitgor;

import com.mygitgor.GUI.Dashboard;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.StockRepository;
import com.mygitgor.repository.inMemory.InMemoryProductRepository;
import com.mygitgor.repository.inMemory.InMemoryStockRepository;
import com.mygitgor.repository.inMemory.InMemoryStoreRepository;
import com.mygitgor.service.ProductService;
import com.mygitgor.service.StockService;
import com.mygitgor.service.StoreService;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductRepository productRepository = new InMemoryProductRepository();
            StockRepository stockRepository = new InMemoryStockRepository();
            ProductService productService = new ProductService(productRepository);
            StockService stockService = new StockService(stockRepository, productRepository);
            StoreService storeService = new StoreService(new InMemoryStoreRepository());

            Dashboard dashboard = new Dashboard(productService, stockService, storeService);
            dashboard.setVisible(true);
        });
    }
}