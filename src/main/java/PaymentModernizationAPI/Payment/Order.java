package PaymentModernizationAPI.Payment;

import PaymentModernizationAPI.Users.Company;
import PaymentModernizationAPI.Users.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * An order placed by a business
 */
public class Order {

    // Unique ID for this order
    private String id;
    // Users associated with this order
    private Company supplier;
    private Company smallBusiness;
    private User deliveryPerson;
    // Items in this order
    private ArrayList<OrderItem> items;
    // Relevant dates for this invoice - creation, due date and time of payment
    private Date invoiceDate;
    private Date dueDate;
    private Date paymentDate;
    // Order status
    private OrderStatus status;

    // Enum for order statuses
    private enum OrderStatus{
        NEW, PAID, DELIVERED, COMPLETE
    }

}
