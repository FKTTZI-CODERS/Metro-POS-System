
package metropos.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import metropos.controller.SuperAdminController;

public class SuperAdminDashboardView extends JFrame
{JButton branch, bm,viewR,cancel;
SuperAdminController s;
    public SuperAdminDashboardView()
    {s= new SuperAdminController();
        setTitle("Super Admin Dashboard");
   init();
   setBounds(0,0,500,600);
   setDefaultCloseOperation(EXIT_ON_CLOSE);
   setVisible(true);
    }
    public void init()
    {
        branch= new JButton("Create Branch");
       branch.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {
              createBranch(); 
           }

            
        });
       cancel= new JButton("Cancel");
       cancel.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {
              dispose();
           }

            
        });
        bm= new JButton("Create Branch Manager");
        bm.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {
               try { 
                   createBranchManager();
               } catch (SQLException ex) {
                   Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
               }
           }

            
        });
        viewR= new JButton("View Reports");
        JPanel btnPanel= new JPanel(new GridLayout(3,0));
        setLayout(new BorderLayout());
        btnPanel.add(branch);
        btnPanel.add(bm);
        btnPanel.add(viewR);
        add(new JLabel("--- Super Admin Dashboard ---"),BorderLayout.NORTH);
        add(btnPanel,BorderLayout.CENTER);
        add(cancel,BorderLayout.SOUTH);
    }
    public void createBranch() 
    {dispose();
    cancel= new JButton("Cancel");
    JPanel btnpanel= new JPanel(new FlowLayout());
    JLabel bcode,bname,city,address,phone;
JTextField codeField,nameField,phoneField,addressField;
   JFrame sa= new JFrame("Create Branch Portal");
   sa.setLayout(new BorderLayout());
   JButton done = new JButton("Done");
  
   bcode= new JLabel("Branch Code: ");
   codeField= new JTextField();
   bname= new JLabel("Branch Name: ");
   nameField= new JTextField();
   address=new JLabel("Address: ");
   addressField= new JTextField();
   phone=new JLabel("Phone: ");
   phoneField= new JTextField();
   city= new JLabel("City: ");
   String[] cities= {"Lahore", "Karachi","Islamabad","Rawalpindi","Quetta","Peshawar","Faisalabad","Multan"};
   JComboBox<String> citycombo = new JComboBox<>(cities);
       JPanel branchpanel;
  branchpanel= new JPanel(new  GridLayout(6,2));
   branchpanel.add(bcode);
   branchpanel.add(codeField);
   branchpanel.add(bname);
   branchpanel.add(nameField);
   branchpanel.add(address);
   branchpanel.add(addressField);
   branchpanel.add(phone);
   branchpanel.add(phoneField);
   branchpanel.add(city);
   branchpanel.add(citycombo);
  btnpanel.add(cancel);
  btnpanel.add(done);
   JLabel title= new JLabel("--- Create a Branch ---");
   sa.add(title,BorderLayout.NORTH);
   sa.add(branchpanel,BorderLayout.CENTER);
   sa.add(btnpanel,BorderLayout.SOUTH);
   cancel.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {sa.dispose();
           new SuperAdminDashboardView();
           }
           });
   done.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           { String getcode=codeField.getText();
   String getname=nameField.getText();
   String getaddress= addressField.getText();
   String getphone=phoneField.getText();
   String getCity=(String)citycombo.getSelectedItem();
               try {
                   s.createBranch(getcode, getname, getCity, getphone, getaddress);
               } catch (SQLException ex) {
                   Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
   });
   sa. setBounds(0,0,500,600);
   sa.setDefaultCloseOperation(EXIT_ON_CLOSE);
   sa.setVisible(true);
   }
    public void createBranchManager() throws SQLException
    {dispose();
       JLabel bmname,bmemail,bcode,salary;
       JTextField nameField,emailField,salaryField;
       JComboBox<String> branchcodeBox;
       ArrayList<String> availableCodes=s.getAvailableCodes();
       branchcodeBox=new JComboBox<>(availableCodes.toArray(new String[0]));
        JFrame sa= new JFrame("Create Branch Portal");
        JPanel bmpanel= new JPanel(new GridLayout(5,2,10,10));
        bmname= new JLabel("Branch Manager Name: ");
        bmpanel.add(bmname);
        nameField=new JTextField();
         bmpanel.add(nameField);
         bmemail= new JLabel("Email: ");
         bmpanel.add(bmemail);
         emailField= new JTextField();
         bmpanel.add(emailField);
         bcode= new JLabel("Branch Code: ");
         bmpanel.add(bcode);
         bmpanel.add(branchcodeBox);
         salary= new JLabel("Salary: ");
         bmpanel.add(salary);
         salaryField= new JTextField();
         bmpanel.add(salaryField);
         
   sa.setLayout(new BorderLayout());
   JButton done = new JButton("Done");
   JPanel btnpanel= new JPanel(new FlowLayout());
   cancel= new JButton("Cancel");
   btnpanel.add(cancel);
   btnpanel.add(done);
   sa.add(new JLabel("--- Branch Manager Form ---"),BorderLayout.NORTH);
   sa.add(bmpanel,BorderLayout.CENTER);
   sa.add(btnpanel,BorderLayout.SOUTH);
   sa. setBounds(0,0,500,600);
   sa.setDefaultCloseOperation(EXIT_ON_CLOSE);
   sa.setVisible(true);
   cancel.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {sa.dispose();
           new SuperAdminDashboardView();
           }
           });
   done.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {String getname=nameField.getText();
           String getemail=emailField.getText();
           String getSalary=salaryField.getText();
             String getcode=(String)branchcodeBox.getSelectedItem();
               try {
                   s.createBranchManager(getname,getemail,getSalary,getcode);
               } catch (SQLException ex) {
                   Logger.getLogger(SuperAdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
  
    
    }); 
 
}
}

