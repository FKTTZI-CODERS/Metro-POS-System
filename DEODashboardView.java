package metropos.view;

import metropos.controller.DEOController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class DEODashboardView extends JFrame {

    private JTable vendorTable, productTable;
    private JButton addVendorButton, addProductButton, editVendorButton, editProductButton, deleteVendorButton, deleteProductButton;
    private DEOController controller;
static Font headerFont,tableTitleFont,buttonFont;
static  Color bgColor,headerColor,buttonColor , lightBlue ,borderColor;
    public DEODashboardView() {
        controller = new DEOController();
        setTitle("DEO Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
       viewFontandColour();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);

        
        JLabel headerLabel = new JLabel("DEO Dashboard", JLabel.CENTER);
        headerLabel.setFont(headerFont);
        headerLabel.setForeground(headerColor);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(bgColor);

      
        vendorTable = createStyledTable();
        JScrollPane vendorScrollPane = new JScrollPane(vendorTable);
        vendorScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                "Vendors",
                0,
                0,
                tableTitleFont,
                headerColor
        ));
        centerPanel.add(vendorScrollPane);

        
        productTable = createStyledTable();
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                "Products",
                0,
                0,
                tableTitleFont,
                headerColor
        ));
        centerPanel.add(productScrollPane);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(bgColor);

        addVendorButton = createStyledButton("Add Vendor", buttonFont, buttonColor);
        editVendorButton = createStyledButton("Edit Vendor", buttonFont, buttonColor);
        deleteVendorButton = createStyledButton("Delete Vendor", buttonFont, buttonColor);
        addProductButton = createStyledButton("Add Product", buttonFont, buttonColor);
        editProductButton = createStyledButton("Edit Product", buttonFont, buttonColor);
        deleteProductButton = createStyledButton("Delete Product", buttonFont, buttonColor);

        buttonPanel.add(addVendorButton);
        buttonPanel.add(editVendorButton);
        buttonPanel.add(deleteVendorButton);
        buttonPanel.add(addProductButton);
        buttonPanel.add(editProductButton);
        buttonPanel.add(deleteProductButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        
        try {
            populateVendorTable();
            populateProductTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Button actions
        addVendorButton.addActionListener(e -> openAddVendorForm());
        addProductButton.addActionListener(e -> openAddProductForm());
        editVendorButton.addActionListener(e -> openEditVendorForm());
        deleteVendorButton.addActionListener(e -> deleteSelectedVendor());
        deleteProductButton.addActionListener(e -> deleteSelectedProduct());

        setVisible(true);
    }

    private void viewFontandColour()
            {
                 this.headerFont = new Font("Poppins", Font.BOLD, 28);
        this.tableTitleFont = new Font("Poppins", Font.BOLD, 16);
         this.buttonFont = new Font("Poppins", Font.PLAIN, 12);
       this.bgColor = Color.WHITE;
        this.headerColor = new Color(38, 70, 83);
        this.buttonColor = new Color(108, 167, 255);
        this.lightBlue = new Color(227, 238, 254);
         this.borderColor = new Color(173, 216, 230);
            }
    private JButton createStyledButton(String text, Font font, Color color) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(new Color(227, 238, 254)); 
        button.setForeground(Color.BLACK); 
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(color.darker(), 1));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 35)); 
        return button;
    }

    
    private JTable createStyledTable() {
        JTable table = new JTable();
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(173, 216, 230)); 
        table.setRowHeight(30);
        table.setFont(new Font("Poppins", Font.PLAIN, 14));

        
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(227, 238, 254)); 
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setFont(new Font("Poppins", Font.BOLD, 14));
        tableHeader.setReorderingAllowed(false);

        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        return table;
    }

    private void populateVendorTable() throws SQLException {
        ResultSet vendors = controller.getVendors();
        vendorTable.setModel(buildTableModel(vendors));
    }

    private void populateProductTable() throws SQLException {
        ResultSet products = controller.getProducts();
        productTable.setModel(buildProductTableModel(products));
    }

    private static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
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

    private static DefaultTableModel buildProductTableModel(ResultSet rs) throws SQLException {
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
    addVendorFrame.setSize(500, 400); 
    addVendorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    addVendorFrame.setLocationRelativeTo(null);

    
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridBagLayout()); 
    mainPanel.setBackground(Color.WHITE);

    
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new GridBagLayout());
    inputPanel.setBackground(new Color(227, 238, 254));
    inputPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3)); 

    
    JLabel formHeading = new JLabel("Add New Vendor");
    formHeading.setFont(new Font("Arial", Font.BOLD, 22));
    formHeading.setHorizontalAlignment(SwingConstants.CENTER);
    formHeading.setForeground(Color.DARK_GRAY);

    
    JLabel vendorNameLabel = new JLabel("Vendor Name:");
    JTextField vendorNameField = new JTextField(20);
    JLabel contactLabel = new JLabel("Contact:");
    JTextField contactField = new JTextField(20);
    JLabel addressLabel = new JLabel("Address:");
    JTextField addressField = new JTextField(20);

    JButton saveButton = new JButton("Save");
    saveButton.setFocusPainted(false);
    saveButton.setBackground(new Color(70, 130, 180)); 
    saveButton.setForeground(Color.WHITE);
    saveButton.setFont(new Font("Arial", Font.BOLD, 16));
    saveButton.setPreferredSize(new Dimension(100, 30)); 

    
    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font fieldFont = new Font("Arial", Font.PLAIN, 14);
    styleComponent(vendorNameLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(contactLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(addressLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(vendorNameField, Color.WHITE, Color.DARK_GRAY, fieldFont);
    styleComponent(contactField, Color.WHITE, Color.DARK_GRAY, fieldFont);
    styleComponent(addressField, Color.WHITE, Color.DARK_GRAY, fieldFont);

  
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); 
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    inputPanel.add(formHeading, gbc);

    gbc.gridwidth = 1;
    gbc.gridy = 1;
    gbc.gridx = 0;
    inputPanel.add(vendorNameLabel, gbc);

    gbc.gridx = 1;
    inputPanel.add(vendorNameField, gbc);

    gbc.gridy = 2;
    gbc.gridx = 0;
    inputPanel.add(contactLabel, gbc);

    gbc.gridx = 1;
    inputPanel.add(contactField, gbc);

    gbc.gridy = 3;
    gbc.gridx = 0;
    inputPanel.add(addressLabel, gbc);

    gbc.gridx = 1;
    inputPanel.add(addressField, gbc);

    gbc.gridy = 4;
    gbc.gridx = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    inputPanel.add(saveButton, gbc);

    mainPanel.add(inputPanel);
    addVendorFrame.add(mainPanel);
    addVendorFrame.setVisible(true);

  
    saveButton.addActionListener(e -> {
        try {
            boolean success = controller.addVendor(
                vendorNameField.getText(),
                contactField.getText(),
                addressField.getText()
            );

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
    });
}


private void styleComponent(JComponent component, Color bgColor, Color fgColor, Font font) {
    if (bgColor != null) {
        component.setBackground(bgColor);
    }
    if (fgColor != null) {
        component.setForeground(fgColor);
    }
    component.setFont(font);
    if (component instanceof JTextField || component instanceof JComboBox) {
        component.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); 
    }
}

    private void openAddProductForm() {
    JFrame addProductFrame = new JFrame("Add New Product");
    addProductFrame.setSize(600, 500); 
    addProductFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    addProductFrame.setLocationRelativeTo(null);

   
    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setBackground(Color.WHITE);

    
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(new Color(227, 238, 254)); 
    formPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3)); 

    JLabel formHeading = new JLabel("Add New Product");
    formHeading.setFont(new Font("Arial", Font.BOLD, 22));
    formHeading.setHorizontalAlignment(SwingConstants.CENTER);
    formHeading.setForeground(Color.DARK_GRAY);

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
    JTextField productNameField = new JTextField(20);
    JLabel categoryLabel = new JLabel("Category:");
    JTextField categoryField = new JTextField(20);
    JLabel quantityLabel = new JLabel("Quantity:");
    JTextField quantityField = new JTextField(20);
    JLabel originalPriceLabel = new JLabel("Original Price:");
    JTextField originalPriceField = new JTextField(20);
    JLabel salePriceLabel = new JLabel("Sale Price:");
    JTextField salePriceField = new JTextField(20);
    JLabel pricePerUnitLabel = new JLabel("Price per Unit:");
    JTextField pricePerUnitField = new JTextField(20);
    JLabel pricePerCartonLabel = new JLabel("Price per Carton:");
    JTextField pricePerCartonField = new JTextField(20);

    JButton saveButton = new JButton("Save");
    saveButton.setFocusPainted(false);
    saveButton.setBackground(new Color(70, 130, 180)); 
    saveButton.setForeground(Color.WHITE);
    saveButton.setFont(new Font("Arial", Font.BOLD, 16));
    saveButton.setPreferredSize(new Dimension(120, 40)); 

    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); 
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
    formPanel.add(formHeading, gbc);

    gbc.gridwidth = 1; gbc.gridy++;
    gbc.gridx = 0; formPanel.add(vendorLabel, gbc);
    gbc.gridx = 1; formPanel.add(vendorComboBox, gbc);

    gbc.gridy++; gbc.gridx = 0; formPanel.add(productNameLabel, gbc);
    gbc.gridx = 1; formPanel.add(productNameField, gbc);

    gbc.gridy++; gbc.gridx = 0; formPanel.add(categoryLabel, gbc);
    gbc.gridx = 1; formPanel.add(categoryField, gbc);

    gbc.gridy++; gbc.gridx = 0; formPanel.add(quantityLabel, gbc);
    gbc.gridx = 1; formPanel.add(quantityField, gbc);

    gbc.gridy++; gbc.gridx = 0; formPanel.add(originalPriceLabel, gbc);
    gbc.gridx = 1; formPanel.add(originalPriceField, gbc);

    gbc.gridy++; gbc.gridx = 0; formPanel.add(salePriceLabel, gbc);
    gbc.gridx = 1; formPanel.add(salePriceField, gbc);

    gbc.gridy++; gbc.gridx = 0; formPanel.add(pricePerUnitLabel, gbc);
    gbc.gridx = 1; formPanel.add(pricePerUnitField, gbc);

    gbc.gridy++; gbc.gridx = 0; formPanel.add(pricePerCartonLabel, gbc);
    gbc.gridx = 1; formPanel.add(pricePerCartonField, gbc);

    gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
    formPanel.add(saveButton, gbc);

    mainPanel.add(formPanel);
    addProductFrame.add(mainPanel);
    addProductFrame.setVisible(true);

    saveButton.addActionListener(e -> {
        try {
            int vendorId = vendorComboBox.getSelectedIndex() + 1;
            boolean success = controller.addProduct(
                vendorId,
                productNameField.getText(),
                categoryField.getText(),
                Integer.parseInt(quantityField.getText()),
                Double.parseDouble(originalPriceField.getText()),
                Double.parseDouble(salePriceField.getText()),
                Double.parseDouble(pricePerUnitField.getText()),
                Double.parseDouble(pricePerCartonField.getText())
            );

            if (success) {
                JOptionPane.showMessageDialog(null, "Product added successfully!");
                addProductFrame.dispose();
                populateProductTable();
            } else {
                JOptionPane.showMessageDialog(null, "Error adding product!");
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    });
}


   
   private void openEditVendorForm() {
    int selectedRow = vendorTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Select a vendor to edit.");
        return;
    }

    String vendorName = vendorTable.getValueAt(selectedRow, 1).toString();
    String contact = vendorTable.getValueAt(selectedRow, 2).toString();
    String address = vendorTable.getValueAt(selectedRow, 3).toString();

    JFrame editVendorFrame = new JFrame("Edit Vendor");
    editVendorFrame.setSize(500, 300);
    editVendorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    editVendorFrame.setLocationRelativeTo(null);

   
    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setBackground(Color.WHITE);

    
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(new Color(227, 238, 254));
    formPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

    JLabel formHeading = new JLabel("Edit Vendor");
    formHeading.setFont(new Font("Arial", Font.BOLD, 22));
    formHeading.setHorizontalAlignment(SwingConstants.CENTER);
    formHeading.setForeground(Color.DARK_GRAY);

    JTextField vendorNameField = new JTextField(vendorName, 20);
    JTextField contactField = new JTextField(contact, 20);
    JTextField addressField = new JTextField(address, 20);

    JButton saveButton = new JButton("Save Changes");
    saveButton.setFocusPainted(false);
    saveButton.setBackground(new Color(70, 130, 180)); 
    saveButton.setForeground(Color.WHITE);
    saveButton.setFont(new Font("Arial", Font.BOLD, 16));
    saveButton.setPreferredSize(new Dimension(150, 40));

    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
    formPanel.add(formHeading, gbc);

    gbc.gridwidth = 1; gbc.gridy++;
    gbc.gridx = 0; formPanel.add(new JLabel("Vendor Name:"), gbc);
    gbc.gridx = 1; formPanel.add(vendorNameField, gbc);

    gbc.gridy++; gbc.gridx = 0; formPanel.add(new JLabel("Contact:"), gbc);
    gbc.gridx = 1; formPanel.add(contactField, gbc);

    gbc.gridy++; gbc.gridx = 0; formPanel.add(new JLabel("Address:"), gbc);
    gbc.gridx = 1; formPanel.add(addressField, gbc);

    gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
    formPanel.add(saveButton, gbc);

    mainPanel.add(formPanel);
    editVendorFrame.add(mainPanel);
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
                JOptionPane.showMessageDialog(null, "Vendor updated successfully!");
                editVendorFrame.dispose();
                populateVendorTable();
            } else {
                JOptionPane.showMessageDialog(null, "Error updating vendor!");
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