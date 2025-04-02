package dreamhome.dao;

import dreamhome.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyImageDAO {

    public static void insertImage(int propertyId, String path) {
        String query = "INSERT INTO propertyimage (propertyId, imagePath) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, propertyId);
            stmt.setString(2, path);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getImagePaths(int propertyId) {
        List<String> paths = new ArrayList<>();
        String query = "SELECT imagePath FROM property_image WHERE propertyId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, propertyId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                paths.add(rs.getString("imagePath"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paths;
    }
}
