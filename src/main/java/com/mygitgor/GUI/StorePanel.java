package com.mygitgor.GUI;

import com.mygitgor.model.Store;
import com.mygitgor.model.StoreStock;
import com.mygitgor.service.StockService;
import com.mygitgor.service.StoreService;
import com.mygitgor.service.StoreStockService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


@Component
@AllArgsConstructor
@NoArgsConstructor
public class StorePanel extends JPanel {
    private StoreService storeService;
    private StoreStockService storeStockService;
    private JTable storeTable;
    private JTable storeStockTable;
    private DefaultTableModel storeTableModel;
    private DefaultTableModel storeStockTableModel;
    private JTextField productCodeField;
    private JTextField quantityField;

    public StorePanel(StoreService storeService, StoreStockService storeStockService) {
        this.storeService = storeService;
        this.storeStockService = storeStockService;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        productCodeField = new JTextField();
        quantityField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Product Code:"));
        inputPanel.add(productCodeField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);

        add(inputPanel, BorderLayout.NORTH);

        add(createTablesPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        refreshStoreTable();
        refreshStoreStockTable();
    }


    private JPanel createTablesPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));

        storeTableModel = new DefaultTableModel(new String[]{"Store Name"}, 0);
        storeTable = new JTable(storeTableModel);
        panel.add(new JScrollPane(storeTable));

        storeStockTableModel = new DefaultTableModel(new String[]{"Product Code", "Quantity"}, 0);
        storeStockTable = new JTable(storeStockTableModel);
        panel.add(new JScrollPane(storeStockTable));

        return panel;
    }


    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4));

        String[] buttonLabels = {
                "Add Store", "Edit Store", "Delete Store", "Sell Product",
                "Return Product", "Check Expired Products", "Add Product to Store", "View Store Products"
        };

        Runnable[] actions = {
                this::addStore, this::editStore, this::deleteStore, this::sellProduct,
                this::returnProduct, this::checkExpiredProducts, this::addProductToStore, this::viewStoreProducts
        };

        IntStream.range(0, buttonLabels.length).forEach(i -> {
            panel.add(createButton(buttonLabels[i], e -> actions[i].run()));
        });

        return panel;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void addStore() {
        JTextField nameField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Store Name:"));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Add Store", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    showError("Store name cannot be empty!");
                    return;
                }

                Store store = new Store();
                store.setName(name);
                store.setStoreStocks(new ArrayList<>());

                storeService.addStore(store);
                refreshStoreTable();
            } catch (Exception e) {
                showError("Error adding store: " + e.getMessage());
            }
        }
    }

    private void editStore() {
        int selectedRow = storeTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarning("Select a store to edit");
            return;
        }

        String oldName = (String) storeTable.getValueAt(selectedRow, 0);
        Store store = storeService.findByName(oldName);

        if (store == null) {
            showError("Store not found");
            return;
        }

        JTextField nameField = new JTextField(store.getName());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Store Name:"));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Edit Store", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String newName = nameField.getText().trim();
                if (newName.isEmpty()) {
                    showError("Store name cannot be empty!");
                    return;
                }

                store.setName(newName);
                storeService.updateStore(store);
                refreshStoreTable();
            } catch (Exception e) {
                showError("Error editing store: " + e.getMessage());
            }
        }
    }

    private void deleteStore() {
        int selectedRow = storeTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarning("Select a store to remove");
            return;
        }

        String storeName = (String) storeTable.getValueAt(selectedRow, 0);
        storeService.deleteStore(storeName);
        refreshStoreTable();
    }

    private void refreshStoreTable() {
        storeTableModel.setRowCount(0);
        storeService.findAll().forEach(store -> storeTableModel.addRow(new Object[]{ store.getName()}));
    }

    //=====================================end_store_function========================================

    private void sellProduct() {
        processStockChange(storeStockService::removeStoreStock, "sell product from");
        refreshStoreStockTable();
    }

    private void returnProduct() {
        getSelectedStoreName().ifPresent(storeName -> {
            try {
                if (productCodeField == null || quantityField == null) {
                    showError("Product code and quantity fields are not initialized.");
                    return;
                }

                String productCode = productCodeField.getText().trim();
                if (productCode.isEmpty()) {
                    showError("Product code cannot be empty");
                    return;
                }

                int quantity = Integer.parseInt(quantityField.getText().trim());
                if (quantity <= 0) {
                    showError("Quantity must be greater than 0");
                    return;
                }

                storeService.returnProductToStock(storeName, productCode, quantity);
                refreshStoreStockTable();

                // Обновление панели StockPanel после возврата товара
                Dashboard dashboard = (Dashboard) SwingUtilities.getWindowAncestor(this); // Получаем ссылку на Dashboard
                if (dashboard != null) {
                    dashboard.updateStockPanel(); // Обновляем StockPanel
                }

            } catch (NumberFormatException e) {
                showError("Invalid quantity");
            } catch (Exception e) {
                showError("Error returning product to stock: " + e.getMessage());
            }
        });
    }





    private void addProductToStore() {
        processStockChange(storeStockService::addStoreStock, "add product to");
    }

    private void checkExpiredProducts() {
        showInfo("Check expired products functionality not implemented yet");
    }

    private void viewStoreProducts() {
        getSelectedStoreName().ifPresent(storeName -> {
            List<StoreStock> storeStocks = storeStockService.findStoreStocksByStore(storeName);

            storeStockTableModel.setRowCount(0);

            storeStocks.forEach(storeStock -> {
                String productCode = storeStock.getStock().getProduct().getCode();
                int quantity = storeStock.getQuantity();
                storeStockTableModel.addRow(new Object[]{productCode, quantity});
            });
            refreshStoreStockTable();
        });
    }

    private void refreshStoreStockTable() {
        storeStockTableModel.setRowCount(0);

        getSelectedStoreName().ifPresentOrElse(storeName -> {
            List<StoreStock> storeStocks = storeStockService.findStoreStocksByStore(storeName);

            if (storeStocks.isEmpty()) {
                showInfo("No products available for this store.");
            } else {
                storeStocks.forEach(storeStock -> {
                    String productCode = storeStock.getStock().getProduct().getCode();
                    int quantity = storeStock.getQuantity();
                    storeStockTableModel.addRow(new Object[]{productCode, quantity});
                });
            }
        }, () -> showWarning("Please select a store first."));
    }

    private void processStockChange(StockAction action, String message) {
        getSelectedStoreName().ifPresent(storeName -> {
            try {
                String productCode = productCodeField.getText().trim();
                int quantity = Integer.parseInt(quantityField.getText().trim());
                action.apply(storeName, productCode, quantity);
                refreshStoreStockTable();
            } catch (NumberFormatException e) {
                showError("Invalid quantity");
            }
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private Optional<String> getSelectedStoreName() {
        int selectedRow = storeTable.getSelectedRow();
        return selectedRow >= 0 ? Optional.of((String) storeTableModel.getValueAt(selectedRow, 0)) : Optional.empty();
    }

    @FunctionalInterface
    private interface StockAction {
        void apply(String storeName, String productCode, int quantity);
    }
}
