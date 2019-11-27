package PaymentModernizationAPI.Documentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for documentation
 */
@RestController
@RequestMapping("")
public class DocumentationController {

    /**
     * Redirect method to access Postman API documentation
     * @return Postman API documentation
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView method() {
        return new ModelAndView("redirect:https://documenter.getpostman.com/view/9582175/SW7dWSQp?version=latest");
    }
}
