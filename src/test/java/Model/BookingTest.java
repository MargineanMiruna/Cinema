package Model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void getId() {
        Booking booking = new Booking(1, 101, 202, LocalDate.of(2024, 12, 8), 3, Arrays.asList(1, 2, 3));
        assertEquals(1, booking.getId());
    }

    @Test
    void getCustomerId() {
        Booking booking = new Booking(1, 101, 202, LocalDate.of(2024, 12, 8), 3, Arrays.asList(1, 2, 3));
        assertEquals(101, booking.getCustomerId());
    }

    @Test
    void getShowtimeId() {
        Booking booking = new Booking(1, 101, 202, LocalDate.of(2024, 12, 8), 3, Arrays.asList(1, 2, 3));
        assertEquals(202, booking.getShowtimeId());
    }

    @Test
    void getDate() {
        Booking booking = new Booking(1, 101, 202, LocalDate.of(2024, 12, 8), 3, Arrays.asList(1, 2, 3));
        assertEquals(LocalDate.of(2024, 12, 8), booking.getDate());
    }

    @Test
    void getNrOfCustomers() {
        Booking booking = new Booking(1, 101, 202, LocalDate.of(2024, 12, 8), 3, Arrays.asList(1, 2, 3));
        assertEquals(3, booking.getNrOfCustomers());
    }

    @Test
    void getTickets() {
        List<Integer> expectedTickets = Arrays.asList(1, 2, 3);
        Booking booking = new Booking(1, 101, 202, LocalDate.of(2024, 12, 8), 3, expectedTickets);
        assertEquals(expectedTickets, booking.getTickets());
    }

    @Test
    void setTickets() {
        Booking booking = new Booking(1, 101, 202, LocalDate.of(2024, 12, 8), 3, Arrays.asList(1, 2, 3));
        List<Integer> newTickets = Arrays.asList(4, 5, 6);
        booking.setTickets(newTickets);
        assertEquals(newTickets, booking.getTickets());
    }

    @Test
    void toCSV() {
        Booking booking = new Booking(1, 101, 202, LocalDate.of(2024, 12, 8), 3, Arrays.asList(1, 2, 3));
        String expectedCSV = "1,101,202,2024-12-08,3,1;2;3";
        assertEquals(expectedCSV, booking.toCSV());
    }

    @Test
    void getHeader() {
        Booking booking = new Booking(1, 101, 202, LocalDate.of(2024, 12, 8), 3, Arrays.asList(1, 2, 3));
        String[] expectedHeader = {"id", "customerId", "showtimeId", "date", "nrOfCustomers", "tickets"};
        assertArrayEquals(expectedHeader, booking.getHeader());
    }

    @Test
    void fromCSV() {
        String csvLine = "1,101,202,2024-12-08,3,1;2;3";
        Booking booking = Booking.fromCSV(csvLine);

        assertEquals(1, booking.getId());
        assertEquals(101, booking.getCustomerId());
        assertEquals(202, booking.getShowtimeId());
        assertEquals(LocalDate.of(2024, 12, 8), booking.getDate());
        assertEquals(3, booking.getNrOfCustomers());
        assertEquals(Arrays.asList(1, 2, 3), booking.getTickets());
    }
}
