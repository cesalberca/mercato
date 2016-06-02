package es.cesalberca.mercato.controller.auth;

import es.cesalberca.mercato.controller.database.DBConnector;
import es.cesalberca.mercato.controller.database.DBHandler;
import es.cesalberca.mercato.controller.shop.Shop;
import es.cesalberca.mercato.model.User;
import java.sql.SQLException;

/**
 * Clase encargada de registrar a un nuevo usuario.
 * @author César Alberca
 */
public class Signup {
    private Shop shop;
    
    public Signup(Shop shop) {
        this.shop = shop;
    }
    
    /**
     * Comprueba si ese nombre de usuario está disponible.
     * @param u Usuario del que se comprobará el nombre.
     * @return Nombre de usuario disponible o no.
     * @throws SQLException Error de sql.
     * @throws ClassNotFoundException Error de carga del jdbc.
     */
    public boolean isUserAvailable(User u) throws SQLException, ClassNotFoundException {
        User existingUser = (User) shop.getDbh().search(DBConnector.getConnection(), u);

        if (existingUser != null) {
            // Si no es nulo eso quiere decir que hay un usuario con ese nombre.
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Inserta al nuevo usuario en la bbdd.
     * @param u Usuario a registrar.
     * @throws SQLException Error de sql.
     * @throws ClassNotFoundException Error de carga de jdbc.
     */
    public void register(User u) throws SQLException, ClassNotFoundException {
        DBHandler dbh = new DBHandler();
        dbh.insert(DBConnector.getConnection(), u);
    }
}
