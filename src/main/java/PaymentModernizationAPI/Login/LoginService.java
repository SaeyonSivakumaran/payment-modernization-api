package PaymentModernizationAPI.Login;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Service for login functionality
 */
@Service
public class LoginService {

    private LoginDAO loginDAO;

    /**
     * Constructor for LoginService
     */
    public LoginService() {
        loginDAO = new LoginDAO();
    }

    /**
     * Returns the JSON authorization information for a user
     *
     * @param authInfo User's information: username and password
     * @return JSON information about authorization
     */
    String isValidLogin(String authInfo) {
        // Create the JSON to be returned
        JSONObject loginJSON = new JSONObject();
        loginJSON.put("isValid", true);
        // Try to decode authentication information and find the auth token
        try {
            // Decoding authentication information
            String decodedInfo = new String(Base64.decodeBase64(authInfo));
            String username = decodedInfo.substring(0, decodedInfo.indexOf(":"));
            String password = decodedInfo.substring(decodedInfo.indexOf(":") + 1);
            // Get info from database for these credentials
            ResultSet userAuthInfo = loginDAO.getAuthDetails(username, password);
            userAuthInfo.next();
            loginJSON.put("authToken", userAuthInfo.getString("auth_token"));
            loginJSON.put("userType", userAuthInfo.getString("user_type"));
            loginJSON.put("fullName", userAuthInfo.getString("full_name"));
            loginJSON.put("bankNum", userAuthInfo.getString("bank_num"));
            loginJSON.put("bank", userAuthInfo.getString("bank"));
            loginJSON.put("streetAddress", userAuthInfo.getString("street_address"));
            loginJSON.put("city", userAuthInfo.getString("city"));
            loginJSON.put("region", userAuthInfo.getString("region"));
            loginJSON.put("country", userAuthInfo.getString("country"));
            loginJSON.put("postalCode", userAuthInfo.getString("postal_code"));
            // Closing userAuthInfo ResultSet
            Statement authInfoStatement = userAuthInfo.getStatement();
            Connection authInfoConnection = authInfoStatement.getConnection();
            authInfoStatement.close();
            authInfoConnection.close();
        } catch (Exception e) {
            loginJSON.put("isValid", false);
        }
        // Return JSON
        return loginJSON.toString();
    }
}
