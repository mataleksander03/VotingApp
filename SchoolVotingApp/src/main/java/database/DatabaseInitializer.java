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

        String createElectionStatusTable = """
            CREATE TABLE IF NOT EXISTS election_status (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                closed BOOLEAN NOT NULL DEFAULT 0
            );
        """;

        // default - otwarte = 0
        String insertDefaultElectionStatus = """
            INSERT INTO election_status (id, closed)
                SELECT 1, 0
                WHERE NOT EXISTS (
                    SELECT 1 FROM election_status WHERE id = 1
                );
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createUsersTable);
            stmt.execute(createVotesTable);
            stmt.execute(createCandidatesTable);
            stmt.execute(createElectionStatusTable);
            stmt.execute(insertDefaultElectionStatus);

            System.out.println("Tabele zostały utworzone (jeśli ich wcześniej nie było).");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Błąd podczas tworzenia tabel!");
        }
    }
}

