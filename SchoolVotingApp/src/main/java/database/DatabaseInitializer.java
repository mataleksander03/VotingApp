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

        String createCandidatesTable = """
            CREATE TABLE IF NOT EXISTS candidates (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                postulates TEXT NOT NULL,
                approved BOOLEAN NOT NULL DEFAULT 0,
                phone_nr TEXT,
                email TEXT,
                why_me TEXT
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
            stmt.execute(createCandidatesTable);

            System.out.println("Tabele zostały utworzone (jeśli ich wcześniej nie było).");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Błąd podczas tworzenia tabel!");
        }
    }
}

