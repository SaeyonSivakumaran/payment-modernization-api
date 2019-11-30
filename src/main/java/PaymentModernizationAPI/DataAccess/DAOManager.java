package PaymentModernizationAPI.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * DAO for connecting to database
 */
public class DAOManager {

    // Connection variables
    private static Connection connection;
    private static Statement statement;
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
        return DriverManager.getConnection(connectionURL, username, password);
    }

    /**
     * Returns statement for database connection
     *
     * @return Statement for database connection
     */
    public static Statement getStatement() throws SQLException{
        Connection newConnection = getConnection();
        connections.add(newConnection);
        return newConnection.createStatement();
    }

    /**
     * Resets the connection variable
     *
     * @throws SQLException Error while getting connection to database
     */
    private static void resetConnection() throws SQLException {
        String password = "a7feaaba";
        String connectionURL = "jdbc:mysql://us-cdbr-iron-east-05.cleardb.net:3306/heroku_b8a1f59b8d70fd1";
        String username = "b9657ba5187062";
        connection = DriverManager.getConnection(connectionURL, username, password);
    }

    /**
     * Resets the statement variable
     *
     * @throws SQLException Error while getting database statement
     */
    private static void resetStatement() throws SQLException {
        statement = connection.createStatement();
    }

    /**
     * Resets the connection and statement for new usage
     *
     * @throws SQLException Error while resetting the connection and statement
     */
    public static void reset() throws SQLException {
        Iterator<Connection> iter = connections.iterator();
        while (iter.hasNext()) {
            Connection conn = iter.next();
            if(conn.isValid(0)){
                conn.close();
                iter.remove();
            }
        }
        //close();
        //resetConnection();
        //resetStatement();
        System.out.println(String.format("Connection reset - Current connections: %s", connections.size()));
    }

    /**
     * Closes the connection and statement
     *
     * @throws SQLException Error while closing the connection and statement
     */
    private static void close() throws SQLException {
        if (statement != null && connection != null) {
            statement.close();
            connection.close();
        }
    }

}
