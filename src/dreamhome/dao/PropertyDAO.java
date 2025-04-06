package dreamhome.dao;

import dreamhome.model.Property;
import dreamhome.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO {

    public static boolean insertProperty(Property property) {
        String query = "INSERT INTO property (address, type, rooms, rent, isAvailable, branchId, ownerId, staffId) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, property.getAddress());
            stmt.setString(2, property.getType());
            stmt.setInt(3, property.getRooms());
            stmt.setFloat(4, property.getRent());
            stmt.setBoolean(5, property.isAvailable());
            stmt.setInt(6, property.getBranchId());
            stmt.setInt(7, property.getOwnerId());
            stmt.setInt(8, property.getStaffId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<Property> getPropertiesByBranch(int branchId, boolean onlyAvailable) {
        List<Property> properties = new ArrayList<>();
        String query = "SELECT * FROM property WHERE branchId = ?" + (onlyAvailable ? " AND isAvailable = 1" : "");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Property p = new Property(
                    rs.getInt("propertyId"),
                    rs.getString("address"),
                    rs.getString("type"),
                    rs.getInt("rooms"),
                    rs.getFloat("rent"),
                    rs.getBoolean("isAvailable"),
                    rs.getInt("branchId"),
                    rs.getInt("ownerId"),
                    rs.getInt("staffId")
                );
                properties.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static void updateAvailability(int propertyId, boolean isAvailable) {
        String query = "UPDATE property SET isAvailable = ? WHERE propertyId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, isAvailable);
            stmt.setInt(2, propertyId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Property> getAllProperties() {
        List<Property> properties = new ArrayList<>();
        String sql = "SELECT * FROM property";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                Property property = new Property(
                    rs.getInt("PropertyID"),
                    rs.getString("Address"),
                    rs.getString("Type"),
                    rs.getInt("Rooms"),
                    rs.getFloat("Rent"),
                    rs.getBoolean("IsAvailable"),
                    rs.getInt("BranchID"),
                    rs.getInt("OwnerID"),
                    rs.getInt("StaffID")
                );
                properties.add(property);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return properties;
    }
    
}
