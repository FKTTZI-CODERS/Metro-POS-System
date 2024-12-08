package metropos.controller;

import java.awt.BorderLayout;
import metropos.model.CashierModel;
import metropos.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashierController {
    private CashierModel model;
    private User u;
  static String payment="";

    public synchronized static String getPayment() {
        return payment;
    }

    public static void setPayment(String payment) {
        CashierController.payment = payment;
    }
static String  email="";
    public CashierController() {
        model = new CashierModel();
        u = new User();
        
    }

    public ArrayList<String> getAvailableProducts() throws SQLException {
        return model.getAvailableProducts();
    }


    public synchronized String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void generateBill(String customerName, String productName, int quantity) throws SQLException {
        if (customerName.isEmpty() || productName.isEmpty() || quantity <= 0) {
            JOptionPane.showMessageDialog(null, "All fields must be filled correctly.");
            return;
        }

        double price = model.getProductPrice(productName);
        double tax = 0.1 * price * quantity;
        double totalPrice = (price * quantity) + tax;
       
    double discountedprice = model.getDiscountedPrice(productName);
    double discountedPrice = price - discountedprice;
    totalPrice = totalPrice-(discountedPrice * quantity);
        model.addBillEntry(customerName,productName, quantity, price,discountedPrice , totalPrice);
    }
    public Object[] addProductToBill(String customerName, String productName, int quantity) throws SQLException {
        if(customerName.isEmpty() || productName.isEmpty()|| quantity <=0)
        {
            JOptionPane.showMessageDialog(null, "Please fill in all blank fields");
            return null;
        }
        if(model.checkQuantity(productName)==0)
        {
            JOptionPane.showMessageDialog(null, "Stock not availble currently");
            return null;
        }
        if(quantity>model.checkQuantity(productName))
        {
            JOptionPane.showMessageDialog(null, "Quantity asked cannot be greater than the available quantity");
            return null;
        }
    double price = model.getProductPrice(productName);
    double discountedPrice = model.getDiscountedPrice(productName);
    double totalPrice = discountedPrice * quantity;

    model.addBillEntry(customerName, productName, quantity, price,discountedPrice, totalPrice);

    model.addSalesEntry(customerName, productName, quantity, totalPrice);

    
    model.updateStock(productName, quantity);

    return new Object[]{productName, quantity, price, discountedPrice, totalPrice};
}


    public DefaultTableModel getBillTableModel() {
        return model.getBillTableModel();
    }

    public void addProductToSalesTable(String customerName, String productName, int quantity,String email) throws SQLException {String mail=email;
        double price = model.getProductPrice(productName);
        double discount = 0; 
        double taxRate = 0.1;
        double tax = price * quantity * taxRate;
        double totalPrice = (price * quantity) + tax;

        model.addProductToSalesTable( productName, quantity, price, discount, totalPrice);
    }

    public DefaultTableModel getSalesTableModel() throws SQLException {
        return model.getSalesTableModel();
    }

   public void generateReceipt(String customerName) throws SQLException {
    ArrayList<Object[]> billDetails = model.getBillDetails(customerName);

    StringBuilder receipt = new StringBuilder("--------- Receipt for " + customerName + " -------\n\n");
    for (Object[] detail : billDetails) {
        receipt.append(String.format("\nProduct: %s  |  Quantity: %d  |  Total Price: %.2f  |  Payment Method: %s\n", detail[0], detail[1], detail[4], getPayment()));
    }

    receipt.append("\nThank you for shopping with us!");

    JFrame receiptFrame = new JFrame("Receipt");
    JTextArea receiptArea = new JTextArea(receipt.toString());
    receiptArea.setEditable(false);
    receiptFrame.add(new JScrollPane(receiptArea), BorderLayout.CENTER);

    JButton printButton = new JButton("Print");
    printButton.addActionListener(e -> {
        try {
            receiptArea.print(); 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(receiptFrame, "Error printing receipt: " + ex.getMessage());
        }
    });

    receiptFrame.add(printButton, BorderLayout.SOUTH);
    receiptFrame.setSize(400, 300);
    receiptFrame.setLocationRelativeTo(null);
    receiptFrame.setVisible(true);
}
   public String getBranchCode() throws SQLException {
    return model.getBranchCodeByEmail(getEmail());
}


}
