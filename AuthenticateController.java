
package metropos.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import metropos.model.DBConnection;
import metropos.model.User;
import metropos.view.BranchManagerDashboardView;
import metropos.view.CashierDashboardView;
import metropos.view.LoginView;
import metropos.view.SuperAdminDashboardView;

public class AuthenticateController 
{String email ;
String password ;
String type;
LoginView l;
Connection conn;
DBConnection db;
User u;
SuperAdminDashboardView sa;
CashierDashboardView c;
BranchManagerDashboardView bm;

public AuthenticateController()
{db= new DBConnection();
    this.conn=(Connection) db.getConnection();
    u= new User();
  

}

    public void login(String e , String pass, String t) throws SQLException
    {if(e.isEmpty()||pass.isEmpty()|| t.isEmpty())
    {
        JOptionPane.showMessageDialog(null,"Please fill in all blank fields");
        return;
           
    }
    else
    {
      if(t.equalsIgnoreCase("Super Admin"))
      {
          if(u.login(e, pass, t))
          {
              JOptionPane.showMessageDialog(null,"Login successFull");
              sa= new SuperAdminDashboardView();
          }
          else
          {JOptionPane.showMessageDialog(null,"Failed to Login!");
          return;
          }
        }
          
      
      else if(t.equals("Branch Manager"))
      {
            if(u.login(e, pass, t))
          {
              JOptionPane.showMessageDialog(null,"Login successFull");
              isFirstLogin(e,"Branch Manager");
              
          }
           else
           {
               JOptionPane.showMessageDialog(null,"Failed to Login!");
          return;
           }
      }
      else if(t.equals("DataEntryOperator"))
      {
            if(u.login(e, pass, t))
          {
              JOptionPane.showMessageDialog(null,"Login successFull");
              isFirstLogin(e,type);
          }
      }
      else
      {
           if(u.login(e, pass, t))
          {
              JOptionPane.showMessageDialog(null,"Login successFull");
              isFirstLogin(e,"Cashier");
              
          }
           else
           {
               JOptionPane.showMessageDialog(null,"Failed to Login!");
          return;
           }
      }
    }
        
    }
    private void isFirstLogin(String email, String type) throws SQLException
    {if(email.isEmpty()|| type.isEmpty())
    { JOptionPane.showMessageDialog(null,"No field can be empty");
        return;
    }
    if(u.isFirstLogin(email,type))
    {  l= new LoginView();
        JOptionPane.showMessageDialog(null,"You must change your password before proceeding.");
       l.changePassScreen(email,type);
    }
        else
    {if(type.equalsIgnoreCase("Cashier"))
    { c= new CashierDashboardView();}
    else if (type.equalsIgnoreCase("Branch Manager"))
    {
        bm= new BranchManagerDashboardView();
    }
    }
    }
    public void changePass(String newpass,String confirmpass,String email,String type) throws SQLException
    {
        if(newpass.isEmpty() || confirmpass.isEmpty() || email.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Please fill in all blank fields");
        return;
        }
        if(!newpass.equals(confirmpass))
        {
            JOptionPane.showMessageDialog(null,"new Password does not match Confirm password!");
            return;
        
        }
        else
        {
            u.changePass(newpass,email,type);
            JOptionPane.showMessageDialog(null,"Password changed successfully!");
            if(type.equalsIgnoreCase("Cashier"))
            { c= new CashierDashboardView();}
            else if(type.equalsIgnoreCase("Branch Manager"))
            { bm= new BranchManagerDashboardView();}
        }
    }
    public void forgotPass(String role,String email) throws SQLException
    {
        if(email.isEmpty() || role.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Fields cannot be empty!");
            return;
        }
        if(u.emailExists(email,role))
        {l=new LoginView();
                l.changePassScreen(email, role);
            }
        else
        { JOptionPane.showMessageDialog(null,"Email not found!");
        return ;
        }
    }

  
}
