package PaymentModernizationAPI.Invoices;

import org.springframework.web.bind.annotation.*;

/**
 * Controller for invoices
 */
@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    /**
     * Constructor for InvoiceController
     */
    InvoiceController() {
        invoiceService = new InvoiceService();
    }

    /**
     * Returns all invoices associated with a specific user
     *
     * @param authorization User's authorization token
     * @return All invoices associated with a specific user
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String invoices(@RequestHeader(value = "Authorization") String authorization) {
        return invoiceService.invoices(authorization);
    }

    /**
     * Return information for a specific invoice
     *
     * @param authorization User's authorization token
     * @param invoiceId     Invoice ID
     * @return Information for a specific invoice
     */
    @RequestMapping(value = "/{invoiceId}", method = RequestMethod.GET)
    public String getInvoice(@RequestHeader(value = "Authorization") String authorization,
                             @PathVariable(value = "invoiceId") String invoiceId) {
        return invoiceService.getInvoice(authorization, invoiceId);
    }

}
