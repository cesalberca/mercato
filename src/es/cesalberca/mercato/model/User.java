package es.cesalberca.mercato.model;

import java.util.ArrayList;

/**
 * Clase que representa la entidad Usuario, el cual es cliente. Éste puede tener pedidos a su nombre.
 * @author César Alberca
 */
public class User {
    private int id;
    private String name;
    private String password;
    private ArrayList<Order> orders;
    
    public User(String name) {
        this.name = name;
    }

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
    
    public int getId() {
        return id;
    }
}
