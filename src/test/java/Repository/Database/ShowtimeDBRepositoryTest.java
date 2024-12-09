package Repository.Database;

import Model.Screen;
import Model.Seat;
import Model.SeatType;
import Model.Showtime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ShowtimeDBRepositoryTest {
    Connection connection;
    final String DB_URL = "jdbc:sqlite:D:/Facultate/Semestrul3/MAP/Cinema-Management-Project/src/test/java/Files/cinemaDBTest.db";
    //final String DB_URL = "jdbc:sqlite:C:/Users/aleol/Facultate/Sem3/FP/Cinema-Management-Project/src/test/java/Files/cinemaDBTest.db";
    ShowtimeDBRepository showtimeRepo;
    SeatDBRepository seatRepo;
    Showtime showtime;
    Seat seat1, seat2, seat3;

    @BeforeEach
    void setUp() {
        try {
            connection = DriverManager.getConnection(DB_URL, "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        showtimeRepo = new ShowtimeDBRepository(connection);
        seatRepo = new SeatDBRepository(connection);
        seat1 = new Seat(1, 1, SeatType.standard);
        seat2 = new Seat(2, 2, SeatType.vip);
        seat3 = new Seat(3, 3, SeatType.premium);
        showtime = new Showtime(1, 1,1, LocalDate.of(2025, 1, 12), LocalTime.of(20,20),120, Arrays.asList(seat1, seat2, seat3));
    }

    @AfterEach
    void tearDown() throws SQLException {
        String createSQL = "DELETE FROM Showtime;" +
                "DELETE FROM AvailableSeats;" +
                "DELETE FROM Seat;";
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
        assertEquals(1, showtimeRepo.generateNewId());
        showtimeRepo.add(showtime);
        assertEquals(2, showtimeRepo.generateNewId());
    }

    @Test
    void testAdd() {
        showtimeRepo.add(showtime);
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.add(seat3);
        assertEquals(1, showtimeRepo.getAll().size());
        assertEquals(showtime.getId(), showtimeRepo.read(1).getId());
        assertEquals(showtime.getMovieId(), showtimeRepo.read(1).getMovieId());
        assertEquals(showtime.getScreenId(), showtimeRepo.read(1).getScreenId());
        assertEquals(showtime.getDate(), showtimeRepo.read(1).getDate());
        assertEquals(showtime.getStartTime(), showtimeRepo.read(1).getStartTime());
        assertEquals(showtime.getDuration(), showtimeRepo.read(1).getDuration());
        assertEquals(seat1.getId(), showtimeRepo.read(1).getSeats().get(0).getId());
        assertEquals(seat2.getId(), showtimeRepo.read(1).getSeats().get(1).getId());
        assertEquals(seat3.getId(), showtimeRepo.read(1).getSeats().get(2).getId());
    }

    @Test
    void testRead() {
        showtimeRepo.add(showtime);
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.add(seat3);
        assertEquals(showtime.getId(), showtimeRepo.read(1).getId());
        assertEquals(showtime.getMovieId(), showtimeRepo.read(1).getMovieId());
        assertEquals(showtime.getScreenId(), showtimeRepo.read(1).getScreenId());
        assertEquals(showtime.getDate(), showtimeRepo.read(1).getDate());
        assertEquals(showtime.getStartTime(), showtimeRepo.read(1).getStartTime());
        assertEquals(showtime.getDuration(), showtimeRepo.read(1).getDuration());
        assertEquals(seat1.getId(), showtimeRepo.read(1).getSeats().get(0).getId());
        assertEquals(seat2.getId(), showtimeRepo.read(1).getSeats().get(1).getId());
        assertEquals(seat3.getId(), showtimeRepo.read(1).getSeats().get(2).getId());
    }

    @Test
    void testDelete() {
        showtimeRepo.add(showtime);
        showtimeRepo.delete(1);
        assertEquals(0, showtimeRepo.getAll().size());
    }

    @Test
    void testUpdate() {
        showtimeRepo.add(showtime);
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.add(seat3);
        Showtime updatedShowtime = new Showtime(1, 2,2, LocalDate.of(2025, 2,1), LocalTime.of(21,45),150, Arrays.asList(seat1, seat3));
        showtimeRepo.update(updatedShowtime);
        assertEquals(updatedShowtime.getScreenId(), showtimeRepo.read(1).getScreenId());
        assertEquals(updatedShowtime.getMovieId(), showtimeRepo.read(1).getMovieId());
        assertEquals(updatedShowtime.getDate(), showtimeRepo.read(1).getDate());
        assertEquals(updatedShowtime.getStartTime(), showtimeRepo.read(1).getStartTime());
        assertEquals(updatedShowtime.getDuration(), showtimeRepo.read(1).getDuration());
        assertEquals(2, showtimeRepo.read(1).getSeats().size());
        assertEquals(seat1.getId(), showtimeRepo.read(1).getSeats().get(0).getId());
        assertNotSame(seat2.getId(), showtimeRepo.read(1).getSeats().get(1).getId());
        assertEquals(seat3.getId(), showtimeRepo.read(1).getSeats().get(1).getId());
        assertNotSame(showtime.getScreenId(), showtimeRepo.read(1).getScreenId());
        assertNotSame(showtime.getMovieId(), showtimeRepo.read(1).getMovieId());
        assertNotSame(showtime.getDate(), showtimeRepo.read(1).getDate());
        assertNotSame(showtime.getStartTime(), showtimeRepo.read(1).getStartTime());
        assertNotSame(showtime.getDuration(), showtimeRepo.read(1).getDuration());
    }

    @Test
    void testGetAll() {
        showtimeRepo.add(showtime);
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.add(seat3);
        assertEquals(1, showtimeRepo.getAll().size());
        assertEquals(showtime.getId(), showtimeRepo.getAll().get(1).getId());
        assertEquals(showtime.getScreenId(), showtimeRepo.getAll().get(1).getScreenId());
        assertEquals(showtime.getMovieId(), showtimeRepo.getAll().get(1).getMovieId());
        assertEquals(showtime.getDate(), showtimeRepo.getAll().get(1).getDate());
        assertEquals(showtime.getStartTime(), showtimeRepo.getAll().get(1).getStartTime());
        assertEquals(showtime.getDuration(), showtimeRepo.getAll().get(1).getDuration());
        assertEquals(seat1.getId(), showtimeRepo.getAll().get(1).getSeats().get(0).getId());
        assertEquals(seat2.getId(), showtimeRepo.getAll().get(1).getSeats().get(1).getId());
        assertEquals(seat3.getId(), showtimeRepo.getAll().get(1).getSeats().get(2).getId());
    }
}