package es.cesalberca.mercato.controller.shop;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.model.Item;
import es.cesalberca.mercato.model.Order;
import es.cesalberca.mercato.model.User;
import static es.cesalberca.mercato.view.JFApp.dbh;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author César Alberca
 */
public class Shop {
    private static Order order = null;
    private static ArrayList<Item> items = null;
    private static User user;
    
    /**
     * Una tienda necesita un cliente y unos productos.
     * Los productos ya existen.
     * @param user 
     */
    public Shop(User user) {
        this.user = user;
        this.items = new ArrayList<>();
    }
    
    public void addToOrder(Item item) {
        order.getItems().add(item);
    }
    
    public void checkout(ArrayList<Item> selectedItems) throws SQLException, ClassNotFoundException {
        for (Item selectedItem : selectedItems) {
            order.addItems(selectedItem);
        }
        dbh.insert(DatabaseConnector.getConnection(), order);
    }

    public static Order getOrder() {
        return order;
    }
}
