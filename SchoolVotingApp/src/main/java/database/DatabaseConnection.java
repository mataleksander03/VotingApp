package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:schoolvoting.db";

    public static Connection getConnection() throws SQLException { // static wiec moge wywolac bez tworzenia obiektu
        try {
            Class.forName("org.sqlite.JDBC"); // laduejmy klase sterownika SQLite
            return DriverManager.getConnection(URL); // tworzy poloczenie z DB i zwraca obiekt, który można użyć do zapytań SQL
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Brak sterownika do bazy danych.");
        }
    }
}
