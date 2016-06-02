package es.cesalberca.mercato.controller.file;

import es.cesalberca.mercato.controller.database.DBHandler;
import es.cesalberca.mercato.model.Item;
import es.cesalberca.mercato.model.Order;
import es.cesalberca.mercato.model.User;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Clase que se ocupa de las exportaciones a ficheros.
 * @author César Alberca
 */
public class FileHandler {
    
    /**
     * Exporta los pedidos de un usuario a html.
     * @param u Usuario del que se generarán los datos.
     * @throws FileNotFoundException Fichero no encontrado.
     */
    public void exportToHtml(User u) throws FileNotFoundException {
        DBHandler dbh = new DBHandler();
        PrintWriter pw = null;
        
        pw = new PrintWriter(u.getName() + ".html");
        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
            pw.println("\t<head>");
                pw.println("\t\t<meta charset='UTF-8'>");
                pw.println("\t\t<title> Pedidos: "  + u.getName() + "</title>");
            pw.println("\t</head>");
            pw.println("\t<body>");
            pw.println("\t\t<h1>Usuario: " + u.getName() + "</h1>");
            for (Order order : u.getOrders()) {
                pw.println("\t\t<h1>Id de pedido: " + order.getId() + "</h1>");
                pw.println("\t\t<table border='1' rules='all'>");
                
                pw.println("\t\t\t<tr>");
                pw.print("\t\t\t\t<th>");
                pw.print("Nombre");
                pw.println("</th>");
                pw.print("\t\t\t\t<th>");
                pw.print("Precio");
                pw.println("</th>");
                pw.print("\t\t\t\t<th>");
                pw.print("Categoría");
                pw.println("</th>");
                pw.println("\t\t\t</th>");
                
                for (Item item : order.getItems()) {
                    pw.println("\t\t\t<tr>");
                    pw.print("\t\t\t\t<td>");
                    pw.print(item.getName());
                    pw.println("</td>");
                    pw.print("\t\t\t\t<td>");
                    pw.print(item.getPrize());
                    pw.println("</td>");
                    pw.print("\t\t\t\t<td>");
                    pw.print(item.getCategory().getName());
                    pw.println("</td>");
                    pw.println("\t\t\t</tr>");
                }   
                pw.println("\t\t</table>");
                pw.println("\t\t<span>Precio total:" + order.getTotalPrizeOrder() + "€ </span>");
            }
            pw.println("\t</body>");
        pw.println("</html>");
        pw.flush();
        pw.close();
    }
}
