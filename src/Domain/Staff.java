package Domain;

/**
 * Represents a staff member in the system, implementing the User interface.
 * A Staff instance contains the staff member's first name, last name, and email address.
 */
public class Staff implements User {
    private String firstName;
    private String lastName;
    private String email;

    /**
     *Constructs a Staff member with the specified first name, last name, and email.
     *
     * @param firstName first name
     * @param lastName last name
     * @param email email
     */

    public Staff(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     *Gets the email address of the staff member.
     * @return the email address as a string.
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Gets the first name of the staff member.
     * @return he first name as a String.
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     *Gets the last name of the staff member.
     * @return the last name as a String.
     */
    @Override
    public String getLastName() {
        return lastName;
    }
}
