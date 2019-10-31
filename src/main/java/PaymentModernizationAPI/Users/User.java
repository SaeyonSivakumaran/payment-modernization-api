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
    // User's full name
    private String name;
    // List of invoices connected to this user
    private ArrayList<Order> orders;
    // User's type
    private UserTypes type;

    // Enum for user types
    public enum UserTypes {
        SUPPLIER, SMALL_BUSINESS, DELIVERY_PERSON
    }

    /**
     * Constructor for user
     * @param username Username
     * @param password Password
     * @param type Type
     */
    public User(String username, String password, String name, UserTypes type){
        this.username = username;
        this.password = password;
        this.name = name;
        this.type = type;
        this.orders = new ArrayList<>();
    }

    /**
     * Get user's username
     * @return User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set user's username
     * @param username New username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get user's password
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set user's password
     * @param password New password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get user's auth token
     * @return User's auth token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Set user's auth token
     * @param authToken New auth token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Get user's orders
     * @return User's orders
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Get user's type
     * @return User's type
     */
    public UserTypes getType() {
        return type;
    }

    /**
     * Set user's type
     * @param type New type
     */
    public void setType(UserTypes type) {
        this.type = type;
    }

    /**
     * Get user's name
     * @return User's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set user's name
     * @param name New name
     */
    public void setName(String name) {
        this.name = name;
    }
}
