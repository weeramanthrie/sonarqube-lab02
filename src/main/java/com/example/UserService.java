package main.java.com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UserService {

    // SECURITY: Use environment variable instead of hardcoded password
    private String dbUser = "root";
    private String dbPassword = System.getenv("DB_PASSWORD"); // set in environment

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    public void findUser(String username) throws SQLException {
        String url = "jdbc:mysql://localhost/db";

        // Use try-with-resources to automatically close Connection and PreparedStatement
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement("SELECT name FROM users WHERE name = ?")) {

            stmt.setString(1, username); // Prevent SQL Injection

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LOGGER.log(Level.INFO, () -> {
                        try {
                            return "User: " + rs.getString("name");
                        } catch (SQLException e) {
                            return "User: (unable to retrieve name)";
                        }
                    });
                }
            }
        }
    }

    // SMELL: You may remove unused method if not needed
    public void notUsed() {
        LOGGER.log(Level.FINE, () -> "I am never called");
    }
}
