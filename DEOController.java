package metropos.controller;

import metropos.model.DataEntryOperator;
import java.sql.*;
import javax.swing.JOptionPane;

public class DEOController {
    private DataEntryOperator model;

    public DEOController() {
        model = new DataEntryOperator();
    }

       // CRUD Operations for Data Entry Operator
    // fetching all the vendor information from the database
    public ResultSet getVendors() throws SQLException {
        return model.getVendors();
    }

    //fetching all the product informatuin from the database
    public ResultSet getProducts() throws SQLException {
        return model.getProducts();
    }

    //adding new vendor
    public boolean addVendor(String name, String contact, String address) throws SQLException {
        if(name.isEmpty() || contact.isEmpty() || address.isEmpty())
    {  JOptionPane.showMessageDialog(null, "Please fill in all blank fields");
            return false;
        
    }
        return model.addVendor(name, contact, address);
    }

    //updating old vendor info
    public boolean updateVendor(int vendorId, String name, String contact, String address) throws SQLException {
        if(vendorId<=0||name.isEmpty() || contact.isEmpty() || address.isEmpty())
    {  JOptionPane.showMessageDialog(null, "Please fill in all blank fields");
            return false;
        
    }
        return model.updateVendor(vendorId, name, contact, address);
    }

    //deleting a vendor
    public boolean deleteVendor(int vendorId) throws SQLException {
        if(vendorId<=0)
        {JOptionPane.showMessageDialog(null, "Cannot delete a Vendor");
            return false;
            
        }
        return model.deleteVendor(vendorId);
    }

    //adding a new product
    public boolean addProduct(int vendorId, String productName, String category,int quantity, double originalPrice, 
                               double salePrice, double pricePerUnit, double pricePerCarton) throws SQLException 
    {if(vendorId<=0 || productName.isEmpty() || category.isEmpty() || quantity <=0 || originalPrice<=0.0 || salePrice<=0.0 || pricePerUnit<=0.0 || pricePerCarton<=0.0)
    {JOptionPane.showMessageDialog(null, "Cannot add a product");
            return false;
        
    }
        return model.addProduct(vendorId, productName, category,quantity, originalPrice, salePrice, pricePerUnit, pricePerCarton);
    }
    public void stockupdate(String product, String category,int quantity) throws SQLException
    {if( product.isEmpty() || category.isEmpty() || quantity <=0)
    {JOptionPane.showMessageDialog(null, "Unable to update the stock");
            return;
        
    }
         model.stockupdate(product,category,quantity);
    }

    //updating an old product's information
    public boolean updateProduct(int productId, String productName, String category, double originalPrice, 
                                  double salePrice, double pricePerUnit, double pricePerCarton) throws SQLException {
        if(productId<=0 || productName.isEmpty() || category.isEmpty()|| originalPrice<=0.0 || salePrice<=0.0 || pricePerUnit<=0.0 || pricePerCarton<=0.0)
    {JOptionPane.showMessageDialog(null, "Cannot update a product");
            return false;
        
    }
        return model.updateProduct(productId, productName, category, originalPrice, salePrice, pricePerUnit, pricePerCarton);
    }

    //deleting a product
    public boolean deleteProduct(int productId) throws SQLException {
        if(productId<=0)
    {JOptionPane.showMessageDialog(null, "Cannot delete a product");
            return false;
        
    }
        return model.deleteProduct(productId);
    }
}