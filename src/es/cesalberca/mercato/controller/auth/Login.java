package es.cesalberca.mercato.controller.auth;

import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.model.User;
import static es.cesalberca.mercato.view.JFApp.dbh;
import java.sql.SQLException;

/**
 * Clase dedicada a la autenticación del usuario.
 * @author César Alberca
 */
public class Login {
    /**
     * Comprueba si el usuario que se intenta loggear es válido y que su contraseña es correcta.
     * @param userTryingToLogin Usuario que intenta iniciar sesión.
     * @return Usuario válido o no.
     * @throws SQLException Error de sql.
     * @throws ClassNotFoundException Error de carga del jdbc.
     */
    public static Boolean isValidUser(User userTryingToLogin) throws SQLException, ClassNotFoundException {
        User user = (User) dbh.search(DatabaseConnector.getConnection(), userTryingToLogin);
        
        if (user != null && userTryingToLogin.getPassword().equals(user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
