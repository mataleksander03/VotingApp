package database;

import model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static String[] getCandidateDetails(int id) {
        String sql = "SELECT postulates, phone_nr, email, why_me FROM candidates WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                String postulates = rs.getString("postulates");
                String phone_nr = rs.getString("phone_nr");
                String email = rs.getString("email");
                String why_me = rs.getString("why_me");
                return new String[]{phone_nr, email, why_me, postulates};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[]{null, null, null, null};
    }

    public static List<Candidate> getAllApprovedCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        String sql = "SELECT id, name, postulates, approved FROM candidates";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String postulates = rs.getString("postulates");
                int approved = rs.getInt("approved");

                if(approved == 0) { // KONIECZNIE ZMIENIĆ NA 1!!! (TERAZ TAK TYLKO DO TESTÓW JEST 0!!!)
                    Candidate candidate = new Candidate(id);
                    candidate.setName(name);
                    candidate.setPostulates(postulates);
                    candidate.setApproved(approved);
                    candidates.add(candidate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    public static void updatePostulates(int id, String postulates) {
        String sql = "UPDATE candidates SET postulates = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, postulates);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
