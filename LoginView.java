
package metropos.view;
import com.sun.jdi.connect.spi.Connection;
import metropos.controller.AuthenticateController;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame 
{JTextField email;
JLabel emailLB,passLB;
JPasswordField pass;
AuthenticateController a;
    JButton superadmin,branchmanager,deo,cashier;
   public LoginView()
   {
       a= new AuthenticateController();
       setTitle("LOGIN VIEW");
   init();
   setBounds(0,0,500,600);
   setDefaultCloseOperation(EXIT_ON_CLOSE);
   setVisible(true);
       
   }
   public void init()
   {   
       setLayout(new GridLayout(4,0));
       superadmin= new JButton("Login as Super Admin");
       superadmin.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {
               saLogin();
           }
       });
       add(superadmin);
       branchmanager= new JButton("Login as Branch Manager");
       branchmanager.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {
               bmLogin();
           }
       });
       add(branchmanager);
       deo= new JButton("Login as Data Entry Operator");
       add(deo);
       deo.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {
               deoLogin();
           }
       });
       cashier= new JButton("Login as Cashier");
       add(cashier);
       cashier.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {
               cashierLogin();
           }
       });
   }
   public void saLogin()
   {dispose();
   JFrame sa= new JFrame("Super Admin Login");
   sa.setLayout(new BorderLayout());
   JButton login = new JButton("Login");
   login.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {
          String getemail=email.getText();
          String getpass= new String(pass.getPassword());
               try {
                   a.login(getemail, getpass, "SuperAdmin");
               } catch (SQLException ex) {
                   Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
               }
      }
   });
       JPanel superadminpanel;
   superadminpanel= new JPanel(new  GridLayout(2,2));
   emailLB= new JLabel ("Email: ");
   passLB= new JLabel("Password: ");
   email= new JTextField();
   pass= new JPasswordField();
   superadminpanel.add(emailLB);
   superadminpanel.add(email);
   superadminpanel.add(passLB);   
   superadminpanel.add(pass);
   sa.add(superadminpanel,BorderLayout.CENTER);
   sa.add(login,BorderLayout.SOUTH);
   sa. setBounds(0,0,500,600);
   sa.setDefaultCloseOperation(EXIT_ON_CLOSE);
   sa.setVisible(true);
   }
   public void cashierLogin()
   { dispose();
   JFrame sa= new JFrame("Cashier Login");
   sa.setLayout(new BorderLayout());
   JButton login = new JButton("Login");
   login.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {
          String getemail=email.getText();
          String getpass= new String(pass.getPassword());
               try {
                   a.login(getemail, getpass, "Cashier");
               } catch (SQLException ex) {
                   Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
               }
      }
   });
       JPanel superadminpanel;
   superadminpanel= new JPanel(new  GridLayout(2,2));
   emailLB= new JLabel ("Email: ");
   passLB= new JLabel("Password: ");
   email= new JTextField();
   pass= new JPasswordField();
   superadminpanel.add(emailLB);
   superadminpanel.add(email);
   superadminpanel.add(passLB);   
   superadminpanel.add(pass);
   sa.add(superadminpanel,BorderLayout.CENTER);
   sa.add(login,BorderLayout.SOUTH);
   sa. setBounds(0,0,500,600);
   sa.setDefaultCloseOperation(EXIT_ON_CLOSE);
   sa.setVisible(true);
       
   }
   public void deoLogin()
   {
        dispose();
   JFrame sa= new JFrame("Data Entry Operator Login");
   sa.setLayout(new BorderLayout());
   JButton login = new JButton("Login");
   login.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {
          String getemail=email.getText();
          String getpass= new String(pass.getPassword());
               try {
                   a.login(getemail, getpass, "DataEntryOperator");
               } catch (SQLException ex) {
                   Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
               }
      }
   });
       JPanel superadminpanel;
   superadminpanel= new JPanel(new  GridLayout(2,2));
   emailLB= new JLabel ("Email: ");
   passLB= new JLabel("Password: ");
   email= new JTextField();
   pass= new JPasswordField();
   superadminpanel.add(emailLB);
   superadminpanel.add(email);
   superadminpanel.add(passLB);   
   superadminpanel.add(pass);
   sa.add(superadminpanel,BorderLayout.CENTER);
   sa.add(login,BorderLayout.SOUTH);
   sa. setBounds(0,0,500,600);
   sa.setDefaultCloseOperation(EXIT_ON_CLOSE);
   sa.setVisible(true);
   }
   public void bmLogin()
   {
       dispose();
   JFrame sa= new JFrame("Branch Manager Login");
   sa.setLayout(new BorderLayout());
   JButton login = new JButton("Login");
   login.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {
          String getemail=email.getText();
          String getpass= new String(pass.getPassword());
               try {
                   a.login(getemail, getpass, "BranchManager");
               } catch (SQLException ex) {
                   Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
               }
      }
   });
       JPanel superadminpanel;
   superadminpanel= new JPanel(new  GridLayout(2,2));
   emailLB= new JLabel ("Email: ");
   passLB= new JLabel("Password: ");
   email= new JTextField();
   pass= new JPasswordField();
   superadminpanel.add(emailLB);
   superadminpanel.add(email);
   superadminpanel.add(passLB);   
   superadminpanel.add(pass);
   sa.add(superadminpanel,BorderLayout.CENTER);
   sa.add(login,BorderLayout.SOUTH);
   sa. setBounds(0,0,500,600);
   sa.setDefaultCloseOperation(EXIT_ON_CLOSE);
   sa.setVisible(true);
   }
}
