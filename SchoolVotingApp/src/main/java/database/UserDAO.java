package database;
// DAO - Data Access Object
import model.Admin;
import model.Candidate;
import model.Student;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserDAO {
    public static void addUser(int id, String name, String role) {
        String sql = "INSERT INTO users (id, name, role) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);             // set ID
            pstmt.setString(2, name);       // set nazwa
            pstmt.setString(3, role);       // set rola

            pstmt.executeUpdate();          // Wykonanie zapytania SQL

            System.out.println("Użytkownik dodany pomyślnie!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Błąd podczas dodawania użytkownika!");
        }
    }

    public static User login(String userID) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userID); // od 1 sie zaczuna nie 0!
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");

                // AKTUALNIE ZAWSZE USER ALE MOZE BY TRZEBA ZMIENIĆ
                User user = null;

                switch (role) {
                    case "STUDENT":
                        user = new Student();
                        break;
                    case "CANDIDATE":
                        user = new Candidate();
                        break;
                    case "ADMIN":
                        user = new Admin();
                        break;
                    default:
                        user = new User() {};
                        break;
                }

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setRole(role);

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // gdy nie znaleziono użytkownika / wystąpił błąd to null
        return null;
    }


    public static void updateToCandidate(int id) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "CANDIDATE");
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

            System.out.println("Użytkownik zmienił status na kandydata!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Błąd podczas update'u użytkownika!");
        }
    }

    public static void downgradeToStudent(int id) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "STUDENT");
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Użytkownik zmienił status na studenta!");
        } catch (SQLException e){
            e.printStackTrace();
            System.err.println("Błąd podczas downgrade'u użytkownika!");
        }
    }

}
