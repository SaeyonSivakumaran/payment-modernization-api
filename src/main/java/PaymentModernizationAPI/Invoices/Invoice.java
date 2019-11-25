package PaymentModernizationAPI.Invoices;

import PaymentModernizationAPI.Users.Company;
import PaymentModernizationAPI.Users.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * An order placed by a business
 */
public class Invoice {

    // Users associated with this order
    private String supplier;
    private String smallBusiness;
    private String deliveryPerson;
    // Items in this order
    private ArrayList<InvoiceItem> items;
    // Relevant dates for this invoice - creation, delivered date, due date and time of payment
    private Date invoiceDate;
    private Date dueDate;
    private Date paymentDate;
    private Date deliveredDate;
    // Order status
    private InvoiceStatus status;

    // Enum for order statuses
    enum InvoiceStatus {
        NEW, PAID, DELIVERED, COMPLETE
    }

    /**
     * Constructor for Invoice
     *
     * @param supplier       Supplier auth token
     * @param smallBusiness  Business full name
     * @param deliveryPerson Delivery person full name
     * @param invoiceDate    Invoice date
     * @param dueDate        Due date of invoice
     * @param status         Invoice status
     */
    Invoice(String supplier, String smallBusiness, String deliveryPerson, Date invoiceDate, Date dueDate, InvoiceStatus status) {
        this.supplier = supplier;
        this.smallBusiness = smallBusiness;
        this.deliveryPerson = deliveryPerson;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    /**
     * Get supplier
     *
     * @return Supplier
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * Set supplier
     *
     * @param supplier New supplier
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * Get business
     *
     * @return Business
     */
    public String getSmallBusiness() {
        return smallBusiness;
    }

    /**
     * Set business
     *
     * @param smallBusiness New business
     */
    public void setSmallBusiness(String smallBusiness) {
        this.smallBusiness = smallBusiness;
    }

    /**
     * Get delivery person
     *
     * @return Delivery person
     */
    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    /**
     * Set delivery person
     *
     * @param deliveryPerson New delivery person
     */
    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    /**
     * Get invoice items
     *
     * @return Invoice items
     */
    public ArrayList<InvoiceItem> getItems() {
        return items;
    }

    /**
     * Set invoice items
     *
     * @param items New invoice items
     */
    public void setItems(ArrayList<InvoiceItem> items) {
        this.items = items;
    }

    /**
     * Get invoice date
     *
     * @return Invoice date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Set invoice date
     *
     * @param invoiceDate New invoice date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * Get due date
     *
     * @return Due date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Set due date
     *
     * @param dueDate New due date
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Get payment date
     *
     * @return Payment date
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * Set payment date
     *
     * @param paymentDate New payment date
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Get delivered date
     *
     * @return Delivered date
     */
    public Date getDeliveredDate() {
        return deliveredDate;
    }

    /**
     * Set delivered date
     *
     * @param deliveredDate New delivered date
     */
    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    /**
     * Get status
     *
     * @return Status
     */
    public InvoiceStatus getStatus() {
        return status;
    }

    /**
     * Set status
     *
     * @param status New status
     */
    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}
