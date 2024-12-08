package Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void getUnderaged() {
        Customer customer = new Customer(1, "John", "Doe", "john.doe@example.com", true, 101);
        assertTrue(customer.getUnderaged());
        customer = new Customer(2, "Jane", "Doe", "jane.doe@example.com", false, 102);
        assertFalse(customer.getUnderaged());
    }

    @Test
    void getMembershipId() {
        Customer customer = new Customer(1, "John", "Doe", "john.doe@example.com", true, 101);
        assertEquals(101, customer.getMembershipId());
    }

    @Test
    void setMembershipId() {
        Customer customer = new Customer(1, "John", "Doe", "john.doe@example.com", true, -1);
        assertEquals(-1, customer.getMembershipId());
        customer.setMembershipId(123);
        assertEquals(123, customer.getMembershipId());
    }

    @Test
    void toCSV() {
        Customer customer = new Customer(1, "John", "Doe", "john.doe@example.com", true, 101);
        String expectedCSV = "1,John,Doe,john.doe@example.com,true,101";
        assertEquals(expectedCSV, customer.toCSV());
    }

    @Test
    void getHeader() {
        Customer customer = new Customer(1, "John", "Doe", "john.doe@example.com", true, 101);
        String expectedHeader = "id,firstName,lastName,email,underaged,membershipId";
        assertEquals(expectedHeader, String.join(",", customer.getHeader()));
    }

    @Test
    void fromCSV() {
        String csvLine = "1,Miruna,Marginean,miruna,false,-1";
        Customer customer = Customer.fromCSV(csvLine);

        assertEquals(1, customer.getId());
        assertEquals("Miruna", customer.getFirstName());
        assertEquals("Marginean", customer.getLastName());
        assertEquals("miruna", customer.getEmail());
        assertFalse(customer.getUnderaged());
        assertEquals(-1, customer.getMembershipId());
    }
}