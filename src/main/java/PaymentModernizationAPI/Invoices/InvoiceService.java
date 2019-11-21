package PaymentModernizationAPI.Invoices;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
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
    InvoiceService() {
        invoiceDAO = new InvoiceDAO();
    }

    /**
     * Returns information about all invoices related to a user
     *
     * @param authorization Authorization token of a user
     * @return JSON information about all invoices related to a user
     */
    String invoices(String authorization) {
        // Create the JSON to be returned
        JSONObject invoiceJSON = new JSONObject();
        invoiceJSON.put("invoices", JSONObject.NULL);
        // Try to get invoices for the user
        try {
            // Getting the invoice information
            ResultSet invoices = invoiceDAO.invoices(authorization);
            // Continuing if at least one invoice is returned
            if (invoices.next()) {
                JSONArray invoicesJSON = retrieveInvoiceInfo(invoices);
                invoiceJSON.put("invoices", invoicesJSON);
            }
        } catch (Exception e) {
            invoiceJSON.put("invoices", JSONObject.NULL);
        }
        // Return JSON
        return invoiceJSON.toString();
    }

    /**
     * Return information for a specific invoice
     *
     * @param authorization User's authorization token
     * @param invoiceId     Invoice ID
     * @return Information for a specific invoice
     */
    String getInvoice(String authorization, String invoiceId) {
        // Try to get the invoice
        try {
            // Create the JSON to be returned
            JSONObject invoiceJSON = new JSONObject();
            // Getting the invoice information
            ResultSet invoice = invoiceDAO.getInvoice(authorization, invoiceId);
            invoice.next();
            // Adding invoice information to JSON
            JSONArray invoicesJSON = retrieveInvoiceInfo(invoice);
            invoiceJSON = invoicesJSON.getJSONObject(0);
            invoiceJSON.put("items", getInvoiceItems(authorization, invoiceId));
            return invoiceJSON.toString();
        } catch (Exception e) {
            return new JSONObject().put("invoice", JSONObject.NULL).toString();
        }
    }

    /**
     * Return all invoice information from the given ResultSet as a JSONArray
     *
     * @param invoices ResultSet containing invoices
     * @return Invoice information from the ResultSet as a JSONArray
     * @throws SQLException Error while retrieving invoice information
     */
    private JSONArray retrieveInvoiceInfo(ResultSet invoices) throws SQLException {
        invoices.beforeFirst();
        JSONArray invoicesJSON = new JSONArray();
        // Looping through the invoices
        while (invoices.next()) {
            JSONObject tempInvoiceJSON = new JSONObject();
            tempInvoiceJSON.put("invoiceDate", JSONObject.NULL);
            tempInvoiceJSON.put("deliveryDate", JSONObject.NULL);
            tempInvoiceJSON.put("paymentDate", JSONObject.NULL);
            // Adding the invoice ID
            tempInvoiceJSON.put("invoiceId", invoices.getString("id"));
            // Adding other user's information
            tempInvoiceJSON.put("supplier", invoices.getString("supplier_name"));
            tempInvoiceJSON.put("business", invoices.getString("business_name"));
            // Adding the dates if they exist
            if (invoices.getString("invoice_date") != null) {
                tempInvoiceJSON.put("invoiceDate", invoices.getString("invoice_date"));
            }
            if (invoices.getString("delivery_date") != null) {
                tempInvoiceJSON.put("deliveryDate", invoices.getString("delivery_date"));
            }
            if (invoices.getString("payment_date") != null) {
                tempInvoiceJSON.put("paymentDate", invoices.getString("payment_date"));
            }
            tempInvoiceJSON.put("status", invoices.getString("status"));
            invoicesJSON.put(tempInvoiceJSON);
        }
        return invoicesJSON;
    }

    /**
     * Get all items in an invoice as a JSONArray
     *
     * @param authorization User's auth token
     * @param invoiceId     Invoice ID
     * @return Items in an invoice as a JSONArray
     * @throws SQLException Error while getting the items of an invoice
     */
    private JSONArray getInvoiceItems(String authorization, String invoiceId) throws SQLException {
        JSONArray items = new JSONArray();
        ResultSet invoiceItems = invoiceDAO.getInvoiceItems(authorization, invoiceId);
        // Adding all items to the array
        while (invoiceItems.next()) {
            JSONObject tempItem = new JSONObject();
            tempItem.put("description", invoiceItems.getString("description"));
            tempItem.put("quantity", invoiceItems.getString("quantity"));
            tempItem.put("price", invoiceItems.getString("price"));
            items.put(tempItem);
        }
        return items;
    }

    /**
     * Get the user's type from the database
     *
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
