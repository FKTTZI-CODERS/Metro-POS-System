
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
                  "city VARCHAR(55) NOT NULL, " +
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
                  "name VARCHAR(55), " +
                  "email VARCHAR(255) UNIQUE, " +
                  "password VARCHAR(255) DEFAULT 'Password_123', " +
                  "Branch_Code VARCHAR(10), " +
                  "salary DECIMAL(10,2), " +
                  "FOREIGN KEY (Branch_Code) REFERENCES Branch(Branch_Code) ON DELETE SET NULL, " +
                  "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
    s = conn.createStatement();
    s.executeUpdate(sql3);
    
    // Cashier table
    String sql4 = "CREATE TABLE IF NOT EXISTS Cashier(" +
                  "Cashier_id INT AUTO_INCREMENT PRIMARY KEY, " +
                  "name VARCHAR(55) NOT NULL UNIQUE, " +
                  "email VARCHAR(55) UNIQUE, " +
                  "password VARCHAR(255) DEFAULT 'Password_123', " +
                  "Branch_Code VARCHAR(10), " +
                  "salary DECIMAL(10,2), " +
                  "FOREIGN KEY (Branch_Code) REFERENCES Branch(Branch_Code) ON DELETE SET NULL, " +
                  "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
    s = conn.createStatement();
    s.executeUpdate(sql4);
    
    // DataEntryOperator table
    String sql5 = "CREATE TABLE IF NOT EXISTS DataEntryOperator(" +
                  "DEO_Code VARCHAR(10) PRIMARY KEY, " +
                  "name VARCHAR(55) NOT NULL UNIQUE, " +
                  "email VARCHAR(55) UNIQUE, " +
                  "password VARCHAR(255) DEFAULT 'Password_123', " +
                  "Branch_Code VARCHAR(10), " +
                  "salary DECIMAL(10,2), " +
                  "FOREIGN KEY (Branch_Code) REFERENCES Branch(Branch_Code) ON DELETE SET NULL, " +
                  "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
    s = conn.createStatement();
    s.executeUpdate(sql5);
    
     //vendor table
     String sql9="CREATE TABLE IF NOT EXISTS Vendor ("+
                "Vendor_id INT AUTO_INCREMENT PRIMARY KEY,"
            + " Vendor_Name VARCHAR(255) NOT NULL,"
            +"Vendor_Contact VARCHAR(255) NOT NULL,"+
              "Vendor_Address VARCHAR(255) NOT NULL)";
    s=conn.createStatement();
    s.executeUpdate(sql9);
    
     //Product table
      String sql8="CREATE TABLE IF NOT EXISTS Product ("+
                "Product_id INT AUTO_INCREMENT PRIMARY KEY,"
            + "Product_Name VARCHAR(255) NOT NULL,"
            +"Category VARCHAR(255) NOT NULL,"+ "Original_Price DECIMAL(10,2) NOT NULL,"+
            "Sale_Price DECIMAL(10,2) NOT NULL," +"Price_per_Unit DECIMAL(10,2) NOT NULL," +
              "Price_per_Carton DECIMAL(10,2) NOT NULL," + "Stock_Quantity INT DEFAULT 0," + "Vendor_id INT,"+
            "FOREIGN KEY (Vendor_id) REFERENCES Vendor(Vendor_id))";
    s=conn.createStatement();
    s.executeUpdate(sql8);
    
    //sales table
    String sql6="CREATE TABLE IF NOT EXISTS Sales ("+
                "sales_id INT AUTO_INCREMENT PRIMARY KEY, "+
            "Product_id int NOT NULL, "+
            "Branch_code VARCHAR(10) NOT NULL, " +
            "Quantity_Sold INT NOT NULL CHECK(Quantity_Sold >=0),"+
            "Total_Price DECIMAL (10,2) NOT NULL CHECK(Total_Price >=0), " +
            "Sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "+
            "FOREIGN KEY (Branch_Code) REFERENCES Branch(Branch_Code) ON DELETE CASCADE,"+"FOREIGN KEY (Product_id) REFERENCES Product(Product_id) ON DELETE CASCADE)";
    s=conn.createStatement();
    s.executeUpdate(sql6);
    
    //stock table
    String sql7="CREATE TABLE IF NOT EXISTS Stock ("+
                "stock_id INT AUTO_INCREMENT PRIMARY KEY,"
            + " Branch_code VARCHAR(10) NOT NULL,"+"Product_id INT NOT NULL, "
            +"Category VARCHAR(255) NOT NULL,"+
            "Quantity_Remaining INT NOT NULL," +
            " Last_Updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "+
            "FOREIGN KEY (Branch_Code) REFERENCES Branch(Branch_Code) ON DELETE CASCADE," +"FOREIGN KEY (Product_id) REFERENCES Product(Product_id) ON DELETE CASCADE)";
    s=conn.createStatement();
    s.executeUpdate(sql7);
    
    
}

        

