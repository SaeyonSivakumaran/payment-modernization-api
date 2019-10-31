package PaymentModernizationAPI.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DAO for connecting to database
 */
public class DAOManager {

    // Connection variables
    private static Connection connection;
    private static Statement statement;

    /**
     * Returns statement for database connection
     * @return Statement for database connection
     */
    public static Statement getStatement(){
        return statement;
    }

    /**
     * Resets the connection variable
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
     * @throws SQLException Error while getting database statement
     */
    private static void resetStatement() throws SQLException {
        statement = connection.createStatement();
    }

    /**
     * Resets the connection and statement for new usage
     * @throws SQLException Error while resetting the connection and statement
     */
    public static void reset() throws SQLException{
        close();
        resetConnection();
        resetStatement();
    }

    /**
     * Closes the connection and statement
     * @throws SQLException Error while closing the connection and statement
     */
    private static void close() throws SQLException{
        if(statement != null && connection != null){
            statement.close();
            connection.close();
        }
    }

}
