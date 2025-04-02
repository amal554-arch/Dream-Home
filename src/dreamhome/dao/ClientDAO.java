package dreamhome.dao;

import dreamhome.model.Client;
import dreamhome.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public static boolean insertClient(Client client) {
        String sql = "INSERT INTO client (FullName, Phone, Email, PreferredType, MaxRent, StaffID, BranchID) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getFullName());
            stmt.setString(2, client.getPhone());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getPreferredType());
            stmt.setFloat(5, client.getMaxRent());
            stmt.setInt(6, client.getStaffId());
            stmt.setInt(7, client.getBranchId());

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Client> getClientsByStaff(int staffId) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client WHERE StaffID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                clients.add(mapResultSetToClient(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public static List<Client> getClientsByBranch(int branchId) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client WHERE BranchID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                clients.add(mapResultSetToClient(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public static Client getClientById(int clientId) {
        String sql = "SELECT * FROM client WHERE ClientID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToClient(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Client mapResultSetToClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setClientId(rs.getInt("ClientID"));
        client.setFullName(rs.getString("FullName"));
        client.setPhone(rs.getString("Phone"));
        client.setEmail(rs.getString("Email"));
        client.setPreferredType(rs.getString("PreferredType"));
        client.setMaxRent(rs.getFloat("MaxRent"));
        client.setStaffId(rs.getInt("StaffID"));
        client.setBranchId(rs.getInt("BranchID"));
        return client;
    }
}
