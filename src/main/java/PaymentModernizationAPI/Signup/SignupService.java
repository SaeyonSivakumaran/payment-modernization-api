package PaymentModernizationAPI.Signup;

import PaymentModernizationAPI.Users.Company;
import PaymentModernizationAPI.Users.User;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Service for sign up functionality
 */
@Service
public class SignupService {

    private SignupDAO signupDAO;

    /**
     * Constructor for SignupService
     */
    SignupService() {
        signupDAO = new SignupDAO();
    }

    /**
     * Returns JSON information related to whether sign up was successful or not
     *
     * @param user User to be signed up
     * @return JSON information related to whether sign up was successful or not
     */
    String signupUser(User user) {
        user.setAuthToken(user.getUsername() + "_AUTH_TOKEN");
        JSONObject signupJSON = new JSONObject();
        try {
            if (signupDAO.signupUser(user) > 0) {
                signupJSON.put("isValid", true);
            } else {
                signupJSON.put("isValid", false);
            }
        } catch (SQLException e) {
            signupJSON.put("isValid", false);
        }
        return signupJSON.toString();
    }

    /**
     * Returns JSON information related to whether sign up was successful or not
     *
     * @param company Company to be signed up
     * @return JSON information related to whether sign up was successful or not
     */
    String signupUser(Company company) {
        company.setAuthToken(company.getUsername() + "_AUTH_TOKEN");
        JSONObject signupJSON = new JSONObject();
        try {
            if (signupDAO.signupCompany(company) > 0) {
                signupJSON.put("isValid", true);
            } else {
                signupJSON.put("isValid", false);
            }
        } catch (SQLException e) {
            signupJSON.put("isValid", false);
        }
        return signupJSON.toString();
    }

}
