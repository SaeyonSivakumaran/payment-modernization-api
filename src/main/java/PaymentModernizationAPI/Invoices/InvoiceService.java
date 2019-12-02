package PaymentModernizationAPI.Invoices;

import org.apache.commons.lang3.EnumUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

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
     * Returns all the invoices associated with a user
     *
     * @param authorization User's auth token
     * @return Invoices associated with a user
     */
    String getInvoices(String authorization) {
        // Create the JSON to be returned
        JSONObject invoicesJSON = new JSONObject();
        invoicesJSON.put("invoices", JSONObject.NULL);
        // Try to get the invoices for the user
        try {
            ResultSet invoices = invoiceDAO.getInvoices(authorization);
            JSONArray invoicesArray = retrieveInvoiceInfo(invoices);
            // Closing invoices connection
            Statement invoicesStatement = invoices.getStatement();
            Connection invoicesConnection = invoicesStatement.getConnection();
            invoicesStatement.close();
            invoicesConnection.close();
            // Adding items to the invoices
            ResultSet items = invoiceDAO.getAllItems();
            HashMap<String, JSONArray> itemsMap = new HashMap<>();
            while (items.next()) {
                String id = items.getString("order_id");
                if (itemsMap.containsKey(id)) {
                    JSONObject tempItem = new JSONObject();
                    tempItem.put("description", items.getString("description"));
                    tempItem.put("quantity", items.getString("quantity"));
                    tempItem.put("price", items.getString("price"));
                    itemsMap.get(id).put(tempItem);
                } else {
                    JSONArray tempItems = new JSONArray();
                    JSONObject tempItem = new JSONObject();
                    tempItem.put("description", items.getString("description"));
                    tempItem.put("quantity", items.getString("quantity"));
                    tempItem.put("price", items.getString("price"));
                    tempItems.put(tempItem);
                    itemsMap.put(id, tempItems);
                }
            }
            // Closing items connection
            Statement itemsStatement = items.getStatement();
            Connection itemsConnection = itemsStatement.getConnection();
            itemsStatement.close();
            itemsConnection.close();
            for (int i = 0; i < invoicesArray.length(); i++) {
                JSONObject invoice = invoicesArray.getJSONObject(i);
                String invoiceId = invoice.getString("invoiceId");
                if (itemsMap.containsKey(invoiceId)) {
                    invoice.put("items", itemsMap.get(invoiceId));
                } else {
                    invoice.put("items", new JSONArray());
                }
            }
            invoicesJSON.put("invoices", invoicesArray);
            System.out.println(String.format("%s: /invoices. %s ", authorization, invoicesJSON.toString()));
        } catch (Exception e) {
            System.out.println(String.format("%s: /invoices. Error: %s", authorization, e.getMessage()));
            e.printStackTrace();
            invoicesJSON.put("invoices", JSONObject.NULL);
        }
        return invoicesJSON.toString();
    }

    /**
     * Return information for a specific invoice
     *
     * @param authorization User's authorization token
     * @param invoiceId     Invoice ID
     * @return Information for a specific invoice
     */
    JSONObject getInvoice(String authorization, String invoiceId) {
        // Try to get the invoice
        try {
            // Getting the invoice information
            ResultSet invoice = invoiceDAO.getInvoice(authorization, invoiceId);
            invoice.next();
            // Adding invoice information to JSON
            JSONArray invoicesJSON = retrieveInvoiceInfo(invoice);
            JSONObject invoiceJSON = invoicesJSON.getJSONObject(0);
            invoiceJSON.put("items", getInvoiceItems(authorization, invoiceId));
            // Closing invoice connection
            Statement invoiceStatement = invoice.getStatement();
            Connection invoiceConnection = invoiceStatement.getConnection();
            invoiceStatement.close();
            invoiceConnection.close();
            System.out.println(String.format("%s: /invoices/%s. %s ", authorization, invoiceId, invoiceJSON.toString()));
            return invoiceJSON;
        } catch (Exception e) {
            System.out.println(String.format("%s: /invoices/%s. Error: %s", authorization, invoiceId, e.getMessage()));
            e.printStackTrace();
            return new JSONObject().put("invoice", JSONObject.NULL);
        }
    }

    /**
     * Returns whether an invoice was created successfully or not
     *
     * @param authorization User's auth token
     * @param invoice       Invoice information
     * @return Whether an invoice was created successfully or not
     */
    String createInvoice(String authorization, Invoice invoice) {
        JSONObject creationJSON = new JSONObject();
        creationJSON.put("isValid", true);
        try {
            // Creating an invoice
            ResultSet invoiceIdResultSet = invoiceDAO.createInvoice(authorization, invoice);
            invoiceIdResultSet.next();
            String invoiceId = invoiceIdResultSet.getString("last_insert_id()");
            // Adding the invoice's items
            for (InvoiceItem item : invoice.getItems()) {
                item.setInvoiceId(invoiceId);
                if (invoiceDAO.addInvoiceItem(item) <= 0) {
                    creationJSON.put("isValid", false);
                    break;
                }
            }
            // Closing invoice id connection
            Statement invoiceIdStatement = invoiceIdResultSet.getStatement();
            Connection invoiceIdConnection = invoiceIdStatement.getConnection();
            invoiceIdStatement.close();
            invoiceIdConnection.close();
        } catch (Exception e) {
            creationJSON.put("isValid", false);
        }
        return creationJSON.toString();
    }

    /**
     * Change the status of an invoice
     *
     * @param authorization User's auth token
     * @param invoiceId     Invoice ID
     * @param status        New status
     * @return Whether the status update was successful or not
     */
    String changeStatus(String authorization, String invoiceId, String status) {
        JSONObject changedJSON = new JSONObject();
        changedJSON.put("isValid", false);
        // Checking if the status is valid
        if (EnumUtils.isValidEnum(Invoice.InvoiceStatus.class, status)) {
            // Changing the status
            try {
                if (invoiceDAO.changeStatus(authorization, invoiceId, status) > 0) {
                    changedJSON.put("isValid", true);
                }
            } catch (Exception e) {
                changedJSON.put("isValid", false);
            }
        }
        return changedJSON.toString();
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
            JSONArray supplierArray = new JSONArray();
            supplierArray.put(invoices.getString("supplier_street_address"));
            supplierArray.put(invoices.getString("supplier_city"));
            supplierArray.put(invoices.getString("supplier_region"));
            supplierArray.put(invoices.getString("supplier_country"));
            supplierArray.put(invoices.getString("supplier_postal_code"));
            tempInvoiceJSON.put("supplierAddress", supplierArray);
            tempInvoiceJSON.put("business", invoices.getString("business_name"));
            JSONArray businessArray = new JSONArray();
            businessArray.put(invoices.getString("business_street_address"));
            businessArray.put(invoices.getString("business_city"));
            businessArray.put(invoices.getString("business_region"));
            businessArray.put(invoices.getString("business_country"));
            businessArray.put(invoices.getString("business_postal_code"));
            tempInvoiceJSON.put("businessAddress", businessArray);
            // Adding the dates if they exist
            if (invoices.getString("invoice_date") != null) {
                tempInvoiceJSON.put("invoiceDate", invoices.getString("invoice_date"));
            }
            if (invoices.getString("due_date") != null) {
                tempInvoiceJSON.put("dueDate", invoices.getString("due_date"));
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
        // Closing invoice items connection
        Statement invoiceItemsStatement = invoiceItems.getStatement();
        Connection invoiceItemsConnection = invoiceItemsStatement.getConnection();
        invoiceItemsStatement.close();
        invoiceItemsConnection.close();
        return items;
    }

}
