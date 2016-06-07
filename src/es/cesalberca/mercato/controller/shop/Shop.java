package es.cesalberca.mercato.controller.shop;

import es.cesalberca.mercato.controller.database.DBConnector;
import es.cesalberca.mercato.controller.database.DBHandler;
import es.cesalberca.mercato.model.Category;
import es.cesalberca.mercato.model.Item;
import es.cesalberca.mercato.model.Order;
import es.cesalberca.mercato.model.User;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase controladora de la tienda.
 * @author César Alberca
 */
public class Shop {
    private User user;
    private ArrayList<Item> itemsOrder = null;
    private DBHandler dbh = null;
    
    public Shop(DBHandler dbh) {
        itemsOrder = new ArrayList<>();
        this.dbh = dbh;
    }
    
    /**
     * Añade un item al pedido.
     * @param item Item a añadir.
     */
    public void addToOrder(Item item) {
        itemsOrder.add(item);
    }
    
    /**
     * Destruye el pedido actual.
     */
    public void clearOrder() {
        itemsOrder.clear();
    }

    /**
     * Guarda el pedido en la base de datos.
     * @throws SQLException Error de sql.
     * @throws ClassNotFoundException JDBC no encontrado.
     */
    public void checkout() throws SQLException, ClassNotFoundException {
        // Buscamos un id disponible para el pedido.
        int idOrder = dbh.getAvailableId(DBConnector.getConnection(), "ORDER");
        Order order = new Order(idOrder, this.itemsOrder, this.user);
        dbh.insert(DBConnector.getConnection(), order);
    }
    
    /**
     * Carga de la base de datos los pedidos del usuario loggeado.
     * @throws SQLException Error de sql.
     */
    public void loadOrders() throws SQLException {
        this.user.setOrders(dbh.getOrdersByUser(DBConnector.getConnection(), this.user.getId()));
    }
    
    /**
     * Busca y devuelve los items de la base de datos.
     * @param category Categoría a partir de la cual se buscarán los items.
     * @return ArrayList de items.
     * @throws SQLException Error de sql.
     * @throws ClassNotFoundException Clase no encontrada.
     */
    public ArrayList<Item> getItemsFromDatabase(String category) throws SQLException, ClassNotFoundException {
        return dbh.searchItemsByCategory(DBConnector.getConnection(), category);
    }
    
    /**
     * Busca y devuelve las categorías de la base de datos.
     * @return ArrayList de categorías.
     * @throws SQLException Error de sql.
     * @throws ClassNotFoundException Clase no encontrada.
     */
    public ArrayList<Category> getCategoriesFromDatabase() throws SQLException, ClassNotFoundException {
        return dbh.getCategories(DBConnector.getConnection());
    }

    public DBHandler getDbh() {
        return dbh;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public ArrayList<Item> getItemsOrder() {
        return itemsOrder;
    }
}
