package com.mygitgor.GUI;

import com.mygitgor.service.ProductService;
import com.mygitgor.service.StockService;
import com.mygitgor.service.StoreService;
import com.mygitgor.service.StoreStockService;

import javax.swing.*;

public class Dashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private ProductService productService;
    private StockService stockService;
    private StoreService storeService;
    private StoreStockService storeStockService;
    private StockPanel stockPanel;
    private ConfigPanel configPanel;

    public Dashboard(ProductService productService,
                     StockService stockService,
                     StoreService storeService,
                     StoreStockService storeStockService) {
        this.productService = productService;
        this.stockService = stockService;
        this.storeService = storeService;
        this.storeStockService = storeStockService;

        setTitle("Shop Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        stockPanel = new StockPanel(stockService);
        configPanel = new ConfigPanel();
        configPanel.initialize();

        tabbedPane.addTab("Products", new ProductPanel(productService));
        tabbedPane.addTab("Stock", stockPanel);
        tabbedPane.addTab("Stores", new StorePanel(storeService, storeStockService));
        tabbedPane.addTab("Config", configPanel);

        add(tabbedPane);
    }

    public void updateStockPanel() {
        stockPanel.refreshStockTable();
    }
}


