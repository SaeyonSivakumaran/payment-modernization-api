package PaymentModernizationAPI.Controllers;

import PaymentModernizationAPI.Services.LoginService;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

/**
 * Controller for login functionality
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    private LoginService loginService;

    /**
     * Constructor for LoginController
     * @throws SQLException SQL error when creating LoginController
     */
    public LoginController() throws SQLException{
        loginService = new LoginService();
    }

    /**
     * Returns information regarding the validity of the provided login information
     * @param authorization User's information: username and password
     * @return Information regarding the validity of the provided login information
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String login(@RequestHeader(value = "Authorization") String authorization){
        return loginService.isValidLogin(authorization);
    }

}
