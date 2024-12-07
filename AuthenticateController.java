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
    String email;

    public String getEmail() {System.out.println(email);
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
    CashierController cc;
    BranchManagerDashboardView bm;
    DEODashboardView deo; 

    public AuthenticateController() {
       this.conn = DBConnection.getInstance().getConnection();

      
        u = new User();
        cc= new CashierController();
    }

    public void login(String e, String pass, String t) throws SQLException {
        if (e.isEmpty() || pass.isEmpty() || t.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all blank fields");
            return;
        } else {
            if (t.equalsIgnoreCase("Super Admin")) {
                if (u.login(e, pass, t)) {
                    JOptionPane.showMessageDialog(null, "Login successful");
                    sa = new SuperAdminDashboardView();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Login!");
                    return;
                }
            } else if (t.equals("Branch Manager")) {
                if (u.login(e, pass, t)) {
                    JOptionPane.showMessageDialog(null, "Login successful");
                    isFirstLogin(e, "Branch Manager");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Login!");
                    return;
                }
            } else if (t.equals("Data Entry Operator")) {
                if (u.login(e, pass, t)) {
                    JOptionPane.showMessageDialog(null, "Login successful");
                    isFirstLogin(e, "Data Entry Operator"); 
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Login!");
                    return;
                }
            } else { // Cashier role
                if (u.login(e, pass, t)) {cc.setEmail(e);
                    JOptionPane.showMessageDialog(null, "Login successful");
                    isFirstLogin(e, "Cashier");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Login!");
                    return;
                }
            }
        }
    }

    private void isFirstLogin(String email, String type) throws SQLException {
        if (email.isEmpty() || type.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No field can be empty");
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