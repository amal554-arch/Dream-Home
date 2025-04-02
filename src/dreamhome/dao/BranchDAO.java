package dreamhome.dao;

import dreamhome.model.Branch;
import dreamhome.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {

    public static List<Branch> getAllBranches() {
        List<Branch> branches = new ArrayList<>();

        String sql = "SELECT * FROM branch";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Branch b = new Branch(
                        rs.getInt("BranchID"),
                        rs.getString("Address"),
                        rs.getString("Phone")
                );
                branches.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return branches;
    }

    public static Branch getBranchById(int id) {
        return getAllBranches().stream()
            .filter(branch -> branch.getBranchID() == id)
            .findFirst()
            .orElse(null);
    }    
}
