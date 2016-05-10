package es.cesalberca.mercato.controller.auth;

import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.model.User;
import es.cesalberca.mercato.model.database.Sqlite;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author César Alberca
 */
public class Login {
    public static int tries = 3;
    
    public static Boolean isValidUser(User userTryingToLogin) throws SQLException, ClassNotFoundException {
        Connection c = null;
        ResultSet rs = null;
        User user = null;
        DatabaseConnector dbc = new DatabaseConnector();
        dbc.getConnection(new Sqlite());
        c = dbc.getConnection(new Sqlite());
        DatabaseHandler.search(c, userTryingToLogin);
        
        while (rs.next()) {
            user = new User(rs.getString("NAME"), rs.getString("PASSWORD"));
        }
        
        DatabaseHandler.disconnect(c);
        
        if (user != null && userTryingToLogin.getPassword().equals(user.getPassword())) {
            return true;
        } else if (tries > 0) {
            tries--;
            return false;
        } else {
            return false;
        }
    }
}
