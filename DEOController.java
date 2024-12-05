package metropos.controller;

import metropos.model.DataEntryOperator;
import java.sql.*;

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
        return model.addVendor(name, contact, address);
    }

    //updating old vendor info
    public boolean updateVendor(int vendorId, String name, String contact, String address) throws SQLException {
        return model.updateVendor(vendorId, name, contact, address);
    }

    //deleting a vendor
    public boolean deleteVendor(int vendorId) throws SQLException {
        return model.deleteVendor(vendorId);
    }

    //adding a new product
    public boolean addProduct(int vendorId, String productName, String category, double originalPrice, 
                               double salePrice, double pricePerUnit, double pricePerCarton) throws SQLException {
        return model.addProduct(vendorId, productName, category, originalPrice, salePrice, pricePerUnit, pricePerCarton);
    }

    //updating an old product's information
    public boolean updateProduct(int productId, String productName, String category, double originalPrice, 
                                  double salePrice, double pricePerUnit, double pricePerCarton) throws SQLException {
        return model.updateProduct(productId, productName, category, originalPrice, salePrice, pricePerUnit, pricePerCarton);
    }

    //deleting a product
    public boolean deleteProduct(int productId) throws SQLException {
        return model.deleteProduct(productId);
    }
}
