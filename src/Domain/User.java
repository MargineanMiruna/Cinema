package Domain;

/**
 * represents a user with basic details such as first name, last name and email.
 */
public interface User {
    /**
     *
     * @return a string representing the user's first name.
     */
    public String getFirstName();

    /**
     *
     * @return a string representing the user's last name.
     */
    public String getLastName();

    /**
     *
     * @return a string representing the user's email.
     */
    public String getEmail();
}
