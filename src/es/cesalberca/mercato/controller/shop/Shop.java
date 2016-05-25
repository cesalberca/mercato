package es.cesalberca.mercato.controller.shop;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.model.Item;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author César Alberca
 */
public class Shop {
    private static ArrayList<Item> itemsOrder;
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

    public static ArrayList<Item> getItemsOrder() {
        return itemsOrder;
    }
    
    public ArrayList<Item> getItemsFromDatabase(String category) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = dbh.searchItemsByCategory(DatabaseConnector.getConnection(), category);
        return items;
    }
}
