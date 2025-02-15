package com.mygitgor.GUI;

import com.mygitgor.service.ProductService;

import javax.swing.*;

public class Dashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private ProductService productService;

    public Dashboard(ProductService productService) {
        this.productService = productService;
        setTitle("Shop Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Products", new ProductPanel(productService));
        tabbedPane.addTab("Stock", new StockPanel());
        tabbedPane.addTab("Stores", new StorePanel());

        add(tabbedPane);
    }
}