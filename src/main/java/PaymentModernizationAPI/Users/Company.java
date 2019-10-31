package PaymentModernizationAPI.Users;

import PaymentModernizationAPI.Payment.BankInformation;

/**
 * A company user
 */
public class Company extends User{

    // Company's banking information
    private BankInformation bankInfo;
    // Company's address
    private Address address;

    /**
     * Constructor for Company
     * @param username Username
     * @param password Password
     * @param type Type
     * @param bankInfo Banking information
     * @param address Address
     */
    public Company(String username, String password, String name, UserTypes type, BankInformation bankInfo, Address address){
        super(username, password, name, type);
        this.bankInfo = bankInfo;
        this.address = address;
    }

    /**
     * Get company's banking information
     * @return Company's banking information
     */
    public BankInformation getBankInfo() {
        return bankInfo;
    }

    /**
     * Set company's banking information
     * @param bankInfo New banking information
     */
    public void setBankInfo(BankInformation bankInfo) {
        this.bankInfo = bankInfo;
    }

    /**
     * Get company's address
     * @return Company's address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Set company's address
     * @param address New address
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
