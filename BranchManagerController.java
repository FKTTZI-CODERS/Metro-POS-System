package metropos.controller;

import metropos.model.BranchManager;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import metropos.model.User;

public class BranchManagerController {
    
    private BranchManager branchManager;
    private User u;

    public BranchManagerController() {
        branchManager = new BranchManager();
        u=new User();
    }

    public void addCashier( String name, String email, String salary, String branchCode) throws SQLException {
        if ( name.isEmpty() || email.isEmpty() || salary.isEmpty() || branchCode.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all blank fields");
            return;
        }
        if (branchManager.addCashier( name, email,salary, branchCode)) {
            JOptionPane.showMessageDialog(null, "Cashier added successfully!");
             u.increaseEmployeeCount(branchCode);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add cashier!");
        }
    }

    public void addDataEntryOperator(String employeeNo, String name, String email, String salary, String branchCode) throws SQLException {
        if (employeeNo.isEmpty() || name.isEmpty() || email.isEmpty()  || salary.isEmpty() || branchCode.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all blank fields");
            return;
        }
        if (branchManager.addDataEntryOperator(employeeNo, name, email,  salary, branchCode)) {
            JOptionPane.showMessageDialog(null, "Data Entry Operator added successfully!");
            u.increaseEmployeeCount(branchCode);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add Data Entry Operator!");
        }
    }
}
