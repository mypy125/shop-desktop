package com.mygitgor.GUI;

import javax.swing.*;
import java.awt.*;

public class StorePanel extends JPanel {
    private JTable storeTable;
    private JButton addButton, editButton, deleteButton;

    public StorePanel() {
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
    }

    private void addStore() {

    }

    private void editStore() {

    }

    private void deleteStore() {

    }
}
