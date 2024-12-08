package Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShowtimeTest {

    @Test
    void getId() {
        Showtime showtime = new Showtime(1, 101, 202, LocalDate.of(2024, 12, 15), LocalTime.of(14, 30), 120, new ArrayList<>());
        assertEquals(1, showtime.getId());
    }

    @Test
    void getScreenId() {
        Showtime showtime = new Showtime(1, 101, 202, LocalDate.of(2024, 12, 15), LocalTime.of(14, 30), 120, new ArrayList<>());
        assertEquals(101, showtime.getScreenId());
    }

    @Test
    void getMovieId() {
        Showtime showtime = new Showtime(1, 101, 202, LocalDate.of(2024, 12, 15), LocalTime.of(14, 30), 120, new ArrayList<>());
        assertEquals(202, showtime.getMovieId());
    }

    @Test
    void getDate() {
        Showtime showtime = new Showtime(1, 101, 202, LocalDate.of(2024, 12, 15), LocalTime.of(14, 30), 120, new ArrayList<>());
        assertEquals(LocalDate.of(2024, 12, 15), showtime.getDate());
    }

    @Test
    void getStartTime() {
        Showtime showtime = new Showtime(1, 101, 202, LocalDate.of(2024, 12, 15), LocalTime.of(14, 30), 120, new ArrayList<>());
        assertEquals(LocalTime.of(14, 30), showtime.getStartTime());
    }

    @Test
    void getDuration() {
        Showtime showtime = new Showtime(1, 101, 202, LocalDate.of(2024, 12, 15), LocalTime.of(14, 30), 120, new ArrayList<>());
        assertEquals(120, showtime.getDuration());
    }

    @Test
    void getSeats() {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1, 1, SeatType.standard));
        Showtime showtime = new Showtime(1, 101, 202, LocalDate.of(2024, 12, 15), LocalTime.of(14, 30), 120, seats);

        assertEquals(1, showtime.getSeats().size());
        assertEquals(1, showtime.getSeats().get(0).getSeatNr());
    }

    @Test
    void setSeats() {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1, 1, SeatType.standard));
        Showtime showtime = new Showtime(1, 101, 202, LocalDate.of(2024, 12, 15), LocalTime.of(14, 30), 120, seats);

        List<Seat> newSeats = new ArrayList<>();
        newSeats.add(new Seat(2, 2, SeatType.premium));
        showtime.setSeats(newSeats);

        assertEquals(1, showtime.getSeats().size());
        assertEquals(2, showtime.getSeats().get(0).getSeatNr());
        assertEquals(SeatType.premium, showtime.getSeats().get(0).getType());
    }

    @Test
    void toCSV() {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1, 1, SeatType.standard));
        Showtime showtime = new Showtime(1, 10, 100, LocalDate.of(2024, 12, 10), LocalTime.of(14, 30), 120, seats);

        String expectedCSV = "1,10,100,2024-12-10,14:30,120,1,1,standard";
        assertEquals(expectedCSV, showtime.toCSV());
    }

    @Test
    void getHeader() {
        Showtime showtime = new Showtime(1, 10, 100, LocalDate.of(2024, 12, 10), LocalTime.of(14, 30), 120, new ArrayList<>());
        String[] expectedHeader = {"id", "screenId", "movieId", "date", "startTime", "duration", "seats"};
        assertArrayEquals(expectedHeader, showtime.getHeader());
    }

    @Test
    void fromCSV() {
        String csvLine = "1,10,100,2024-12-10,14:30,120,1,1,standard";
        Showtime showtime = Showtime.fromCSV(csvLine);

        assertEquals(1, showtime.getId());
        assertEquals(10, showtime.getScreenId());
        assertEquals(100, showtime.getMovieId());
        assertEquals(LocalDate.of(2024, 12, 10), showtime.getDate());
        assertEquals(LocalTime.of(14, 30), showtime.getStartTime());
        assertEquals(120, showtime.getDuration());
        assertEquals(1, showtime.getSeats().size());
        assertEquals(1, showtime.getSeats().get(0).getSeatNr());
        assertEquals(SeatType.standard, showtime.getSeats().get(0).getType());
    }
}