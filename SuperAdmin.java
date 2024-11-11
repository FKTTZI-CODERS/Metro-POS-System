
package metropos.model;

import com.sun.jdi.connect.spi.Connection;
import java.awt.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;


public class SuperAdmin extends User
{
public boolean createBranch(String code, String name, String city, String phone , String address) throws SQLException
    {
        String query = "Insert into Branch(Branch_Code,name,city,address,phone) VALUES (?,?,?,?,?)";
        PreparedStatement ps= conn.prepareStatement(query);
        ps.setString(1,code);
            ps.setString(2,name);
              ps.setString(3,city);
                ps.setString(4,address);
                ps.setString(5, phone);
                   int res  =ps.executeUpdate();
        return res>0;  
              
    }
    public ArrayList<String> getAvailableCodes() throws SQLException
    {ArrayList<String> branchCodes= new ArrayList<>();
    String query="select Branch_Code FROM Branch where Branch_Code NOT IN (select Branch_Code from BranchManager)";
    Statement s= conn.createStatement();
    ResultSet rs= s.executeQuery(query);
    while(rs.next())
    {
        branchCodes.add(rs.getString("Branch_Code"));
    }
    return branchCodes;
        
    }
    public boolean createBranchManager(String name, String email,String salary,String code) throws SQLException
    {String query = "Insert into BranchManager(name,email,Branch_Code,salary) VALUES (?,?,?,?)";
        PreparedStatement ps= conn.prepareStatement(query);
            ps.setString(1,name);
              ps.setString(2,email);
                ps.setString(3,code);
                ps.setString(4, salary);
                   int res  =ps.executeUpdate();
        return res>0;  
        
    }
}
