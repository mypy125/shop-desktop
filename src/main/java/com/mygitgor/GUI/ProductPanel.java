package com.mygitgor.GUI;

import com.mygitgor.model.Product;
import com.mygitgor.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class ProductPanel extends JPanel {
    private ProductService productService;

    private JTable productTable;
    private JButton addButton, editButton, deleteButton;

    public ProductPanel(ProductService productService) {
        this.productService = productService;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        productTable = new JTable();
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addProduct());
        editButton.addActionListener(e -> editProduct());
        deleteButton.addActionListener(e -> deleteProduct());

        refreshProductTable();
    }

    private void addProduct() {
        JTextField codeField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField productionDateField = new JTextField();
        JTextField expirationDateField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Code Product"));
        panel.add(codeField);
        panel.add(new JLabel("Price"));
        panel.add(priceField);
        panel.add(new JLabel("Production date (yyyy-MM-dd):"));
        panel.add(productionDateField);
        panel.add(new JLabel("Expiration date (yyyy-MM-dd):"));
        panel.add(expirationDateField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String code = codeField.getText();
                double price = Double.parseDouble(priceField.getText());
                Date productionDate = new SimpleDateFormat("yyyy-MM-dd").parse(productionDateField.getText());
                Date expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(expirationDateField.getText());

                Product product = new Product();
                product.setCode(code);
                product.setPrice(price);
                product.setProductionDate(productionDate);
                product.setExpirationDate(expirationDate);

                productService.addProduct(product);

                refreshProductTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding product " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a product to edit", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String code = (String) productTable.getValueAt(selectedRow, 0);

        Product product = productService.findByCode(code);
        if (product == null) {
            JOptionPane.showMessageDialog(this, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField priceField = new JTextField(String.valueOf(product.getPrice()));
        JTextField productionDateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(product.getProductionDate()));
        JTextField expirationDateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(product.getExpirationDate()));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Price"));
        panel.add(priceField);
        panel.add(new JLabel("Production date (yyyy-MM-dd):"));
        panel.add(productionDateField);
        panel.add(new JLabel("Expiration date (yyyy-MM-dd):"));
        panel.add(expirationDateField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Edit product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                product.setPrice(Double.parseDouble(priceField.getText()));
                product.setProductionDate(new SimpleDateFormat("yyyy-MM-dd").parse(productionDateField.getText()));
                product.setExpirationDate(new SimpleDateFormat("yyyy-MM-dd").parse(expirationDateField.getText()));

                productService.updateProduct(product);

                refreshProductTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error while editing product " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshProductTable() {
        List<Product> products = productService.findAll();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Code");
        model.addColumn("Price");
        model.addColumn("Product date");
        model.addColumn("Expiration date");

        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getCode(),
                    product.getPrice(),
                    new SimpleDateFormat("yyyy-MM-dd").format(product.getProductionDate()),
                    new SimpleDateFormat("yyyy-MM-dd").format(product.getExpirationDate())
            });
        }

        productTable.setModel(model);
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a product to remove", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String code = (String) productTable.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to remove the product?", "Confirm deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                productService.deleteProduct(code);

                refreshProductTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error while deleting product " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}