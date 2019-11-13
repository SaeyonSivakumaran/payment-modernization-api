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

    ResultSet invoices(String authorization) throws SQLException {
        DAOManager.reset();
        String invoiceQuery = String.format("CALL get_brief_invoices('%s');", authorization);
        return DAOManager.getStatement().executeQuery(invoiceQuery);
    }

}
