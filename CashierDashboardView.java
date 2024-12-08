import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

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

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel logoLabel = new JLabel(new ImageIcon("small.png"));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(logoLabel, BorderLayout.CENTER);
        topPanel.setBackground(new Color(255, 255, 255));
        add(topPanel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        btnPanel.setBackground(new Color(255, 255, 255));

        bill = createStyledButton("Generate Bill");
        sales = createStyledButton("View Sales Report");
        logout = createStyledButton("Log Out");

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

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(btnPanel);
        centerPanel.setBackground(Color.white);
        add(centerPanel, BorderLayout.CENTER);

       
        
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(316, 60));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(108, 167, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    private void generateBill() throws SQLException {
        JLabel custNameLabel = createModernLabel("Customer Name:");
        JLabel prodNameLabel = createModernLabel("Product Name:");
        JLabel quantityLabel = createModernLabel("Quantity:");
        JLabel paymentLabel = createModernLabel("Payment Method:");

        JTextField custNameField = createModernTextField();
        JTextField quantityField = createModernTextField();

        ArrayList<String> availableProducts = controller.getAvailableProducts();
        JComboBox<String> prodNameCombo = createModernComboBox(availableProducts);

        ArrayList<String> availableMethods = new ArrayList<>();
        availableMethods.add("Cash");
        availableMethods.add("Credit Card");
        JComboBox<String> paymentbox = createModernComboBox(availableMethods);

        String[] columnNames = {"Product", "Quantity", "Price", "Discounted Price", "Total Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        JButton addButton = createModernButton("Add");
        JButton printButton = createModernButton("Print");
        JButton cancelButton = createModernButton("Cancel");

        JFrame billFrame = new JFrame("Generate Bill");
        billFrame.setLayout(new BorderLayout());
        billFrame.setSize(1000, 400);
        billFrame.setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inputPanel.add(custNameLabel);
        inputPanel.add(custNameField);
        inputPanel.add(prodNameLabel);
        inputPanel.add(prodNameCombo);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(paymentLabel);
        inputPanel.add(paymentbox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 255, 255));

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
                String paymentName = (String) paymentbox.getSelectedItem();
                controller.setPayment(paymentName);

                Object[] productDetails = controller.addProductToBill(customerName, productName, quantity);
                if (productDetails == null) {
                    JOptionPane.showMessageDialog(null, "Cannot generate Bill for this product!");
                    return;
                }
                tableModel.addRow(productDetails);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(billFrame, "Quantity must be a valid number.");
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

    private JLabel createModernLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private JTextField createModernTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 30));
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        textField.setBackground(new Color(255, 255, 255));
        textField.setForeground(new Color(50, 50, 50));
        textField.setCaretColor(new Color(0, 123, 255));
        return textField;
    }

    private JComboBox<String> createModernComboBox(ArrayList<String> items) {
        JComboBox<String> comboBox = new JComboBox<>(items.toArray(new String[0]));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboBox.setBackground(new Color(255, 255, 255));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        return comboBox;
    }

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 102, 204));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255));
            }
        });
        return button;
    }

    private void viewReport() {
        try {
            DefaultTableModel model = controller.getSalesTableModel();
            JTable table = new JTable(model);

            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.setBackground(new Color(255, 255, 255));
            table.setGridColor(new Color(200, 200, 200));
            table.setRowHeight(30);
            table.setSelectionBackground(new Color(0, 123, 255));
            table.setSelectionForeground(Color.WHITE);
            table.setFocusable(false);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

            JFrame reportFrame = new JFrame("Sales Report");
            reportFrame.setSize(600, 400);
            reportFrame.setLocationRelativeTo(null);

            JPanel reportPanel = new JPanel();
            reportPanel.setLayout(new BorderLayout());
            reportPanel.setBackground(new Color(255, 255, 255));
            reportPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            reportPanel.add(scrollPane, BorderLayout.CENTER);

            JButton closeButton = createModernButton("Close Report");
            closeButton.addActionListener(e -> reportFrame.dispose());

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(255, 255, 255));
            buttonPanel.add(closeButton);

            reportPanel.add(buttonPanel, BorderLayout.SOUTH);

            reportFrame.add(reportPanel);
            reportFrame.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        this.dispose();
    }
}
