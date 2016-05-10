package es.cesalberca.mercato.model.database;

/**
 *
 * @author César Alberca
 */
public class Sqlite {
    private static final String SQLITE_DIRECTORY = "bbdd/mercatodb";
    private static final String JDBC_CONNECTION = "jdbc:sqlite:";

    public static String getSQLITE_DIRECTORY() {
        return SQLITE_DIRECTORY;
    }

    public static String getJDBC_CONNECTION() {
        return JDBC_CONNECTION;
    }
}
