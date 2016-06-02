package es.cesalberca.mercato.controller.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona las conexiones a la base de datos.
 * @author César Alberca
 */
public class DBConnector {
    // Directorio de sqlite
    private static final String SQLITE_DIRECTORY = "bbdd/mercatodb";
    // Conexión con el jdbc
    private static final String JDBC_CONNECTION = "jdbc:sqlite:";
    // Conexión a la bbbdd
    private static Connection connection = null;
    
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Consigue una nueva conexión a la base de datos.
     * @return Conexión a la base de datos.
     * @throws ClassNotFoundException Clase no encontrada.
     * @throws SQLException Error al conectarse.
     */
    public static void newConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        // Sqlite crea una nueva base de datos si no encuentra una en esa ruta. Con lo que primeramente tendremos que comprobar que ese fichero existe.
        File f = new File(SQLITE_DIRECTORY);
        if (f.exists() && !f.isDirectory()) {
            connection = DriverManager.getConnection(JDBC_CONNECTION + SQLITE_DIRECTORY);
        } else {
            throw new ClassNotFoundException();
        }
    }

    /**
     * Desconecta de la bbdd habiéndole dado la conexión.
     * @throws SQLException Error al desconectar.
     */
    public static void disconnect() throws SQLException {
        connection.close();
    }
}
