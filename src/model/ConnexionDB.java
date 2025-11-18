package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnexionDB {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/gestionetudiants?useSSL=false&serverTimezone=UTC";


    private static final String DB_USER = "root";


    private static final String DB_PASS = "";


    public static Connection getConnection() throws SQLException {
        try {

            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        } catch (SQLException e) {
            System.err.println("ERREUR de connexion à la BDD : " + e.getMessage());

            throw e;
        }
    }

}