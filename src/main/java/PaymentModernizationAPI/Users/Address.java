package PaymentModernizationAPI.Users;

/**
 * An address
 */
public class Address {

    // Address fields
    private String streetAddress;
    private String city;
    private String region;
    private String country;
    private String postalCode;

    /**
     * Constructor for Address
     * @param streetAddress Street address
     * @param city City
     * @param region Region
     * @param country Country
     * @param postalCode Postal code
     */
    public Address(String streetAddress, String city, String region, String country, String postalCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.region = region;
        this.country = country;
        this.postalCode = postalCode;
    }

    /**
     * Get street address
     * @return Street address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Set street address
     * @param streetAddress New street address
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Get city
     * @return City
     */
    public String getCity() {
        return city;
    }

    /**
     * Set city
     * @param city New city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get region
     * @return Region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Set region
     * @param region New region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Get country
     * @return Country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set country
     * @param country New country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get postal code
     * @return Postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Set postal code
     * @param postalCode New postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
