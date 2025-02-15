package com.mygitgor.GUI;
import com.mygitgor.model.Store;
import com.mygitgor.service.StoreService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StorePanel extends JPanel {
    private StoreService storeService;
    private JTable storeTable;
    private JButton addButton, editButton, deleteButton;

    public StorePanel(StoreService storeService) {
        this.storeService = storeService;
        initUI();
    }

    private void initUI(){
        setLayout(new BorderLayout());

        storeTable = new JTable();
        add(new JScrollPane(storeTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Store");
        editButton = new JButton("Edit Store");
        deleteButton = new JButton("Delete Store");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStore());
        editButton.addActionListener(e -> editStore());
        deleteButton.addActionListener(e -> deleteStore());

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
                store.setStocks(new ArrayList<>());

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

    private void refreshStoreTable() {
        List<Store> stores = storeService.findAllStores();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Store Name");

        for (Store store : stores) {
            model.addRow(new Object[]{store.getName()});
        }
        storeTable.setModel(model);
    }
}
