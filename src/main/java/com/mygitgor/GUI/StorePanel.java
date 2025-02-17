package com.mygitgor.GUI;

import com.mygitgor.model.Store;
import com.mygitgor.service.StoreService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class StorePanel extends JPanel {
    private StoreService storeService;
    private JTable storeTable;
    private JButton addButton, editButton, deleteButton, sellButton, returnButton, checkExpiredButton, addProductButton, viewProductsButton;

    public StorePanel(StoreService storeService) {
        this.storeService = storeService;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        storeTable = new JTable();
        add(new JScrollPane(storeTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Store");
        editButton = new JButton("Edit Store");
        deleteButton = new JButton("Delete Store");
        sellButton = new JButton("Sell Product");
        returnButton = new JButton("Return Product");
        checkExpiredButton = new JButton("Check Expired Products");
        addProductButton = new JButton("Add Product");
        viewProductsButton = new JButton("View Products");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(checkExpiredButton);
        buttonPanel.add(addProductButton);
        buttonPanel.add(viewProductsButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStore());
        editButton.addActionListener(e -> editStore());
        deleteButton.addActionListener(e -> deleteStore());
        sellButton.addActionListener(e -> sellProduct());
        returnButton.addActionListener(e -> returnProduct());
        checkExpiredButton.addActionListener(e -> checkExpiredProducts());
        addProductButton.addActionListener(e -> addProductToStore());
        viewProductsButton.addActionListener(e -> viewStoreProducts());

        refreshStoreTable();
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
                    JOptionPane.showMessageDialog(this, "Store name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Store store = new Store();
                store.setName(name);
                store.setStoreStocks(new ArrayList<>());

                storeService.addStore(store);
                refreshStoreTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding store: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void editStore() {
        int selectedRow = storeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a store to edit", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String oldName = (String) storeTable.getValueAt(selectedRow, 0);
        Store store = storeService.findStoreByName(oldName);

        if (store == null) {
            JOptionPane.showMessageDialog(this, "Store not found", "Error", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(this, "Store name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                store.setName(newName);
                storeService.updateStore(store);
                refreshStoreTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error editing store: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteStore() {
        int selectedRow = storeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a store to remove", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String storeName = (String) storeTable.getValueAt(selectedRow, 0);
        storeService.removeStore(storeName);
        refreshStoreTable();
    }

    private void sellProduct() {
        int selectedRow = storeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a store to sell product", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String storeName = (String) storeTable.getValueAt(selectedRow, 0);
        JTextField productCodeField = new JTextField();
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Product Code:"));
        panel.add(productCodeField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Sell Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String productCode = productCodeField.getText().trim();
                int quantity = Integer.parseInt(quantityField.getText().trim());

                storeService.sellProduct(storeName, productCode, quantity);
                refreshStoreTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error selling product: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void returnProduct() {
        int selectedRow = storeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a store to return product", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String storeName = (String) storeTable.getValueAt(selectedRow, 0);
        JTextField productCodeField = new JTextField();
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Product Code:"));
        panel.add(productCodeField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Return Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String productCode = productCodeField.getText().trim();
                int quantity = Integer.parseInt(quantityField.getText().trim());

                storeService.returnProductToStock(storeName, productCode, quantity);
                refreshStoreTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error returning product: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void checkExpiredProducts() {
        try {
            storeService.checkAndReturnExpiredProducts();
            refreshStoreTable();
            JOptionPane.showMessageDialog(this, "Expired products checked and removed", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error checking expired products: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProductToStore() {
        int selectedRow = storeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a store to add product", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String storeName = (String) storeTable.getValueAt(selectedRow, 0);
        JTextField productCodeField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField productionDateField = new JTextField();
        JTextField expirationDateField = new JTextField();
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Product Code:"));
        panel.add(productCodeField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Production Date (yyyy-MM-dd):"));
        panel.add(productionDateField);
        panel.add(new JLabel("Expiration Date (yyyy-MM-dd):"));
        panel.add(expirationDateField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Add Product to Store", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String productCode = productCodeField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                Date productionDate = new SimpleDateFormat("yyyy-MM-dd").parse(productionDateField.getText().trim());
                Date expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(expirationDateField.getText().trim());
                int quantity = Integer.parseInt(quantityField.getText().trim());

                storeService.addProductToStore(storeName, productCode, price, productionDate, expirationDate, quantity);
                refreshStoreTable();
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding product to store: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewStoreProducts() {
        int selectedRow = storeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a store to view products", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String storeName = (String) storeTable.getValueAt(selectedRow, 0);
        Store store = storeService.findStoreByName(storeName);

        if (store == null || store.getStoreStocks().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No products found in the selected store", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder productList = new StringBuilder();
        for (var storeStock : store.getStoreStocks()) {
            productList.append("Product Code: ").append(storeStock.getStock().getProductCode())
                    .append(", Quantity: ").append(storeStock.getQuantity())
                    .append(", Price: ").append(storeStock.getStock().getProduct().getPrice())
                    .append("\n");
        }

        JTextArea textArea = new JTextArea(productList.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Store Products", JOptionPane.INFORMATION_MESSAGE);
    }



//    private void refreshStoreTable() {
//        List<Store> stores = storeService.findAllStores();
//        DefaultTableModel model = new DefaultTableModel();
//        model.addColumn("Store Name");
//
//        for (Store store : stores) {
//            model.addRow(new Object[]{store.getName()});
//        }
//        storeTable.setModel(model);
//    }

    private void refreshStoreTable() {
        List<Store> stores = storeService.findAllStores();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Store Name");
        model.addColumn("Products");

        for (Store store : stores) {
            String products = store.getStoreStocks().isEmpty() ? "No products" :
                    store.getStoreStocks().stream()
                            .map(ss -> ss.getStock().getProductCode() + " (" + ss.getQuantity() + ")")
                            .collect(Collectors.joining(", "));

            model.addRow(new Object[]{store.getName(), products});
        }

        storeTable.setModel(model);
    }



}