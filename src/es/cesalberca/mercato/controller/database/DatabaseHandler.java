package es.cesalberca.mercato.controller.database;

import es.cesalberca.mercato.model.Category;
import es.cesalberca.mercato.model.Item;
import es.cesalberca.mercato.model.Order;
import es.cesalberca.mercato.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase que gestiona la parte común de las bbdd.
 * @author César Alberca
 */
public class DatabaseHandler {
    /**
     * Función que se encarga de hacer un cast al objeto apropiado en caso que se reciba un objeto genérico.
     * @param c Conexión a la base de datos.
     * @param obj Objeto del que se hará el cast.
     * @throws SQLException Error de sql.
     */
    public void insertInto(Connection c, Object obj) throws SQLException {
        if (obj instanceof Order) {
            this.insertInto(c, (Order) obj);
        } else if(obj instanceof User) {
            this.insertInto(c, (User) obj);
        } 
    }
    
    /**
     * Hace un insert en la base de datos dada la conexión y un ArrayList de pedidos.
     * @param c Conexión a la base de datos.
     * @param orders Pedidos que se tienen que registrar
     * @throws SQLException Error en el insert.
     */
    public void insertInto(Connection c, ArrayList<Order> orders) throws SQLException {
        PreparedStatement ps = null;
        String sqlInsert = "INSERT INTO ORDER VALUES (?, ?, ?, ?, ?)";
        ps = c.prepareStatement(sqlInsert);
        ps.setString(1, orders.get(0).getItems().get(0).getName());
        ps.executeUpdate();
    }
    
    /**
     * Hace un insert en la base de datos dada la conexión y un objeto de tipo User.
     * @param c Conexión a la base de datos.
     * @param user Usuario que se insertará
     * @throws SQLException Error al hacer el insert.
     */
    public void insertInto(Connection c, User user) throws SQLException {
        PreparedStatement ps = null;
        String sqlInsert = "INSERT INTO USER (NAME, PASSWORD) VALUES (?, ?)";
        ps = c.prepareStatement(sqlInsert);
        ps.setString(1, user.getName());
        ps.setString(2, user.getPassword());
        ps.executeUpdate();
    }
    
    /**
     * Hace un insert en la base de datos dada la conexión y un objeto de tipo User.
     * @param c Conexión a la base de datos.
     * @param u Usuario que se insertará
     * @throws SQLException Error al hacer el insert.
     */
    public void insertInto(Connection c, Item item) throws SQLException {
        PreparedStatement ps = null;
        String sqlInsert = "INSERT INTO ITEM (NAME, PRIZE, ID_CATEGORY) VALUES (?, ?, ?)";
        ps = c.prepareStatement(sqlInsert);
        ps.setString(1, item.getName());
        // Los precios admiten decimales, pero sqlite no. Con lo que guardamos los precios multiplicados por 100 para quitar los decimales. Luego hacemos un cast a int.
        ps.setInt(2, (int)(item.getPrize() * 100));
        ps.setString(3, item.getCategory().getName());
        ps.executeUpdate();
    }
    
    /**
     * Hace un select de la tabla a la que pertenece ese objeto.
     * @param c
     * @param obj
     * @return
     * @throws SQLException 
     */
    public ResultSet selectAll(Connection c, String table) throws SQLException {
        String selectSQL = "SELECT * FROM " + table + ";";
        PreparedStatement preparedStatement = c.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }
    
    /**
     * Función que se encarga de hacer un cast al objeto apropiado en caso que se reciba un objeto genérico.
     * @param c
     * @param obj
     * @return
     * @throws SQLException 
     */
    public ResultSet search(Connection c, Object obj) throws SQLException {
        ResultSet rs = null;
        if (obj instanceof User) {
            rs = search(c, (User) obj);
        } else if (obj instanceof Item) {
            rs = search(c, (Item) obj);
        }
        return rs;
   }
    
    /**
     * Busca un determinado usuario basado en su nombre y devuelve sus datos.
     * @param c Conexión a la base de datos.
     * @param searchedUser Usuario a buscar.
     * @return
     * @throws SQLException 
     */
    public ResultSet search(Connection c, User searchedUser) throws SQLException {
        PreparedStatement ps = null;
        String selectSQL = "SELECT * FROM USER WHERE NAME LIKE ?";
        ps = c.prepareStatement(selectSQL);
        ps.setString(1, "%" + searchedUser.getName() + "%");
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    /**
     * Busca un determinado producto basado en su nombre y devuelve sus datos.
     * @param c Conexión a la base de datos.
     * @param searchedItem Item a buscar.
     * @return
     * @throws SQLException 
     */
    public ResultSet search(Connection c, Item searchedItem) throws SQLException {
        PreparedStatement ps = null;
        String selectSQL = "SELECT * FROM ITEM WHERE NAME LIKE ?";
        ps = c.prepareStatement(selectSQL);
        ps.setString(1, "%" + searchedItem.getName() + "%");
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    public ResultSet searchItemsByCategory(Connection c, String category) throws SQLException {
        PreparedStatement ps = null;
        String selectSQL = "SELECT * FROM ITEM WHERE ID_CATEGORY = (SELECT ID FROM CATEGORY WHERE NAME LIKE ?)";
        ps = c.prepareStatement(selectSQL);
        ps.setString(1, "%" + category + "%");
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}
