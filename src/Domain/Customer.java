package Domain;

public class Customer implements User {
    private String firstName;
    private String lastName;
    private boolean underaged;

    public Customer(String firstName, String lastName, boolean underaged) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.underaged = underaged;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    boolean getUnderaged(){
        return underaged;
    }
}
