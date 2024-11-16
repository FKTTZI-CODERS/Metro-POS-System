

package metropos.controller;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import metropos.model.DBConnection;
import metropos.model.User;
import metropos.view.LoginView;
import metropos.view.SuperAdminDashboardView;
import metropos.view.BranchManagerDashboardView;
import metropos.view.ChangePasswordView;
import java.sql.PreparedStatement;

public class AuthenticateController 
{String email ;
String password ;
String type;
LoginView l;
Connection conn;
DBConnection db;
User u;
SuperAdminDashboardView sa;
BranchManagerDashboardView bm;
public AuthenticateController()
{db= new DBConnection();
    this.conn=(Connection) db.getConnection();
    u= new User();

}

    public void login(String e , String pass, String t) throws SQLException
    {if(e.isEmpty() || pass.isEmpty())
    {
        JOptionPane.showMessageDialog(null,"Please fill in all blank fields");
        return;
           
    }
    else
    {
      if(t.equals("SuperAdmin"))
      {
          if(u.login(e, pass, t))
          {
              JOptionPane.showMessageDialog(null,"Login successFull");
              sa= new SuperAdminDashboardView();
          }
          
      }
      else if(t.equals("BranchManager")){
       boolean isFirstLogin = u.checkFirstLogin(e); 
                    if (isFirstLogin) {
                        ChangePasswordView changePasswordView = new ChangePasswordView(e);
                    } else {
                        bm = new BranchManagerDashboardView();
                    }
                }
      else if(t.equals("DataEntryOperator"))
      {
            if(u.login(e, pass, t))
          {
              JOptionPane.showMessageDialog(null,"Login successFull");
          }
      }
      else
      {
           if(u.login(e, pass, t))
          {
              JOptionPane.showMessageDialog(null,"Login successFull");
          }
      }
    }
        
    }
    public boolean updatePassword(String email, String newPassword) throws SQLException {
    String query = "UPDATE BranchManager SET password = ?, firstLogin = FALSE WHERE email = ?";
    PreparedStatement ps = conn.prepareStatement(query);
    ps.setString(1, newPassword);
    ps.setString(2, email);
    
    int rowsAffected = ps.executeUpdate();
    
    return rowsAffected > 0; //successfully updated password
}

}
