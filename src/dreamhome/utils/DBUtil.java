package dreamhome.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(
                    ConfigLoader.getDbUrl(),
                    ConfigLoader.get("db.user"),
                    ConfigLoader.get("db.password")
                );
            } catch (SQLException e) {
                System.err.println("⚠ Failed to connect to database: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.err.println("⚠ Failed to close connection: " + e.getMessage());
        }
    }
}
