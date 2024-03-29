package PaymentModernizationAPI.Invoices;

import PaymentModernizationAPI.DataAccess.DAOManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
        DAOManager.reset();
        CallableStatement cs = DAOManager.getConnection().prepareCall("{call get_invoices(?)}");
        cs.setString("auth_token", authorization);
        cs.execute();
        return cs.getResultSet();
    }

    /**
     * Returns all the invoice items in the database
     *
     * @return All the invoice items in the database
     * @throws SQLException Error while getting invoice items
     */
    ResultSet getAllItems() throws SQLException {
        DAOManager.reset();
        String itemsQuery = "SELECT * FROM invoice_items;";
        return DAOManager.getStatement().executeQuery(itemsQuery);
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
        Statement statement = DAOManager.getStatement();
        int rows = statement.executeUpdate(addItemQuery);
        // Closing the connection
        Connection connection = statement.getConnection();
        statement.close();
        connection.close();
        return rows;
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
        Statement statement = DAOManager.getStatement();
        int rows = statement.executeUpdate(changeStatusQuery);
        // Closing the connection
        Connection connection = statement.getConnection();
        statement.close();
        connection.close();
        return rows;
    }

    /**
     * Change delivery date of an invoice
     *
     * @param authorization User's auth token
     * @param invoiceId     Invoice ID
     * @param deliveryDate  New delivery date
     * @return Number of rows affected
     * @throws SQLException Error while changing delivery date
     */
    int changeDeliveryDate(String authorization, String invoiceId, String deliveryDate) throws SQLException {
        DAOManager.reset();
        String changeDeliveryDateQuery = String.format("CALL update_delivery_date('%s', '%s', '%s');",
                authorization, invoiceId, deliveryDate);
        Statement statement = DAOManager.getStatement();
        int rows = statement.executeUpdate(changeDeliveryDateQuery);
        // Closing the connection
        Connection connection = statement.getConnection();
        statement.close();
        connection.close();
        return rows;
    }

    /**
     * Change payment date of an invoice
     *
     * @param authorization User's auth token
     * @param invoiceId     Invoice ID
     * @param paymentDate  New payment date
     * @return Number of rows affected
     * @throws SQLException Error while changing payment date
     */
    int changePaymentDate(String authorization, String invoiceId, String paymentDate) throws SQLException {
        DAOManager.reset();
        String changePaymentDateQuery = String.format("CALL update_payment_date('%s', '%s', '%s');",
                authorization, invoiceId, paymentDate);
        Statement statement = DAOManager.getStatement();
        int rows = statement.executeUpdate(changePaymentDateQuery);
        // Closing the connection
        Connection connection = statement.getConnection();
        statement.close();
        connection.close();
        return rows;
    }

    /**
     * Change driver of an invoice
     *
     * @param authorization User's auth token
     * @param invoiceId     Invoice ID
     * @param driver        New driver
     * @return Number of rows affected
     * @throws SQLException Error while changing driver
     */
    int changeDriver(String authorization, String invoiceId, String driver) throws SQLException {
        DAOManager.reset();
        String changeDriverQuery = String.format("CALL update_driver('%s', '%s', '%s');",
                authorization, invoiceId, driver);
        Statement statement = DAOManager.getStatement();
        int rows = statement.executeUpdate(changeDriverQuery);
        // Closing the connection
        Connection connection = statement.getConnection();
        statement.close();
        connection.close();
        return rows;
    }

}
