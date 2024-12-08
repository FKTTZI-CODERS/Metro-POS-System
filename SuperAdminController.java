
package metropos.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import metropos.model.SuperAdmin;
import metropos.model.User;

public class SuperAdminController 
{String bcode;
String bname;
String bcity;
String bphone;
String baddress;
SuperAdmin s;
User u;
public SuperAdminController()
{
    s= new SuperAdmin();
}
 public void createBranch(String code, String name, String city , String phone , String address) throws SQLException  
 {if(code.isEmpty()||name.isEmpty()||city.isEmpty()||phone.isEmpty()||address.isEmpty())
 {
     JOptionPane.showMessageDialog(null,"Please fill in all blank fields");
        return;
 }
 else if(!phone.matches("\\d{11}"))
 {
     JOptionPane.showMessageDialog(null,"Phone number must be exactly 11 digits!");
        return;
 }
 else
 {
   if(s.createBranch(code, name, city, phone, address))
   {
         JOptionPane.showMessageDialog(null,"Branch Created Successfully!");
     }
   else
   {JOptionPane.showMessageDialog(null,"Failed to create the branch!");
       
   }
     
 }
}
 public ArrayList<String> getAvailableCodes() throws SQLException
 {
     return s.getAvailableCodes();
 }
 public void createBranchManager(String name, String email,String salary,String code) throws SQLException
 {if(name.isEmpty()||email.isEmpty()|| salary.isEmpty()||code.isEmpty())
 {JOptionPane.showMessageDialog(null,"Please fill in all blank fields");
        return;
     
 }
 else
 {
   if(s.createBranchManager(name, email, salary, code))
   {u=new User();
        JOptionPane.showMessageDialog(null,"Branch Manager Created Successfully!");
        u.increaseEmployeeCount(code);
        
   }
    else
   {JOptionPane.showMessageDialog(null,"Failed to create the branch Manager!");
       
   }
     
 }
}
 public boolean viewReports(String code) throws SQLException
 {if(code== null || code.trim().isEmpty())
 {
     JOptionPane.showMessageDialog(null, "Branch code cannot be empty!");
     return false;
 }
     boolean branchExists= s.checkBranchCode(code);
 if(branchExists)
 {
     return true;
 }
     return false;
 }
 public Object[][]getProfit(String code,String duration,String start,String end) throws SQLException
 {if (!duration.equalsIgnoreCase("Today") 
        && !duration.equalsIgnoreCase("Weekly") 
        && !duration.equalsIgnoreCase("Monthly") 
        && !duration.equalsIgnoreCase("Yearly")&& !duration.equalsIgnoreCase("Specific Range")) {
        JOptionPane.showMessageDialog(null, "Invalid duration type!");
        return new Object[0][0];
    }
     
 
        return s.getProfit(code, duration,start,end);
    
    
     
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
        return s.getRemainingStockData(code, duration);
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
        return s.getSalesData(code, duration);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error fetching sales data: " + e.getMessage());
        return new Object[0][0];
    }
         
}
}

