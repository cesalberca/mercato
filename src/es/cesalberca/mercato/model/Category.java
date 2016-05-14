package es.cesalberca.mercato.model;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public ArrayList<Category> retrieveCategoriesFromDB() throws ClassNotFoundException, SQLException {
//        Connection c = null;
//        Statement stmt = null;
//        DatabaseConnector dbc = new DatabaseConnector();
//        c = dbc.getConnection();
//        ResultSet rs = dbc.selectAll(c, this);
        return null;
    }
}
