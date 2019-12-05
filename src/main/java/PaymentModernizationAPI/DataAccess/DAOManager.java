package PaymentModernizationAPI.DataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * DAO for connecting to database
 */
public class DAOManager {

    // Connection variables
    private static ArrayList<Connection> connections = new ArrayList<>();

    /**
     * Returns connection to database
     *
     * @return Connection to database
     */
    public static Connection getConnection() throws SQLException{
        String password = "a7feaaba";
        String connectionURL = "jdbc:mysql://us-cdbr-iron-east-05.cleardb.net:3306/heroku_b8a1f59b8d70fd1";
        String username = "b9657ba5187062";
        Connection conn = DriverManager.getConnection(connectionURL, username, password);
        connections.add(conn);
        return conn;
    }

    /**
     * Returns statement for database connection
     *
     * @return Statement for database connection
     */
    public static Statement getStatement() throws SQLException{
        return getConnection().createStatement();
    }

    /**
     * Removes all closed connections
     *
     * @throws SQLException Error while removing closed connections
     */
    public static void reset() throws SQLException {
        /*Iterator<Connection> iterator = connections.iterator();
        while(iterator.hasNext()){
            if(iterator.next().isClosed()){
                iterator.remove();
            }
        }*/
    }

}
