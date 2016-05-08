package es.cesalberca.mercato.model;

import es.cesalberca.mercato.controller.database.Sqlite;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Super clase que especifica que sus hijos son objetos que se pueden guardar en base de datos.
 * @author César Alberca
 */
public class DatabaseObject {
    
    //TODO add suport for mysql.
    
    /**
     * Función que guarda un objeto en base de datos. Recibe como parámetro un objeto genérico. El gestor de la base de datos se encargará de hacer el cast de forma automática.
     * @param ob Objeto genérico.
     * @throws SQLException Error de sql.
     * @throws ClassNotFoundException Clase no encontrada.
     */
    public static void save(Object ob) throws ClassNotFoundException, SQLException {
        Connection c = null;
        Sqlite sqlite = new Sqlite();
        c = sqlite.getConnection();
        sqlite.insertInto(c, ob);
        sqlite.disconnect(c);
    }
    
    /**
     * Devuelve todos los registros de ese tipo de obejto.
     * @param ob
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static ResultSet retrieve(Object ob) throws ClassNotFoundException, SQLException {
        Connection c = null;
        ResultSet rs = null;
        Sqlite sqlite = new Sqlite();
        c = sqlite.getConnection();
        rs = sqlite.selectAll(c, ob);
        sqlite.disconnect(c);
        return rs;
    }
}
