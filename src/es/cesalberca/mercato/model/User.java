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
    private boolean loggedIn;

    /**
     * Este constructor es usado para iniciar sesión.
     * @param name Nombre de usuario.
     * @param pass Contraseña del usuario.
     */
    public User(String name, String pass) {
        this.name = name;
        this.password = pass;
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", password=" + password + ", orders=" + orders + '}';
    }
}