public boolean login(String email , String pass, String type) throws SQLException
{
    if(type.equals("Super Admin"))
    {createTable();
        String query= "Select * from superAdmin where email = ? AND password = ?";
         PreparedStatement ps= conn.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, pass);
        ResultSet res = ps.executeQuery();
        return res.next();  
    }
    else if (type.equals("Branch Manager"))
    {
        String query= "Select * from BranchManager where email = ? AND password = ?";
         PreparedStatement ps= conn.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, pass);
        ResultSet res = ps.executeQuery();
        return res.next();  
    }
    else if(type.equals("Data Entry Operator"))
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
public boolean isFirstLogin(String email, String type) throws SQLException {
    String query = "";

    if (type.equalsIgnoreCase("Cashier")) {
        query = "SELECT password FROM cashier WHERE email=?";
    } else if (type.equalsIgnoreCase("Branch Manager")) {
        query = "SELECT password FROM BranchManager WHERE email=?";
    } else if (type.equalsIgnoreCase("Data Entry Operator")) {  // Added DEO condition
        query = "SELECT password FROM DataEntryOperator WHERE email=?";
    }

    PreparedStatement ps = conn.prepareStatement(query);
    ps.setString(1, email);
    ResultSet res = ps.executeQuery();
    if (res.next()) {
        String pass = res.getString("password");
        return "Password_123".equals(pass);
    }

    return false;
}

public void changePass(String newpass,String email,String type) throws SQLException
{String query="";
    if(type.equalsIgnoreCase("Cashier"))
    {query="update cashier set password= ? where email=?";}
    else if(type.equalsIgnoreCase("Branch Manager"))
    {
        query="update BranchManager set password= ? where email=?";
    }
    else if(type.equalsIgnoreCase("Super Admin"))
    {
         query="update superAdmin set password= ? where email=?";
    }
    else
    {
         query="update DataEntryOperator set password= ? where email=?";
    }
    PreparedStatement ps = conn.prepareStatement(query);
    ps.setString(1,newpass);
    ps.setString(2,email);
    ps.executeUpdate();
}
public void increaseEmployeeCount(String bcode) throws SQLException
{
    String q1="select employeeCount from Branch where Branch_Code=?";
    PreparedStatement ps = conn.prepareStatement(q1);
    ps.setString(1,bcode);
    ResultSet rs=ps.executeQuery();
    if(rs.next())
        
    {
        int empcount=rs.getInt("employeeCount");
       empcount++;
       String q2="update Branch set employeeCount=? where Branch_Code=?";
       ps=conn.prepareStatement(q2);
       ps.setInt(1,empcount);
       ps.setString(2,bcode);
       ps.executeUpdate(); 
    }
}
public boolean emailExists(String email,String role) throws SQLException
{String query="";
    if(role.equalsIgnoreCase("Super Admin"))
    {query= "select * from superAdmin where email = ?";}
    else if (role.equalsIgnoreCase("Branch Manager"))
    {query= "select * from BranchManager where email = ?";}
    else if (role.equalsIgnoreCase("Cashier"))
    {query="select * from Cashier where email=?";}
    else
    {
        query="select * from DataEntryOperator where email=?";
    }
    PreparedStatement ps = conn.prepareStatement(query);
    ps.setString(1,email);
    ResultSet rs= ps.executeQuery();
    if(rs.next() && rs.getInt(1)>0)
    {
        return true;
        
    }
    return false;
}


}
