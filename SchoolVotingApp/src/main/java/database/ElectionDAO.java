package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ElectionDAO {
    // Sprawdź status wyborów
    public static boolean isElectionClosed() {
        String sql = "SELECT closed FROM election_status WHERE id = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Domyślnie otwarte
    }

    // Zamknij wybory
    public static void closeElection() {
        String sql = "UPDATE election_status SET closed = 1 WHERE id = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
