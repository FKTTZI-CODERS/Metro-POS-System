
package metropos.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
        JPanel btnPanel= new JPanel(new GridLayout(3,0));
        setLayout(new BorderLayout());
        btnPanel.add(branch);
        btnPanel.add(bm);
        btnPanel.add(viewR);
        add(new JLabel("--- Super Admin Dashboard ---"),BorderLayout.NORTH);
        add(btnPanel,BorderLayout.CENTER);
        add(cancel,BorderLayout.SOUTH);
    }
    private void viewReports() throws SQLException
    { 
        String code=JOptionPane.showInputDialog("Enter the branch code");
        if(s.viewReports(code))
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
        JButton today ,weekly, monthly, yearly,specific;
        today= new JButton("Today");
        weekly= new JButton("Weekly");
        monthly=new JButton("Monthly");
        yearly=new JButton("Yearly");
        specific= new JButton("Specific Range");
        p1.add(today);
        p1.add(weekly);
        p1.add(monthly);
        p1.add(yearly);
        p1.add(specific);
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
          specific.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent ae)
           {reportType(code, "Specific Range");}
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

                // Validate that the user provided both dates
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
    private void profitReport(String code, String duration,String start,String end) throws SQLException
    {dispose();
    JFrame f= new JFrame(duration+" Profit Report");
    f.setBounds(0,0,500,600);
    f.setLayout(new BorderLayout());
   JLabel displayMsg= new JLabel("Profit Report for Branch: "+ code + " ( "+duration+" ) ",JLabel.CENTER);
    f.add(displayMsg,BorderLayout.NORTH);
String[] profitreport={"Product Name", "Profit"};
Object[][] profit= s.getProfit(code,duration,start,end);
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
Object[][] stock= s.getRemainingStockData(code,duration);
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
Object[][] sales= s.getSalesData(code,duration);
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
    private void createBranch() 
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
    private void createBranchManager() throws SQLException
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

