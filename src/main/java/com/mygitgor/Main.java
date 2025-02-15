package com.mygitgor;

import com.mygitgor.GUI.Dashboard;
import com.mygitgor.GUI.ProductPanel;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.inMemory.InMemoryProductRepository;
import com.mygitgor.service.ProductService;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductRepository productRepository = new InMemoryProductRepository();
            ProductService productService = new ProductService(productRepository);

            JFrame frame = new JFrame("Shop Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

//            Dashboard dashboard = new Dashboard(productService);
//            dashboard.setVisible(true);

            ProductPanel productPanel = new ProductPanel(productService);
            frame.add(productPanel);

            frame.setVisible(true);
        });
    }
}