package database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer { // AKtualnie pod 0 jest dodany ADMIN Mateusz!

    public static void initialize() {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                role TEXT NOT NULL
            );
        """;

        String createVotesTable = """
            CREATE TABLE IF NOT EXISTS votes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                student_id INTEGER NOT NULL,
                candidate_id INTEGER NOT NULL
            );
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createUsersTable);
            stmt.execute(createVotesTable);

            System.out.println("Tabele zostały utworzone (jeśli ich wcześniej nie było).");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Błąd podczas tworzenia tabel!");
        }
    }
}

