package metropos.view;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import metropos.controller.CashierController;

public class CashierDashboardView extends JFrame {
    private JButton logout, bill, sales;
    private CashierController controller;

    public CashierDashboardView() {
        controller = new CashierController();
        setTitle("Cashier Dashboard");
        setSize(600, 400);
        init();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        setLayout(new BorderLayout());
        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        bill = new JButton("Generate Bill");
        sales = new JButton("View Sales Report");
        logout = new JButton("Log Out");

        bill.addActionListener(e -> {
            try {
                generateBill();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        sales.addActionListener(e -> viewReport());
        logout.addActionListener(e -> logout());

        btnPanel.add(bill);
        btnPanel.add(sales);
        btnPanel.add(logout);

        JLabel display = new JLabel("Cashier Dashboard", JLabel.CENTER);
        add(display, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
    }

   private void generateBill() throws SQLException {
    JLabel custNameLabel = new JLabel("Customer Name:");
    JLabel prodNameLabel = new JLabel("Product Name:");
    JLabel quantityLabel = new JLabel("Quantity:");
JLabel paymentLabel = new JLabel("Payment Method:");
    JTextField custNameField = new JTextField();
    JTextField quantityField = new JTextField();
    ArrayList<String> availableProducts = controller.getAvailableProducts();
    JComboBox<String> prodNameCombo = new JComboBox<>(availableProducts.toArray(new String[0]));
    ArrayList<String> availableMethods=new ArrayList<>();
availableMethods.add("Cash");
availableMethods.add("Credit Card");
JComboBox<String> paymentbox= new JComboBox<>(availableMethods.toArray(new String[0]));
    // Table to display added products
    String[] columnNames = {"Product", "Quantity", "Price", "Discounted Price", "Total Price"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    JTable productTable = new JTable(tableModel);
    JScrollPane tableScrollPane = new JScrollPane(productTable);

  
    JButton addButton = new JButton("Add");
    JButton printButton = new JButton("Print");
    JButton cancelButton = new JButton("Cancel");

    
    JFrame billFrame = new JFrame("Generate Bill");
    billFrame.setLayout(new BorderLayout());
    billFrame.setSize(600, 400);
    billFrame.setLocationRelativeTo(null);

    JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
    inputPanel.add(custNameLabel);
    inputPanel.add(custNameField);
    inputPanel.add(prodNameLabel);
    inputPanel.add(prodNameCombo);
    inputPanel.add(quantityLabel);
    inputPanel.add(quantityField);
    inputPanel.add(paymentLabel);
    inputPanel.add(paymentbox);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(addButton);
    buttonPanel.add(printButton);
    buttonPanel.add(cancelButton);

    billFrame.add(inputPanel, BorderLayout.NORTH);
    billFrame.add(tableScrollPane, BorderLayout.CENTER);
    billFrame.add(buttonPanel, BorderLayout.SOUTH);

    billFrame.setVisible(true);

    addButton.addActionListener(e -> {
        try {
            String customerName = custNameField.getText();
            String productName = (String) prodNameCombo.getSelectedItem();
            int quantity = Integer.parseInt(quantityField.getText());
            String paymentName=(String)paymentbox.getSelectedItem();
            controller.setPayment(paymentName);

            
            Object[] productDetails = controller.addProductToBill(customerName, productName, quantity);
            if(productDetails==null)
            {
                JOptionPane.showMessageDialog(null, "Cannot generate Bill for this product!");
            return;
            }
            tableModel.addRow(productDetails);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(billFrame, "Quantity must be a valid number.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(billFrame, "Database error: " + ex.getMessage());
        }
    });

    printButton.addActionListener(e -> {
        try {
            String customerName = custNameField.getText();
            controller.generateReceipt(customerName);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(billFrame, "Error generating receipt: " + ex.getMessage());
        }
    });

    cancelButton.addActionListener(e -> billFrame.dispose());
}


    private void viewReport() {
        try {
            DefaultTableModel model = controller.getSalesTableModel();
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame reportFrame = new JFrame("Sales Report");
            reportFrame.setSize(600, 400);
            reportFrame.add(scrollPane);
            reportFrame.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching report.");
        }
    }

    private void logout() {
        new LoginView(); 
        this.dispose();
    }
}
