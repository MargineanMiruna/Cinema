package Domain;

/**
 * The Customer class represents a customer user in the system.
 * It contains details such as the customer's name, age status, email, and membership information.
 */
public class Customer implements User {
    String firstName;
    String lastName;
    boolean underaged;
    String email;
    int membershipId = 0;

    /**
     * Constructs a Customer with the specified first name, last name, email, and age status.
     * @param firstName the first name of the customer
     * @param lastName the last name of the customer
     * @param email the email address of the customer
     * @param underaged true if the customer is underaged, false otherwise
     */
    public Customer(String firstName, String lastName, String email, boolean underaged) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.underaged = underaged;
    }

    /**
     * Gets the email address of the customer.
     * @return the email address of the customer
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Gets the first name of the customer.
     * @return the first name of the customer
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name of the customer.
     * @return the last name of the customer
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * Checks if the customer is underaged.
     * @return true if the customer is underaged, false otherwise
     */
    public boolean getUnderaged(){
        return underaged;
    }

    /**
     * Gets the membership ID associated with the customer.
     * @return the membership ID, or 0 if no membership is assigned
     */
    public int getMembershipId(){ return membershipId; }

    /**
     * Sets the membership ID for the customer.
     * @param membershipId the membership ID to associate with the customer
     */
    public void setMembershipId(int membershipId){ this.membershipId = membershipId; }
}
