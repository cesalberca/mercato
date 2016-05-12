package es.cesalberca.mercato.controller.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author César Alberca
 */
public class DatabaseConnector extends DatabaseHandler{
    private static final String SQLITE_DIRECTORY = "bbdd/mercatodb";
    private static final String JDBC_CONNECTION = "jdbc:sqlite:";
    private static Connection connection = null;
    
    public DatabaseConnector() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = getNewConnection();
    }

    public static Connection getConnection() {
        return connection;
    }
    
    @Override
    public Connection getNewConnection() throws ClassNotFoundException, SQLException {
        Connection c = DriverManager.getConnection(JDBC_CONNECTION + SQLITE_DIRECTORY);
        return c;
    }

    @Override
    public void disconnect(Connection c) throws SQLException {
        c.close();
    }

    
    
}
