package database;

import java.sql.*;

public class DatabaseManager {
    private static Connection connection;

    // Method to connect to the Database
    public static void connect() {
        try {
            // Replace with your actual local database URL, username, and password later
            String url = "jdbc:mysql://localhost:3306/your_test_db";
            String user = "root";
            String password = "your_password";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }

    // Method to run a query and get a single text/string value back
    public static String getSingleValue(String query, String columnName) {
        String result = null;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result = rs.getString(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Method to close the database connection
    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}