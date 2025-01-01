package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VoteDAO {
    public static void addVote(int studentId, int candidateId) {
        String sql = "INSERT INTO votes (student_id, candidate_id) VALUES (?, ?)";
        connectAndSet(studentId, candidateId, sql);
    }

    public static boolean updateVote(int studentId, int newCandidateId) {
        String sql = "UPDATE votes SET candidate_id = ? WHERE student_id = ?";
        return connectAndSet(studentId, newCandidateId, sql);
    }

    private static boolean connectAndSet(int studentId, int candidateId, String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, candidateId);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
