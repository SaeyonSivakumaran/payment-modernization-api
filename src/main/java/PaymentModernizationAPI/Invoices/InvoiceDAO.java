package PaymentModernizationAPI.Invoices;

import PaymentModernizationAPI.DataAccess.DAOManager;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * DAO for invoices
 */
@Repository
public class InvoiceDAO {

    /**
     * Returns data regarding all invoices related to a user
     *
     * @param authorization User's auth token
     * @return Data regarding all invoices related to a user
     * @throws SQLException Error while retrieving invoice information
     */
    ResultSet invoices(String authorization) throws SQLException {
        DAOManager.reset();
        String invoiceQuery = String.format("CALL get_brief_invoices('%s');", authorization);
        return DAOManager.getStatement().executeQuery(invoiceQuery);
    }

    /**
     * Returns type of a user from the database
     *
     * @param authorization User's auth token
     * @return User's type
     * @throws SQLException Error while retrieving user's type
     */
    ResultSet userType(String authorization) throws SQLException {
        DAOManager.reset();
        String typeQuery = String.format("SELECT user_type FROM users WHERE auth_token='%s'", authorization);
        return DAOManager.getStatement().executeQuery(typeQuery);
    }

    /**
     * Return invoice information
     *
     * @param authorization User's auth token
     * @param invoiceId     Invoice ID
     * @return Invoice information
     * @throws SQLException Error while retrieving invoice information
     */
    ResultSet getInvoice(String authorization, String invoiceId) throws SQLException {
        DAOManager.reset();
        String invoiceQuery = String.format("CALL get_invoice('%s', '%s');", authorization, invoiceId);
        return DAOManager.getStatement().executeQuery(invoiceQuery);
    }

    /**
     * Return items for an invoice
     *
     * @param authorization User's auth token
     * @param invoiceId     Invoice ID
     * @return Invoice item information
     * @throws SQLException Error while retrieving invoice item information
     */
    ResultSet getInvoiceItems(String authorization, String invoiceId) throws SQLException {
        DAOManager.reset();
        String invoiceItemQuery = String.format("CALL get_invoice_items('%s', '%s');", authorization, invoiceId);
        return DAOManager.getStatement().executeQuery(invoiceItemQuery);
    }

    /**
     * Create invoice and return ID of invoice created
     *
     * @param authorization User's auth token
     * @param invoice       Invoice information
     * @return ID of invoice created
     * @throws SQLException Error while creating a new invoice
     */
    ResultSet createInvoice(String authorization, Invoice invoice) throws SQLException {
        DAOManager.reset();
        String invoiceDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(invoice.getInvoiceDate());
        String dueDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(invoice.getDueDate());
        String createQuery = String.format("CALL insert_invoice_info('%s', '%s', '%s', '%s', '%s', '%s');",
                authorization, invoice.getSmallBusiness(), invoice.getDeliveryPerson(), invoiceDate, dueDate,
                invoice.getStatus().toString());
        return DAOManager.getStatement().executeQuery(createQuery);
    }

    /**
     * Add an item to a specific invoice
     *
     * @param item          Item to be added to the invoice
     * @return Number of rows affected
     * @throws SQLException Error while adding item to the invoice
     */
    int addInvoiceItem(InvoiceItem item) throws SQLException {
        DAOManager.reset();
        String addItemQuery = String.format("CALL insert_invoice_items('%s', '%s', '%s', '%s');",
                item.getInvoiceId(), item.getDescription(), String.valueOf(item.getQuantity()),
                String.valueOf(item.getPrice()));
        return DAOManager.getStatement().executeUpdate(addItemQuery);
    }

}
