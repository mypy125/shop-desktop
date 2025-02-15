package com.mygitgor.GUI;

import javax.swing.*;
import java.awt.*;

public class StockPanel extends JPanel {
    private JTable stockTable;
    private JButton addButton, removeButton;

    public StockPanel() {
        setLayout(new BorderLayout());

        stockTable = new JTable();
        add(new JScrollPane(stockTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Stock");
        removeButton = new JButton("Remove Stock");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStock());
        removeButton.addActionListener(e -> removeStock());
    }

    private void addStock() {

    }

    private void removeStock() {

    }
}