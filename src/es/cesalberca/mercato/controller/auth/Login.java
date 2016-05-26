package es.cesalberca.mercato.controller.auth;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.controller.shop.Shop;
import es.cesalberca.mercato.model.User;
import java.sql.SQLException;

/**
 * Clase dedicada a la autenticación del usuario.
 * @author César Alberca
 */
public class Login {
    // Intentos disponibles
    private int tries = 3;
    private boolean isUserLoggedIn = false;
    private Shop shop;
    
    public Login(Shop shop) {
        this.shop = shop;
    }

    public boolean isIsUserLoggedIn() {
        return isUserLoggedIn;
    }
    
    /**
     * Comprueba si el usuario que se intenta loggear es válido y que su contraseña es correcta.
     * @param userTryingToLogin Usuario que intenta iniciar sesión.
     * @return Usuario válido o no.
     * @throws SQLException Error de sql.
     * @throws ClassNotFoundException Error de carga del jdbc.
     */
    public Boolean isValidUser(User userTryingToLogin) throws SQLException, ClassNotFoundException {
        User user = (User) shop.getDbh().search(DatabaseConnector.getConnection(), userTryingToLogin);
        
        if (user != null && userTryingToLogin.getPassword().equals(user.getPassword())) {
            isUserLoggedIn = true;
            return true;
        } else {
            return false;
        }
    }
}
