package Domain;

public class Customer implements User {
    private String firstName;
    private String lastName;
    private boolean underaged;
    private String email;

    public Customer(String firstName, String lastName, String email, boolean underaged) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.underaged = underaged;
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

    public boolean getUnderaged(){
        return underaged;
    }


}
