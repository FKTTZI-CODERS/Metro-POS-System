package metropos.model;

import java.sql.*;
import metropos.controller.AuthenticateController;

public class DataEntryOperator extends User {
AuthenticateController a;
    public ResultSet getVendors() throws SQLException {
        String query = "SELECT * FROM Vendor";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

   
   public ResultSet getProducts() throws SQLException {
    String query = "SELECT p.Product_id, p.Product_Name, p.Category, p.Original_Price, p.Sale_Price, " +
                   "p.Price_per_Unit, p.Price_per_Carton, p.Stock_Quantity, v.Vendor_Name " +
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

    public boolean addProduct(int vendorId, String productName, String category, int quantity,
                               double originalPrice, double salePrice, double pricePerUnit, 
                               double pricePerCarton) throws SQLException {a= new AuthenticateController();
        String query = "INSERT INTO Product (Product_Name, Category, Original_Price,Stock_Quantity, Sale_Price, " +
                       "Price_per_Unit, Price_per_Carton, Vendor_id) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        String q2="INSERT INTO stock (Branch_Code,Product_id, Category,Quantity_Remaining) VALUES (?, (SELECT Product_id FROM Product WHERE Product_Name = ?), ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        PreparedStatement ps2 = conn.prepareStatement(q2);
        ps.setString(1, productName);
        ps.setString(2, category);
        ps.setDouble(3, originalPrice);
         ps.setInt(4, quantity);
        ps.setDouble(5, salePrice);
        ps.setDouble(6, pricePerUnit);
        ps.setDouble(7, pricePerCarton);
        ps.setInt(8, vendorId);  // Use Vendor_id as foreign key
        ps2.setString(1,a.getCode());
        ps2.setString(2, productName);
        ps2.setString(3,category);
        ps2.setInt(4,quantity);
        ps2.executeUpdate();
        return ps.executeUpdate() > 0;
    }
public void stockupdate(String product,String category,int quantity) throws SQLException
{
      String q2="INSERT INTO stock (Branch_Code,Product_id, Category,Quantity_Remaining) VALUES (?, (SELECT Product_id FROM Product WHERE Product_Name = ?), ?, ?)";
       PreparedStatement ps2 = conn.prepareStatement(q2);
       ps2.setString(1,a.getCode());
        ps2.setString(2, product);
        ps2.setString(3,category);
        ps2.setInt(4,quantity);
        ps2.executeUpdate();
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