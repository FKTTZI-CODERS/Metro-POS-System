
package metropos.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

 public   class User 
{protected String userID;
Connection conn;
DBConnection db;
    protected String name;
    protected String email;
    protected String password;
    protected String type;
protected Double salary;
 public User() {
     db= new DBConnection();
        this.conn = (Connection) db.getConnection();
    }
public User(String id , String name , String email, String pass, String type,Double salary)
{
    this.userID=id;
    this.name=name;
    this.email=email;
    this.password=pass;
    this.type=type;
    this.salary=salary;
}
public void createTable() throws SQLException
{
    // Super Admin table
    String sql1 = "CREATE TABLE IF NOT EXISTS superAdmin( " +
                  "id INT PRIMARY KEY AUTO_INCREMENT, " +
                  "email VARCHAR(50) DEFAULT 'admin@gmail.com', " +
                  "password VARCHAR(255) DEFAULT 'Password_123', " +
                  "CURRENT_STAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
    Statement s = conn.createStatement();
    s.executeUpdate(sql1);
    //default super admin
     String sqlInsertSuperAdmin = "INSERT INTO superAdmin (email, password) "
            + "SELECT 'admin@gmail.com', 'Password_123' "
            + "WHERE NOT EXISTS (SELECT 1 FROM superAdmin WHERE email = 'admin@gmail.com')";
    s.executeUpdate(sqlInsertSuperAdmin);
    
    // Branch table
    String sql2 = "CREATE TABLE IF NOT EXISTS Branch(" +
                  "Branch_Code VARCHAR(10) PRIMARY KEY, " +
                  "name VARCHAR(55) NOT NULL UNIQUE, " +
                  "city VARCHAR(55) NOT NULL UNIQUE, " +
                  "address VARCHAR(55) NOT NULL UNIQUE, " +
                  "phone VARCHAR(11), " +
                  "active BOOLEAN DEFAULT TRUE, " +
                  "employeeCount INT DEFAULT 0, " +
                  "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
    s = conn.createStatement();
    s.executeUpdate(sql2);
    
    // Branch Manager table
    String sql3 = "CREATE TABLE IF NOT EXISTS BranchManager(" +
                  "id INT AUTO_INCREMENT PRIMARY KEY, " +
                  "manager_Code VARCHAR(10) UNIQUE, " +
                  "name VARCHAR(55), " +
                  "email VARCHAR(255) UNIQUE, " +
                  "password VARCHAR(255) DEFAULT 'Password_123', " +
                  "Branch_Code VARCHAR(10), " +
                  "salary DECIMAL(10,2), " +
                  "firstLogin BOOLEAN DEFAULT TRUE, "+
                  "FOREIGN KEY (Branch_Code) REFERENCES Branch(Branch_Code) ON DELETE SET NULL, " +
                  "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
    s = conn.createStatement();
    s.executeUpdate(sql3);
    
    // Cashier table
String sql4 = "CREATE TABLE IF NOT EXISTS Cashier(" +
              "employeeNo INT AUTO_INCREMENT PRIMARY KEY, " +
              "Cashier_Code VARCHAR(10) UNIQUE, " +
              "name VARCHAR(55) NOT NULL, " +
              "email VARCHAR(55) UNIQUE, " +
              "password VARCHAR(255) DEFAULT 'Password_123', " +
              "Branch_Code VARCHAR(10), " +
              "salary DECIMAL(10,2), " +
              "FOREIGN KEY (Branch_Code) REFERENCES Branch(Branch_Code) ON DELETE SET NULL, " +
              "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
s.executeUpdate(sql4);

// DataEntryOperator table
String sql5 = "CREATE TABLE IF NOT EXISTS DataEntryOperator(" +
              "employeeNo INT AUTO_INCREMENT PRIMARY KEY, " +
              "DEO_Code VARCHAR(10) UNIQUE, " +
              "name VARCHAR(55) NOT NULL, " +
              "email VARCHAR(55) UNIQUE, " +
              "password VARCHAR(255) DEFAULT 'Password_123', " +
              "Branch_Code VARCHAR(10), " +
              "salary DECIMAL(10,2), " +
              "FOREIGN KEY (Branch_Code) REFERENCES Branch(Branch_Code) ON DELETE SET NULL, " +
              "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
s.executeUpdate(sql5);


}

        

public boolean login(String email , String pass, String type) throws SQLException
{
    if(type.equals("SuperAdmin"))
    {createTable();
        String query= "Select * from superAdmin where email = ? AND password = ?";
         PreparedStatement ps= conn.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, pass);
        ResultSet res = ps.executeQuery();
        return res.next();  
    }
    else if (type.equals("BranchManager"))
    {
        String query= "Select * from BranchManager where email = ? AND password = ?";
         PreparedStatement ps= conn.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, pass);
        ResultSet res = ps.executeQuery();
        return res.next();  
    }
    else if(type.equals("DataEntryOperator"))
    {
        String query= "Select * from DataEntryOperator where email = ? AND password = ?";
         PreparedStatement ps= conn.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, pass);
        ResultSet res = ps.executeQuery();
        return res.next();  
    }
else
    {
        String query= "Select * from cashier where email = ? AND password = ?";
         PreparedStatement ps= conn.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, pass);
        ResultSet res = ps.executeQuery();
        return res.next();  
    }
    
}
public boolean checkFirstLogin(String email) throws SQLException {
    String query = "SELECT firstLogin FROM BranchManager WHERE email = ?";
    PreparedStatement ps = conn.prepareStatement(query);
    ps.setString(1, email);
    ResultSet res = ps.executeQuery();
    
    if (res.next()) {
        return res.getBoolean("firstLogin");
    }
    return false;
}



}
