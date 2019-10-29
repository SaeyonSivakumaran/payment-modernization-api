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

}
