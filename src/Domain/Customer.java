package Domain;

public class Customer implements User {
    private int id;
    private String firstName;
    private String lastName;
    private boolean underaged;

    public Customer(int id, String firstName, String lastName, boolean underaged) {
        this.id = id;
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

    @Override
    public int getID() {
        return id;
    }
}
