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
     * Hace un insert en la base de datos dada la conexión y un ArrayList de pedidos.
     * @param c Conexión a la base de datos.
     * @param orders Pedidos que se tienen que registrar
     * @throws SQLException Error en el insert.
     */
    public void insert(Connection c, Order order) throws SQLException {
        PreparedStatement ps = null;
        String sqlInsert;
        for (Item item : order.getItems()) {
            sqlInsert = "INSERT INTO ORDER_ITEM VALUES (?, ?)";
            ps = c.prepareStatement(sqlInsert);
            ps.setString(1, "NULL");
            ps.setInt(2, item.getId());
            ps.executeUpdate();
        }
    }
    
    /**
     * Hace un insert en la base de datos dada la conexión y un objeto de tipo User.
     * @param c Conexión a la base de datos.
     * @param user Usuario que se insertará
     * @throws SQLException Error al hacer el insert.
     */
    public void insert(Connection c, User user) throws SQLException {
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
    public void insert(Connection c, Item item) throws SQLException {
        PreparedStatement ps = null;
        String sqlInsert = "INSERT INTO ITEM (NAME, PRIZE, ID_CATEGORY) VALUES (?, ?, ?)";
        ps = c.prepareStatement(sqlInsert);
        ps.setString(1, item.getName());
        // Los precios admiten decimales, pero sqlite no. Con lo que guardamos los precios multiplicados por 100 para quitar los decimales. Luego hacemos un cast a int.
        ps.setInt(2, (int)(item.getPrize() * 100));
        ps.setInt(3, item.getCategory());
        ps.executeUpdate();
    }
    
    /**
     * Hace un select de la tabla a la que pertenece ese objeto.
     * @param c Conexión a la base de datos.
     * @param table Nombre de la table de la que se extraerán los datos.
     * @return El result set con el resultado.
     * @throws SQLException Error de sql.
     */
    public ResultSet selectAll(Connection c, String table) throws SQLException {
        String selectSQL = "SELECT * FROM " + table + ";";
        PreparedStatement preparedStatement = c.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }
    
    /**
     * Busca un determinado usuario basado en su nombre y devuelve sus datos.
     * @param c Conexión a la base de datos.
     * @param searchedUser Usuario a buscar.
     * @return
     * @throws SQLException 
     */
    public Object search(Connection c, User searchedUser) throws SQLException {
        PreparedStatement ps = null;
        User u = null;
        String selectSQL = "SELECT * FROM USER WHERE NAME LIKE ?";
        ps = c.prepareStatement(selectSQL);
        ps.setString(1, "%" + searchedUser.getName() + "%");
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            u = new User(rs.getString("NAME"), rs.getString("PASSWORD"));
        }
        
        return u;
    }
    
    /**
     * Busca un determinado producto basado en su nombre y devuelve sus datos.
     * @param c Conexión a la base de datos.
     * @param searchedItem Item a buscar.
     * @return
     * @throws SQLException 
     */
    public Object search(Connection c, Item searchedItem) throws SQLException {
        PreparedStatement ps = null;
        Item i = null;
        String selectSQL = "SELECT * FROM ITEM WHERE NAME LIKE ?";
        ps = c.prepareStatement(selectSQL);
        ps.setString(1, searchedItem.getName());
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            i = new Item(rs.getInt("ID"), rs.getString("NAME"), rs.getFloat("PRIZE") / 100, rs.getInt("ID_CATEGORY"));
        }
        
        return i;
    }
    
    /**
     * Busca items basados en su categoría.
     * @param c Conexión a la base de datos.
     * @param category Categoría por la que filtrar.
     * @return ArrayList con los items encontrados.
     * @throws SQLException Error de sql.
     */
    public ArrayList<Item> searchItemsByCategory(Connection c, String category) throws SQLException {
        PreparedStatement ps = null;
        ArrayList<Item> items = new ArrayList<>();
        Item i = null;
        String selectSQL = "SELECT * FROM ITEM WHERE ID_CATEGORY = (SELECT ID FROM CATEGORY WHERE NAME LIKE ?)";
        ps = c.prepareStatement(selectSQL);
        ps.setString(1, category);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            items.add(new Item(rs.getString("NAME")));
        }
        
        return items;
    }
    
    /**
     * Busca una categoría por id.
     * @param c Conexión a la base de datos.
     * @param categoryId Id de la categoría a buscar.
     * @return La categoría encontrada.
     * @throws SQLException Error de sql.
     */
    public Category getCategoryById(Connection c, int categoryId) throws SQLException {
        PreparedStatement ps = null;
        String selectSQL = "SELECT * FROM CATEGORY WHERE ID = ?";
        ps = c.prepareStatement(selectSQL);
        ps.setInt(1, categoryId);
        ResultSet rs = ps.executeQuery();
        Category category = null;
        
        while(rs.next()) {
            category = new Category(rs.getString("NAME"), rs.getInt("ID"));
        }
        
        return category;
    }
}
