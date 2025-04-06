package dreamhome.dao;

import dreamhome.model.Staff;
import dreamhome.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    public static Staff getStaffByEmail(String email) {
        String query = "SELECT * FROM staff WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Staff(
                    rs.getInt("staffId"),
                    rs.getString("fullName"),
                    rs.getString("role"),
                    rs.getFloat("salary"),
                    rs.getString("email"),
                    rs.getString("passwordHash"),
                    rs.getString("phone"),
                    rs.getDate("dateJoined").toLocalDate(),
                    rs.getInt("branchId")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insertStaff(Staff staff) {
        String sql = "INSERT INTO staff (fullName, role, salary, email, passwordHash, phone, dateJoined, branchId) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, staff.getFullName());
            stmt.setString(2, staff.getRole());
            stmt.setDouble(3, staff.getSalary());
            stmt.setString(4, staff.getEmail());
            stmt.setString(5, staff.getPasswordHash());
            stmt.setString(6, staff.getPhone());
            stmt.setInt(7, staff.getBranchId());
    
            return stmt.executeUpdate() == 1;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }   
    
    public static List<Staff> getStaffByBranch(int branchId) {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff WHERE branchId = ?";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Staff staff = new Staff(
                    rs.getInt("staffId"),
                    rs.getString("fullName"),
                    rs.getString("role"),
                    rs.getDouble("salary"),
                    rs.getString("email"),
                    rs.getString("passwordHash"),
                    rs.getString("phone"),
                    rs.getDate("dateJoined").toLocalDate(),
                    rs.getInt("branchId")
                );
                staffList.add(staff);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return staffList;
    }

    public static boolean updateStaffProfile(Staff staff) {
        String sql = "UPDATE staff SET FullName = ?, Phone = ?, Email = ? WHERE StaffID = ?";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, staff.getFullName());
            stmt.setString(2, staff.getPhone());
            stmt.setString(3, staff.getEmail());
            stmt.setInt(4, staff.getStaffId());
    
            return stmt.executeUpdate() == 1;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean updatePassword(int staffId, String newHash) {
        String sql = "UPDATE staff SET PasswordHash = ? WHERE StaffID = ?";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, newHash);
            stmt.setInt(2, staffId);
    
            return stmt.executeUpdate() == 1;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }    
    
}
