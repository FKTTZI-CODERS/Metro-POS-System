package metropos.model;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import metropos.controller.AuthenticateController;


public class CashierModel {
    private Connection conn;
    private User u;
AuthenticateController a;
    public CashierModel() {
       this.conn = DBConnection.getInstance().getConnection();
        u = new User();
      a= new AuthenticateController();
    }
 

    public ArrayList<String> getAvailableProducts() throws SQLException {
        ArrayList<String> products = new ArrayList<>();
        String query = "SELECT Product_Name FROM Product";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            products.add(rs.getString("Product_Name"));
        }
        return products;
    }
public int checkQuantity(String product) throws SQLException
{
    String q1="select Product_id from product where Product_Name=?";
    int product_id=0;
     PreparedStatement stmt = conn.prepareStatement(q1);
     stmt.setString(1,product);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            product_id=rs.getInt("Product_id");
        }
        String q2="select Quantity_Remaining from stock where Product_id=?";
        int quantity=0;
         PreparedStatement ps = conn.prepareStatement(q2);
     ps.setInt(1,product_id);
        ResultSet res = ps.executeQuery();
        if(res.next())
        {
            quantity=res.getInt("Quantity_Remaining");
        }
        return quantity;
    
}
   public double getProductPrice(String productName) throws SQLException {
    String query = "SELECT Original_Price FROM Product WHERE product_name = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, productName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble("Original_Price");
        } else {
            throw new SQLException("Product not found in the database.");
        }
    }
}


    public void addBillEntry(String customerName, String productName, int quantity, double price, 
                        double discountedPrice, double totalPrice) throws SQLException {
    String query = "INSERT INTO bill (customer_name, product_name, quantity, price, discounted_price, total_price) " +
                   "VALUES (?, ?, ?, ?, ?, ? )";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, customerName);
        stmt.setString(2, productName);
        stmt.setInt(3, quantity);
        stmt.setDouble(4, price);  
        stmt.setDouble(5, discountedPrice);
        stmt.setDouble(6, totalPrice);
        stmt.executeUpdate();
    }
}


 public void addProductToSalesTable(String productName, int quantity, double price, double discount, double totalPrice) throws SQLException {
    String branchCode =a.getCode() ;
     System.out.println("in add product to sales table "+branchCode);

    String query = "INSERT INTO sales (product_id, Quantity_Sold, price_per_unit, Sale_Price, Total_Price, Branch_Code) " +
                   "VALUES ((SELECT Product_id FROM Product WHERE Product_Name = ?), ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setString(1, productName);
        ps.setInt(2, quantity);
        ps.setDouble(3, price);
        ps.setDouble(4, discount);
        ps.setDouble(5, totalPrice);
        ps.setString(6, branchCode);
        ps.executeUpdate();
    }
}

public double getDiscountedPrice(String productName) throws SQLException {
    String query = "SELECT Sale_Price FROM Product WHERE product_name = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, productName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble("Sale_Price");
        } else {
            throw new SQLException("Product not found in the database.");
        }
    }
}
public void addSalesEntry(String customerName, String productName, int quantity, double totalPrice) throws SQLException {
String bcode=a.getCode();
    System.out.println("in add sales entry of model: "+bcode);
    String query = "INSERT INTO sales (product_id, Quantity_Sold, Total_Price,Branch_Code) VALUES ((SELECT Product_id FROM Product WHERE Product_Name = ?), ?, ?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1,productName);
        stmt.setInt(2, quantity);
        stmt.setDouble(3, totalPrice);
        stmt.setString(4,bcode);
        stmt.executeUpdate();
    }
}

    public DefaultTableModel getBillTableModel() {
        return new DefaultTableModel();
    }

    public DefaultTableModel getSalesTableModel() throws SQLException {
        String query = "SELECT p.Product_Name,s.Quantity_Sold,s.Total_Price FROM sales s JOIN product p on s.Product_id=p.Product_id where DATE(Sale_date)=CURDATE()";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }
            tableModel.addRow(row);
        }
        return tableModel;
    }
public void updateStock(String productName, int quantity) throws SQLException {
    String query = "UPDATE stock SET Quantity_Remaining = Quantity_Remaining - ? WHERE Product_id = (SELECT Product_id FROM Product WHERE Product_Name = ?)";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, quantity);
        stmt.setString(2, productName);
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated == 0) {
            throw new SQLException("Stock update failed. Product may not exist.");
        }
    }
}
public ArrayList<Object[]> getBillDetails(String customerName) throws SQLException {
    String query = "SELECT product_name, quantity, price, discounted_price, total_price " +
                   "FROM bill WHERE customer_name = ?";
    ArrayList<Object[]> billDetails = new ArrayList<>();
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, customerName);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Object[] row = new Object[5];
            row[0] = rs.getString("product_name");
            row[1] = rs.getInt("quantity");
            row[2] = rs.getDouble("price");
            row[3] = rs.getDouble("discounted_price");
            row[4] = rs.getDouble("total_price");
            billDetails.add(row);
        }
    }
    return billDetails;
}


   
    public String getBranchCodeByEmail(String email) throws SQLException {
    String query = "SELECT Branch_Code FROM Cashier WHERE email = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {System.out.println("Branch code in getBranchCode function: "+rs.getString("Branch_Code"));
            return rs.getString("Branch_Code");
        } else {
            throw new SQLException("Branch_Code not found for the given email.");
        }
    }
}


    
}
