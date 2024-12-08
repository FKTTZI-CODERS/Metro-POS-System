package metropos.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import metropos.controller.AuthenticateController;
import metropos.controller.BranchManagerController;

public class BranchManagerDashboardView extends JFrame {
    private JButton addCashierButton, addDataEntryOperatorButton, cancelButton, viewR;
    private BranchManagerController controller;

       public BranchManagerDashboardView() {
        controller = new BranchManagerController();
        setTitle("Branch Manager Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       
        Font headerFont = new Font("Poppins", Font.BOLD, 28);
        Font buttonFont = new Font("Poppins", Font.PLAIN, 16);  
        Color bgColor = Color.WHITE;
        Color headerColor = new Color(38, 70, 83); 
        Color buttonColor = new Color(108, 167, 255); 
        Color buttonBoxColor = new Color(240, 248, 255); 
        Color lightBlue = new Color(227, 238, 254); 

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);

        JLabel headerLabel = new JLabel("Branch Manager Dashboard", JLabel.CENTER);
        headerLabel.setFont(headerFont);
        headerLabel.setForeground(headerColor);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

       
        JPanel buttonBox = new JPanel();
        buttonBox.setLayout(new GridBagLayout()); 
        buttonBox.setBackground(buttonBoxColor);
        buttonBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.anchor = GridBagConstraints.CENTER;

        
        addCashierButton = createStyledButton("Add Cashier", buttonFont, buttonColor);
        addDataEntryOperatorButton = createStyledButton("Add Data Entry Operator", buttonFont, buttonColor);
        viewR = createStyledButton("View Reports", buttonFont, buttonColor);
        cancelButton = createStyledButton("Cancel", buttonFont, buttonColor);

       
        buttonBox.add(addCashierButton, gbc);
        gbc.gridy++;
        buttonBox.add(addDataEntryOperatorButton, gbc);
        gbc.gridy++;
        buttonBox.add(viewR, gbc);
        gbc.gridy++;
        buttonBox.add(cancelButton, gbc);

       
        mainPanel.add(buttonBox, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        addCashierButton.addActionListener(e -> {
            try {
                addCashier();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        addDataEntryOperatorButton.addActionListener(e -> {
            try {
                addDataEntryOperator();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        viewR.addActionListener(e -> {
            try {
                viewReports();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        cancelButton.addActionListener(e -> dispose());
    }

       private JButton createStyledButton(String text, Font font, Color color) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(new Color(227, 238, 254)); 
        button.setForeground(Color.BLACK); 
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(color.darker(), 3));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 35)); 
        return button;
    }
private void addCashier() throws SQLException {
    JFrame cashierFrame = new JFrame("Add Cashier");
    cashierFrame.setSize(500, 400);
    cashierFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    cashierFrame.setLocationRelativeTo(null);

    
    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setBackground(new Color(227, 238, 254));

    
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(Color.WHITE);
    formPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

    
    JLabel formHeading = new JLabel("Add Cashier");
    formHeading.setFont(new Font("Arial", Font.BOLD, 22));
    formHeading.setHorizontalAlignment(SwingConstants.CENTER);
    formHeading.setForeground(Color.DARK_GRAY);

   
    String code = controller.getbranchcode();
    JTextField nameField = new JTextField(20);
    JTextField emailField = new JTextField(20);
    JTextField salaryField = new JTextField(20);
    JTextField branchCodeField = new JTextField(code);
    branchCodeField.setEditable(false);

    
    JLabel nameLabel = new JLabel("Name:");
    JLabel emailLabel = new JLabel("Email:");
    JLabel salaryLabel = new JLabel("Salary:");
    JLabel branchCodeLabel = new JLabel("Branch Code:");

   
    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font fieldFont = new Font("Arial", Font.PLAIN, 14);
    styleComponent(nameLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(emailLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(salaryLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(branchCodeLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(nameField, Color.WHITE, Color.DARK_GRAY, fieldFont);
    styleComponent(emailField, Color.WHITE, Color.DARK_GRAY, fieldFont);
    styleComponent(salaryField, Color.WHITE, Color.DARK_GRAY, fieldFont);
    styleComponent(branchCodeField, Color.WHITE, Color.DARK_GRAY, fieldFont);

    
    JButton doneButton = new JButton("Done");
    JButton cancelButton = new JButton("Cancel");
    doneButton.setFocusPainted(false);
    doneButton.setBackground(new Color(70, 130, 180));
    doneButton.setForeground(Color.WHITE);
    doneButton.setFont(new Font("Arial", Font.BOLD, 16));
    doneButton.setPreferredSize(new Dimension(100, 30));
    cancelButton.setFocusPainted(false);
    cancelButton.setBackground(Color.LIGHT_GRAY);
    cancelButton.setForeground(Color.WHITE);
    cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
    cancelButton.setPreferredSize(new Dimension(100, 30));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    formPanel.add(formHeading, gbc);

    gbc.gridwidth = 1;
    gbc.gridy = 1;
    gbc.gridx = 0;
    formPanel.add(nameLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(nameField, gbc);

    gbc.gridy = 2;
    gbc.gridx = 0;
    formPanel.add(emailLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(emailField, gbc);

    gbc.gridy = 3;
    gbc.gridx = 0;
    formPanel.add(salaryLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(salaryField, gbc);

    gbc.gridy = 4;
    gbc.gridx = 0;
    formPanel.add(branchCodeLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(branchCodeField, gbc);

   
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.setBackground(new Color(227, 238, 254));
    buttonPanel.add(doneButton);
    buttonPanel.add(cancelButton);

   
    gbc.gridy = 5;
    gbc.gridx = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    formPanel.add(buttonPanel, gbc);

    mainPanel.add(formPanel);
    cashierFrame.add(mainPanel);
    cashierFrame.setVisible(true);

    
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
}

    private void viewReports() throws SQLException {
        String code = new AuthenticateController().getCode();
        if (controller.viewReports(code)) {
            reportDuration(code);
        } else {
            JOptionPane.showMessageDialog(null, "Branch code does not exist!");
        }
    }

    private void reportDuration(String code) {
        dispose();
        JFrame f = new JFrame("Reports Dashboard");
        f.setBounds(0, 0, 500, 600);
        f.setLayout(new BorderLayout());
        JPanel p1 = new JPanel(new GridLayout(5, 1));
        JButton today, weekly, monthly, yearly, specific;
        today = new JButton("Today");
        weekly = new JButton("Weekly");
        monthly = new JButton("Monthly");
        yearly = new JButton("Yearly");
        specific = new JButton("Specific Range");
        p1.add(today);
        p1.add(weekly);
        p1.add(monthly);
        p1.add(yearly);
        p1.add(specific);
        JLabel displaytxt = new JLabel("Report Time Menu");
        f.add(displaytxt, BorderLayout.NORTH);
        f.add(p1, BorderLayout.CENTER);

        today.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reportType(code, "Today");
            }
        });
        weekly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reportType(code, "Weekly");
            }
        });
        monthly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reportType(code, "Monthly");
            }
        });
        yearly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reportType(code, "Yearly");
            }
        });
        specific.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reportType(code, "Specific Range");
            }
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
           {   try {if(duration.equalsIgnoreCase("Today")){
               profitReport(code, "Today",null,null);
           }
           else if(duration.equalsIgnoreCase("Weekly"))
           {
               profitReport(code, "Weekly",null,null);
           }
           else if(duration.equalsIgnoreCase("Monthly"))
           {
                profitReport(code, "Monthly",null,null);
           }
           else if(duration.equalsIgnoreCase("Yearly"))
           {profitReport(code, "Yearly",null,null);
               
           }
           else 
           { String startDate = JOptionPane.showInputDialog(null, "Enter the start date (YYYY-MM-DD):", "Specify Range", JOptionPane.PLAIN_MESSAGE);
                String endDate = JOptionPane.showInputDialog(null, "Enter the end date (YYYY-MM-DD):", "Specify Range", JOptionPane.PLAIN_MESSAGE);

               
                if (startDate != null && !startDate.trim().isEmpty() &&
                    endDate != null && !endDate.trim().isEmpty()) {
                    profitReport(code, "Specific Range", startDate, endDate);
                } else {
                    JOptionPane.showMessageDialog(null, "Both start and end dates are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
               
              
           }
               } catch (SQLException ex) {
                   Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
               }
}
       });
        RemainingStock.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {if(duration.equalsIgnoreCase("Today")){
               remainingstockReport(code, "Today",null,null);
           }
           else if(duration.equalsIgnoreCase("Weekly"))
           {
               remainingstockReport(code, "Weekly",null,null);
           }
           else if(duration.equalsIgnoreCase("Monthly"))
           {
                remainingstockReport(code, "Monthly",null,null);
           }
           else if(duration.equalsIgnoreCase("Yearly"))
           {remainingstockReport(code, "Yearly",null,null);
               
           }
           else 
           { String startDate = JOptionPane.showInputDialog(null, "Enter the start date (YYYY-MM-DD):", "Specify Range", JOptionPane.PLAIN_MESSAGE);
                String endDate = JOptionPane.showInputDialog(null, "Enter the end date (YYYY-MM-DD):", "Specify Range", JOptionPane.PLAIN_MESSAGE);

                if (startDate != null && !startDate.trim().isEmpty() &&
                    endDate != null && !endDate.trim().isEmpty()) {
                    remainingstockReport(code, "Specific Range", startDate, endDate);
                } else {
                    JOptionPane.showMessageDialog(null, "Both start and end dates are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }}
       }
});
               
        Sales.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {if(duration.equalsIgnoreCase("Today")){
               salesReport(code, "Today",null,null);
           }
           else if(duration.equalsIgnoreCase("Weekly"))
           {
              salesReport(code, "Weekly",null,null);
           }
           else if(duration.equalsIgnoreCase("Monthly"))
           {
                salesReport(code, "Monthly",null,null);
           }
           else if(duration.equalsIgnoreCase("Yearly"))
           {salesReport(code, "Yearly",null,null);
               
           }
           else 
           { String startDate = JOptionPane.showInputDialog(null, "Enter the start date (YYYY-MM-DD):", "Specify Range", JOptionPane.PLAIN_MESSAGE);
                String endDate = JOptionPane.showInputDialog(null, "Enter the end date (YYYY-MM-DD):", "Specify Range", JOptionPane.PLAIN_MESSAGE);

               
                if (startDate != null && !startDate.trim().isEmpty() &&
                    endDate != null && !endDate.trim().isEmpty()) {
                    salesReport(code, "Specific Range", startDate, endDate);
                } else {
                    JOptionPane.showMessageDialog(null, "Both start and end dates are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }}
       }
       });
         f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
        
    }
    private void profitReport(String code, String duration,String start,String end) throws SQLException
    {dispose();
    JFrame f= new JFrame(duration+" Profit Report");
    f.setBounds(0,0,500,600);
    f.setLayout(new BorderLayout());
   JLabel displayMsg= new JLabel("Profit Report for Branch: "+ code + " ( "+duration+" ) ",JLabel.CENTER);
    f.add(displayMsg,BorderLayout.NORTH);
String[] profitreport={"Product Name", "Profit"};
Object[][] profit= controller.getProfit(code,duration,start,end);
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
    private void remainingstockReport(String code,String duration,String start,String end)
    {dispose();
    JFrame f= new JFrame(duration+" Remaining Stock Report");
    f.setBounds(0,0,500,600);
    f.setLayout(new BorderLayout());
   JLabel displayMsg= new JLabel("Remaining Stock Report for Branch: "+ code + " ( "+duration+" ) ",JLabel.CENTER);
    f.add(displayMsg,BorderLayout.NORTH);
String[] report={"Product Name", "Category", "Remaining Quantity"};
Object[][] stock= controller.getRemainingStockData(code,duration,start,end);
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
    private void salesReport(String code, String duration,String start,String end)
    {dispose();
    JFrame f= new JFrame(duration+" Sales Report");
    f.setBounds(0,0,500,600);
    f.setLayout(new BorderLayout());
   JLabel displayMsg= new JLabel("Sales Report for Branch: "+ code + " ( "+duration+" ) ",JLabel.CENTER);
    f.add(displayMsg,BorderLayout.NORTH);
String[] report={"Product Name", "Quantity Sold", "Total Sales"};
Object[][] sales=controller.getSalesData(code,duration,start,end);
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
    deoFrame.setSize(500, 400);
    deoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    deoFrame.setLocationRelativeTo(null);

   
    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setBackground(new Color(227, 238, 254));

    
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(Color.WHITE);
    formPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

    
    JLabel formHeading = new JLabel("Add Data Entry Operator");
    formHeading.setFont(new Font("Arial", Font.BOLD, 22));
    formHeading.setHorizontalAlignment(SwingConstants.CENTER);
    formHeading.setForeground(Color.DARK_GRAY);

    
    String code = controller.getbranchcode();
    JTextField deoCodeField = new JTextField(20);
    JTextField nameField = new JTextField(20);
    JTextField emailField = new JTextField(20);
    JTextField salaryField = new JTextField(20);
    JTextField branchCodeField = new JTextField(code);
    branchCodeField.setEditable(false);

    
    JLabel deoCodeLabel = new JLabel("DEO Code:");
    JLabel nameLabel = new JLabel("Name:");
    JLabel emailLabel = new JLabel("Email:");
    JLabel salaryLabel = new JLabel("Salary:");
    JLabel branchCodeLabel = new JLabel("Branch Code:");

    
    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font fieldFont = new Font("Arial", Font.PLAIN, 14);
    styleComponent(deoCodeLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(nameLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(emailLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(salaryLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(branchCodeLabel, null, Color.DARK_GRAY, labelFont);
    styleComponent(deoCodeField, Color.WHITE, Color.DARK_GRAY, fieldFont);
    styleComponent(nameField, Color.WHITE, Color.DARK_GRAY, fieldFont);
    styleComponent(emailField, Color.WHITE, Color.DARK_GRAY, fieldFont);
    styleComponent(salaryField, Color.WHITE, Color.DARK_GRAY, fieldFont);
    styleComponent(branchCodeField, Color.WHITE, Color.DARK_GRAY, fieldFont);

    
    JButton doneButton = new JButton("Done");
    JButton cancelButton = new JButton("Cancel");
    doneButton.setFocusPainted(false);
    doneButton.setBackground(new Color(70, 130, 180));
    doneButton.setForeground(Color.WHITE);
    doneButton.setFont(new Font("Arial", Font.BOLD, 16));
    doneButton.setPreferredSize(new Dimension(100, 30));
    cancelButton.setFocusPainted(false);
    cancelButton.setBackground(Color.LIGHT_GRAY);
    cancelButton.setForeground(Color.WHITE);
    cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
    cancelButton.setPreferredSize(new Dimension(100, 30));

   
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    formPanel.add(formHeading, gbc);

    gbc.gridwidth = 1;
    gbc.gridy = 1;
    gbc.gridx = 0;
    formPanel.add(deoCodeLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(deoCodeField, gbc);

    gbc.gridy = 2;
    gbc.gridx = 0;
    formPanel.add(nameLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(nameField, gbc);

    gbc.gridy = 3;
    gbc.gridx = 0;
    formPanel.add(emailLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(emailField, gbc);

    gbc.gridy = 4;
    gbc.gridx = 0;
    formPanel.add(salaryLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(salaryField, gbc);

    gbc.gridy = 5;
    gbc.gridx = 0;
    formPanel.add(branchCodeLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(branchCodeField, gbc);

    
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.setBackground(new Color(227, 238, 254));
    buttonPanel.add(doneButton);
    buttonPanel.add(cancelButton);

   
    gbc.gridy = 6;
    gbc.gridx = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    formPanel.add(buttonPanel, gbc);

    mainPanel.add(formPanel);
    deoFrame.add(mainPanel);
    deoFrame.setVisible(true);

    
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
}

    private void styleComponent(JComponent component, Color bgColor, Color fgColor, Font font) {
    if (bgColor != null) {
        component.setBackground(bgColor);
    }
    if (fgColor != null) {
        component.setForeground(fgColor);
    }
    component.setFont(font);
    if (component instanceof JTextField || component instanceof JComboBox) {
        component.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); 
    }
}
}
