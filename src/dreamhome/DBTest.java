package dreamhome;

import dreamhome.utils.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTest {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            System.out.println("✅ Connected to the database successfully!");

            // Optional test query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NOW()");
            if (rs.next()) {
                System.out.println("🕒 Current DB Time: " + rs.getString(1));
            }            

        } catch (Exception e) {
            System.err.println("❌ Connection failed: " + e.getMessage());
        }
    }
}
