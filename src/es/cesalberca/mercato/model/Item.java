package es.cesalberca.mercato.model;

/**
 * Clase que representa un producto que puede ser añadido a la cesta de la compra.
 * @author César Alberca
 */
public class Item {
    private int id;
    private String name;
    private float prize;
    private Category category;
    
    /**
     * Este constructor es utiizado para búsquedas.
     * @param name Nombre del item.
     */
    public Item(String name) {
        this.name = name;
    }

    public Item(int id, String name, float prize, Category category) {
        this.id = id;
        this.name = name;
        this.prize = prize;
        this.category = category;
    }

    public int getId() {
        return id;
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
