package es.cesalberca.mercato.controller.auth;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.model.User;
import static es.cesalberca.mercato.view.JFApp.dbh;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase encargada de registrar a un nuevo usuario.
 * @author César Alberca
 */
public class Signup {
    /**
     * Comprueba si ese nombre de usuario está disponible.
     * @param u Usuario del que se comprobará el nombre.
     * @return Nombre de usuario disponible o no.
     * @throws SQLException Error de sql.
     * @throws ClassNotFoundException Error de carga del jdbc.
     */
    public static boolean isUserAvailable(User u) throws SQLException, ClassNotFoundException {
        User existingUser = (User) dbh.search(DatabaseConnector.getConnection(), u);
        return existingUser == null;
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
