package es.cesalberca.mercato.controller.auth;

import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase dedicada a la autenticación del usuario.
 * @author César Alberca
 */
public class Login {
    // Intentos disponibles
    public static int tries = 3;
    
    /**
     * Comprueba si el usuario que se intenta loggear es válido y que su contraseña es correcta.
     * @param userTryingToLogin Usuario que intenta iniciar sesión.
     * @return Usuario válido o no.
     * @throws SQLException Error de sql.
     * @throws ClassNotFoundException Error de carga del jdbc.
     */
    public static Boolean isValidUser(User userTryingToLogin) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        User user = null;
        DatabaseHandler dbh = new DatabaseHandler();
        rs = dbh.search(DatabaseConnector.getConnection(), userTryingToLogin);
        
        while (rs.next()) {
            user = new User(rs.getString("NAME"), rs.getString("PASSWORD"));
        }
        
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
