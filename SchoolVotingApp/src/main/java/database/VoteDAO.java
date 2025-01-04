package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoteDAO {
    public static void addVote(int studentId, int candidateId) {
        String sql = "INSERT INTO votes (student_id, candidate_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, candidateId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateVote(int studentId, int newCandidateId) {
        String sql = "UPDATE votes SET candidate_id = ? WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newCandidateId);
            stmt.setInt(2, studentId);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Czy dany student zagłosował już na danego kandydata
    public static boolean hasVotedForCandidate(int studentId, int candidateId) {
        String sql = "SELECT COUNT(*) FROM votes WHERE student_id = ? AND candidate_id = ?"; // COUNT zwraca wystąpienia
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, candidateId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Jeśli wynik > 0, to głos/y istnieje/ą get(count)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // jeśli coś pójdzie nie tak to jakby nie głodował
    }

    // Metoda do pobierania liczby głosów dla każdego kandydata
    public static List<String> getVoteResults() {
        String sql = "SELECT candidate_id, COUNT(*) as vote_count FROM votes GROUP BY candidate_id ORDER BY vote_count DESC";
        List<String> results = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int candidateId = rs.getInt("candidate_id");
                int voteCount = rs.getInt("vote_count");
                results.add("Kandydat: " + candidateId + " - głosy: " + voteCount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // Zwraca liczbe wszytkich oddanych głosów
    public static int getTotalVotesCount() {
        String sql = "SELECT COUNT(*) AS total_votes FROM votes";
        int totalVotes = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                totalVotes = rs.getInt("total_votes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalVotes;
    }

}
