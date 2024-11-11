package Domain;

public class Staff implements User {
    private String firstName;
    private String lastName;
    private String email;

    /**
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

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }
}
