package metropos.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import metropos.controller.BranchManagerController;

public class BranchManagerDashboardView extends JFrame {
    private JButton addCashierButton, addDataEntryOperatorButton, cancelButton;
    private BranchManagerController controller;

    public BranchManagerDashboardView() {
        controller = new BranchManagerController();
        setTitle("Branch Manager Dashboard");
        init();
        setBounds(0, 0, 500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void init() {
        addCashierButton = new JButton("Add Cashier");
        addCashierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addCashier();
                } catch (SQLException ex) {
                    Logger.getLogger(BranchManagerDashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        addDataEntryOperatorButton = new JButton("Add Data Entry Operator");
        addDataEntryOperatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addDataEntryOperator();
                } catch (SQLException ex) {
                    Logger.getLogger(BranchManagerDashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(3, 0));
        buttonPanel.add(addCashierButton);
        buttonPanel.add(addDataEntryOperatorButton);

        setLayout(new BorderLayout());
        add(new JLabel("--- Branch Manager Dashboard ---"), BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(cancelButton, BorderLayout.SOUTH);
    }

    private void addCashier() throws SQLException {
        JFrame cashierFrame = new JFrame("Add Cashier");
        cashierFrame.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JTextField cashierCodeField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField branchCodeField = new JTextField();

        formPanel.add(new JLabel("Cashier Code:"));
        formPanel.add(cashierCodeField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Salary:"));
        formPanel.add(salaryField);
        formPanel.add(new JLabel("Branch Code:"));
        formPanel.add(branchCodeField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton doneButton = new JButton("Done");
        JButton cancelButton = new JButton("Cancel");

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cashierCode = cashierCodeField.getText();
                String name = nameField.getText();
                String email = emailField.getText();
                String salary = salaryField.getText();
                String branchCode = branchCodeField.getText();

                try {
                    controller.addCashier(cashierCode, name, email, salary, branchCode);
                    cashierFrame.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error adding cashier: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cashierFrame.dispose();
            }
        });

        buttonPanel.add(doneButton);
        buttonPanel.add(cancelButton);

        cashierFrame.add(new JLabel("--- Add Cashier ---"), BorderLayout.NORTH);
        cashierFrame.add(formPanel, BorderLayout.CENTER);
        cashierFrame.add(buttonPanel, BorderLayout.SOUTH);

        cashierFrame.setBounds(0, 0, 500, 400);
        cashierFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cashierFrame.setVisible(true);
    }

    private void addDataEntryOperator() throws SQLException {
        JFrame deoFrame = new JFrame("Add Data Entry Operator");
        deoFrame.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JTextField deoCodeField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField branchCodeField = new JTextField();

        formPanel.add(new JLabel("DEO Code:"));
        formPanel.add(deoCodeField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Salary:"));
        formPanel.add(salaryField);
        formPanel.add(new JLabel("Branch Code:"));
        formPanel.add(branchCodeField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton doneButton = new JButton("Done");
        JButton cancelButton = new JButton("Cancel");

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deoCode = deoCodeField.getText();
                String name = nameField.getText();
                String email = emailField.getText();
                String salary = salaryField.getText();
                String branchCode = branchCodeField.getText();

                try {
                    controller.addDataEntryOperator(deoCode, name, email, salary, branchCode);
                    deoFrame.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error adding data entry operator: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deoFrame.dispose();
            }
        });

        buttonPanel.add(doneButton);
        buttonPanel.add(cancelButton);

        deoFrame.add(new JLabel("--- Add Data Entry Operator ---"), BorderLayout.NORTH);
        deoFrame.add(formPanel, BorderLayout.CENTER);
        deoFrame.add(buttonPanel, BorderLayout.SOUTH);

        deoFrame.setBounds(0, 0, 500, 400);
        deoFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        deoFrame.setVisible(true);
    }
}
