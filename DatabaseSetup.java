import java.sql.*;

public class DatabaseSetup {
    static final String URL = "jdbc:mysql://localhost:3306/";
    static final String USER = "root";
    static final String PASSWORD = "system";
    
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement stmt = conn.createStatement();
            
            stmt.execute("CREATE DATABASE IF NOT EXISTS expense_tracker");
            stmt.execute("USE expense_tracker");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS expenses (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "description VARCHAR(255) NOT NULL, " +
                "amount DECIMAL(10,2) NOT NULL, " +
                "category VARCHAR(100) NOT NULL, " +
                "date DATE NOT NULL)");
            
            System.out.println("Database setup complete!");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}