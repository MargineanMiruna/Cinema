package Repository.Database;

import Model.Screen;
import Model.Seat;
import Model.SeatType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ScreenDBRepositoryTest {
    Connection connection;
    final String DB_URL = "jdbc:sqlite:D:/Facultate/Semestrul3/MAP/Cinema-Management-Project/src/test/java/Files/cinemaDBTest.db";
    //final String DB_URL = "jdbc:sqlite:C:/Users/aleol/Facultate/Sem3/FP/Cinema-Management-Project/src/test/java/Files/cinemaDBTest.db";
    ScreenDBRepository screenRepo;
    Screen screen;
    Seat seat1, seat2, seat3;

    @BeforeEach
    void setUp() throws SQLException {
        try {
            connection = DriverManager.getConnection(DB_URL, "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        screenRepo = new ScreenDBRepository(connection);
        seat1 = new Seat(1, 1, SeatType.standard);
        seat2 = new Seat(2, 2, SeatType.vip);
        seat3 = new Seat(3, 3, SeatType.premium);
        screen = new Screen(1, 1,1,1, Arrays.asList(seat1, seat2, seat3));

        String createSQL = "CREATE TABLE IF NOT EXISTS Screen (id INT, nrStandardSeats INT, nrVipSeats INT, nrPremiumSeats INT, PRIMARY KEY(id));";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        String createSQL = "DELETE FROM Screen";
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
        assertEquals(1, screenRepo.generateNewId());
        screenRepo.add(screen);
        assertEquals(2, screenRepo.generateNewId());
    }

    @Test
    void testAdd() {
        screenRepo.add(screen);
        assertEquals(1, screenRepo.getAll().size());
        assertEquals(screen.getId(), screenRepo.read(1).getId());
        assertEquals(screen.getNrStandardSeats(), screenRepo.read(1).getNrStandardSeats());
        assertEquals(screen.getNrVipSeats(), screenRepo.read(1).getNrVipSeats());
        assertEquals(screen.getNrPremiumSeats(), screenRepo.read(1).getNrPremiumSeats());
        assertEquals(screen.getSeats(), screenRepo.read(1).getSeats());
    }

    @Test
    void read() {
        screenRepo.add(screen);
        assertEquals(screen.getId(), screenRepo.read(1).getId());
        assertEquals(screen.getNrStandardSeats(), screenRepo.read(1).getNrStandardSeats());
        assertEquals(screen.getNrVipSeats(), screenRepo.read(1).getNrVipSeats());
        assertEquals(screen.getNrPremiumSeats(), screenRepo.read(1).getNrPremiumSeats());
        assertEquals(screen.getSeats(), screenRepo.read(1).getSeats());
    }

    @Test
    void delete() {
        screenRepo.add(screen);
        screenRepo.delete(1);
        assertEquals(0, screenRepo.getAll().size());
        assertNotSame(screen.getId(), screenRepo.read(1).getId());
    }

    @Test
    void update() {
        screenRepo.add(screen);
        Screen updatedScreen = new Screen(1, 1, 0, 1, Arrays.asList(seat1, seat3));
        screenRepo.update(updatedScreen);
        assertEquals(updatedScreen.getNrStandardSeats(), screenRepo.read(1).getNrStandardSeats());
        assertEquals(updatedScreen.getNrVipSeats(), screenRepo.read(1).getNrVipSeats());
        assertEquals(updatedScreen.getNrPremiumSeats(), screenRepo.read(1).getNrPremiumSeats());
        assertEquals(updatedScreen.getSeats(), screenRepo.read(1).getSeats());
        assertEquals(screen.getNrStandardSeats(), screenRepo.read(1).getNrStandardSeats());
        assertNotSame(screen.getNrVipSeats(), screenRepo.read(1).getNrVipSeats());
        assertEquals(screen.getNrPremiumSeats(), screenRepo.read(1).getNrPremiumSeats());
        assertNotSame(screen.getSeats(), screenRepo.read(1).getSeats());
    }

    @Test
    void getAll() {
        screenRepo.add(screen);
        assertEquals(1, screenRepo.getAll().size());
        assertEquals(screen.getId(), screenRepo.getAll().get(1).getId());
        assertEquals(screen.getNrStandardSeats(), screenRepo.getAll().get(1).getNrStandardSeats());
        assertEquals(screen.getNrVipSeats(), screenRepo.getAll().get(1).getNrVipSeats());
        assertEquals(screen.getNrPremiumSeats(), screenRepo.getAll().get(1).getNrPremiumSeats());
        assertEquals(screen.getSeats(), screenRepo.getAll().get(1).getSeats());
    }
}