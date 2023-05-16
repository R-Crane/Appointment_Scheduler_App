package c195Project.model;

public class Customer {

    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostal;
    private String customerPhone;
    private String divisionName;
    private int divisionID;
    private String countryName;

    /**
     *
     * @return
     */
    public int getCustomerID() { return customerID; }

    /**
     *
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     * @return
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     *
     * @param customerAddress
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     *
     * @return
     */
    public String getCustomerPostal() {
        return customerPostal;
    }

    /**
     *
     * @param customerPostal
     */
    public void setCustomerPostal(String customerPostal) {
        this.customerPostal = customerPostal;
    }

    /**
     *
     * @return
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     *
     * @param customerPhone
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     *
     * @return
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     *
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     *
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     *
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     *
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     *
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     *
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerPostal
     * @param customerPhone
     * @param divisionName
     * @param divisionID
     * @param countryName
     */
    public Customer(int customerID, String customerName, String customerAddress, String customerPostal, String customerPhone,
                    String divisionName, int divisionID, String countryName) {

        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostal = customerPostal;
        this.customerPhone = customerPhone;
        this.divisionName = divisionName;
        this.divisionID = divisionID;
        this.countryName = countryName;
    }

    /**
     * This returns the listing when called in a combo box to show the ID along with the name referenced
     * @return
     */
    @Override
    public String toString() {
        return (" Customer ID : " + Integer.toString(customerID) + " Name : " + customerName);
    }
}
