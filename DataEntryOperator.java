package metropos.model;

import java.sql.*;

public class DataEntryOperator extends User {

    public ResultSet getVendors() throws SQLException {
        String query = "SELECT * FROM Vendor";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

   
    public ResultSet getProducts() throws SQLException {
        String query = "SELECT p.Product_Name, p.Category, p.Sale_Price, v.Vendor_Name " +
                       "FROM Product p " +
                       "JOIN Vendor v ON p.Vendor_id = v.Vendor_id";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

   
    public boolean addVendor(String name, String contact, String address) throws SQLException {
        String query = "INSERT INTO Vendor (Vendor_Name, Vendor_Contact, Vendor_Address) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, contact);
        ps.setString(3, address);
        return ps.executeUpdate() > 0;
    }

   
    public boolean updateVendor(int vendorId, String name, String contact, String address) throws SQLException {
        String query = "UPDATE Vendor SET Vendor_Name = ?, Vendor_Contact = ?, Vendor_Address = ? WHERE Vendor_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, contact);
        ps.setString(3, address);
        ps.setInt(4, vendorId);
        return ps.executeUpdate() > 0;
    }

  
    public boolean deleteVendor(int vendorId) throws SQLException {
        String query = "DELETE FROM Vendor WHERE Vendor_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, vendorId);
        return ps.executeUpdate() > 0;
    }

    public boolean addProduct(int vendorId, String productName, String category, 
                               double originalPrice, double salePrice, double pricePerUnit, 
                               double pricePerCarton) throws SQLException {
        String query = "INSERT INTO Product (Product_Name, Category, Original_Price, Sale_Price, " +
                       "Price_per_Unit, Price_per_Carton, Vendor_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, productName);
        ps.setString(2, category);
        ps.setDouble(3, originalPrice);
        ps.setDouble(4, salePrice);
        ps.setDouble(5, pricePerUnit);
        ps.setDouble(6, pricePerCarton);
        ps.setInt(7, vendorId);  // Use Vendor_id as foreign key
        return ps.executeUpdate() > 0;
    }

   
    public boolean updateProduct(int productId, String productName, String category, 
                                  double originalPrice, double salePrice, double pricePerUnit, 
                                  double pricePerCarton) throws SQLException {
        String query = "UPDATE Product SET Product_Name = ?, Category = ?, Original_Price = ?, " +
                       "Sale_Price = ?, Price_per_Unit = ?, Price_per_Carton = ? WHERE Product_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, productName);
        ps.setString(2, category);
        ps.setDouble(3, originalPrice);
        ps.setDouble(4, salePrice);
        ps.setDouble(5, pricePerUnit);
        ps.setDouble(6, pricePerCarton);
        ps.setInt(7, productId);
        return ps.executeUpdate() > 0;
    }

   
    public boolean deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM Product WHERE Product_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, productId);
        return ps.executeUpdate() > 0;
    }
}
