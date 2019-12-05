package PaymentModernizationAPI.Signup;

import PaymentModernizationAPI.DataAccess.DAOManager;
import PaymentModernizationAPI.Invoices.BankInformation;
import PaymentModernizationAPI.Users.Address;
import PaymentModernizationAPI.Users.Company;
import PaymentModernizationAPI.Users.User;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * DAO for sign up
 */
@Repository
public class SignupDAO {

    /**
     * Returns number of affected rows when inserting a user
     *
     * @param user User to be inserted
     * @return Number of affected rows
     * @throws SQLException Error while inserting user
     */
    int signupUser(User user) throws SQLException {
        DAOManager.reset();
        String signupQuery = String.format("CALL insert_user('%s', '%s', '%s', '%s', '%s');", user.getUsername(),
                user.getPassword(), user.getAuthToken(), user.getName(), user.getType().toString());
        return DAOManager.getStatement().executeUpdate(signupQuery);
    }

    /**
     * Returns number of affected rows when inserting a company
     *
     * @param company Company to be inserted
     * @return Number of affected rows
     * @throws SQLException Error while inserting company
     */
    int signupCompany(Company company) throws SQLException {
        DAOManager.reset();
        BankInformation bankInfo = company.getBankInfo();
        Address address = company.getAddress();
        String signupQuery = String.format("CALL insert_company('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', " +
                        "'%s', '%s', '%s', '%s', '%s');", company.getUsername(), company.getPassword(),
                company.getAuthToken(), company.getName(), company.getType().toString(), bankInfo.getAccountNum(),
                bankInfo.getCardNum(), bankInfo.getBank().toString(), address.getStreetAddress(), address.getCity(),
                address.getRegion(), address.getCountry(), address.getPostalCode());
        System.out.println(signupQuery);
        return DAOManager.getStatement().executeUpdate(signupQuery);
    }

}
