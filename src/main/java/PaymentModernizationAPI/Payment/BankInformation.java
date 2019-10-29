package PaymentModernizationAPI.Payment;

/**
 * A class representing a user's bank information
 */
public class BankInformation {

    // Banking information
    private String accountNum;
    private String cardNum;
    private Banks bank;

    // Enum for supported banks
    private enum Banks{
        SCOTIABANK, RBC, CIBC, TD, BMO
    }

}
