package PaymentModernizationAPI.Controllers;

import PaymentModernizationAPI.Services.LoginService;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for login functionality
 */
@RestController
@RequestMapping(value = "/Login")
public class LoginController {

    private LoginService loginService = new LoginService();

    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean login(@RequestHeader(value = "Authorization") String authorization) {
        return loginService.isValidLogin(authorization);
    }

}
