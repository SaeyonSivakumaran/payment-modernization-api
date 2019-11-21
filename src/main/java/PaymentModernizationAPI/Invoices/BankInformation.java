package PaymentModernizationAPI.Invoices;

/**
 * A class representing a user's bank information
 */
public class BankInformation {

    // Banking information
    private String accountNum;
    private String cardNum;
    private Banks bank;

    // Enum for supported banks
    public enum Banks {
        SCOTIABANK, RBC, CIBC, TD, BMO
    }

    /**
     * Constructor for BankInformation
     *
     * @param accountNum Account number
     * @param cardNum    Card number
     * @param bank       Bank
     */
    public BankInformation(String accountNum, String cardNum, Banks bank) {
        this.accountNum = accountNum;
        this.cardNum = cardNum;
        this.bank = bank;
    }

    /**
     * Get account number
     *
     * @return Account number
     */
    public String getAccountNum() {
        return accountNum;
    }

    /**
     * Set account number
     *
     * @param accountNum New account number
     */
    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    /**
     * Get card number
     *
     * @return Card number
     */
    public String getCardNum() {
        return cardNum;
    }

    /**
     * Set card number
     *
     * @param cardNum New card number
     */
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    /**
     * Get bank
     *
     * @return Bank
     */
    public Banks getBank() {
        return bank;
    }

    /**
     * Set bank
     *
     * @param bank Bank
     */
    public void setBank(Banks bank) {
        this.bank = bank;
    }
}
