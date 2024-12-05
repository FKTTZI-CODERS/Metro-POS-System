package metropos.view;

import metropos.controller.DEOController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class DEODashboardView extends JFrame {

    private JTable vendorTable, productTable;
    private JButton addVendorButton, addProductButton;
    private DEOController controller;

    public DEODashboardView() {
        controller = new DEOController();
        setTitle("Data Entry Operator Dashboard");
        setSize(800, 800); // Increased size to fit both tables
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

       
        vendorTable = new JTable();
        JScrollPane vendorScrollPane = new JScrollPane(vendorTable);
        panel.add(vendorScrollPane, BorderLayout.NORTH); 

      
        productTable = new JTable();
        JScrollPane productScrollPane = new JScrollPane(productTable);
        panel.add(productScrollPane, BorderLayout.CENTER); 

       
        JPanel buttonPanel = new JPanel();
        addVendorButton = new JButton("Add New Vendor");
        addProductButton = new JButton("Add New Product");

        buttonPanel.add(addVendorButton);
        buttonPanel.add(addProductButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

       
        add(panel);

       
        try {
            populateVendorTable();
            populateProductTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setVisible(true);

       
        addVendorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddVendorForm();
            }
        });// Action listener for adding new product
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddProductForm();
            }
        });
    }

    private void populateVendorTable() throws SQLException {
        ResultSet vendors = controller.getVendors();
        vendorTable.setModel(buildTableModel(vendors));
    }

  
    private void populateProductTable() throws SQLException {
        ResultSet products = controller.getProducts();
        productTable.setModel(buildProductTableModel(products));
    }

   
    private static TableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        Vector<String> columnNames = new Vector<>();

        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getObject(i));
            }
            data.add(row);
        }

        return new DefaultTableModel(data, columnNames);
    }

    
    private static TableModel buildProductTableModel(ResultSet rs) throws SQLException {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Product Name");
        columnNames.add("Category");
        columnNames.add("Sale Price");
        columnNames.add("Vendor");

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getString("Product_Name"));
            row.add(rs.getString("Category"));
            row.add(rs.getDouble("Sale_Price"));
            row.add(rs.getString("Vendor_Name"));
            data.add(row);
        }

        return new DefaultTableModel(data, columnNames);
    }

 
    private void openAddVendorForm() {
        JFrame addVendorFrame = new JFrame("Add New Vendor");
        addVendorFrame.setSize(400, 300);
        addVendorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addVendorFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel vendorNameLabel = new JLabel("Vendor Name:");
        JTextField vendorNameField = new JTextField();
        JLabel contactLabel = new JLabel("Contact:");
        JTextField contactField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();

        JButton saveButton = new JButton("Save");

        panel.add(vendorNameLabel);
        panel.add(vendorNameField);
        panel.add(contactLabel);
        panel.add(contactField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(saveButton);

        addVendorFrame.add(panel);
        addVendorFrame.setVisible(true);

        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean success = controller.addVendor(vendorNameField.getText(), contactField.getText(), addressField.getText());

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Vendor added successfully!");
                        addVendorFrame.dispose();
                        populateVendorTable(); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Error adding vendor!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    
    private void openAddProductForm() {
        JFrame addProductFrame = new JFrame("Add New Product");
        addProductFrame.setSize(400, 400);
        addProductFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addProductFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        
        JLabel vendorLabel = new JLabel("Select Vendor:");
        JComboBox<String> vendorComboBox = new JComboBox<>();
        try {
            ResultSet vendors = controller.getVendors();
            while (vendors.next()) {
                vendorComboBox.addItem(vendors.getString("Vendor_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JLabel productNameLabel = new JLabel("Product Name:");
        JTextField productNameField = new JTextField();
        JLabel categoryLabel = new JLabel("Category:");
        JTextField categoryField = new JTextField();
        JLabel originalPriceLabel = new JLabel("Original Price:");
        JTextField originalPriceField = new JTextField();
        JLabel salePriceLabel = new JLabel("Sale Price:");
        JTextField salePriceField = new JTextField();
        JLabel pricePerUnitLabel = new JLabel("Price per Unit:");
        JTextField pricePerUnitField = new JTextField();
        JLabel pricePerCartonLabel = new JLabel("Price per Carton:");
        JTextField pricePerCartonField = new JTextField();

        JButton saveButton = new JButton("Save");

        panel.add(vendorLabel);
        panel.add(vendorComboBox);
        panel.add(productNameLabel);
        panel.add(productNameField);
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(originalPriceLabel);
        panel.add(originalPriceField);
        panel.add(salePriceLabel);
        panel.add(salePriceField);
        panel.add(pricePerUnitLabel);
        panel.add(pricePerUnitField);
        panel.add(pricePerCartonLabel);
        panel.add(pricePerCartonField);
        panel.add(saveButton);

        addProductFrame.add(panel);
        addProductFrame.setVisible(true);

        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int vendorId = vendorComboBox.getSelectedIndex() + 1; // Assuming vendor IDs start from 1
                    boolean success = controller.addProduct(vendorId, productNameField.getText(), categoryField.getText(),
                            Double.parseDouble(originalPriceField.getText()), Double.parseDouble(salePriceField.getText()),
                            Double.parseDouble(pricePerUnitField.getText()), Double.parseDouble(pricePerCartonField.getText()));

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Product added successfully!");
                        addProductFrame.dispose();
                        populateProductTable(); // Refresh product table
                    } else {
                        JOptionPane.showMessageDialog(null, "Error adding product!");
                    }
                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DEODashboardView().setVisible(true);
            }
        });
    }
}
