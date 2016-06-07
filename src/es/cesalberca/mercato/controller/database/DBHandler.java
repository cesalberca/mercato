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
 * Clase que gestiona las operaciones con la base de datos.
 * @author César Alberca
 */
public class DBHandler {
    
    /**
     * Hace un insert en la base de datos dada la conexión y un ArrayList de pedidos.
     * @param c Conexión a la base de datos.
     * @param order Pedido que se tiene que registrar.
     * @throws SQLException Error en el insert.
     */
    public void insert(Connection c, Order order) throws SQLException {
        PreparedStatement ps = null;
        String sqlInsertItem;
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
            sqlInsertItem = "INSERT INTO ORDER_ITEM VALUES (?, ?);";
            ps = c.prepareStatement(sqlInsertItem);
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
     * Hace un insert en la base de datos dada la conexión y un objeto de tipo item.
     * @param c Conexión a la base de datos.
     * @param item Item que se insertará
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
     * Este bloque de funciones polimórficas admite por parámetro un objeto a partir del cual se buscará por nombre.
     * Para poder hacer polimorfismo los argumentos de las funciones deben ser distintos, por ello se pide un objeto.
     */
    
    /**
     * Busca un determinado usuario basado en su nombre y devuelve sus datos.
     * @param c Conexión a la base de datos.
     * @param searchedUser Usuario a buscar.
     * @return Usuario encontrado.
     * @throws SQLException Error de sql.
     */
    public Object search(Connection c, User searchedUser) throws SQLException {
        PreparedStatement ps = null;
        User user = null;
        
        String selectSQL = "SELECT * FROM USER WHERE NAME LIKE ?";
        ps = c.prepareStatement(selectSQL);
        ps.setString(1, searchedUser.getName());
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            user = new User(rs.getInt("ID"), rs.getString("NAME"), rs.getString("PASSWORD"));
        }
        
        return user;
    }
    
    /**
     * Busca un determinado producto basado en su nombre y devuelve sus datos.
     * @param c Conexión a la base de datos.
     * @param searchedItem Item a buscar.
     * @return Item encontrado.
     * @throws SQLException Error de sql.
     */
    public Object search(Connection c, Item searchedItem) throws SQLException {
        PreparedStatement ps = null;
        Item item = null;
        Category category = null;
        
        String selectSQL = "SELECT * FROM ITEM WHERE NAME LIKE ?";
        ps = c.prepareStatement(selectSQL);
        ps.setString(1, searchedItem.getName());
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            category = this.getCategoryById(c, rs.getInt("ID_CATEGORY"));
            item = new Item(rs.getInt("ID"), rs.getString("NAME"), rs.getFloat("PRIZE") / 100, category);
        }
        
