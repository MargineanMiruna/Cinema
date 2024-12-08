package Model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PremiumMembershipTest {

    @Test
    void getPrice() {
        PremiumMembership membership = new PremiumMembership(1, 101, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));
        assertEquals(100, membership.getPrice());
    }

    @Test
    void offerDiscount() {
        PremiumMembership membership = new PremiumMembership(1, 101, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));
        double originalPrice = 200;
        double expectedPrice = 200 - 200 * (20 / 100.0);
        assertEquals(expectedPrice, membership.offerDiscount(originalPrice));
    }

    @Test
    void toCSV() {
        PremiumMembership membership = new PremiumMembership(1, 101, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));
        String expectedCSV = "1,101,2024-01-01,2024-01-31";
        assertEquals(expectedCSV, membership.toCSV());
    }

    @Test
    void getHeader() {
        PremiumMembership membership = new PremiumMembership(1, 101, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));
        String[] expectedHeader = {"id", "customerId", "startDate", "endDate"};
        assertArrayEquals(expectedHeader, membership.getHeader());
    }

    @Test
    void fromCSV() {
        String csvLine = "1,101,2024-01-01,2024-01-31";
        PremiumMembership membership = PremiumMembership.fromCSV(csvLine);

        assertEquals(1, membership.getId());
        assertEquals(101, membership.getCustomerId());
        assertEquals(LocalDate.of(2024, 1, 1), membership.getStartDate());
        assertEquals(LocalDate.of(2024, 1, 31), membership.getEndDate());
    }
}
