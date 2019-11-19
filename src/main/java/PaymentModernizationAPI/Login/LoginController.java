package PaymentModernizationAPI.Login;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for login functionality
 */
@RestController
public class LoginController {

    private LoginService loginService;

    /**
     * Constructor for LoginController
     */
    public LoginController(){
        loginService = new LoginService();
    }

    /**
     * Returns information regarding the validity of the provided login information
     * @param authorization User's information: username and password
     * @return Information regarding the validity of the provided login information
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestHeader(value = "Authorization") String authorization){
        return loginService.isValidLogin(authorization);
    }

}
