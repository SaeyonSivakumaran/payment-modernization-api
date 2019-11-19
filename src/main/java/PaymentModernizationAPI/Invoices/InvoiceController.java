package PaymentModernizationAPI.Invoices;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * Controller for invoices
 */
@RestController
@RequestMapping(value = "/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    /**
     * Constructor for InvoiceController
     */
    InvoiceController(){
        invoiceService = new InvoiceService();
    }

    /**
     * Returns all invoices associated with a specific user
     * @param authorization User's authorization token
     * @return All invoices associated with a specific user
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String invoices(@RequestHeader(value = "Authorization") String authorization) {
        return invoiceService.invoices(authorization);
    }

}
