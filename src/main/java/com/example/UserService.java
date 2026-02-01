package main.java.com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {

    // SECURITY: Use environment variable instead of hardcoded password
    private String dbUser = "root";
    private String dbPassword = System.getenv("DB_PASSWORD"); // set in environment

    public void findUser(String username) throws Exception {
        String url = "jdbc:mysql://localhost/db";

        // Use try-with-resources to automatically close Connection and PreparedStatement
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE name = ?")) {

            stmt.setString(1, username); // Prevent SQL Injection

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("User: " + rs.getString("name"));
                }
            }
        }
    }

    // SMELL: You may remove unused method if not needed
    public void notUsed() {
        System.out.println("I am never called");
    }
}
