package metropos.view;

import metropos.controller.DEOController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class DEODashboardView extends JFrame {

    private JTable vendorTable, productTable;
    private JButton addVendorButton, addProductButton, editVendorButton, editProductButton, deleteVendorButton, deleteProductButton;
    private DEOController controller;

    public DEODashboardView() {
        controller = new DEOController();
        setTitle("Data Entry Operator Dashboard");
        setSize(1000, 800); 
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
        addVendorButton = new JButton("Add Vendor");
        editVendorButton = new JButton("Edit Vendor");
        deleteVendorButton = new JButton("Delete Vendor");
        addProductButton = new JButton("Add Product");
        deleteProductButton = new JButton("Delete Product");

        buttonPanel.add(addVendorButton);
        buttonPanel.add(editVendorButton);
        buttonPanel.add(deleteVendorButton);
        buttonPanel.add(addProductButton);
        buttonPanel.add(deleteProductButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        
        add(panel);

        
        try {
            populateVendorTable();
            populateProductTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        addVendorButton.addActionListener(e -> openAddVendorForm());
        addProductButton.addActionListener(e -> openAddProductForm());
        editVendorButton.addActionListener(e -> openEditVendorForm());
        deleteVendorButton.addActionListener(e -> deleteSelectedVendor());
        deleteProductButton.addActionListener(e -> deleteSelectedProduct());

        setVisible(true);
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
        columnNames.add("Product ID");
        columnNames.add("Product Name");
        columnNames.add("Category");
        columnNames.add("Sale Price");
        columnNames.add("Vendor");
        

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getInt("Product_id"));
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
        panel.setLayout(new GridLayout(9, 2));

        
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
         JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
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
        panel.add(quantityLabel);
        panel.add(quantityField);
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
                    int vendorId = vendorComboBox.getSelectedIndex() + 1; 
                    boolean success = controller.addProduct(vendorId, productNameField.getText(), categoryField.getText(),Integer.parseInt(quantityField.getText()),
                            Double.parseDouble(originalPriceField.getText()), Double.parseDouble(salePriceField.getText()),
                            
                            Double.parseDouble(pricePerUnitField.getText()), Double.parseDouble(pricePerCartonField.getText()));

                    if (success) {
                            
                        JOptionPane.showMessageDialog(null, "Product added successfully!");
                        addProductFrame.dispose();
                        populateProductTable();//controller.stockupdate(productNameField.getText(), categoryField.getText(),Integer.parseInt(quantityField.getText()));
                    } else {
                        JOptionPane.showMessageDialog(null, "Error adding product!");
                    }
                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    private void openEditVendorForm() {
        int selectedRow = vendorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a vendor to edit.");
            return;
        }

        String vendorName = vendorTable.getValueAt(selectedRow, 1).toString();
        String contact = vendorTable.getValueAt(selectedRow, 2).toString();
        String address = vendorTable.getValueAt(selectedRow, 3).toString();

        JFrame editVendorFrame = new JFrame("Edit Vendor");
        editVendorFrame.setSize(400, 300);
        editVendorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editVendorFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JTextField vendorNameField = new JTextField(vendorName);
        JTextField contactField = new JTextField(contact);
        JTextField addressField = new JTextField(address);
        JButton saveButton = new JButton("Save Changes");

        panel.add(new JLabel("Vendor Name:"));
        panel.add(vendorNameField);
        panel.add(new JLabel("Contact:"));
        panel.add(contactField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(saveButton);

        editVendorFrame.add(panel);
        editVendorFrame.setVisible(true);

        saveButton.addActionListener(e -> {
            try {
                boolean success = controller.updateVendor(
                        Integer.parseInt(vendorTable.getValueAt(selectedRow, 0).toString()),
                        vendorNameField.getText(),
                        contactField.getText(),
                        addressField.getText()
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Vendor updated successfully!");
                    editVendorFrame.dispose();
                    populateVendorTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Error updating vendor.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    
    private void deleteSelectedVendor() {
        int selectedRow = vendorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a vendor to delete.");
            return;
        }

        int vendorId = Integer.parseInt(vendorTable.getValueAt(selectedRow, 0).toString());
        try {
            boolean success = controller.deleteVendor(vendorId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Vendor deleted successfully!");
                populateVendorTable();
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting vendor.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    private void deleteSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a product to delete.");
            return;
        }

        int productId = Integer.parseInt(productTable.getValueAt(selectedRow, 0).toString());
        try {
            boolean success = controller.deleteProduct(productId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                populateProductTable();
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DEODashboardView::new);
    }
}