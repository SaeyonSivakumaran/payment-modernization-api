package PaymentModernizationAPI.Invoices;

import PaymentModernizationAPI.DataAccess.DAOManager;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

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

}
