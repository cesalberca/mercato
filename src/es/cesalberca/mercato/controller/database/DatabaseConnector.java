package es.cesalberca.mercato.controller.database;

import static es.cesalberca.mercato.controller.database.DatabaseHandler.DATABASE_TYPE;
import es.cesalberca.mercato.model.database.Mysql;
import es.cesalberca.mercato.model.database.Sqlite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author César Alberca
 */
public class DatabaseConnector extends DatabaseHandler{

    @Override
    public Connection getConnection(Sqlite sqlite) throws ClassNotFoundException, SQLException {
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(sqlite.getJDBC_CONNECTION() + sqlite.getSQLITE_DIRECTORY());
        return c;
    }

    @Override
    public Connection getConnection(Mysql mysql) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
