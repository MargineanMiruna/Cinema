package Repository.Database;

import Model.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class TicketDBRepositoryTest {
    Connection connection;
    //final String DB_URL = "jdbc:sqlite:D:/Facultate/Semestrul3/MAP/Cinema-Management-Project/src/test/java/Files/cinemaDBTest.db";
    final String DB_URL = "jdbc:sqlite:C:/Users/aleol/Facultate/Sem3/FP/Cinema-Management-Project/src/test/java/Files/cinemaDBTest.db";
    TicketDBRepository ticketRepo;
    Ticket ticket1;
    Ticket ticket2;

    @BeforeEach
    void setUp() {
        try {
            connection = DriverManager.getConnection(DB_URL, "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ticketRepo = new TicketDBRepository(connection);
        ticket1 = new Ticket(1, 1, 1, 1, 30);
        ticket2 = new Ticket(2, 1, 1, 2, 40);
    }

    @AfterEach
    void tearDown() throws SQLException {
        String createSQL = "DELETE FROM Ticket;";
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
        assertEquals(1, ticketRepo.generateNewId());
        ticketRepo.add(ticket1);
        assertEquals(2, ticketRepo.generateNewId());
    }

    @Test
    void testAdd() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(2, ticketRepo.getAll().size());
        assertEquals(ticket1.getId(), ticketRepo.read(1).getId());
        assertEquals(ticket1.getBookingId(), ticketRepo.read(1).getBookingId());
        assertEquals(ticket1.getScreenId(), ticketRepo.read(1).getScreenId());
        assertEquals(ticket1.getSeatNr(), ticketRepo.read(1).getSeatNr());
        assertEquals(ticket1.getPrice(), ticketRepo.read(1).getPrice());
        assertEquals(ticket2.getId(), ticketRepo.getAll().get(2).getId());
        assertEquals(ticket2.getBookingId(), ticketRepo.getAll().get(2).getBookingId());
        assertEquals(ticket2.getScreenId(), ticketRepo.getAll().get(2).getScreenId());
        assertEquals(ticket2.getSeatNr(), ticketRepo.getAll().get(2).getSeatNr());
        assertEquals(ticket2.getPrice(), ticketRepo.getAll().get(2).getPrice());
    }

    @Test
    void getRead() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(ticket1.getId(), ticketRepo.read(1).getId());
        assertEquals(ticket1.getBookingId(), ticketRepo.read(1).getBookingId());
        assertEquals(ticket1.getScreenId(), ticketRepo.read(1).getScreenId());
        assertEquals(ticket1.getSeatNr(), ticketRepo.read(1).getSeatNr());
        assertEquals(ticket1.getPrice(), ticketRepo.read(1).getPrice());
        assertEquals(ticket2.getId(), ticketRepo.getAll().get(2).getId());
        assertEquals(ticket2.getBookingId(), ticketRepo.getAll().get(2).getBookingId());
        assertEquals(ticket2.getScreenId(), ticketRepo.getAll().get(2).getScreenId());
        assertEquals(ticket2.getSeatNr(), ticketRepo.getAll().get(2).getSeatNr());
        assertEquals(ticket2.getPrice(), ticketRepo.getAll().get(2).getPrice());
    }

    @Test
    void testDelete() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        ticketRepo.delete(1);
        assertEquals(1, ticketRepo.getAll().size());
        assertNotSame(ticketRepo.read(1).getId(), ticket1.getId());
        assertEquals(ticket2.getId(), ticketRepo.read(2).getId());
    }

    @Test
    void testUpdate() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        Ticket ticket3 = new Ticket(2, 1, 1, 3, 50);
        ticketRepo.update(ticket3);
        assertEquals(ticket3.getBookingId(), ticketRepo.read(2).getBookingId());
        assertEquals(ticket3.getScreenId(), ticketRepo.read(2).getScreenId());
        assertEquals(ticket3.getSeatNr(), ticketRepo.read(2).getSeatNr());
        assertEquals(ticket3.getPrice(), ticketRepo.read(2).getPrice());
        assertEquals(ticket2.getBookingId(), ticketRepo.read(2).getBookingId());
        assertEquals(ticket2.getScreenId(), ticketRepo.read(2).getScreenId());
        assertNotSame(ticket2.getSeatNr(), ticketRepo.read(2).getSeatNr());
        assertNotSame(ticket2.getPrice(), ticketRepo.read(2).getPrice());
    }

    @Test
    void testGetAll() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(2, ticketRepo.getAll().size());
        assertEquals(ticket1.getId(), ticketRepo.getAll().get(1).getId());
        assertEquals(ticket1.getBookingId(), ticketRepo.getAll().get(1).getBookingId());
        assertEquals(ticket1.getScreenId(), ticketRepo.getAll().get(1).getScreenId());
        assertEquals(ticket1.getSeatNr(), ticketRepo.getAll().get(1).getSeatNr());
        assertEquals(ticket1.getPrice(), ticketRepo.getAll().get(1).getPrice());
        assertEquals(ticket2.getId(), ticketRepo.getAll().get(2).getId());
        assertEquals(ticket2.getBookingId(), ticketRepo.getAll().get(2).getBookingId());
        assertEquals(ticket2.getScreenId(), ticketRepo.getAll().get(2).getScreenId());
        assertEquals(ticket2.getSeatNr(), ticketRepo.getAll().get(2).getSeatNr());
        assertEquals(ticket2.getPrice(), ticketRepo.getAll().get(2).getPrice());
    }
}