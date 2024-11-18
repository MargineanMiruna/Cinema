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

    /**
     * Converts the customer to a CSV format string.
     */
    @Override
    public String toCSV() {
        return String.join(",", String.valueOf(id), firstName, lastName, email, String.valueOf(underaged), String.valueOf(membershipId));
    }

    /**
     * Returns the header for a CSV file representing customers.
     */
    @Override
    public String[] getHeader() {
        return new String[]{"id", "firstName", "lastName", "email", "underaged", "membershipId"};
    }

    /**
     * Creates a Customer object from a CSV line.
     */
    public static Customer fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        Customer customer = new Customer(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4]));
        customer.setMembershipId(Integer.parseInt(parts[5]));
        return customer;
    }
}
