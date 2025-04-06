package dreamhome.dao;

import dreamhome.model.Lease;
import dreamhome.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaseDAO {

    public static boolean updateLeaseStatus(int leaseId, String status) {
        String updateLeaseSQL = "UPDATE lease SET Status = ? WHERE LeaseID = ?";
        String updatePropertySQL = "UPDATE property SET IsAvailable = ? WHERE PropertyID = (SELECT PropertyID FROM lease WHERE LeaseID = ?)";
    
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false); // Begin transaction
    
            // Update lease status
            try (PreparedStatement leaseStmt = conn.prepareStatement(updateLeaseSQL)) {
                leaseStmt.setString(1, status);
                leaseStmt.setInt(2, leaseId);
                leaseStmt.executeUpdate();
            }
    
            // Update property availability
            try (PreparedStatement propertyStmt = conn.prepareStatement(updatePropertySQL)) {
                int isAvailable = status.equalsIgnoreCase("Approved") ? 0 : 1;
                propertyStmt.setInt(1, isAvailable);
                propertyStmt.setInt(2, leaseId);
                propertyStmt.executeUpdate();
            }
    
            conn.commit(); // Commit transaction
            return true;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }    

    public static List<Lease> getLeasesForStaff(int staffId) {
        List<Lease> leases = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM lease WHERE StaffID = ? AND Status = 'Pending'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, staffId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                leases.add(new Lease(
                    rs.getInt("LeaseID"),
                    rs.getInt("PropertyID"),
                    rs.getInt("ClientID"),
                    rs.getInt("StaffID"),
                    rs.getDate("StartDate").toString(),
                    rs.getDate("EndDate").toString(),
                    rs.getString("Status"),
                    rs.getDouble("DepositAmount"),
                    rs.getString("PaymentFrequency"),
                    rs.getString("PaymentMethod")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leases;
    }
    
}
