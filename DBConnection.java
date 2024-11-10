
package metropos.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConnection {
   Connection con;
   String u,p;
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
         
          String connectionUrl = "jdbc:mysql://localhost:3306/pos";
con = DriverManager.getConnection(connectionUrl,"root","");
if(con!=null)
{
    System.out.println("Connected");
}
else
{
    System.out.println("not connected");
}
        }

        catch(SQLException | ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage() );
        }
        return con;
    
}
    
   
}

