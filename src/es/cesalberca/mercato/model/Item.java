package es.cesalberca.mercato.model;

/**
 * Clase que representa un producto que puede ser añadido a la cesta de la compra.
 * @author César Alberca
 */
public class Item {
    private int id;
    private String name;
    private float prize;
    private int idCategory;
    
    /**
     * Este constructor
     * @param name 
     */
    public Item(String name) {
        this.name = name;
    }

    public Item(int id, String name, float prize, int idCategory) {
        this.id = id;
        this.name = name;
        this.prize = prize;
        this.idCategory = idCategory;
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

    public int getCategory() {
        return idCategory;
    }
}
