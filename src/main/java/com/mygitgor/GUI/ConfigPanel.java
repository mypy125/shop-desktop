package com.mygitgor.GUI;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

@Component
public class ConfigPanel extends JPanel {
    private static final Logger logger = Logger.getLogger(ConfigPanel.class.getName());
    private JComboBox<String> dbTypeComboBox;
    private JTextField hostField, portField, dbNameField, userField;
    private JPasswordField passwordField;
    private JButton testConnectionButton, saveButton;
    private Properties config;

    @Value("${spring.datasource.url:}")
    private String datasourceUrl;

    @Value("${spring.datasource.username:}")
    private String datasourceUsername;

    @Value("${spring.datasource.password:}")
    private String datasourcePassword;

    public ConfigPanel() {
        initialize();
    }

    @PostConstruct
    public void initialize() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));

        formPanel.add(new JLabel("DB Type:"));
        dbTypeComboBox = new JComboBox<>(new String[]{"PostgreSQL", "MySQL", "SQLite"});
        formPanel.add(dbTypeComboBox);

        formPanel.add(new JLabel("Host:"));
        hostField = new JTextField("localhost");
        formPanel.add(hostField);

        formPanel.add(new JLabel("Port:"));
        portField = new JTextField("5432");
        formPanel.add(portField);

        formPanel.add(new JLabel("DB Name:"));
        dbNameField = new JTextField("testdb");
        formPanel.add(dbNameField);

        formPanel.add(new JLabel("User:"));
        userField = new JTextField("root");
        formPanel.add(userField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        testConnectionButton = new JButton("Test");
        saveButton = new JButton("Save");
        buttonPanel.add(testConnectionButton);
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadConfig();
        dbTypeComboBox.addActionListener(e -> updateDefaultPort());
        testConnectionButton.addActionListener(this::testDatabaseConnection);
        saveButton.addActionListener(e -> saveConfig());
    }

    private void updateDefaultPort() {
        switch ((String) dbTypeComboBox.getSelectedItem()) {
            case "PostgreSQL" -> portField.setText("5432");
            case "MySQL" -> portField.setText("3306");
            case "SQLite" -> {
                hostField.setText("N/A");
                portField.setText("");
                userField.setText("N/A");
                passwordField.setText("");
            }
        }
        boolean isSQLite = "SQLite".equals(dbTypeComboBox.getSelectedItem());
        hostField.setEnabled(!isSQLite);
        portField.setEnabled(!isSQLite);
        userField.setEnabled(!isSQLite);
        passwordField.setEnabled(!isSQLite);
    }

    private void loadConfig() {
        config = new Properties();
        File configFile = new File("config.properties");
        if (configFile.exists()) {
            try (FileInputStream input = new FileInputStream(configFile)) {
                config.load(input);
                dbTypeComboBox.setSelectedItem(config.getProperty("db.type", "PostgreSQL"));
                hostField.setText(config.getProperty("db.host", "localhost"));
                portField.setText(config.getProperty("db.port", "5432"));
                dbNameField.setText(config.getProperty("db.name", "testdb"));
                userField.setText(config.getProperty("db.user", "root"));
                passwordField.setText(config.getProperty("db.password", ""));
            } catch (IOException e) {
                logger.warning("Error loading config file: " + e.getMessage());
            }
        } else {
            logger.warning("Config file not found, using default values.");
        }

        if (datasourceUrl != null && !datasourceUrl.isEmpty()) {
            String[] urlParts = datasourceUrl.split(":");
            if (urlParts.length > 1) {
                String dbType = urlParts[1];
                dbTypeComboBox.setSelectedItem(dbType.equals("h2") ? "SQLite" : dbType.substring(0, 1).toUpperCase() + dbType.substring(1));
            }
        }
        if (datasourceUsername != null && !datasourceUsername.isEmpty()) {
            userField.setText(datasourceUsername);
        }
        if (datasourcePassword != null && !datasourcePassword.isEmpty()) {
            passwordField.setText(datasourcePassword);
        }
    }

    private void saveConfig() {
        config.setProperty("db.type", (String) dbTypeComboBox.getSelectedItem());
        config.setProperty("db.host", hostField.getText());
        config.setProperty("db.port", portField.getText());
        config.setProperty("db.name", dbNameField.getText());
        config.setProperty("db.user", userField.getText());
        config.setProperty("db.password", new String(passwordField.getPassword()));

        try (FileOutputStream output = new FileOutputStream("config.properties")) {
            config.store(output, "Database Configuration");
            JOptionPane.showMessageDialog(this, "Configuration saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving configuration: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void testDatabaseConnection(ActionEvent event) {
        String dbType = (String) dbTypeComboBox.getSelectedItem();
        String url = switch (dbType) {
            case "PostgreSQL" -> "jdbc:postgresql://" + hostField.getText() + ":" + portField.getText() + "/" + dbNameField.getText();
            case "MySQL" -> "jdbc:mysql://" + hostField.getText() + ":" + portField.getText() + "/" + dbNameField.getText();
            case "SQLite" -> "jdbc:sqlite:" + dbNameField.getText() + ".db";
            default -> "";
        };

        try (Connection connection = DriverManager.getConnection(url, userField.getText(), new String(passwordField.getPassword()))) {
            logger.info("Connected successfully to " + url);
            JOptionPane.showMessageDialog(this, "Connection successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            logger.severe("Connection failed: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Connection failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}