package es.cesalberca.mercato.model;

import java.sql.SQLException;

/**
 * Clase que representa un producto que puede ser añadido a la cesta de la compra.
 * @author César Alberca
 */
public class Item {
    private String name;
    private float prize;
    private Category category;

    public Item(String name, float prize, Category category) {
        this.name = name;
        this.prize = prize;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public float getPrize() {
        return prize;
    }

    public Category getCategory() {
        return category;
    }
}
