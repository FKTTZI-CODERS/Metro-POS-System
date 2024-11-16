package metropos.view;

import metropos.controller.AuthenticateController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ChangePasswordView extends JFrame {
    private JTextField newPasswordField;
    private JTextField confirmPasswordField;
    private String email;

    public ChangePasswordView(String email) {
        this.email = email;
        setTitle("Change Password");
        init();
        setBounds(0, 0, 400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void init() {
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel newPasswordLabel = new JLabel("New Password: ");
        newPasswordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password: ");
        confirmPasswordField = new JPasswordField();

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = newPasswordField.getText();
                String confirmPassword = confirmPasswordField.getText();

                if (newPassword.equals(confirmPassword)) {
                    AuthenticateController controller = new AuthenticateController();
                    try {
                        
                        if (controller.updatePassword(email, newPassword)) {
                            JOptionPane.showMessageDialog(null, "Password updated successfully!");
                            dispose();
                            new BranchManagerDashboardView();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error updating password!");
                        }
                    } catch (SQLException ex) {
                        
                        JOptionPane.showMessageDialog(null, "Error occurred while updating password: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match!");
                }
            }
        });

        add(newPasswordLabel);
        add(newPasswordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(saveButton);
    }
}
