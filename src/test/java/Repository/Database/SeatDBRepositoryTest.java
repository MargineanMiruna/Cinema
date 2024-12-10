package Repository.Database;

import Model.Seat;
import Model.SeatType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class SeatDBRepositoryTest {
    Connection connection;
    final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/test/java/Files/cinemaDB.db";
    SeatDBRepository seatRepo;
    Seat seat1;
    Seat seat2;

    @BeforeEach
    void setUp() {
        try {
            connection = DriverManager.getConnection(DB_URL, "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        seatRepo = new SeatDBRepository(connection);
        seat1 = new Seat(1, 1, SeatType.standard);
        seat2 = new Seat(2, 2, SeatType.premium);
    }

    @AfterEach
    void tearDown() throws SQLException {
        String createSQL = "DELETE FROM Seat;";
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
        assertEquals(1, seatRepo.generateNewId());
        seatRepo.add(seat1);
        assertEquals(2, seatRepo.generateNewId());
    }

    @Test
    void testAdd() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        assertEquals(2, seatRepo.getAll().size());
        assertEquals(seat1.getId(), seatRepo.read(1).getId());
        assertEquals(seat1.getSeatNr(), seatRepo.read(1).getSeatNr());
        assertEquals(seat1.getType(), seatRepo.read(1).getType());
        assertEquals(seat2.getId(), seatRepo.read(2).getId());
        assertEquals(seat2.getSeatNr(), seatRepo.read(2).getSeatNr());
        assertEquals(seat2.getType(), seatRepo.read(2).getType());
    }

    @Test
    void testRead() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        assertEquals(seat1.getId(), seatRepo.read(1).getId());
        assertEquals(seat1.getSeatNr(), seatRepo.read(1).getSeatNr());
        assertEquals(seat1.getType(), seatRepo.read(1).getType());
        assertEquals(seat2.getId(), seatRepo.read(2).getId());
        assertEquals(seat2.getSeatNr(), seatRepo.read(2).getSeatNr());
        assertEquals(seat2.getType(), seatRepo.read(2).getType());
    }

    @Test
    void testDelete() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.delete(1);
        assertEquals(1, seatRepo.getAll().size());
        assertEquals(seat2.getId(), seatRepo.read(2).getId());
    }

    @Test
    void testUpdate() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        Seat seat3 = new Seat(2, 3, SeatType.vip);
        seatRepo.update(seat3);
        assertEquals(seat3.getSeatNr(), seatRepo.read(2).getSeatNr());
        assertEquals(seat3.getType(), seatRepo.read(2).getType());
        assertNotSame(seat2.getSeatNr(), seatRepo.read(1).getSeatNr());
        assertNotSame(seat2.getType(), seatRepo.read(1).getType());
    }

    @Test
    void testGetAll() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        assertEquals(2, seatRepo.getAll().size());
        assertEquals(seat1.getId(), seatRepo.getAll().get(1).getId());
        assertEquals(seat1.getSeatNr(), seatRepo.getAll().get(1).getSeatNr());
        assertEquals(seat1.getType(), seatRepo.getAll().get(1).getType());
        assertEquals(seat2.getId(), seatRepo.getAll().get(2).getId());
        assertEquals(seat2.getSeatNr(), seatRepo.getAll().get(2).getSeatNr());
        assertEquals(seat2.getType(), seatRepo.getAll().get(2).getType());
    }
}