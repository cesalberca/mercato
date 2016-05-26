package es.cesalberca.mercato.controller.auth;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.controller.shop.Shop;
import es.cesalberca.mercato.model.User;
import java.sql.ResultSet;
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
        ResultSet rs = null;
        rs = shop.getDbh().selectAll(DatabaseConnector.getConnection(), "User");
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
    
    /**
     * Inserta al nuevo usuario en la bbdd.
     * @param u Usuario a registrar.
     * @throws SQLException Error de sql.
     * @throws ClassNotFoundException Error de carga de jdbc.
     */
    public static void register(User u) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbh = new DatabaseHandler();
        dbh.insert(DatabaseConnector.getConnection(), u);
    }
}
