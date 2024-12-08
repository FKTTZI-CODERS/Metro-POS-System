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
    f.setSize(500, 600);
    f.setDefaultCloseOperation(EXIT_ON_CLOSE);
    f.setLayout(new BorderLayout());

    
    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setBackground(new Color(227, 238, 254));

    
    JPanel p1 = new JPanel(new GridBagLayout());
    p1.setBackground(Color.WHITE);
    p1.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

    
    JLabel displaytxt = new JLabel("Report Time Menu");
    displaytxt.setFont(new Font("Arial", Font.BOLD, 22));
    displaytxt.setHorizontalAlignment(SwingConstants.CENTER);
    displaytxt.setForeground(Color.DARK_GRAY);

    
    JButton today = new JButton("Today");
    JButton weekly = new JButton("Weekly");
    JButton monthly = new JButton("Monthly");
    JButton yearly = new JButton("Yearly");
    JButton specific = new JButton("Specific Range");

    styleButton(today);
    styleButton(weekly);
    styleButton(monthly);
    styleButton(yearly);
    styleButton(specific);

    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    p1.add(today, gbc);

    gbc.gridy++;
    p1.add(weekly, gbc);

    gbc.gridy++;
    p1.add(monthly, gbc);

    gbc.gridy++;
    p1.add(yearly, gbc);

    gbc.gridy++;
    p1.add(specific, gbc);

    
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    mainPanel.add(displaytxt, gbc);

    gbc.gridy++;
    mainPanel.add(p1, gbc);

   
    f.add(mainPanel, BorderLayout.CENTER);

    
    today.addActionListener(e -> reportType(code, "Today"));
    weekly.addActionListener(e -> reportType(code, "Weekly"));
    monthly.addActionListener(e -> reportType(code, "Monthly"));
    yearly.addActionListener(e -> reportType(code, "Yearly"));
    specific.addActionListener(e -> reportType(code, "Specific Range"));

    f.setVisible(true);
}
public void reportType(String code, String duration) {
    dispose();

    JFrame f = new JFrame("Reports Type");
    f.setSize(500, 600);
    f.setDefaultCloseOperation(EXIT_ON_CLOSE);
    f.setLayout(new BorderLayout());

    
    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setBackground(new Color(227, 238, 254));

   
    JPanel p1 = new JPanel(new GridBagLayout());
    p1.setBackground(Color.WHITE);
    p1.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

    
    JLabel displaytxt = new JLabel("Report Type Menu");
    displaytxt.setFont(new Font("Arial", Font.BOLD, 22));
    displaytxt.setHorizontalAlignment(SwingConstants.CENTER);
    displaytxt.setForeground(Color.DARK_GRAY);

    
    JButton sales = new JButton("Sales");
    JButton remainingStock = new JButton("Remaining Stock");
    JButton profit = new JButton("Profit");

    styleButton(sales);
    styleButton(remainingStock);
    styleButton(profit);

    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    
    gbc.gridx = 0;
    gbc.gridy = 0;
    p1.add(sales, gbc);

    gbc.gridy++;
    p1.add(remainingStock, gbc);

    gbc.gridy++;
    p1.add(profit, gbc);

    
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    mainPanel.add(displaytxt, gbc);

    gbc.gridy++;
    mainPanel.add(p1, gbc);

    
    f.add(mainPanel, BorderLayout.CENTER);

    
    sales.addActionListener(e -> handleReportAction(code, duration, "Sales"));
    remainingStock.addActionListener(e -> handleReportAction(code, duration, "Remaining Stock"));
    profit.addActionListener(e -> handleReportAction(code, duration, "Profit"));

    f.setVisible(true);
}

private void handleReportAction(String code, String duration, String reportType) {
    try {
        if (duration.equalsIgnoreCase("Today")) {
            switch (reportType) {
                case "Sales" -> salesReport(code, "Today", null, null);
                case "Remaining Stock" -> remainingstockReport(code, "Today", null, null);
                case "Profit" -> profitReport(code, "Today", null, null);
            }
        } else if (duration.equalsIgnoreCase("Weekly")) {
            switch (reportType) {
                case "Sales" -> salesReport(code, "Weekly", null, null);
                case "Remaining Stock" -> remainingstockReport(code, "Weekly", null, null);
                case "Profit" -> profitReport(code, "Weekly", null, null);
            }
        } else if (duration.equalsIgnoreCase("Monthly")) {
            switch (reportType) {
                case "Sales" -> salesReport(code, "Monthly", null, null);
                case "Remaining Stock" -> remainingstockReport(code, "Monthly", null, null);
                case "Profit" -> profitReport(code, "Monthly", null, null);
            }
        } else if (duration.equalsIgnoreCase("Yearly")) {
            switch (reportType) {
                case "Sales" -> salesReport(code, "Yearly", null, null);
                case "Remaining Stock" -> remainingstockReport(code, "Yearly", null, null);
                case "Profit" -> profitReport(code, "Yearly", null, null);
            }
        } else {
            String startDate = JOptionPane.showInputDialog(null, "Enter the start date (YYYY-MM-DD):", "Specify Range", JOptionPane.PLAIN_MESSAGE);
            String endDate = JOptionPane.showInputDialog(null, "Enter the end date (YYYY-MM-DD):", "Specify Range", JOptionPane.PLAIN_MESSAGE);

            if (startDate != null && !startDate.trim().isEmpty() && endDate != null && !endDate.trim().isEmpty()) {
                switch (reportType) {
                    case "Sales" -> salesReport(code, "Specific Range", startDate, endDate);
                    case "Remaining Stock" -> remainingstockReport(code, "Specific Range", startDate, endDate);
                    case "Profit" -> profitReport(code, "Specific Range", startDate, endDate);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Both start and end dates are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private void styleButton(JButton button) {
    button.setFocusPainted(false);
    button.setBackground(new Color(70, 130, 180));
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Arial", Font.BOLD, 16));
    button.setPreferredSize(new Dimension(150, 40));
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
