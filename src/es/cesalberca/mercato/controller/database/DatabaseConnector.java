package es.cesalberca.mercato.controller.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona las conexiones a la base de datos.
 * @author César Alberca
 */
public class DatabaseConnector {
    private static final String SQLITE_DIRECTORY = "bbdd/mercatodb";
    private static final String JDBC_CONNECTION = "jdbc:sqlite:";
    private static Connection connection = null;
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return connection;
    }
    
    /**
     * Consigue una conexión a la base de datos.
     * @return Conexión a la base de datos.
     * @throws ClassNotFoundException Clase no encontrada.
     * @throws SQLException Error al conectarse.
     */
    public static void newConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(JDBC_CONNECTION + SQLITE_DIRECTORY);
    }

    /**
     * Desconecta de la bbdd habiéndole dado la conexión.
     * @param c Conexión la cual se debe desconectar.
     * @throws SQLException Error al desconectar.
     */
    public static void disconnect() throws SQLException {
        connection.close();
    }
}
