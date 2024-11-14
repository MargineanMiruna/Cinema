package Model;

/**
 * The Customer class represents a customer user in the system.
 */
public class Customer extends User {
    private boolean underaged;
    private int membershipId;

    /**
     * Constructs a Customer with the specified first name, last name, email, and age status.
     *
     * @param id The unique identifier of the customer
     * @param firstName The first name of the customer
     * @param lastName The last name of the customer
     * @param email The email address of the customer
     * @param underaged true if the customer is underaged, false otherwise
     */
    public Customer(int id, String firstName, String lastName, String email, boolean underaged) {
        super(id, firstName, lastName, email);
        this.underaged = underaged;
        this.membershipId = -1;
    }

    /**
     * Checks if the customer is underaged.
     *
     * @return true if the customer is underaged, false otherwise
     */
    public boolean getUnderaged() {
        return underaged;
    }

    /**
     * Gets the membership ID associated with the customer.
     *
     * @return The membership ID, or -1 if no membership is assigned
     */
    public int getMembershipId() {
        return membershipId;
    }

    /**
     * Sets the membership ID for the customer.
     *
     * @param membershipId The membership ID to associate with the customer
     */
    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }
}
