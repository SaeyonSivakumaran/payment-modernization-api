package PaymentModernizationAPI.Users;

import PaymentModernizationAPI.Payment.Order;

import java.util.ArrayList;

/**
 * A user
 */
public class User {

    // User's authentication information
    private String username;
    private String password;
    private String authToken;
    // List of invoices connected to this user
    private ArrayList<Order> orders;
    // User's type
    private UserTypes type;

    // Enum for user types
    private enum UserTypes {
        SUPPLIER, SMALL_BUSINESS, DELIVERY_PERSON
    }

}
