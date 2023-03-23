package basemodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {

    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private static int customerIdGenerator = 100;
    private static int customerId;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private int zip;

    private int cardOnFile;
    private String expDate;
    private int ccv;

    private String billingFirstName;
    private String billingLastName;
    private String billingAddress1;
    private String billingAddress2;
    private String billingCity;
    private String billingState;
    private int billingZip;

    public Customer(int customerId, String firstName, String lastName, String address1, String address2, String city,
                    String state, int zip, int cardOnFile, String expDate, int ccv, String billingFirstName,
                    String billingLastName, String billingAddress1, String billingAddress2, String billingCity,
                    String billingState, int billingZip) {
        this.customerId = customerIdGenerator++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.cardOnFile = cardOnFile;
        this.expDate = expDate;
        this.ccv = ccv;
        this.billingFirstName = billingFirstName;
        this.billingLastName = billingLastName;
        this.billingAddress1 = billingAddress1;
        this.billingAddress2 = billingAddress2;
        this.billingCity = billingCity;
        this.billingState = billingState;
        this.billingZip = billingZip;
    }

    public static int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getCardOnFile() {
        return cardOnFile;
    }

    public void setCardOnFile(int cardOnFile) {
        this.cardOnFile = cardOnFile;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public String getBillingFirstName() {
        return billingFirstName;
    }

    public void setBillingFirstName(String billingFirstName) {
        this.billingFirstName = billingFirstName;
    }

    public String getBillingLastName() {
        return billingLastName;
    }

    public void setBillingLastName(String billingLastName) {
        this.billingLastName = billingLastName;
    }

    public String getBillingAddress1() {
        return billingAddress1;
    }

    public void setBillingAddress1(String billingAddress1) {
        this.billingAddress1 = billingAddress1;
    }

    public String getBillingAddress2() {
        return billingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
        this.billingAddress2 = billingAddress2;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public int getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(int billingZip) {
        this.billingZip = billingZip;
    }

    public ObservableList<Customer> getCustomerList() {
        return customerList;
    }

    public void addCustomerToList(Customer newCustomer) {
        customerList.add(newCustomer);
    }
}
