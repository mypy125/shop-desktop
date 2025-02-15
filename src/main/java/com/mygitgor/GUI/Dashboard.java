package com.mygitgor.GUI;

import com.mygitgor.service.ProductService;
import com.mygitgor.service.StockService;
import com.mygitgor.service.StoreService;

import javax.swing.*;

public class Dashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private ProductService productService;
    private StockService stockService;
    private StoreService storeService;

    public Dashboard(ProductService productService,
                     StockService stockService,
                     StoreService storeService
    ) {
        this.productService = productService;
        this.stockService= stockService;
        this.storeService=storeService;

        setTitle("Shop Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Products", new ProductPanel(productService));
        tabbedPane.addTab("Stock", new StockPanel(stockService));
        tabbedPane.addTab("Stores", new StorePanel(storeService));

        add(tabbedPane);
    }
}