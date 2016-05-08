package es.cesalberca.mercato.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Clase que representa la entidad Usuario, el cual es cliente. Éste puede tener pedidos a su nombre.
 * @author César Alberca
 */
public class User extends DatabaseObject {
    private String name;
    private String password;
    private ArrayList<Order> orders;

    public User(String name, String pass) {
        this.name = name;
        this.password = pass;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
