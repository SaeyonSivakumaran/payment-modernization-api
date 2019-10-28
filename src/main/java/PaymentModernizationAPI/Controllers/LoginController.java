package PaymentModernizationAPI.Controllers;

import PaymentModernizationAPI.Services.LoginService;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.json.JSONObject;

/**
 * Controller for login functionality
 */
@RestController
@RequestMapping(value = "/Login")
public class LoginController {

    private LoginService loginService = new LoginService();

    /**
     * Returns information regarding the validity of the provided login information
     * @param authorization User's information: username and password
     * @return Information regarding the validity of the provided login information
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String login(@RequestHeader(value = "Authorization") String authorization) {
        return new JSONObject().put("isValid", loginService.isValidLogin(authorization)).toString();
    }

}
