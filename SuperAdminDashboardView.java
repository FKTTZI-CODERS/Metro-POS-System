import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuperAdminDashboardView {

    static class SidebarItem extends JButton {
        public SidebarItem(String label, String iconPath) {
            super(label);
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            setPreferredSize(new Dimension(240, 40));
            setBackground(Color.WHITE);
            setIcon(new ImageIcon(iconPath));
            setFont(new Font("Arial", Font.PLAIN, 14));
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder());

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                     highlightSelectedItem(SidebarItem.this);
                }
            });
        }
    }

    static class StatCard extends JPanel {
        public StatCard(String count, String title, Color bgColor, Color borderColor) {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(252, 120));
            setBackground(bgColor);
            setBorder(BorderFactory.createLineBorder(borderColor, 2));

            JLabel countLabel = new JLabel(count, SwingConstants.CENTER);
            countLabel.setFont(new Font("Arial", Font.BOLD, 28));
            countLabel.setForeground(Color.BLACK);

            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            titleLabel.setForeground(Color.DARK_GRAY);

            add(countLabel, BorderLayout.CENTER);
            add(titleLabel, BorderLayout.SOUTH);
        }
    }

    static class PlaceholderBox extends JPanel {
        public PlaceholderBox(String title) {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(350, 200));
            setBackground(Color.LIGHT_GRAY);
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            add(titleLabel, BorderLayout.CENTER);
        }
    }

    private static void highlightSelectedItem(JButton selectedItem) {
      
        for (Component comp : sidebarPanel.getComponents()) {
            if (comp instanceof SidebarItem) {
                SidebarItem item = (SidebarItem) comp;
                item.setBackground(Color.WHITE); 
            }
        }
        selectedItem.setBackground(Color.decode("#6CA7FF"));
    }

    private static JPanel sidebarPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SuperAdminDashboardView::createAndShowGUI);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
       
        JPanel logoPanel = new JPanel();
        logoPanel.setPreferredSize(new Dimension(1200, 80));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setLayout(new BorderLayout());
        
        // Left side: Logo
        JLabel logoLabel = new JLabel(new ImageIcon("images\\dashboardlogo.png")); // Logo path
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        logoPanel.add(logoLabel, BorderLayout.WEST);
        
        // Right side: Profile icon and search bar
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setBackground(Color.WHITE);
        
        JTextField searchField = new JTextField("Enter Branch Code", 20);
        searchField.setFont(new Font("Arial", Font.ITALIC, 14));
        searchField.setForeground(Color.GRAY);
        searchField.setPreferredSize(new Dimension(200, 30));
        
        // Placeholder effect for the search field
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("Enter Branch Code")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
        
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Enter Branch Code");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        
        JLabel searchIcon = new JLabel(new ImageIcon("images\\Search.png")); // Search icon
        rightPanel.add(searchField);
        rightPanel.add(searchIcon);
        
        JLabel profileIcon = new JLabel(new ImageIcon("images\\superprofile.png")); // Profile icon
        rightPanel.add(profileIcon);
        
        logoPanel.add(rightPanel, BorderLayout.EAST);
        
      
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(Color.WHITE);
        sidebarPanel.setPreferredSize(new Dimension(250, 600));
        sidebarPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
            BorderFactory.createEmptyBorder(20, 0, 10, 10)));
    
        SidebarItem homeButton = new SidebarItem("Home", "images\\SuperHome.png");
        SidebarItem branchButton = new SidebarItem("Branches", "images\\Branches.png");
        SidebarItem branchManagerButton = new SidebarItem("Branch Managers", "images\\BranchManagers.png");
        SidebarItem logoutButton = new SidebarItem("Log Out", "images\\logout.png");
    
        
        branchButton.addActionListener(e -> addBranch());
        branchManagerButton.addActionListener(e -> addBranchManager());
    
        
        sidebarPanel.add(homeButton);
        sidebarPanel.add(Box.createVerticalStrut(5));
        sidebarPanel.add(branchButton);
        sidebarPanel.add(Box.createVerticalStrut(5));
        sidebarPanel.add(branchManagerButton);
        sidebarPanel.add(Box.createVerticalStrut(5));
        sidebarPanel.add(logoutButton);
    
        
        highlightSelectedItem(homeButton);
    
        
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBackground(Color.WHITE);
    
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JLabel headerTitle = new JLabel("Home");
        headerTitle.setFont(new Font("Mulish", Font.BOLD, 20));
        headerPanel.add(headerTitle, BorderLayout.WEST);
    
        
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BorderLayout());
    
       
        JPanel homeHeadingPanel = new JPanel();
        JLabel homeHeadingLabel = new JLabel("Home");
        homeHeadingPanel.setBackground(Color.decode("#6CA7FF"));
        homeHeadingLabel.setFont(new Font("Mulish", Font.BOLD, 18));
        homeHeadingLabel.setForeground(Color.white);
        homeHeadingPanel.add(homeHeadingLabel);
        statsPanel.add(homeHeadingPanel, BorderLayout.NORTH);
    
        
        JPanel statCardsPanel = new JPanel();
        statCardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        statCardsPanel.setBackground(Color.WHITE);
    
        statCardsPanel.add(new StatCard("300", "No. of Branches", Color.decode("#e0f7fa"), Color.decode("#0288d1")));
        statCardsPanel.add(new StatCard("1.2K", "No. of Employees", Color.decode("#f8bbd0"), Color.decode("#d32f2f")));
        statCardsPanel.add(new StatCard("$2.16K", "Total Revenue", Color.decode("#bbdefb"), Color.decode("#039be5")));
    
        statsPanel.add(statCardsPanel, BorderLayout.CENTER);
    
        mainContent.add(statsPanel, BorderLayout.NORTH);
    
        // Placeholders for Sales, Profits, and Inventory
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(1, 3, 20, 20));
        dataPanel.setBackground(Color.WHITE);
    
        dataPanel.add(new PlaceholderBox("Sales"));
        dataPanel.add(new PlaceholderBox("Profits Overview"));
        dataPanel.add(new PlaceholderBox("Inventory"));
    
        mainContent.add(dataPanel, BorderLayout.CENTER);
    
        
        frame.add(logoPanel, BorderLayout.NORTH);
        frame.add(sidebarPanel, BorderLayout.WEST);
        frame.add(mainContent, BorderLayout.CENTER);
    
       
        frame.setSize(1200, 800);
        frame.setVisible(true);
    }
    
   
