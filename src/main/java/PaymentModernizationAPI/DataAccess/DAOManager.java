package PaymentModernizationAPI.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DAO for connecting to database
 */
class DAOManager {

    /**
     * Returns connection to database
     * @return Connection to database
     * @throws SQLException Error while getting connection to database
     */
    private static Connection getConnection() throws SQLException {
        String password = "a7feaaba";
        String connectionURL = "jdbc:mysql://us-cdbr-iron-east-05.cleardb.net:3306/heroku_b8a1f59b8d70fd1";
        String username = "b9657ba5187062";
        return DriverManager.getConnection(connectionURL, username, password);
    }

    /**
     * Returns database statement
     * @return Database statement
     * @throws SQLException Error while getting database statement
     */
    static Statement getStatement() throws SQLException {
        return getConnection().createStatement();
    }

}
