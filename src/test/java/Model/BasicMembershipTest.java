package Model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BasicMembershipTest {

    @Test
    void getPrice() {
        BasicMembership membership = new BasicMembership(1, 101, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 31));
        assertEquals(50, membership.getPrice(), "The price should be 50.");
    }

    @Test
    void offerDiscount() {
        BasicMembership membership = new BasicMembership(1, 101, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 31));
        double originalPrice = 100;
        double expectedDiscountedPrice = 90; // 100 - 10% = 90
        assertEquals(expectedDiscountedPrice, membership.offerDiscount(originalPrice), "The discounted price should be 90.");
    }

    @Test
    void toCSV() {
        BasicMembership membership = new BasicMembership(1, 101, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 31));
        String expectedCSV = "1,101,2024-12-01,2024-12-31";
        assertEquals(expectedCSV, membership.toCSV(), "The CSV output should match the expected format.");
    }

    @Test
    void getHeader() {
        BasicMembership membership = new BasicMembership(1, 101, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 31));
        String[] expectedHeader = {"id", "customerId", "startDate", "endDate"};
        assertArrayEquals(expectedHeader, membership.getHeader(), "The header should match the expected values.");
    }

    @Test
    void fromCSV() {
        String csvLine = "1,101,2024-12-01,2024-12-31";
        BasicMembership membership = BasicMembership.fromCSV(csvLine);

        assertEquals(1, membership.getId(), "The ID should be 1.");
        assertEquals(101, membership.getCustomerId(), "The customer ID should be 101.");
        assertEquals(LocalDate.of(2024, 12, 1), membership.getStartDate(), "The start date should be 2024-12-01.");
        assertEquals(LocalDate.of(2024, 12, 31), membership.getEndDate(), "The end date should be 2024-12-31.");
    }
}
