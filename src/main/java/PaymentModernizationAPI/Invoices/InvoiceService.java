package PaymentModernizationAPI.Invoices;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    /**
     * Returns information about all invoices related to a user
     * @param authorization Authorization token of a user
     * @return JSON information about all invoices related to a user
     */
    String invoices(String authorization){
        // Create the JSON to be returned
        JSONObject invoiceJSON = new JSONObject();
        invoiceJSON.put("invoices", JSONObject.NULL);
        // Try to get invoices for the user
        try{
            // Getting the invoice information
            ResultSet invoices = invoiceDAO.invoices(authorization);
            // Continuing if at least one invoice is returned
            if(invoices.next()){
                invoices.beforeFirst();
                JSONArray invoicesJSON = new JSONArray();
                // Looping through the invoices
                while(invoices.next()){
                    JSONObject tempInvoiceJSON = new JSONObject();
                    tempInvoiceJSON.put("invoiceDate", JSONObject.NULL);
                    tempInvoiceJSON.put("deliveryDate", JSONObject.NULL);
                    tempInvoiceJSON.put("paymentDate", JSONObject.NULL);
                    // Add other user's information
                    tempInvoiceJSON.put("supplier", invoices.getString("supplier_name"));
                    tempInvoiceJSON.put("business", invoices.getString("business_name"));
                    // Adding the dates if they exist
                    if(invoices.getString("invoice_date") != null){
                        tempInvoiceJSON.put("invoiceDate", invoices.getString("invoice_date"));
                    }
                    if(invoices.getString("delivery_date") != null){
                        tempInvoiceJSON.put("deliveryDate", invoices.getString("delivery_date"));
                    }
                    if(invoices.getString("payment_date") != null){
                        tempInvoiceJSON.put("paymentDate", invoices.getString("payment_date"));
                    }
                    tempInvoiceJSON.put("status", invoices.getString("status"));
                    invoicesJSON.put(tempInvoiceJSON);
                }
                invoiceJSON.put("invoices", invoicesJSON);
            }
        } catch(Exception e){
            invoiceJSON.put("invoices", JSONObject.NULL);
        }
        // Return JSON
        return invoiceJSON.toString();
    }

    /**
     * Get the user's type from the database
     * @param authorization User's auth token
     * @return User's type
     * @throws SQLException Error while retrieving user type
     */
    private String getUserType(String authorization) throws SQLException {
        ResultSet typeInfo = invoiceDAO.userType(authorization);
        typeInfo.next();
        return typeInfo.getString("user_type");
    }

}