        return item;
    }
    
    /**
     * Busca un item por id.
     * @param c Conexión a la bbdd.
     * @param idItem Id a buscar.
     * @return Item encontrado.
     * @throws SQLException Error de sql.
     */
    public Item getItemById(Connection c, int idItem) throws SQLException {
        PreparedStatement ps = null;
        Item item = null;
        Category category = null;
        
        String selectSQL = "SELECT * FROM ITEM WHERE ID = ?";
        ps = c.prepareStatement(selectSQL);
        ps.setInt(1, idItem);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            category = this.getCategoryById(c, rs.getInt("ID_CATEGORY"));
            item = new Item(rs.getInt("ID"), rs.getString("NAME"), rs.getFloat("PRIZE") / 100, category);
        }
        
        return item;
    }
    
    /**
     * Devuelve un usuario de la base de datos dado su id.
     * @param c Conexión a la base de datos.
     * @param idUsuaio Id del usuario.
     * @return Usuario encontrado.
     * @throws SQLException Error de sql.
     */
    public User getUserById(Connection c, int idUsuaio) throws SQLException {
        PreparedStatement ps = null;
        User user = null;
        
        String selectSQL = "SELECT * FROM USER WHERE ID = ?";
        ps = c.prepareStatement(selectSQL);
        ps.setInt(1, idUsuaio);
        ResultSet rs = ps.executeQuery();
        
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
        Item i = null;
        ArrayList<Item> items = new ArrayList<>();
        
        String selectSQL = "SELECT * FROM ITEM WHERE ID_CATEGORY = (SELECT ID FROM CATEGORY WHERE NAME LIKE ?)";
        ps = c.prepareStatement(selectSQL);
        ps.setString(1, sCategory);
        ResultSet rs = ps.executeQuery();
        Category category;
        
        while(rs.next()) {
            category = this.getCategoryById(c, rs.getInt("ID_CATEGORY"));
            items.add(new Item(rs.getInt("ID"), rs.getString("NAME"), rs.getFloat("PRIZE") / 100, category));
        }
        
        return items;
    }
    
    /**
     * Devuelve un arraylist de categorias de la base de datos.
     * @param c Conexión con la base de datos.
     * @return ArrayList de todas las categorías de la base de datos.
     * @throws SQLException Error de sql.
     */
    public ArrayList<Category> getCategories(Connection c) throws SQLException {
        PreparedStatement ps = null;
        Category category = null;
        ArrayList<Category> categories = new ArrayList<>();
        
        String selectSQL = "SELECT * FROM Category;";
        PreparedStatement preparedStatement = c.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
            
        while (rs.next()) {
            categories.add(new Category(rs.getInt("ID"), rs.getString("NAME")));
        }
        
        return categories;
    }
    
    /**
     * Busca una categoría por id.
     * @param c Conexión a la base de datos.
     * @param idCategory Id de la categoría a buscar.
     * @return La categoría encontrada.
     * @throws SQLException Error de sql.
     */
    public Category getCategoryById(Connection c, int idCategory) throws SQLException {
        PreparedStatement ps = null;
        Category category = null;
        
        String selectSQL = "SELECT * FROM CATEGORY WHERE ID = ?";
        ps = c.prepareStatement(selectSQL);
        ps.setInt(1, idCategory);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            category = new Category(rs.getInt("ID"), rs.getString("NAME"));
        }
        
        return category;
    }

    /**
     * Devuelve un pedido de la base de datos dado su id.
     * @param c Conexión a base de datos.
     * @param idOrder Id del pedido.
     * @return Pedido encontrado.
     * @throws SQLException Error de sql.
     */
    public Order getOrderById(Connection c, int idOrder) throws SQLException {
        PreparedStatement ps = null;
        Order order = null;
        User user = null;
        Item item = null;
        // Inicializamos el id del pedido a -1
        int orderId = -1;
        
        // Buscamos todos los items comprados por un usuario dado y mostramos además a qué id de pedido pertenecen.
        String selectSQL = "SELECT OI.ID_ORDER AS OI_ID_ORDER, OI.ID_ITEM AS OI_ID_ITEM, U.ID AS U_ID\n" +
        "FROM ORDER_ITEM OI, USER U, USER_ORDER UO\n" +
        "WHERE OI.ID_ORDER = UO.ID_ORDER AND U.ID = UO.ID_USER AND OI.ID_ORDER = ?;";
        ps = c.prepareStatement(selectSQL);
        ps.setInt(1, idOrder);
        ResultSet rs = ps.executeQuery();
        ArrayList<Item> items = new ArrayList<>();
        
        while(rs.next()) {
            // User y orderId será los mismos desde un principio aunque se vaya cambiando de filas.
            user = getUserById(c, rs.getInt("U_ID"));
            orderId = rs.getInt("OI_ID_ORDER");
            item = getItemById(c, rs.getInt("OI_ID_ITEM"));
            items.add(item);
        }
        
        order = new Order(orderId, items, user);
        return order;
    }
    
    /**
     * Busca todos los pedidos de un usuario dado su id.
     * @param c Conexión a la base de datos.
     * @param idUser Id del usuario.
     * @return ArrayList de pedidos de ese usuario.
     * @throws SQLException Error de sql.
     */
    public ArrayList<Order> getOrdersByUser(Connection c, int idUser) throws SQLException {
        PreparedStatement ps = null;
        Order order = null;
        ArrayList<Order> orders = new ArrayList<>();
        
        String selectSQL = "SELECT * FROM USER_ORDER WHERE ID_USER = ?;";
        ps = c.prepareStatement(selectSQL);
        ps.setInt(1, idUser);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            order = getOrderById(c, rs.getInt("ID_ORDER"));
            orders.add(order);
        }
        
        return orders;
    }
    
     /**
     * Busca un id disponible de una tabla
     * @param c Conexión a la base de datos.
     * @param table Tabla de la que se conseguirá un id disponible.
     * @return Id disponible
     * @throws SQLException Error de sql.
     */
    public int getAvailableId(Connection c, String table) throws SQLException {
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
