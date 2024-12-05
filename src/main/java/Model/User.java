package Model;

/**
 * An abstract class that represents an user of the app.
 */
public abstract class User implements HasId {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;

    /**
     * Constructs a new User with the specified ID, first name, last name and email.
     *
     * @param id The unique identifier for the user
     * @param firstName The first name of the user
     * @param lastName The last name of the usewr
     * @param email The unique email of the user
     */
    protected User(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The unique identifier of the user.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param id The unique identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }
}
