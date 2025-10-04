import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String URL = "jdbc:mysql://localhost:3306/expense_tracker";
    static final String USER = "root";
    static final String PASSWORD = "system"; 
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== Expense Tracker ===");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> addExpense(scanner);
                case 2 -> viewExpenses();
                case 3 -> { 
                    System.out.println("Bye");
                    return; 
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
    
    static void addExpense(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("Description: ");
            String desc = scanner.nextLine();
            
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            System.out.print("Category: ");
            String category = scanner.nextLine();
            
            String sql = "INSERT INTO expenses (description, amount, category, date) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, desc);
            stmt.setDouble(2, amount);
            stmt.setString(3, category);
            
            stmt.executeUpdate();
            System.out.println("Expense added!");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    static void viewExpenses() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM expenses ORDER BY date DESC");
            
            System.out.println("\nYour Expenses:");
            double total = 0;
            while (rs.next()) {
                System.out.printf("ID: %d | %s - $%.2f (%s) on %s\n",
                    rs.getInt("id"), rs.getString("description"), 
                    rs.getDouble("amount"), rs.getString("category"),
                    rs.getDate("date"));
                total += rs.getDouble("amount");
            }
            System.out.printf("Total: $%.2f\n", total);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}