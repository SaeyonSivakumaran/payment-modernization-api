package PaymentModernizationAPI.Login;

import PaymentModernizationAPI.DataAccess.DAOManager;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO for login
 */
@Repository
public class LoginDAO {

    /**
     * Returns data for a specific username and password
     *
     * @param username Username
     * @param password Password
     * @return Info from table for the username and password
     * @throws SQLException Error when retrieving details
     */
    ResultSet getAuthDetails(String username, String password) throws SQLException {
        DAOManager.reset();
        String infoQuery = String.format("SELECT auth_token, user_type, full_name FROM heroku_b8a1f59b8d70fd1.users " +
                "WHERE user_name='%s' AND password='%s';", username, password);
        return DAOManager.getStatement().executeQuery(infoQuery);
    }

}
