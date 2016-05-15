package es.cesalberca.mercato.model;

import java.util.ArrayList;

/**
 * Clase de Categoría a la que puede pertenecer un ítem.
 * @author César Alberca
 */
public class Category {
    private String name;
    
    /**
     * Construye una categoría a partir del nombre de la misma.
     * @param name Nombre de la categoría.
     */
    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
