
package metropos.view;
import metropos.controller.AuthenticateController;
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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginView extends JFrame 
{JTextField email;
JLabel emailLB,passLB;
JPasswordField pass;
AuthenticateController a;
    JButton superadmin,branchmanager,deo,cashier;
   public LoginView()
   {
       a= new AuthenticateController();
       setTitle("Swift POS Login");
   init();
   setSize(1200, 700);
   setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   setVisible(true);
       
   }
   public void init()
   {   
     setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(227, 238, 254));
        leftPanel.setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);

        leftPanel.setPreferredSize(new Dimension((int) (getWidth() * 0.55), getHeight()));

        JLabel logoLeft = new JLabel(new ImageIcon("src/metropos/image/big.png"));
        logoLeft.setHorizontalAlignment(SwingConstants.CENTER);

       
        logoLeft.setPreferredSize(new Dimension(318, 233));
        leftPanel.add(logoLeft, BorderLayout.CENTER);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(new Color(227, 238, 254));

        JLabel welcomeText = new JLabel("Welcome To Swift POS");
        welcomeText.setFont(new Font("Poppins", Font.BOLD, 24));
        welcomeText.setForeground(new Color(38, 70, 83));
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(welcomeText);

        JTextArea description = new JTextArea("Transform your business with our advanced POS system! "
                + "Seamlessly manage sales, track inventory, and gain insights into customer trends—all "
                + "from one intuitive platform.");
        description.setFont(new Font("Poppins", Font.PLAIN, 14));
        description.setForeground(new Color(42, 42, 42));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setOpaque(false);
        description.setEditable(false);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setMaximumSize(new Dimension(400, 100));
        description.setMargin(new Insets(10, 10, 100, 10));
        welcomePanel.add(description);

        leftPanel.add(welcomePanel, BorderLayout.SOUTH);
        
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        add(rightPanel, BorderLayout.CENTER);

        JLabel logoRight = new JLabel(new ImageIcon("src/metropos/image/small.png"));
        logoRight.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoRight.setPreferredSize(new Dimension(158, 116));
        rightPanel.add(logoRight);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel signInPanel = new JPanel(new GridBagLayout());
        signInPanel.setBackground(Color.WHITE);
        signInPanel.setPreferredSize(new Dimension(350, 250));
        signInPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(250, 250, 250), 4),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        signInPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(signInPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Let's Sign You In");
        title.setFont(new Font("Poppins", Font.BOLD, 22));
        title.setForeground(Color.decode("#1E3446"));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        signInPanel.add(title, gbc);

        JPanel rolePanel = new JPanel(new BorderLayout());
        rolePanel.setBackground(Color.WHITE);

        JLabel roleIcon = new JLabel(new ImageIcon("src/metropos/image/profile-2user.png")); 
        roleIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        rolePanel.add(roleIcon, BorderLayout.WEST);

        JComboBox<String> roleDropdown = new JComboBox<>(new String[]{"Choose your role", "Super Admin", "Branch Manager", "Data Entry Operator", "Cashier"});
        roleDropdown.setFont(new Font("Poppins", Font.PLAIN, 14));
        roleDropdown.setForeground(Color.decode("#8E9295"));
        roleDropdown.setBackground(Color.decode("#FAFAFA"));
        rolePanel.add(roleDropdown, BorderLayout.CENTER);

        gbc.gridy = 1;
        signInPanel.add(rolePanel, gbc);

        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.setBackground(Color.WHITE);

        JLabel emailIcon = new JLabel(new ImageIcon("src/metropos/image/Email.png"));
        emailIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5)); // Space for icon on the left
        emailPanel.add(emailIcon, BorderLayout.WEST);

        JTextField emailField = new JTextField("Email ID");
        emailField.setFont(new Font("Poppins", Font.PLAIN, 14));
        emailField.setForeground(Color.decode("#8E9295"));
        emailField.setBackground(Color.decode("#FAFAFA"));
        emailField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        emailField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("Email ID")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setForeground(Color.decode("#8E9295"));
                    emailField.setText("Email ID");
                }
            }
        });
        emailPanel.add(emailField, BorderLayout.CENTER);
        gbc.gridy = 2;
        signInPanel.add(emailPanel, gbc);

        
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.WHITE);

        JLabel passwordIcon = new JLabel(new ImageIcon("src/metropos/image/Vector (1).png"));
        passwordIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5)); // Space for icon on the left
        passwordPanel.add(passwordIcon, BorderLayout.WEST);

        JPasswordField passwordField = new JPasswordField("Password");
        passwordField.setFont(new Font("Poppins", Font.PLAIN, 14));
        passwordField.setForeground(Color.decode("#8E9295"));
        passwordField.setBackground(Color.decode("#FAFAFA"));
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        passwordField.setEchoChar((char) 0); 
        passwordField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Password")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.decode("#8E9295"));
                    passwordField.setText("Password");
                }
            }
        });
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        
        JButton eyeButton = new JButton(new ImageIcon("src/metropos/image/EyeIconOutlined.png"));
        eyeButton.setBorderPainted(false);
        eyeButton.setContentAreaFilled(false);
        eyeButton.setFocusPainted(false);
        eyeButton.setPreferredSize(new Dimension(30, 30));

       
        eyeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordField.getEchoChar() == '*') {
                    passwordField.setEchoChar((char) 0); 
                } else {
                    passwordField.setEchoChar('*'); 
                }
            }
        });

        JPanel eyePanel = new JPanel(new BorderLayout());
        eyePanel.setBackground(Color.WHITE);
        eyePanel.add(eyeButton, BorderLayout.EAST);
        passwordPanel.add(eyePanel, BorderLayout.EAST);

        gbc.gridy = 3;
        signInPanel.add(passwordPanel, gbc);

        JLabel forgotPassword = new JLabel("Forgot Password?");
        forgotPassword.setFont(new Font("Poppins", Font.PLAIN, 12));
        forgotPassword.setForeground(Color.BLUE);
        forgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        signInPanel.add(forgotPassword, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        JButton signInButton = new JButton("Sign In");
        signInButton.setFont(new Font("Poppins", Font.PLAIN, 14));
        signInButton.setBackground(new Color(108, 167, 255));
        signInButton.setForeground(Color.WHITE);
        signInButton.setPreferredSize(new Dimension(300, 40));
        signInButton.setFocusPainted(false);
        buttonPanel.add(signInButton);
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        signInPanel.add(buttonPanel, gbc);
signInButton.addActionListener(new ActionListener()
{
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String role=(String) roleDropdown.getSelectedItem();
        String email = emailField.getText();
        String password=  new String (passwordField.getPassword());
        if(role.equalsIgnoreCase("Super Admin"))
        {try {
                   a.login(email, password, "Super Admin");
               } catch (SQLException ex) {
                   Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
               }

        }
        else if(role.equalsIgnoreCase("Branch Manager"))
        { try {
                   a.login(email, password, "Branch Manager");
               } catch (SQLException ex) {
                   Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
               }

        }
        else if(role.equalsIgnoreCase("DataEntryOperator"))
        { try {
                   a.login(email, password, "DataEntryOperator");
               } catch (SQLException ex) {
                   Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
               }

        }
        else{
             try {
                   a.login(email, password, "Cashier");
               } catch (SQLException ex) {
                   Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
               }
        }
    }
});
forgotPassword.addMouseListener(new java.awt.event.MouseAdapter()
{
    public void mouseClicked(java.awt.event.MouseEvent e)
    {String email=javax.swing.JOptionPane.showInputDialog("Enter your email");
    String role=javax.swing.JOptionPane.showInputDialog("Enter your role");
        try {
            a.forgotPass(role,email);
        } catch (SQLException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
});

       
    }
  
   public void changePassScreen(String email, String type)
   {JFrame f= new JFrame("Change Password");
   f.setBounds(0,0,400,300);
   JLabel display= new JLabel("Change Password");
   JPanel passPanel= new JPanel(new GridLayout(3,2));
   
   JLabel newpasslb= new JLabel("New Password: ");
   JLabel confirmpasslb= new JLabel("Confirm Password: ");
   JPasswordField newpassfield= new JPasswordField();
   JPasswordField confirmpassfield= new JPasswordField();
   JButton change= new JButton("Change Password");
   change.addActionListener(new ActionListener()
   {
       @Override
       public void actionPerformed(ActionEvent ae)
       {String newpass= new String(newpassfield.getPassword());
       String confirmpass= new String(confirmpassfield.getPassword());
           try {
               a.changePass(newpass,confirmpass,email,type);
           } catch (SQLException ex) {
               Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }
   });
   
       passPanel.add(newpasslb);
       passPanel.add(newpassfield);
       passPanel.add(confirmpasslb);
       passPanel.add(confirmpassfield);
       f.add(change,BorderLayout.SOUTH);
       f.add(display,BorderLayout.NORTH);
   f.add(passPanel,BorderLayout.CENTER);
   f.setDefaultCloseOperation(EXIT_ON_CLOSE);
   f.setVisible(true);
   }
  
}
