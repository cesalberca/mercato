package es.cesalberca.controller.shop;

import es.cesalberca.mercato.model.Item;
import es.cesalberca.mercato.model.Order;
import es.cesalberca.mercato.model.User;
import java.util.ArrayList;

/**
 *
 * @author César Alberca
 */
public class Shop {
    private static Order order = null;
    private static ArrayList<Item> items = null;
    
    public Shop(User user) {
        this.items = new ArrayList<>();
    }
    
    public void addItemToOrder(Item item) {
        order.getItems().add(item);
    }
    
    public void checkout() {
        
    }

    public static Order getOrder() {
        return order;
    }
}
