package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CandidateDAO {
    public static void addCandidate(int id, String name, String postulates) {
        String sql = "INSERT INTO candidates (id, name, postulates) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, postulates);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registerCandidate(int id, String phone_nr, String email, String why_me) {
        String sql = "UPDATE candidates SET phone_nr = ?, email = ?, why_me = ? WHERE id = ?";

        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, phone_nr);
            pstmt.setString(2, email);
            pstmt.setString(3, why_me);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }



    }

    public static void deleteCandidate(int id) {
        String sql = "DELETE FROM candidates WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getApprovalStatus(int id) {
        String sql = "SELECT approved FROM candidates WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                String approvalStatus = rs.getString("approved");
                return Integer.parseInt(approvalStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getRegistartionStatus(int id) {
        String sql = "SELECT phone_nr, email, why_me FROM candidates WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                String phone_nr = rs.getString("phone_nr");
                String email = rs.getString("email");
                String why_me = rs.getString("why_me");
                if(phone_nr != null && email != null && why_me != null) {
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
