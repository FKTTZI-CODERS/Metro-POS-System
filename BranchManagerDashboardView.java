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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import metropos.controller.AuthenticateController;
import metropos.controller.BranchManagerController;

public class BranchManagerDashboardView extends JFrame {
    private JButton addCashierButton, addDataEntryOperatorButton, cancelButton,viewR;
    private BranchManagerController controller;

    public BranchManagerDashboardView() {
        controller = new BranchManagerController();
        setTitle("Branch Manager Dashboard");
        init();
        setBounds(0, 0, 500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void init() {
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
viewR= new JButton("View Reports");
        JPanel buttonPanel = new JPanel(new GridLayout(3, 0));
        buttonPanel.add(addCashierButton);
        buttonPanel.add(addDataEntryOperatorButton);
        buttonPanel.add(viewR);
        setLayout(new BorderLayout());
        add(new JLabel("--- Branch Manager Dashboard ---"), BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(cancelButton, BorderLayout.SOUTH);
         viewR.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {   try {
               viewReports();
               } catch (SQLException ex) {
                   Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
               }
               
           }
       });
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
               
                String name = nameField.getText();
                String email = emailField.getText();
                String salary = salaryField.getText();
                String branchCode = branchCodeField.getText();

                try {
                    controller.addCashier(name, email, salary, branchCode);
                } catch (SQLException ex) {
                    Logger.getLogger(BranchManagerDashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                cashierFrame.dispose();
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
 private void viewReports() throws SQLException
    { AuthenticateController a= new AuthenticateController();
        String code=a.getCode();
        if(controller.viewReports(code))
        {reportDuration(code);
            
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Branch code does not exists!");
        }
    }
 private void reportDuration(String code)
    {dispose();
        JFrame f= new JFrame("Reports Dashboard");
        f.setBounds(0,0,500,600);
        f.setLayout(new BorderLayout());
        JPanel p1= new JPanel(new GridLayout(4,1));
        JButton today ,weekly, monthly, yearly;
        today= new JButton("Today");
        weekly= new JButton("Weekly");
        monthly=new JButton("Monthly");
        yearly=new JButton("Yearly");
        p1.add(today);
        p1.add(weekly);
        p1.add(monthly);
        p1.add(yearly);
        JLabel displaytxt=new JLabel("Report Time Menu");
        f.add(displaytxt,BorderLayout.NORTH);
        f.add(p1,BorderLayout.CENTER);
        
        
        today.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {reportType(code, "Today");}
       });
         weekly.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {reportType(code, "Weekly");}
       });
         monthly.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {reportType(code, "Monthly");}
       });
         yearly.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {reportType(code, "Yearly");}
       });
         f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    public void reportType(String code, String duration)
    {dispose();
        JFrame f= new JFrame("Reports Type");
        f.setBounds(0,0,500,600);
        f.setLayout(new BorderLayout());
        JPanel p1= new JPanel(new GridLayout(3,1));
        JButton Sales, RemainingStock, Profit;
        
       Sales= new JButton("Sales");
        RemainingStock= new JButton("Remaining Stock");
        Profit=new JButton("Profit");
        p1.add(Sales);
        p1.add(RemainingStock);
        p1.add(Profit);
        JLabel displaytxt=new JLabel("Report Type Menu");
        f.add(displaytxt,BorderLayout.NORTH);
        f.add(p1,BorderLayout.CENTER);
        
        
        Profit.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {   try {
               profitReport(code, "Today");
               } catch (SQLException ex) {
                   Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
               }
}
       });
        RemainingStock.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {remainingstockReport(code,duration);}
       });
        Sales.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {salesReport(code, duration);}
       });
         f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
        
    }
    private void profitReport(String code, String duration) throws SQLException
    {dispose();
    JFrame f= new JFrame(duration+" Profit Report");
    f.setBounds(0,0,500,600);
    f.setLayout(new BorderLayout());
   JLabel displayMsg= new JLabel("Profit Report for Branch: "+ code + " ( "+duration+" ) ",JLabel.CENTER);
    f.add(displayMsg,BorderLayout.NORTH);
String[] profitreport={"Product Name", "Profit"};
Object[][] profit= controller.getProfit(code,duration);
JTable t= new JTable(profit,profitreport);
JScrollPane scrollpane= new JScrollPane(t);
f.add(scrollpane,BorderLayout.CENTER);

JButton back = new JButton("Back");
back.addActionListener(new ActionListener()
{
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        reportType(code,duration);
        f.dispose();
    }
    });
f.add(back,BorderLayout.SOUTH);
f.setDefaultCloseOperation(EXIT_ON_CLOSE);
f.setVisible(true); 
}
    private void remainingstockReport(String code,String duration)
    {dispose();
    JFrame f= new JFrame(duration+" Remaining Stock Report");
    f.setBounds(0,0,500,600);
    f.setLayout(new BorderLayout());
   JLabel displayMsg= new JLabel("Remaining Stock Report for Branch: "+ code + " ( "+duration+" ) ",JLabel.CENTER);
    f.add(displayMsg,BorderLayout.NORTH);
String[] report={"Product Name", "Category", "Remaining Quantity"};
Object[][] stock= controller.getRemainingStockData(code,duration);
JTable t= new JTable(stock,report);
JScrollPane scrollpane= new JScrollPane(t);
f.add(scrollpane,BorderLayout.CENTER);

JButton back = new JButton("Back");
back.addActionListener(new ActionListener()
{
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        reportType(code,duration);
        f.dispose();
    }
    });
f.add(back,BorderLayout.SOUTH);
f.setDefaultCloseOperation(EXIT_ON_CLOSE);
f.setVisible(true); 
        
    }
    private void salesReport(String code, String duration)
    {dispose();
    JFrame f= new JFrame(duration+" Sales Report");
    f.setBounds(0,0,500,600);
    f.setLayout(new BorderLayout());
   JLabel displayMsg= new JLabel("Sales Report for Branch: "+ code + " ( "+duration+" ) ",JLabel.CENTER);
    f.add(displayMsg,BorderLayout.NORTH);
String[] report={"Product Name", "Quantity Sold", "Total Sales"};
Object[][] sales=controller.getSalesData(code,duration);
JTable t= new JTable(sales,report);
JScrollPane scrollpane= new JScrollPane(t);
f.add(scrollpane,BorderLayout.CENTER);

JButton back = new JButton("Back");
back.addActionListener(new ActionListener()
{
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        reportType(code,duration);
        f.dispose();
    }
    });
f.add(back,BorderLayout.SOUTH);
f.setDefaultCloseOperation(EXIT_ON_CLOSE);
f.setVisible(true);   
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
                } catch (SQLException ex) {
                    Logger.getLogger(BranchManagerDashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                deoFrame.dispose();
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
