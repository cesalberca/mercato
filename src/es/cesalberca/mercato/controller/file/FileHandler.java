package es.cesalberca.mercato.controller.file;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.model.User;
import java.sql.SQLException;

/**
 *
 * @author César Alberca
 */
public class FileHandler {
    
    public FileHandler() {
        
    }
    
    public void exportToHtml(User u) throws SQLException {
        DatabaseHandler dbh = new DatabaseHandler();
        dbh.getOrdersByUser(DatabaseConnector.getConnection(), u);
    }
    
}
