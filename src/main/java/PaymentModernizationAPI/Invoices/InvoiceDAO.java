package PaymentModernizationAPI.Invoices;

import PaymentModernizationAPI.DataAccess.DAOManager;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * DAO for invoices
 */
@Repository
public class InvoiceDAO {

    /**
     * Returns all the invoices associated with a user
     *
     * @param authorization User's auth token
     * @return Invoices associated with a user
     * @throws SQLException Error while retrieving invoices
     */
    ResultSet getInvoices(String authorization) throws SQLException {
        //DAOManager.reset();
        CallableStatement cs = DAOManager.getConnection().prepareCall("{call get_invoices(?)}");
        cs.setString("auth_token", authorization);
        cs.execute();
        return cs.getResultSet();
    }

    /**
     * Returns all the invoice items in the database
     *
     * @param reset Whether to reset the connection or not
     * @return All the invoice items in the database
     * @throws SQLException Error while getting invoice items
     */
    ResultSet getAllItems(boolean reset) throws SQLException {
        if(reset){
            DAOManager.reset();
        }
        String itemsQuery = String.format("SELECT * FROM invoice_items;");
        return DAOManager.getStatement().executeQuery(itemsQuery);
    }

    /**
     * Returns the IDs of all invoices related to a certain user
     *
     * @param authorization User's auth token
     * @return IDs of all invoices related to the user
     * @throws SQLException Error while retrieving invoice IDs
     */
    ResultSet getInvoiceIDs(String authorization) throws SQLException {
        DAOManager.reset();
        String idQuery = String.format("CALL get_invoice_ids('%s');", authorization);
        return DAOManager.getStatement().executeQuery(idQuery);
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
     * @param item Item to be added to the invoice
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

    /**
     * Change status of an invoice
     *
     * @param authorization User's auth token
     * @param invoiceId     Invoice ID
     * @param status        New status
     * @return Number of rows affected
     * @throws SQLException Error while changing status
     */
    int changeStatus(String authorization, String invoiceId, String status) throws SQLException {
        DAOManager.reset();
        String changeStatusQuery = String.format("CALL update_status('%s', '%s', '%s');",
                authorization, invoiceId, status);
        return DAOManager.getStatement().executeUpdate(changeStatusQuery);
    }

}
