package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void getId() {
        Ticket ticket = new Ticket(1, 1001, 10, 5, 12.50);
        assertEquals(1, ticket.getId());
    }

    @Test
    void getBookingId() {
        Ticket ticket = new Ticket(1, 1001, 10, 5, 12.50);
        assertEquals(1001, ticket.getBookingId());
    }

    @Test
    void getScreenId() {
        Ticket ticket = new Ticket(1, 1001, 10, 5, 12.50);
        assertEquals(10, ticket.getScreenId());
    }

    @Test
    void getSeatNr() {
        Ticket ticket = new Ticket(1, 1001, 10, 5, 12.50);
        assertEquals(5, ticket.getSeatNr());
    }

    @Test
    void getPrice() {
        Ticket ticket = new Ticket(1, 1001, 10, 5, 12.50);
        assertEquals(12.50, ticket.getPrice(), 0.01);
    }

    @Test
    void toCSV() {
        Ticket ticket = new Ticket(1, 1001, 10, 5, 12.50);
        String expectedCSV = "1,1001,10,5,12.5";
        assertEquals(expectedCSV, ticket.toCSV());
    }

    @Test
    void getHeader() {
        Ticket ticket = new Ticket(1, 1001, 10, 5, 12.50);
        String expectedHeader = "id,bookingId,screenId,seatNr,price";
        assertEquals(expectedHeader, String.join(",",ticket.getHeader()));

    }

    @Test
    void fromCSV() {
        String csvLine = "1,1001,10,5,12.5";
        Ticket ticket = Ticket.fromCSV(csvLine);

        assertEquals(1, ticket.getId());
        assertEquals(1001, ticket.getBookingId());
        assertEquals(10, ticket.getScreenId());
        assertEquals(5, ticket.getSeatNr());
        assertEquals(12.50, ticket.getPrice(), 0.01);
    }
}