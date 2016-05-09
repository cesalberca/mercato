package es.cesalberca.mercato.model;

import java.util.ArrayList;

/**
 *
 * @author César Alberca
 */
public class Order {
    private float totalPrize = 0;
    private ArrayList<Item> items;
    private User user;

    public Order(ArrayList<Item> items, User user) {
        this.items = items;
        this.user = user;
        this.setTotalPrize(items);
    }

    public void setTotalPrize(ArrayList<Item> items) {
        for (Item item : items) {
            this.totalPrize += item.getPrize();
        }
    }
    
    public float getTotalPrize() {
        return totalPrize;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }
}
