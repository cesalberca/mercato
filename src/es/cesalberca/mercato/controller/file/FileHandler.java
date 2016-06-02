package es.cesalberca.mercato.controller.file;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.model.Item;
import es.cesalberca.mercato.model.Order;
import es.cesalberca.mercato.model.User;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 *
 * @author César Alberca
 */
public class FileHandler {
    
    /**
     * Exporta los pedidos de un usuario a html.
     * @param u
     * @throws SQLException
     * @throws FileNotFoundException 
     */
    public void exportToHtml(User u) throws FileNotFoundException {
        DatabaseHandler dbh = new DatabaseHandler();
        PrintWriter pw = null;
        
        pw = new PrintWriter(u.getName() + ".html");
        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
            pw.println("\t<head>");
                pw.println("\t\t<meta charset='UTF-8'>");
                pw.println("\t\t<title> Pedidos: "  + u.getName() + "</title>");
            pw.println("\t</head>");
            pw.println("\t<body>");
            for (Order order : u.getOrders()) {
                pw.println("\t\t<table>");
                for (Item item : order.getItems()) {
                    pw.println("\t\t\t<tr>");
                        pw.println("\t\t\t\t<td>");
                        pw.print(item.getName());
                        pw.print("</td>");
                        pw.println("\t\t\t\t<td>");
                        pw.print(item.getPrize());
                        pw.print("</td>");
                        pw.println("\t\t\t\t<td>");
                        pw.print(item.getCategory().getName());
                        pw.print("</td>");
                        pw.println("\t\t\t\t<td>");
                        pw.print("</td>");
                    pw.println("\t\t\t</tr>");
                }   
                pw.println("\t\t</table>");
            }
            pw.println("\t</body>");
        pw.println("</html>");
        pw.flush();
        pw.close();
    }
}
