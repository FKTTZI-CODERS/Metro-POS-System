package metropos.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BranchManager extends User {
    
    public boolean addCashier(String cashierCode, String name, String email, String salary, String branchCode) throws SQLException {
        String query = "INSERT INTO Cashier (Cashier_Code, name, email, salary, Branch_Code) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, cashierCode);
        ps.setString(2, name);
        ps.setString(3, email);
        ps.setString(4, salary);
        ps.setString(5, branchCode);
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
}
