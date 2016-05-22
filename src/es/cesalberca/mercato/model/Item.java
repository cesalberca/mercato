package es.cesalberca.mercato.model;

/**
 * Clase que representa un producto que puede ser añadido a la cesta de la compra.
 * @author César Alberca
 */
public class Item {
    private String name;
    private float prize;
    private int idCategory;
    
    public Item(String name) {
        this.name = name;
    }

    public Item(String name, float prize, int idCategory) {
        this.name = name;
        this.prize = prize;
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public float getPrize() {
        return prize;
    }

    public int getCategory() {
        return idCategory;
    }
}
