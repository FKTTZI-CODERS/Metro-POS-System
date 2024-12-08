import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class SuperAdminDashboardView extends JFrame {
    JButton branch, bm, viewR, cancel;
    SuperAdminController s;
    String logoPath = "small.png"; 
    
    public SuperAdminDashboardView() {
        s = new SuperAdminController();
        setTitle("Super Admin Dashboard");
        init();
        setBounds(0, 0, 600, 700);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void init() {
      
        branch = createStyledButton("Create Branch");
        bm = createStyledButton("Create Branch Manager");
        viewR = createStyledButton("View Reports");
        cancel = createStyledButton("Cancel");
    
       
        JLabel logoLabel = createLogoLabel(logoPath);
    
      
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;  
        gbc.gridy = 0; 
        gbc.insets = new Insets(5, 10, 10, 10);  
        btnPanel.setBackground(Color.white);
        
        btnPanel.add(branch, gbc);
        
        gbc.gridy++; 
        btnPanel.add(bm, gbc);
        
        gbc.gridy++;
        btnPanel.add(viewR, gbc);
    
       
        JPanel cancelPanel = new JPanel();
        cancelPanel.setOpaque(false);
        cancelPanel.add(cancel);
    
        
        setLayout(new BorderLayout());
        add(logoLabel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER); 
        add(cancelPanel, BorderLayout.SOUTH);
    
        getContentPane().setBackground(Color.WHITE); 
    }
    


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(316, 60)); 
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(108, 167, 255));  
        button.setForeground(Color.WHITE);  
        button.setFocusPainted(false);  
        button.setBorder(BorderFactory.createEmptyBorder());  
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                handleButtonAction(text);
            }
        });
        return button;
    }

 
    private JLabel createLogoLabel(String path) {
        ImageIcon icon = new ImageIcon(path); 
        Image img = icon.getImage();  
    
        
        int originalWidth = img.getWidth(null);
        int originalHeight = img.getHeight(null);
    
        int newWidth = 200; 
        int newHeight = (int) ((double) originalHeight / originalWidth * newWidth);
    
      
        Image resizedImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);  
        icon = new ImageIcon(resizedImg);
    
        JLabel logoLabel = new JLabel(icon); 
        logoLabel.setHorizontalAlignment(JLabel.CENTER);  
        return logoLabel;
    }
    
    
    private void handleButtonAction(String action) {
        switch (action) {
            case "Create Branch":
                createBranch();
                break;
            case "Create Branch Manager":
                try {
                    createBranchManager();
                } catch (SQLException ex) {
                    Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "View Reports":
                try {
                    viewReports();
                } catch (SQLException ex) {
                    Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Cancel":
                dispose();
                break;
        }
    }
    private String showBranchCodeDialog() {
       
        JDialog dialog = new JDialog(this, "Enter Branch Code", true); 
        dialog.setLayout(new FlowLayout());
    
        JLabel label = new JLabel("Enter the Branch Code: ");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setPreferredSize(new Dimension(200, 30));
    
       
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setBackground(new Color(108, 167, 255));
        okButton.setForeground(Color.WHITE);
        okButton.setPreferredSize(new Dimension(80, 40));
    
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(255, 87, 34));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(100, 40));
    
       
        dialog.add(label);
        dialog.add(textField);
        dialog.add(okButton);
        dialog.add(cancelButton);
    
      
        okButton.addActionListener(e -> {
            dialog.dispose(); 
        });
    
        cancelButton.addActionListener(e -> {
            textField.setText(""); 
            dialog.dispose(); 
        });
    
       
        dialog.setSize(300, 150); 
        dialog.setLocationRelativeTo(this); 
        dialog.setVisible(true);  
    
        return textField.getText(); 
    }
    
    private void viewReports() throws SQLException {
        String code = showBranchCodeDialog();
        if (code != null && !code.isEmpty()) {
            if (s.viewReports(code)) {
                reportDuration(code);
            } else {
                JOptionPane.showMessageDialog(null, "Branch code does not exist!");
            }
        }
    }
    
    
    private void reportDuration(String code) {
        dispose();
        JFrame f = new JFrame("Reports Dashboard");
        f.setBounds(0, 0, 700, 600);
        f.setLayout(new BorderLayout());
    

        JPanel p1 = new JPanel(new GridLayout(5, 1, 10, 20));
        p1.setOpaque(false);
    
        JButton today = createStyledButton("Today");
        JButton weekly = createStyledButton("Weekly");
        JButton monthly = createStyledButton("Monthly");
        JButton yearly = createStyledButton("Yearly");
        JButton specific = createStyledButton("Specific Range");
    
        p1.add(today);
        p1.add(weekly);
        p1.add(monthly);
        p1.add(yearly);
        p1.add(specific);
    
        JLabel displaytxt = new JLabel("Report Time Menu", JLabel.CENTER);
        displaytxt.setFont(new Font("Arial", Font.BOLD, 18));
    
        f.add(displaytxt, BorderLayout.NORTH);
        f.add(p1, BorderLayout.CENTER);
    

        today.addActionListener(e -> reportType(code, "Today"));
        weekly.addActionListener(e -> reportType(code, "Weekly"));
        monthly.addActionListener(e -> reportType(code, "Monthly"));
        yearly.addActionListener(e -> reportType(code, "Yearly"));
        specific.addActionListener(e -> reportType(code, "Specific Range"));
    
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    
    private void reportType(String code, String duration) {
        dispose();
        JFrame f = new JFrame("Reports Type");
        f.setBounds(0, 0, 500, 600);
        f.setLayout(new BorderLayout());
    
        JPanel p1 = new JPanel(new GridLayout(3, 1, 10, 20));
        p1.setOpaque(false);
    
        JButton Sales = createStyledButton("Sales");
        JButton RemainingStock = createStyledButton("Remaining Stock");
        JButton Profit = createStyledButton("Profit");
    
        p1.add(Sales);
        p1.add(RemainingStock);
        p1.add(Profit);
    
        JLabel displaytxt = new JLabel("Report Type Menu", JLabel.CENTER);
        displaytxt.setFont(new Font("Arial", Font.BOLD, 18));
    
        f.add(displaytxt, BorderLayout.NORTH);
        f.add(p1, BorderLayout.CENTER);
    
      
        Profit.addActionListener(e -> {
            try {
                profitReport(code, duration, null, null);
            } catch (SQLException ex) {
                Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        RemainingStock.addActionListener(e -> {
            try {
                remainingstockReport(code, duration, null, null);
            } catch (SQLException ex) {
                Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Sales.addActionListener(e -> {
            try {
                salesReport(code, duration, null, null);
            } catch (SQLException ex) {
                Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    
    private void profitReport(String code, String duration, String start, String end) throws SQLException {
        dispose();
        JFrame f = new JFrame(duration + " Profit Report");
        f.setBounds(0, 0, 500, 600);
        f.setLayout(new BorderLayout());
    
        JLabel displayMsg = new JLabel("Profit Report for Branch: " + code + " (" + duration + ")", JLabel.CENTER);
        displayMsg.setFont(new Font("Arial", Font.BOLD, 18));
        f.add(displayMsg, BorderLayout.NORTH);
    
        String[] profitreport = {"Product Name", "Profit"};
        Object[][] profit = s.getProfit(code, duration, start, end);
        JTable t = new JTable(profit, profitreport);
        JScrollPane scrollpane = new JScrollPane(t);
        f.add(scrollpane, BorderLayout.CENTER);
    
        JButton back = createStyledButton("Back");
        back.addActionListener(ae -> {
            reportType(code, duration);
            f.dispose();
        });
        f.add(back, BorderLayout.SOUTH);
    
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    
    private void remainingstockReport(String code, String duration, String start, String end) throws SQLException {
        dispose();
        JFrame f = new JFrame(duration + " Remaining Stock Report");
        f.setBounds(0, 0, 500, 600);
        f.setLayout(new BorderLayout());
    
        JLabel displayMsg = new JLabel("Remaining Stock Report for Branch: " + code + " (" + duration + ")", JLabel.CENTER);
        displayMsg.setFont(new Font("Arial", Font.BOLD, 18));
        f.add(displayMsg, BorderLayout.NORTH);
    
        String[] report = {"Product Name", "Category", "Remaining Quantity"};
        Object[][] stock = s.getRemainingStockData(code, duration, start, end);
        JTable t = new JTable(stock, report);
        JScrollPane scrollpane = new JScrollPane(t);
        f.add(scrollpane, BorderLayout.CENTER);
    
        JButton back = createStyledButton("Back");
        back.addActionListener(ae -> {
            reportType(code, duration);
            f.dispose();
        });
        f.add(back, BorderLayout.SOUTH);
    
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    
    private void salesReport(String code, String duration, String start, String end) throws SQLException {
        dispose();
        JFrame f = new JFrame(duration + " Sales Report");
        f.setBounds(0, 0, 700, 600);
        f.setLayout(new BorderLayout());
    
        JLabel displayMsg = new JLabel("Sales Report for Branch: " + code + " (" + duration + ")", JLabel.CENTER);
        displayMsg.setFont(new Font("Arial", Font.BOLD, 18));
        f.add(displayMsg, BorderLayout.NORTH);
    
        String[] report = {"Product Name", "Quantity Sold", "Total Sales"};
        Object[][] sales = s.getSalesData(code, duration, start, end);
        JTable t = new JTable(sales, report);
        JScrollPane scrollpane = new JScrollPane(t);
        f.add(scrollpane, BorderLayout.CENTER);
    
        JButton back = createStyledButton("Back");
        back.addActionListener(ae -> {
            reportType(code, duration);
            f.dispose();
        });
        f.add(back, BorderLayout.SOUTH);
    
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    


    private void createBranch() {
        dispose();
        cancel = createStyledButton("Cancel"); 
        JButton done = createStyledButton("Done");  
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));  
    

        JLabel title = new JLabel("--- Create a Branch ---");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.gray);
        title.setHorizontalAlignment(SwingConstants.CENTER);
    
        JLabel bcode = new JLabel("Branch Code: ");
        bcode.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField codeField = new JTextField();
        codeField.setPreferredSize(new Dimension(200, 30));
        codeField.setFont(new Font("Arial", Font.PLAIN, 14));
    
        JLabel bname = new JLabel("Branch Name: ");
        bname.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
    
        JLabel address = new JLabel("Address: ");
        address.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(200, 30));
        addressField.setFont(new Font("Arial", Font.PLAIN, 14));
    
        JLabel phone = new JLabel("Phone: ");
        phone.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(200, 30));
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
    
        JLabel city = new JLabel("City: ");
        city.setFont(new Font("Arial", Font.PLAIN, 14));
        String[] cities = {"Lahore", "Karachi", "Islamabad", "Rawalpindi", "Quetta", "Peshawar", "Faisalabad", "Multan"};
        JComboBox<String> cityCombo = new JComboBox<>(cities);
        cityCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        cityCombo.setPreferredSize(new Dimension(200, 30));
    
 
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 20));
        formPanel.setOpaque(false);
        formPanel.add(bcode);
        formPanel.add(codeField);
        formPanel.add(bname);
        formPanel.add(nameField);
        formPanel.add(address);
        formPanel.add(addressField);
        formPanel.add(phone);
        formPanel.add(phoneField);
        formPanel.add(city);
        formPanel.add(cityCombo);
    
      
        btnPanel.add(cancel);
        btnPanel.add(done); 
    
    
        JFrame sa = new JFrame("Create Branch Portal");
        sa.setLayout(new BorderLayout());
        sa.add(title, BorderLayout.NORTH);
        sa.add(formPanel, BorderLayout.CENTER);
        sa.add(btnPanel, BorderLayout.SOUTH);
    
      
        cancel.addActionListener(ae -> {
            sa.dispose();
            new SuperAdminDashboardView();
        });
    
        done.addActionListener(ae -> {
            String getcode = codeField.getText();
            String getname = nameField.getText();
            String getaddress = addressField.getText();
            String getphone = phoneField.getText();
            String getCity = (String) cityCombo.getSelectedItem();
            try {
                s.createBranch(getcode, getname, getCity, getphone, getaddress);
            } catch (SQLException ex) {
                Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    
        sa.setBounds(0, 0, 700, 600);
        sa.setDefaultCloseOperation(EXIT_ON_CLOSE);
        sa.setVisible(true);
       
    }
    
   
    private void createBranchManager() throws SQLException {
        dispose();
        JLabel bmname, bmemail, bcode, salary;
        JTextField nameField, emailField, salaryField;
        JComboBox<String> branchcodeBox;
        ArrayList<String> availableCodes = s.getAvailableCodes();
        branchcodeBox = new JComboBox<>(availableCodes.toArray(new String[0]));
        branchcodeBox.setFont(new Font("Arial", Font.PLAIN, 14));
        branchcodeBox.setPreferredSize(new Dimension(200, 30));
    
  
        JFrame sa = new JFrame("Create Branch Manager Portal");
    
 
        JPanel bmpanel = new JPanel(new GridLayout(5, 2, 10, 20));
        bmpanel.setOpaque(false);
    

        bmname = new JLabel("Branch Manager Name: ");
        bmname.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField = new JTextField();
        styleTextField(nameField);
    
        bmemail = new JLabel("Email: ");
        bmemail.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField = new JTextField();
        styleTextField(emailField);
    
        bcode = new JLabel("Branch Code: ");
        bcode.setFont(new Font("Arial", Font.PLAIN, 14));
    
        salary = new JLabel("Salary: ");
        salary.setFont(new Font("Arial", Font.PLAIN, 14));
        salaryField = new JTextField();
        styleTextField(salaryField);
    
     
        bmpanel.add(bmname);
        bmpanel.add(nameField);
        bmpanel.add(bmemail);
        bmpanel.add(emailField);
        bmpanel.add(bcode);
        bmpanel.add(branchcodeBox);
        bmpanel.add(salary);
        bmpanel.add(salaryField);
    
   
        JButton done = createStyledButton("Done");
        cancel = createStyledButton("Cancel");
    
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setOpaque(false);
        btnPanel.add(cancel);
        btnPanel.add(done);
    
    
        JLabel title = new JLabel("--- Branch Manager Form ---");
        title.setForeground(Color.GRAY);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
    
    
        sa.setLayout(new BorderLayout());
        sa.add(title, BorderLayout.NORTH);
        sa.add(bmpanel, BorderLayout.CENTER);
        sa.add(btnPanel, BorderLayout.SOUTH);
    
    
        sa.setBounds(0, 0, 700, 600);
        sa.setDefaultCloseOperation(EXIT_ON_CLOSE);
        sa.setVisible(true);
    
      
        cancel.addActionListener(ae -> {
            sa.dispose();
            new SuperAdminDashboardView();
        });
    
        done.addActionListener(ae -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String salaryText = salaryField.getText();
            String branchCode = (String) branchcodeBox.getSelectedItem();
            try {
                s.createBranchManager(name, email, branchCode, salaryText);
            } catch (SQLException ex) {
                Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void styleTextField(JTextField textField) {
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(new Color(255,255,255));  
        textField.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));  // Subtle border
    }
    
  
}
