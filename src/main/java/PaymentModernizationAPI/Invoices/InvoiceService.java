package PaymentModernizationAPI.Invoices;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        invoiceJSON.put("isValid", true);
        invoiceJSON.put("invoices", JSONObject.NULL);
        // Try to get invoices for the user
        try{
            // Getting the user's type
            String userType = getUserType(authorization);
            // Getting the invoice information
            ResultSet invoices = invoiceDAO.invoices(authorization);
            // Continuing if at least one invoice is returned
            if(invoices.next()){
                invoices.beforeFirst();
                JSONArray invoicesJSON = new JSONArray();
                // Looping through the invoices
                while(invoices.next()){
                    JSONObject tempInvoiceJSON = new JSONObject();
                    // Add other user's information based on who this user is
                    switch (userType){
                        case "DELIVERY_PERSON":
                            tempInvoiceJSON.put("supplier", invoices.getString("supplier_name"));
                            tempInvoiceJSON.put("business", invoices.getString("business_name"));
                            break;
                        case "SMALL_BUSINESS":
                            tempInvoiceJSON.put("supplier", invoices.getString("supplier_name"));
                            break;
                        case "SUPPLIER":
                            tempInvoiceJSON.put("business", invoices.getString("business_name"));
                            break;
                    }
                    tempInvoiceJSON.put("invoiceDate", invoices.getString("invoice_date"));
                    tempInvoiceJSON.put("deliveryDate", invoices.getString("delivery_date"));
                    tempInvoiceJSON.put("paymentDate", invoices.getString("payment_date"));
                    tempInvoiceJSON.put("status", invoices.getString("status"));
                    invoicesJSON.put(tempInvoiceJSON);
                }
                invoiceJSON.put("invoices", invoicesJSON);
            } else {
                invoiceJSON.put("isValid", false);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            invoiceJSON.put("isValid", false);
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
