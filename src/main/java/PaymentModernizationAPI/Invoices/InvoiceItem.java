package PaymentModernizationAPI.Invoices;

/**
 * An item in an order
 */
public class InvoiceItem {

    // Information about the item being ordered
    private String invoiceId;
    private int quantity;
    private String description;
    private double price;

    /**
     * Constructor for InvoiceItem
     *
     * @param quantity    Quantity
     * @param description Description of item
     * @param price       Price of item
     */
    InvoiceItem(int quantity, String description, double price) {
        this.quantity = quantity;
        this.description = description;
        this.price = price;
    }

    /**
     * Get invoice ID
     *
     * @return Invoice ID
     */
    public String getInvoiceId() {
        return invoiceId;
    }

    /**
     * Set invoice ID
     *
     * @param invoiceId New invoice ID
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Get quantity
     *
     * @return Quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set quantity
     *
     * @param quantity New quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get description
     *
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     *
     * @param description New description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get price
     *
     * @return Price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set price
     *
     * @param price New price
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
