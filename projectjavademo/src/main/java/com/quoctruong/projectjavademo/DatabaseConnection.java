package com.quoctruong.projectjavademo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
    // Thông tin kết nối
    private static final String URL = "jdbc:mysql://localhost:3306/shop_quan_ao";
    private static final String USER = "myuser";
    private static final String PASSWORD = "mypassword";
    
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            // 1. Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!");
            
            // 2. Tạo kết nối
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database successfully!");
            
            // 3. Tạo statement
            stmt = conn.createStatement();
            System.out.println("Created statement successfully!");
            
            // 4. Truy vấn dữ liệu
            String query = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("\nUsers in database:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("ID: " + id + ", Username: " + username + ", Password: " + password);
            }
            
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("\nConnection closed.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
