
package metropos.controller;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import metropos.model.DBConnection;
import metropos.model.User;
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
      else if(t.equals("BranchManager"))
      {
            if(u.login(e, pass, t))
          {
              JOptionPane.showMessageDialog(null,"Login successFull");
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
}
