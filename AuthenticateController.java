package metropos.controller;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import metropos.model.DBConnection;
import metropos.model.User;
import metropos.view.BranchManagerDashboardView;
import metropos.view.DEODashboardView; 
import metropos.view.LoginView;
import metropos.view.SuperAdminDashboardView;
import metropos.view.CashierDashboardView;

public class AuthenticateController {
    private static String email;
private static String code="";
    public static synchronized String getEmail() {System.out.println("In get Email function "+email);
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    String password;
    String type;
    LoginView l;
    Connection conn;

    User u;
    SuperAdminDashboardView sa;
    CashierDashboardView c;
  
    BranchManagerDashboardView bm;
    DEODashboardView deo; 

    public AuthenticateController() {
       this.conn = DBConnection.getInstance().getConnection();

      
        u = new User();
        
    }

    public void login(String e, String pass, String t) throws SQLException {
        if (e.isEmpty() || pass.isEmpty() || t.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all blank fields");
            return;
        }
        if (!e.endsWith("@gmail.com")) {
        JOptionPane.showMessageDialog(null, "Email must end with @gmail.com");
        return;
    }
        else {
            if (t.equalsIgnoreCase("Super Admin")) {
                if (u.login(e, pass, t)) {
                    JOptionPane.showMessageDialog(null, "Login successful");
                    sa = new SuperAdminDashboardView();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Login!");
                    return;
                }
            } else if (t.equals("Branch Manager")) {
                if (u.login(e, pass, t)) {setBranchEmail(e);
                    JOptionPane.showMessageDialog(null, "Login successful");
                    isFirstLogin(e, "Branch Manager");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Login!");
                    return;
                }
            } else if (t.equals("Data Entry Operator")) {
                if (u.login(e, pass, t)) {setDEOEmail(e);
                    JOptionPane.showMessageDialog(null, "Login successful");
                    isFirstLogin(e, "Data Entry Operator"); 
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Login!");
                    return;
                }
            } else { // Cashier role
                if (u.login(e, pass, t)) { setCashierEmail(e);
                    JOptionPane.showMessageDialog(null, "Login successful");
                    isFirstLogin(e, "Cashier");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Login!");
                    return;
                }
            }
        }
    }
public void setBranchEmail(String email) throws SQLException
{
   this.code=(u.getBmBranchCode(email));
    System.out.println("Set:"+code);
    
}
public void setDEOEmail(String email) throws SQLException
{
   this.code=(u.getDEOBranchCode(email));
    System.out.println("Set:"+code);
    
}
public synchronized void setCashierEmail(String email) throws SQLException
{
   this.code=(u.getBranchCodeOfCashier(email));
    System.out.println("Set:"+code);
    
}

    public synchronized String getCode() {System.out.println("Getter: "+code);
        return this.code;
    }

    
    private void isFirstLogin(String email, String type) throws SQLException {
        if (email.isEmpty() || type.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No field can be empty");
            return;
        }
        if (!email.endsWith("@gmail.com")) {
        JOptionPane.showMessageDialog(null, "Email must end with @gmail.com");
        return;
    }
        if (u.isFirstLogin(email, type)) {
            l = new LoginView();
            JOptionPane.showMessageDialog(null, "You must change your password before proceeding.");
            l.changePassScreen(email, type);
        } else {
            if (type.equalsIgnoreCase("Cashier")) {
                c = new CashierDashboardView();
            } else if (type.equalsIgnoreCase("Branch Manager")) {
                bm = new BranchManagerDashboardView();
            } else if (type.equalsIgnoreCase("Data Entry Operator")) {
                deo = new DEODashboardView(); 
            }
        }
    }

    public void changePass(String newpass, String confirmpass, String email, String type) throws SQLException {
        if (newpass.isEmpty() || confirmpass.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all blank fields");
            return;
        }
        if (!newpass.equals(confirmpass)) {
            JOptionPane.showMessageDialog(null, "New Password does not match Confirm password!");
            return;
        } else {
            u.changePass(newpass, email, type);
            JOptionPane.showMessageDialog(null, "Password changed successfully!");
            if (type.equalsIgnoreCase("Cashier")) {
                c = new CashierDashboardView();
            } else if (type.equalsIgnoreCase("Branch Manager")) {
                bm = new BranchManagerDashboardView();
            } else if (type.equalsIgnoreCase("Data Entry Operator")) {
                deo = new DEODashboardView(); 
            }
        }
    }

    public void forgotPass(String role, String email) throws SQLException {
        if (email.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fields cannot be empty!");
            return;
        }
        if (u.emailExists(email, role)) {
            l = new LoginView();
            l.changePassScreen(email, role);
        } else {
            JOptionPane.showMessageDialog(null, "Email not found!");
            return;
        }
    }
}