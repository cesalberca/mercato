package es.cesalberca.mercato.model;

import java.util.ArrayList;

/**
 * Clase de Categoría a la que puede pertenecer un ítem.
 * @author César Alberca
 */
public class Category {
    private String name;
    private static ArrayList<Category> categories;
    
    /**
     * Construye una categoría a partir del nombre de la misma.
     * @param name Nombre de la categoría.
     */
    public Category(String name) {
        this.name = name;
        categories.add(this);
    }

    public String getName() {
        return name;
    }
    
    public ArrayList<Category> getCategories() {
        return categories;
    }
    
    /**
     * Se conecta a la base de datos, que se encarga de hacer un select y devuelve todas las categorías disponibles.
     * @return ArrayList con todas las categorías
     */

}
