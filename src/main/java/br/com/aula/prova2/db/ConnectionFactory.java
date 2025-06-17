package br.com.aula.prova2.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String URL = "jdbc:postgresql://localhost:5432/prova2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "lamborguini129";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
