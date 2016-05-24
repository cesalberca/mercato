package es.cesalberca.mercato.model;

import java.util.ArrayList;

/**
 * Clase que representa un pedido que puede hacer un usuario. Este pedido tiene asignados items y un usuario.
 * @author César Alberca
 */
public class Order {
    private ArrayList<Item> items;
    private User user;

    public Order(ArrayList<Item> items, User user) {
        this.items = items;
        this.user = user;
    }

    public void addItems(Item item) {
        this.items.add(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }
}
