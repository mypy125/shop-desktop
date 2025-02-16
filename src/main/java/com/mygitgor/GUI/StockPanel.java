package com.mygitgor.GUI;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import com.mygitgor.service.StockService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class StockPanel extends JPanel {
    private JTable stockTable;
    private JButton addButton, removeButton, moveToStoreButton;
    private StockService stockService;

    public StockPanel(StockService stockService) {
        this.stockService = stockService;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        stockTable = new JTable();
        add(new JScrollPane(stockTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Stock");
        removeButton = new JButton("Remove Stock");
        moveToStoreButton = new JButton("Move Stock to Store");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(moveToStoreButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStock());
        removeButton.addActionListener(e -> removeStock());
        moveToStoreButton.addActionListener(e -> moveStockToStore());

        refreshStockTable();
    }

    private void addStock() {
        JTextField productCodeField = new JTextField();
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Product Code:"));
        panel.add(productCodeField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Add Stock", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String productCode = productCodeField.getText().trim();
                int quantity = Integer.parseInt(quantityField.getText().trim());

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                stockService.addStock(productCode, quantity);
                refreshStockTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding stock: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeStock() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a stock to remove", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String productCode = (String) stockTable.getValueAt(selectedRow, 0);
        stockService.removeStock(productCode);
        refreshStockTable();
    }

    private void moveStockToStore() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a stock to move", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String productCode = (String) stockTable.getValueAt(selectedRow, 0);
        JTextField storeNameField = new JTextField();
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Store Name:"));
        panel.add(storeNameField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Move Stock to Store", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String storeName = storeNameField.getText().trim();
                int quantity = Integer.parseInt(quantityField.getText().trim());

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                stockService.moveStockToStore(storeName, productCode, quantity);
                refreshStockTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error moving stock: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshStockTable() {
        List<Stock> stocks = stockService.findAllStocks();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product Code");
        model.addColumn("Product Name");
        model.addColumn("Quantity");

        for (Stock stock : stocks) {
            Product product = stock.getProduct();
            if (product != null) {
                model.addRow(new Object[]{
                        stock.getProductCode(),
                        product.getCode(),
                        stock.getQuantity()
                });
            } else {
                model.addRow(new Object[]{
                        stock.getProductCode(),
                        "N/A",
                        stock.getQuantity()
                });
            }
        }
        stockTable.setModel(model);
    }
}