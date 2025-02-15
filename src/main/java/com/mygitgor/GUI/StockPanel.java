package com.mygitgor.GUI;

import com.mygitgor.model.Stock;
import com.mygitgor.service.StockService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StockPanel extends JPanel {
    private JTable stockTable;
    private JButton addButton, removeButton;
    private StockService stockService;

    public StockPanel(StockService stockService) {
        this.stockService = stockService;
        initUI();
    }

    private void initUI(){
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

        refreshStockTable();
    }


    private void addStock() {
        String productCode = JOptionPane.showInputDialog(this, "Enter Product Code:");
        if (productCode == null) return;

        int quantity;
        try {
            quantity = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Quantity:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        stockService.addStock(productCode, quantity);
        refreshStockTable();
    }

    private void removeStock() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a stock item to remove", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String productCode = (String) stockTable.getValueAt(selectedRow, 0);
        stockService.removeStock(productCode);
        refreshStockTable();
    }

    private void refreshStockTable() {
        List<Stock> stocks = stockService.findAllStocks();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product Code");
        model.addColumn("Quantity");

        for (Stock stock : stocks) {
            model.addRow(new Object[]{stock.getProductCode(), stock.getQuantity()});
        }
        stockTable.setModel(model);
    }
}
