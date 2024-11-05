package Domain;

public class Staff implements User {
    private String firstName;
    private String lastName;
    int id;

    void Staff(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public int getID() {
        return id;
    }


}
