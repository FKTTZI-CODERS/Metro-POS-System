
package metropos.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import metropos.model.SuperAdmin;

public class SuperAdminController 
{String bcode;
String bname;
String bcity;
String bphone;
String baddress;
SuperAdmin s;
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
   {
        JOptionPane.showMessageDialog(null,"Branch Manager Created Successfully!");
        
   }
    else
   {JOptionPane.showMessageDialog(null,"Failed to create the branch Manager!");
       
   }
     
 }
}
}
