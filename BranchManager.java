package metropos.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BranchManager extends User {
    User u;
    public boolean addCashier( String name, String email, String salary, String branchCode) throws SQLException {
        String query = "INSERT INTO Cashier (name, email, salary, Branch_Code) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
  
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, salary);
        ps.setString(4, branchCode);
        return ps.executeUpdate() > 0;
    }

    public boolean addDataEntryOperator(String deoCode, String name, String email, String salary, String branchCode) throws SQLException {
        String query = "INSERT INTO DataEntryOperator (DEO_Code, name, email, salary, Branch_Code) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, deoCode);
        ps.setString(2, name);
        ps.setString(3, email);
        ps.setString(4, salary);
        ps.setString(5, branchCode);
        return ps.executeUpdate() > 0;
    }
   public Object[][]getProfit(String code,String duration) throws SQLException
    {
        ArrayList<Object[]> getProfit= new ArrayList<>();
        String query="";
        switch(duration)
        {
            case "Today":
                query="SELECT p.Product_Name , SUM(s.Quantity_Sold * s.Total_Price) FROM Sales s JOIN Product p on s.Product_id=p.Product_id WHERE s.Branch_Code=? AND DATE(s.Sale_Date)=CURDATE() GROUP BY p.Product_Name";
                break;
                
            case "Weekly":
                query="SELECT p.Product_Name , SUM(s.Quantity_Sold * s.Total_Price) FROM Sales s JOIN Product p on s.Product_id=p.Product_id WHERE s.Branch_Code=? AND WEEK(s.Sale_Date)=WEEK(CURDATE()) GROUP BY p.Product_Name";
                break;  
                
                 case "Monthly":
                query="SELECT p.Product_Name , SUM(s.Quantity_Sold * s.Total_Price) FROM Sales s JOIN Product p on s.Product_id=p.Product_id WHERE s.Branch_Code=? AND MONTH(s.Sale_Date)=MONTH(CURDATE()) GROUP BY p.Product_Name";
                break;
                
                 case "Yearly":
                query="SELECT p.Product_Name , SUM(s.Quantity_Sold * s.Total_Price) FROM Sales s JOIN Product p on s.Product_id=p.Product_id WHERE s.Branch_Code=? AND YEAR(s.Sale_Date)=YEAR(CURDATE()) GROUP BY p.Product_Name";
                break;
                
                 default:
                     throw new IllegalArgumentException("Invalid duration type: "+duration);
        }
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,code);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String prod_name=rs.getString(1);
            double profit=rs.getDouble(2);
           getProfit.add(new Object[]{prod_name,profit});
        }
        Object[][]profitList= new Object[getProfit.size()][2];
        for(int i=0;i<getProfit.size();i++)
        {
            profitList[i]=getProfit.get(i);
        }
        return profitList;
    }
    public Object[][] getSalesData(String code,String duration) throws SQLException
    {
        ArrayList<Object[]> getSales= new ArrayList<>();
        String query="";
        switch(duration)
        {
            case "Today":
                query="SELECT p.Product_Name , SUM(s.Quantity_Sold), SUM(s.Total_Price)"+ "FROM Sales s JOIN Product p on s.Product_id=p.Product_id WHERE s.Branch_Code=? AND DATE(s.Sale_Date)=CURDATE() GROUP BY p.Product_Name";
                break;
                
            case "Weekly":
                query="SELECT p.Product_Name , SUM(s.Quantity_Sold), SUM(s.Total_Price)"+ "FROM Sales s JOIN Product p on s.Product_id=p.Product_id WHERE s.Branch_Code=? AND WEEK(s.Sale_Date)=WEEK(CURDATE()) GROUP BY p.Product_Name";
                break;  
                
                 case "Monthly":
                query="SELECT p.Product_Name , SUM(s.Quantity_Sold), SUM(s.Total_Price)"+ "FROM Sales s JOIN Product p on s.Product_id=p.Product_id WHERE s.Branch_Code=? AND MONTH(s.Sale_Date)=MONTH(CURDATE()) GROUP BY p.Product_Name";
                break;
                
                 case "Yearly":
                query="SELECT p.Product_Name , SUM(s.Quantity_Sold), SUM(s.Total_Price)"+ "FROM Sales s JOIN Product p on s.Product_id=p.Product_id WHERE s.Branch_Code=? AND YEAR(s.Sale_Date)=YEAR(CURDATE()) GROUP BY p.Product_Name";
                break;
                
                 default:
                     throw new IllegalArgumentException("Invalid duration type: "+duration);
        }
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,code);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String prod_name=rs.getString(1);
            int quantity=rs.getInt(2);
            double totalSales=rs.getDouble(3);
            getSales.add(new Object[]{prod_name,quantity,totalSales});
        }
        Object[][]sale= new Object[getSales.size()][3];
        for(int i=0;i<getSales.size();i++)
        {
            sale[i]=getSales.get(i);
        }
        return sale;
    }
    public synchronized String getbranchcode() throws SQLException
    {u= new User();
    String bmail=u.getEmail();
    String query="select Branch_Code from BranchManager where email=?";
     PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,bmail);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            return rs.getString("Branch_Code");
        }
        return null;
    }
    public Object[][] getRemainingStockData(String code, String duration) throws SQLException
    {
         ArrayList<Object[]> getremainingstock= new ArrayList<>();
        String query="";
        switch(duration)
        {
            case "Today":
                query="SELECT p.Product_Name , s.Category, SUM(s.Quantity_Remaining) FROM Stock s JOIN Product p on s.Product_id=p.Product_id WHERE s.Branch_Code=? AND DATE(s.Last_Updated)=CURDATE() GROUP BY p.Product_Name, s.Category";
                break;
                
            case "Weekly":
                query="SELECT p.Product_Name , s.Category, SUM(s.Quantity_Remaining) FROM Stock s JOIN Product p on s.Product_id=p.Product_id WHERE s.Branch_Code=? AND WEEK(s.Last_Updated)=WEEK(CURDATE()) GROUP BY p.Product_Name, s.Category";
                break;  
                
                 case "Monthly":
               query="SELECT p.Product_Name , s.Category, SUM(s.Quantity_Remaining) FROM Stock WHERE s.Branch_Code=? AND MONTH(s.Last_Updated)=MONTH(CURDATE()) GROUP BY p.Product_Name, s.Category";
                break;
                
                 case "Yearly":
                 query="SELECT p.Product_Name , s.Category, SUM(s.Quantity_Remaining) FROM Stock s JOIN Product p on s.Product_id=p.Product_id WHERE s.Branch_Code=? AND YEAR(s.Last_Updated)=YEAR(CURDATE()) GROUP BY p.Product_Name, s.Category";
                break;
                
                 default:
                     throw new IllegalArgumentException("Invalid duration type: "+duration);
        }
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,code);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String prod_name=rs.getString(1);
            String category=rs.getString(2);
            int remainingQuantity=rs.getInt(3);
            getremainingstock.add(new Object[]{prod_name,category,remainingQuantity});
        }
        Object[][]stock= new Object[getremainingstock.size()][3];
        for(int i=0;i<getremainingstock.size();i++)
        {
            stock[i]=getremainingstock.get(i);
        }
        return stock;
    }          
}
