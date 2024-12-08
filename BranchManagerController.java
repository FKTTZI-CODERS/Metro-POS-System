package metropos.controller;

import metropos.model.BranchManager;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import metropos.model.User;

public class BranchManagerController {
    
    private BranchManager branchManager;
    private User u;

    public BranchManagerController() {
        branchManager = new BranchManager();
        u=new User();
    }

    public void addCashier( String name, String email, String salary, String branchCode) throws SQLException {
        if ( name.isEmpty() || email.isEmpty() || salary.isEmpty() || branchCode.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all blank fields");
            return;
        }
        if (branchManager.addCashier( name, email,salary, branchCode)) {
            JOptionPane.showMessageDialog(null, "Cashier added successfully!");
             u.increaseEmployeeCount(branchCode);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add cashier!");
        }
    }

    public void addDataEntryOperator(String employeeNo, String name, String email, String salary, String branchCode) throws SQLException {
        if (employeeNo.isEmpty() || name.isEmpty() || email.isEmpty()  || salary.isEmpty() || branchCode.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all blank fields");
            return;
        }
        if (branchManager.addDataEntryOperator(employeeNo, name, email,  salary, branchCode)) {
            JOptionPane.showMessageDialog(null, "Data Entry Operator added successfully!");
            u.increaseEmployeeCount(branchCode);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add Data Entry Operator!");
        }
    }
     public boolean viewReports(String code) throws SQLException
 {if(code== null || code.trim().isEmpty())
 {
     JOptionPane.showMessageDialog(null, "Branch code cannot be empty!");
     return false;
 }
 return true;
 }
 public Object[][]getProfit(String code,String duration) throws SQLException
 {if (!duration.equalsIgnoreCase("Today") 
        && !duration.equalsIgnoreCase("Weekly") 
        && !duration.equalsIgnoreCase("Monthly") 
        && !duration.equalsIgnoreCase("Yearly")) {
        JOptionPane.showMessageDialog(null, "Invalid duration type!");
        return new Object[0][0];
    }
     
 
        return branchManager.getProfit(code, duration);
    
    
     
 }
 public Object[][]getRemainingStockData(String code,String duration)
 {
     if (!duration.equalsIgnoreCase("Today") 
        && !duration.equalsIgnoreCase("Weekly") 
        && !duration.equalsIgnoreCase("Monthly") 
        && !duration.equalsIgnoreCase("Yearly")) {
        JOptionPane.showMessageDialog(null, "Invalid duration type!");
        return new Object[0][0];
    }
     
 try {
        return branchManager.getRemainingStockData(code, duration);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error fetching Stock data: " + e.getMessage());
        return new Object[0][0];
    }
 }
 public Object[][] getSalesData(String code,String duration)
 { if (!duration.equalsIgnoreCase("Today") 
        && !duration.equalsIgnoreCase("Weekly") 
        && !duration.equalsIgnoreCase("Monthly") 
        && !duration.equalsIgnoreCase("Yearly")) {
        JOptionPane.showMessageDialog(null, "Invalid duration type!");
        return new Object[0][0];
    }
     
 try {
        return branchManager.getSalesData(code, duration);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error fetching sales data: " + e.getMessage());
        return new Object[0][0];
    }
         
}
}
