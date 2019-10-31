package PaymentModernizationAPI.Login;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;

/**
 * Service for login functionality
 */
@Service
public class LoginService {

    private LoginDAO loginDAO;

    /**
     * Constructor for LoginService
     */
    public LoginService(){
        loginDAO = new LoginDAO();
    }

    /**
     * Returns the JSON authorization information for a user
     * @param authInfo User's information: username and password
     * @return JSON information about authorization
     */
    String isValidLogin(String authInfo){
        // Create the JSON to be returned
        JSONObject loginJSON = new JSONObject();
        loginJSON.put("isValid", true);
        loginJSON.put("authToken", JSONObject.NULL);
        // Try to decode authentication information and find the auth token
        try{
            // Decoding authentication information
            String decodedInfo = new String(Base64.decodeBase64(authInfo));
            String username = decodedInfo.substring(0, decodedInfo.indexOf(":"));
            String password = decodedInfo.substring(decodedInfo.indexOf(":") + 1);
            // Get info from database for these credentials
            ResultSet userAuthInfo = loginDAO.getAuthDetails(username, password);
            userAuthInfo.next();
            loginJSON.put("authToken", userAuthInfo.getString("auth_token"));
        } catch(Exception e){
            loginJSON.put("isValid", false);
            loginJSON.put("errorMsg", e.getMessage());
        }
        // Return JSON
        return loginJSON.toString();
    }
}
