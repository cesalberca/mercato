package es.cesalberca.mercato.controller.shop;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.model.Category;
import es.cesalberca.mercato.model.Item;
import es.cesalberca.mercato.model.Order;
import es.cesalberca.mercato.model.User;
import java.sql.ResultSet;
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
    
    public void checkout() throws SQLException, ClassNotFoundException {
        if (this.user.isLoggedIn()) {
            int orderId = dbh.getLastId(DatabaseConnector.getConnection(), "ORDER");
            Order order = new Order(orderId, this.itemsOrder, this.user);
            dbh.insert(DatabaseConnector.getConnection(), order);
        }
    }
    
    public ArrayList<Item> getItemsFromDatabase(String category) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = dbh.searchItemsByCategory(DatabaseConnector.getConnection(), category);
        return items;
    }
    
    public ArrayList<Category> getCategoriesFromDatabase() throws SQLException, ClassNotFoundException {
        ArrayList<Category> categories = dbh.getCategories(DatabaseConnector.getConnection());
        return categories;
    }

    public DatabaseHandler getDbh() {
        return dbh;
    }
    
    public User getUser() {
        return user;
    }
}
