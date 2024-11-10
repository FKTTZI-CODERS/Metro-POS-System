
package metropos.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class SuperAdminDashboardView extends JFrame
{JButton branch, bm,viewR;
    public SuperAdminDashboardView()
    {
        setTitle("Super Admin Dashboard");
   init();
   setBounds(0,0,500,600);
   setDefaultCloseOperation(EXIT_ON_CLOSE);
   setVisible(true);
    }
    public void init()
    {
        branch= new JButton("Create Branch");
        bm= new JButton("Create Branch Manager");
        viewR= new JButton("View Reports");
        setLayout(new GridLayout(3,0));
        add(branch);
        add(bm);
        add(viewR);
    }
}
