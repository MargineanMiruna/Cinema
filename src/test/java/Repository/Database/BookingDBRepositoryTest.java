package Repository.Database;

import Model.Booking;
import Model.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BookingDBRepositoryTest {
    Connection connection;
    final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/test/java/Files/cinemaDB.db";
    BookingDBRepository bookingRepo;
    TicketDBRepository ticketRepo;
    Booking booking;
    Ticket ticket1, ticket2;

    @BeforeEach
    void setUp() {
        try {
            connection = DriverManager.getConnection(DB_URL, "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        bookingRepo = new BookingDBRepository(connection);
        ticketRepo = new TicketDBRepository(connection);
        ticket1 = new Ticket(1, 1, 1, 1, 30);
        ticket2 = new Ticket(2, 1, 1, 2, 40);
        booking = new Booking(1, 1, 1, LocalDate.of(2024, 12, 6), 2, Arrays.asList(1,2));
    }

    @AfterEach
    void tearDown() throws SQLException {
        String createSQL = "DELETE FROM Booking;" +
                "DELETE FROM Ticket;";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        connection.close();
    }

    @Test
    void testGenerateNewId() {
        assertEquals(1, bookingRepo.generateNewId());
        bookingRepo.add(booking);
        assertEquals(2, bookingRepo.generateNewId());
    }

    @Test
    void testAdd() {
        bookingRepo.add(booking);
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(1, bookingRepo.getAll().size());
        assertEquals(booking.getId(), bookingRepo.read(1).getId());
        assertEquals(booking.getCustomerId(), bookingRepo.read(1).getCustomerId());
        assertEquals(booking.getShowtimeId(), bookingRepo.read(1).getShowtimeId());
        assertEquals(booking.getDate(), bookingRepo.read(1).getDate());
        assertEquals(booking.getNrOfCustomers(), bookingRepo.read(1).getNrOfCustomers());
        assertEquals(ticket1.getId(), bookingRepo.read(1).getTickets().get(0));
        assertEquals(ticket2.getId(), bookingRepo.read(1).getTickets().get(1));
    }

    @Test
    void testRead() {
        bookingRepo.add(booking);
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(booking.getId(), bookingRepo.read(1).getId());
        assertEquals(booking.getCustomerId(), bookingRepo.read(1).getCustomerId());
        assertEquals(booking.getShowtimeId(), bookingRepo.read(1).getShowtimeId());
        assertEquals(booking.getDate(), bookingRepo.read(1).getDate());
        assertEquals(booking.getNrOfCustomers(), bookingRepo.read(1).getNrOfCustomers());
        assertEquals(ticket1.getId(), bookingRepo.read(1).getTickets().get(0));
        assertEquals(ticket2.getId(), bookingRepo.read(1).getTickets().get(1));
    }

    @Test
    void testDelete() {
        bookingRepo.add(booking);
        bookingRepo.delete(1);
        assertEquals(0, bookingRepo.getAll().size());
    }

    @Test
    void testUpdate() {
        bookingRepo.add(booking);
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        Booking updatedBooking = new Booking(1, 2, 1, LocalDate.of(2024, 12, 6), 1, Arrays.asList(1));
        bookingRepo.update(updatedBooking);
        assertEquals(updatedBooking.getCustomerId(), bookingRepo.read(1).getCustomerId());
        assertEquals(updatedBooking.getShowtimeId(), bookingRepo.read(1).getShowtimeId());
        assertEquals(updatedBooking.getDate(), bookingRepo.read(1).getDate());
        assertEquals(updatedBooking.getNrOfCustomers(), bookingRepo.read(1).getNrOfCustomers());
        assertEquals(ticket1.getId(), bookingRepo.read(1).getTickets().get(0));
        assertEquals(1, bookingRepo.read(1).getTickets().size());
        assertNotSame(booking.getCustomerId(), bookingRepo.read(1).getCustomerId());
        assertEquals(booking.getShowtimeId(), bookingRepo.read(1).getShowtimeId());
        assertEquals(booking.getDate(), bookingRepo.read(1).getDate());
        assertNotSame(booking.getNrOfCustomers(), bookingRepo.read(1).getNrOfCustomers());
    }

    @Test
    void testGetAll() {
        bookingRepo.add(booking);
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(1, bookingRepo.getAll().size());
        assertEquals(booking.getId(), bookingRepo.getAll().get(1).getId());
        assertEquals(booking.getCustomerId(), bookingRepo.getAll().get(1).getCustomerId());
        assertEquals(booking.getShowtimeId(), bookingRepo.getAll().get(1).getShowtimeId());
        assertEquals(booking.getDate(), bookingRepo.getAll().get(1).getDate());
        assertEquals(booking.getNrOfCustomers(), bookingRepo.getAll().get(1).getNrOfCustomers());
        assertEquals(ticket1.getId(), bookingRepo.getAll().get(1).getTickets().get(0));
        assertEquals(ticket2.getId(), bookingRepo.getAll().get(1).getTickets().get(1));
    }
}