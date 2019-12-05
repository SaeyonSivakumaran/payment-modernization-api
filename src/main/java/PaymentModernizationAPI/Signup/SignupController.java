package PaymentModernizationAPI.Signup;

import PaymentModernizationAPI.Invoices.BankInformation;
import PaymentModernizationAPI.Users.Address;
import PaymentModernizationAPI.Users.Company;
import PaymentModernizationAPI.Users.User;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for sign up functionality
 */
@RestController
public class SignupController {

    private SignupService signupService;

    /**
     * Constructor for SignupController
     */
    SignupController() {
        this.signupService = new SignupService();
    }

    /**
     * Returns information outlining whether the sign up was successful
     *
     * @param userMap Sign up form information
     * @return Information outlining whether the sign up was successful
     */
    @RequestMapping(value = "/signup-user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String signupUser(@RequestBody MultiValueMap<String, String> userMap) {
        // Get sign up field information
        String username = userMap.get("username").get(0);
        String password = userMap.get("password").get(0);
        String name = userMap.get("name").get(0);
        User.UserTypes type = User.UserTypes.valueOf(userMap.get("type").get(0));
        // Create a user and try to sign them up
        User user = new User(username, password, name, type);
        return signupService.signupUser(user);
    }

    /**
     * Returns information outlining whether the sign up was successful
     *
     * @param companyMap Sign up form information
     * @return Information outlining whether the sign up was successful
     */
    @RequestMapping(value = "/signup-company", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String signupCompany(@RequestBody MultiValueMap<String, String> companyMap) {
        // Get sign up field information
        String username = companyMap.get("username").get(0);
        String password = companyMap.get("password").get(0);
        String name = companyMap.get("name").get(0);
        User.UserTypes type = User.UserTypes.valueOf(companyMap.get("type").get(0));
        String accountNum = companyMap.get("accountNum").get(0);
        BankInformation.Banks bank = BankInformation.Banks.valueOf(companyMap.get("bank").get(0));
        String streetAddress = companyMap.get("streetAddress").get(0);
        String city = companyMap.get("city").get(0);
        String region = companyMap.get("region").get(0);
        String country = companyMap.get("country").get(0);
        String postalCode = companyMap.get("postalCode").get(0);
        // Create a company and try to sign them up
        BankInformation bankInfo = new BankInformation(accountNum, null, bank);
        Address address = new Address(streetAddress, city, region, country, postalCode);
        Company company = new Company(username, password, name, type, bankInfo, address);
        return signupService.signupUser(company);
    }

}
