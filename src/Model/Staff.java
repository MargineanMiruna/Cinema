package Model;

/**
 * The staff class represents a staff member in the system.
 */
public class Staff extends User {
    /**
     *Constructs a Staff member with the specified first name, last name, and email.
     *
     * @param id The unique identifier of the staff member
     * @param firstName The first name of the staff member
     * @param lastName The last name of the staff member
     * @param email The email of the staff member
     */
    public Staff(int id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email);
    }
}
