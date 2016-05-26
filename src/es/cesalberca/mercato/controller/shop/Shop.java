package es.cesalberca.mercato.controller.shop;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.model.Item;
import es.cesalberca.mercato.model.User;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author César Alberca
 */
public class Shop {
    private User user;
    private ArrayList<Item> itemsOrder;
    private DatabaseHandler dbh;
    
    public Shop(DatabaseHandler dbh) throws ClassNotFoundException, SQLException {
        itemsOrder = new ArrayList<>();
        this.dbh = dbh;
    }
    
    public void addToOrder(Item item) {
        itemsOrder.add(item);
    }
    
    public void clearOrder() {
        itemsOrder.clear();
    }

    public ArrayList<Item> getItemsOrder() {
        return itemsOrder;
    }
    
    public void checkout() {
        if (this.user.isLoggedIn()) {
            // 
        }
    }
    
    public ArrayList<Item> getItemsFromDatabase(String category) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = dbh.searchItemsByCategory(DatabaseConnector.getConnection(), category);
        return items;
    }
}
