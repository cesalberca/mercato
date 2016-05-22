package es.cesalberca.mercato.model;

/**
 * Clase de Categoría a la que puede pertenecer un ítem.
 * @author César Alberca
 */
public class Category {
    private String name;
    private int id;

    /**
     * Construye una categoría a partir del nombre de la misma.
     * @param name Nombre de la categoría.
     */
    public Category(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
