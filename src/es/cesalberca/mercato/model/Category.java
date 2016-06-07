package es.cesalberca.mercato.model;

/**
 * Clase de Categoría a la que puede pertenecer un ítem.
 * @author César Alberca
 */
public class Category {
    private int id;
    private String name;

    /**
     * Construye una categoría a partir del nombre de la misma.
     * @param id Id de la categoría.
     * @param name Nombre de la categoría.
     */
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
