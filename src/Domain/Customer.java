package Domain;

public class Customer implements User {
    String firstName;
    String lastName;
    boolean underaged;
    String email;
    int membershipId = 0;

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

    public int getMembershipId(){ return membershipId; }

    public void setMembershipId(int membershipId){ this.membershipId = membershipId; }
}
