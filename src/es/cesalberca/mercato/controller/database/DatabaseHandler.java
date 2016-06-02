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
        String sqlInsertUser;
        String sqlOrder;
        
        // Parece ser que la palabra "ORDER" es una palabra reservada.
        sqlOrder = "INSERT INTO `ORDER` VALUES (?);";
        ps = c.prepareStatement(sqlOrder);
        ps.setInt(1, order.getId());
        ps.executeUpdate();
        
        sqlInsertUser = "INSERT INTO USER_ORDER VALUES (?, ?);";
        ps = c.prepareStatement(sqlInsertUser);
        ps.setInt(1, order.getUser().getId());
        ps.setInt(2, order.getId());
        ps.executeUpdate();
        
        for (Item item : order.getItems()) {
            sqlInsert = "INSERT INTO ORDER_ITEM VALUES (?, ?);";
            ps = c.prepareStatement(sqlInsert);
            ps.setInt(1, order.getId());
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
        ps.setInt(3, item.getCategory().getId());
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
            u = new User(rs.getInt("ID"), rs.getString("NAME"), rs.getString("PASSWORD"));
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
        int idCat;
        Category category;
        
        while(rs.next()) {
            idCat = rs.getInt("ID_CATEGORY");
            category = this.getCategoryById(c, idCat);
            i = new Item(rs.getInt("ID"), rs.getString("NAME"), rs.getFloat("PRIZE") / 100, category);
        }
        
        return i;
    }
    
    /**
     * Busca un item por id.
     * @param c Conexión a la bbdd.
     * @param id Id a buscar.
     * @return Item encontrado.
     * @throws SQLException 
     */
    public Item getItemById(Connection c, int id) throws SQLException {
        PreparedStatement ps = null;
        Item i = null;
        String selectSQL = "SELECT * FROM ITEM WHERE ID = ?";
        ps = c.prepareStatement(selectSQL);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Category category;
        
        while(rs.next()) {
            category = this.getCategoryById(c, rs.getInt("ID_CATEGORY"));
            i = new Item(rs.getInt("ID"), rs.getString("NAME"), rs.getFloat("PRIZE") / 100, category);
        }
        
        return i;
    }
    
    /**
     * Devuelve un usuario de la base de datos dado su id.
     * @param c
     * @param id
     * @return
     * @throws SQLException 
     */
    public User getUserById(Connection c, int id) throws SQLException {
        PreparedStatement ps = null;
        String selectSQL = "SELECT * FROM USER WHERE ID = ?";
        ps = c.prepareStatement(selectSQL);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        User user = null;
        
        if(rs.next()) {
            user = new User(rs.getInt("ID"), rs.getString("NAME"), rs.getString("PASSWORD"));
        }
        
        return user;
    }
    
    /**
     * Busca items basados en su categoría.
     * @param c Conexión a la base de datos.
     * @param sCategory Categoría por la que filtrar.
     * @return ArrayList con los items encontrados.
     * @throws SQLException Error de sql.
     */
    public ArrayList<Item> searchItemsByCategory(Connection c, String sCategory) throws SQLException {
        PreparedStatement ps = null;
        ArrayList<Item> items = new ArrayList<>();
        Item i = null;
        String selectSQL = "SELECT * FROM ITEM WHERE ID_CATEGORY = (SELECT ID FROM CATEGORY WHERE NAME LIKE ?)";
        ps = c.prepareStatement(selectSQL);
        ps.setString(1, sCategory);
        ResultSet rs = ps.executeQuery();
        int idCat;
        Category category;
        
        while(rs.next()) {
            idCat = rs.getInt("ID_CATEGORY");
            category = this.getCategoryById(c, idCat);
            items.add(new Item(rs.getInt("ID"), rs.getString("NAME"), rs.getFloat("PRIZE") / 100, category));
        }
        
        return items;
    }
    
    /**
     * Devuelve un arraylist de categorias de la base de datos.
     * @param c
     * @return
     * @throws SQLException 
     */
    public ArrayList<Category> getCategories(Connection c) throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        String selectSQL = "SELECT * FROM Category;";
        PreparedStatement preparedStatement = c.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
        Category category = null;
            
        while (rs.next()) {
            categories.add(new Category(rs.getString("NAME"), rs.getInt("ID")));
        }
        
        return categories;
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

    /**
     * Devuelve un pedido de la base de datos dado su id.
     * @param c
     * @param id
     * @return
     * @throws SQLException 
     */
    public Order getOrderById(Connection c, int id) throws SQLException {
        PreparedStatement ps = null;
        String selectSQL = "SELECT OI.ID_ORDER AS OI_ID_ORDER, OI.ID_ITEM AS OI_ID_ITEM, U.ID AS U_ID\n" +
"FROM ORDER_ITEM OI, USER U, USER_ORDER UO\n" +
"WHERE OI.ID_ORDER = UO.ID_ORDER AND U.ID = UO.ID_USER AND OI.ID_ORDER = ?;";
        ps = c.prepareStatement(selectSQL);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Order order = null;
        User user = null;
        Item item = null;
        int orderId = -1;
        ArrayList<Item> items = new ArrayList<>();
        
        while(rs.next()) {
            user = getUserById(c, rs.getInt("U_ID"));
            item = getItemById(c, rs.getInt("OI_ID_ITEM"));
            items.add(item);
            orderId = rs.getInt("OI_ID_ORDER");
        }
        
        order = new Order(items, user);
        return order;
    }
    
    /**
     * Busca todos los pedidos de un usuario dado su id.
     * @param c Conexión a la base de datos.
     * @param i Id del usuario.
     * @return ArrayList de pedidos de ese usuario.
     * @throws SQLException Error de sql.
     */
    public ArrayList<Order> getOrdersByUser(Connection c, int id) throws SQLException {
        PreparedStatement ps = null;
        String selectSQL = "SELECT * FROM USER_ORDER WHERE ID_USER = ?;";
        ps = c.prepareStatement(selectSQL);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        ArrayList<Order> orders = new ArrayList<>();
        Order order = null;
        
        while(rs.next()) {
            order = getOrderById(c, rs.getInt("ID_ORDER"));
            orders.add(order);
        }
        
        return orders;
    }
    
     /**
     * Busca el último id disponible de una tabla
     * @param c
     * @param table
     * @return
     * @throws SQLException 
     */
    public int getLastId(Connection c, String table) throws SQLException {
        int lastId = -1;
        String selectSQL = "SELECT MAX(ID) FROM `" + table + "`";
        PreparedStatement preparedStatement = c.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
        
        if(rs.next()) {
            lastId = rs.getInt(1);
        }
        
        return lastId + 1;
    }
    
}
