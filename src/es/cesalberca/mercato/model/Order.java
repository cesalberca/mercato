package es.cesalberca.mercato.model;

import java.util.ArrayList;

/**
 * Clase que representa un pedido que puede hacer un usuario. Este pedido tiene asignados items y un usuario.
 * @author César Alberca
 */
public class Order {
    private int id;
    private ArrayList<Item> items;
    private User user;

    public Order(int id, ArrayList<Item> items, User user) {
        this.id = id;
        this.items = items;
        this.user = user;
    }
    
    public int getId() {
        return id;
    }
    
    public ArrayList<Item> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }
    
    public float getTotalPrizeOrder() {
        float totalPrize = 0;
        for (Item item : items) {
            totalPrize += item.getPrize();
        }
        return totalPrize;
    }
}