private static void addBranch() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(6, 2)); 

    JTextField branchIDField = new JTextField();
    JTextField branchNameField = new JTextField();

    
    String[] locations = {"Select Location", "Lahore", "Islamabad", "Karachi", "Gujranwala", "Faisalabad", "Multan"};
    JComboBox<String> locationComboBox = new JComboBox<>(locations);

    JTextField branchPhoneField = new JTextField();
    JTextField branchAddressField = new JTextField();

    panel.add(new JLabel("Branch ID:"));
    panel.add(branchIDField);
    panel.add(new JLabel("Branch Name:"));
    panel.add(branchNameField);
    panel.add(new JLabel("Branch Location:"));
    panel.add(locationComboBox);
    panel.add(new JLabel("Branch Phone:"));
    panel.add(branchPhoneField);
    panel.add(new JLabel("Branch Address:")); 
    panel.add(branchAddressField); 

    int option = JOptionPane.showConfirmDialog(null, panel, "Add Branch", JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
        String branchID = branchIDField.getText();
        String branchName = branchNameField.getText();
        String branchLocation = (String) locationComboBox.getSelectedItem();
        String branchPhone = branchPhoneField.getText();
        String branchAddress = branchAddressField.getText(); 
        //business logic to save branch details here
        JOptionPane.showMessageDialog(null, "Branch Added: " + branchID + ", " + branchName + ", " + branchLocation + ", " + branchPhone + ", " + branchAddress);
    }
}

    
    private static void addBranchManager() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JTextField managerNameField = new JTextField();
        JTextField managerIDField = new JTextField();
        JTextField managerBranchField = new JTextField();
        JTextField managerPhoneField = new JTextField();
        JTextField managerEmailField = new JTextField();
        JTextField managerAddressField = new JTextField();

        panel.add(new JLabel("Manager Name:"));
        panel.add(managerNameField);
        panel.add(new JLabel("Manager ID:"));
        panel.add(managerIDField);
        panel.add(new JLabel("Branch:"));
        panel.add(managerBranchField);
        panel.add(new JLabel("Manager Phone:"));
        panel.add(managerPhoneField);
        panel.add(new JLabel("Manager Email:"));
        panel.add(managerEmailField);
        panel.add(new JLabel("Manager Address:"));
        panel.add(managerAddressField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Add Branch Manager", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String managerName = managerNameField.getText();
            String managerID = managerIDField.getText();
            String branch = managerBranchField.getText();
            String managerPhone = managerPhoneField.getText();
            String managerEmail = managerEmailField.getText();
            String managerAddress = managerAddressField.getText();

            //business logic to save branch manager details here
            JOptionPane.showMessageDialog(null, "Branch Manager Added: " + managerName + " (ID: " + managerID + ") at " + branch + ", " + managerPhone + ", " + managerEmail + ", " + managerAddress);
        }
    }
}
