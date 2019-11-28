package PaymentModernizationAPI.Invoices;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        return invoiceService.getInvoices(authorization);
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
        return invoiceService.getInvoice(authorization, invoiceId).toString();
    }

    /**
     * Create new invoice
     *
     * @param authorization User's authorization token
     * @param invoiceMap    Invoice information
     * @return Whether creation of invoice was successful or not
     */
    @RequestMapping(value = "/new-invoice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createInvoice(@RequestHeader(value = "Authorization") String authorization,
                                @RequestBody MultiValueMap<String, String> invoiceMap) {
        try {
            // Getting invoice information
            String business = invoiceMap.get("business").get(0);
            String deliveryPerson = invoiceMap.get("deliveryPerson").get(0);
            Date invoiceDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(invoiceMap.get("invoiceDate").get(0));
            Date dueDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(invoiceMap.get("dueDate").get(0));
            // Getting the invoice items
            JSONArray items = new JSONObject(invoiceMap.get("items").get(0)).getJSONArray("items");
            ArrayList<InvoiceItem> invoiceItems = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                invoiceItems.add(new InvoiceItem(item.getInt("quantity"), item.getString("description"), item.getDouble("price")));
            }
            // Creating the invoice
            Invoice invoice = new Invoice(authorization, business, deliveryPerson, invoiceDate, dueDate, Invoice.InvoiceStatus.valueOf("NEW"));
            invoice.setItems(invoiceItems);
            return invoiceService.createInvoice(authorization, invoice);
        } catch (Exception e) {
            return invoiceService.createInvoice(authorization, null);
        }
    }

    /**
     * Update status of an existing invoice
     *
     * @param authorization User's auth token
     * @param statusMap     Status information
     * @param invoiceId     Invoice ID
     * @return Whether status change was successful or not
     */
    @RequestMapping(value = "/update-status/{invoiceId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String changeStatus(@RequestHeader(value = "Authorization") String authorization,
                               @RequestBody MultiValueMap<String, String> statusMap,
                               @PathVariable(value = "invoiceId") String invoiceId) {
        return invoiceService.changeStatus(authorization, invoiceId, statusMap.get("newStatus").get(0));
    }

}
