package metropos.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConnection {
    private static DBConnection instance; // Singleton instance
    private Connection con;

    // Private constructor to prevent instantiation
    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost:3306/pos";
            con = DriverManager.getConnection(connectionUrl, "root", "");
            if (con != null) {
                System.out.println("Connected");
            } else {
                System.out.println("Not connected");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    // Public method to provide access to the instance
    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    // Method to get the connection
    public Connection getConnection() {
        return con;
    }
}
