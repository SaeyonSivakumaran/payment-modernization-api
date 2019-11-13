package PaymentModernizationAPI.Invoices;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;

/**
 * Service for invoices
 */
@Service
public class InvoiceService {

    private InvoiceDAO invoiceDAO;

    /**
     * Constructor for InvoiceService
     */
    InvoiceService(){
        invoiceDAO = new InvoiceDAO();
    }

    String invoices(String authorization){
        // Create the JSON to be returned
        JSONObject invoiceJSON = new JSONObject();
        invoiceJSON.put("isValid", true);
        invoiceJSON.put("invoices", JSONObject.NULL);
        // Try to get invoices for the user
        try{
            ResultSet invoices = invoiceDAO.invoices(authorization);
            // Continuing if at least one invoice is returned
            if(invoices.next()){
                invoices.beforeFirst();
                JSONArray invoicesJSON = new JSONArray();
                // Looping through the invoices
                while(invoices.next()){
                    JSONObject tempInvoiceJSON = new JSONObject();
                    tempInvoiceJSON.put("invoiceDate", invoices.getString("invoice_date"));
                    tempInvoiceJSON.put("status", invoices.getString("status"));
                    invoicesJSON.put(tempInvoiceJSON);
                }
                invoiceJSON.put("invoices", invoicesJSON);
            } else {
                invoiceJSON.put("isValid", false);
            }
        } catch(Exception e){
            invoiceJSON.put("isValid", false);
        }
        // Return JSON
        return invoiceJSON.toString();
    }

}
