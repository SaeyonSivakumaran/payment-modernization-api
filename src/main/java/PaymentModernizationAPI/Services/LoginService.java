package PaymentModernizationAPI.Services;

import org.springframework.stereotype.Service;

/**
 * Service for login functionality
 */
@Service
public class LoginService {
    /**
     * Returns whether the login information provided is valid (matches a user)
     * @param authInfo User's information: username and password
     * @return Whether the login is valid or not
     */
    public boolean isValidLogin(String authInfo){
        return authInfo.equals("valid"); //THIS IS FOR TESTING
    }
}
