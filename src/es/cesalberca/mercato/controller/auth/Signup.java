package es.cesalberca.mercato.controller.auth;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author César Alberca
 */
public class Signup {
    public static boolean isUserAvailable(User u) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbh = new DatabaseHandler();
        ResultSet rs = null;
        rs = dbh.selectAll(DatabaseConnector.getConnection(), u);
        User existingUser = null;
        
        while (rs.next()) {
            existingUser = new User(rs.getString("NAME"), rs.getString("PASSWORD"));
            if (existingUser.getName().equals(u.getName())) {
                // Si encuentra al menos una coincidencia detiene el bucle.
                return false;
            }
        }
        
        // Si ha llegado a este punto eso quiere decir que no hay ninguna coincidencia.
        return true;
    }
    
    public static void register(User u) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbh = new DatabaseHandler();
        dbh.insertInto(DatabaseConnector.getConnection(), u);
    }
}
