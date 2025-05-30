package com.farmacia.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3307/sistemagestionfarmaciasql";
    private static final String USER = "Luis";
    private static final String PASSWORD = "12345";
    private static Connection instance;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (instance == null || instance.isClosed()) {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                instance = DriverManager.getConnection(URL, USER, PASSWORD);
                instance.setAutoCommit(false);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MariaDB JDBC Driver no encontrado", e);
            }
        }
        return instance;
    }

    public static void closeConnection() {
        if (instance != null) {
            try {
                if (!instance.isClosed()) {
                    instance.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static void rollbackConnection() {
        if (instance != null) {
            try {
                if (!instance.getAutoCommit() && !instance.isClosed()) {
                    instance.rollback();
                }
            } catch (SQLException e) {
                System.err.println("Error al hacer rollback: " + e.getMessage());
            }
        }
    }
}