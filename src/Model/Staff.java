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

    /**
     * Converts the Staff object to a CSV format string.
     */
    @Override
    public String toCSV() {
        return String.join(",", String.valueOf(getId()), getFirstName(), getLastName(), getEmail());
    }

    /**
     * Returns the header for the CSV file representing staff members.
     */
    @Override
    public String[] getHeader() {
        return new String[]{"id", "firstName", "lastName", "email"};
    }

    /**
     * Creates a Staff object from a CSV line.
     */
    @Override
    public Staff fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        return new Staff(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
    }
}
