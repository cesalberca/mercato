package es.cesalberca.mercato.controller.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author César Alberca
 */
public class Sqlite extends DatabaseHandler{
    private final String SQLITE_DIRECTORY = "bbdd/mercatodb";
    private final String JDBC_CONNECTION = "jdbc:sqlite:";

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException{
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(this.JDBC_CONNECTION + this.SQLITE_DIRECTORY);
        return c;
    }
}
