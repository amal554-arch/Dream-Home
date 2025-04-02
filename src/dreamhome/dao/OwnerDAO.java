package dreamhome.dao;

import dreamhome.model.Owner;
import dreamhome.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerDAO {

    public static boolean insertOwner(Owner owner) {
        String query = "INSERT INTO owner (fullName, phone, email, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, owner.getFullName());
            stmt.setString(2, owner.getPhone());
            stmt.setString(3, owner.getEmail());
            stmt.setString(4, owner.getAddress());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean existsByEmailOrPhone(String email, String phone) {
        String query = "SELECT ownerId FROM owner WHERE email = ? OR phone = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, phone);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // True if any record found

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Owner getOwnerById(int id) {
        String query = "SELECT * FROM owner WHERE ownerId = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Owner(
                    rs.getInt("ownerId"),
                    rs.getString("fullName"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Owner> searchOwners(String keyword) {
        List<Owner> owners = new ArrayList<>();
        String query = "SELECT * FROM owner WHERE fullName LIKE ? OR email LIKE ? OR phone LIKE ?";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            String like = "%" + keyword + "%";
            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Owner owner = new Owner(
                    rs.getInt("ownerId"),
                    rs.getString("fullName"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                );
                owners.add(owner);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return owners;
    }

    public static List<Owner> getAllOwners() {
        List<Owner> list = new ArrayList<>();
        String query = "SELECT * FROM owner";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                Owner owner = new Owner(
                    rs.getInt("ownerId"),
                    rs.getString("fullName"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                );
                list.add(owner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return list;
    }    
}
