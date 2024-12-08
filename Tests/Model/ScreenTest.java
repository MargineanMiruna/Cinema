package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class ScreenTest {

    @Test
    void getId() {
        Screen screen = new Screen(1, 10, 5, 5, Arrays.asList(new Seat(1, 1, SeatType.standard)));
        assertEquals(1, screen.getId());
    }

    @Test
    void getNrStandardSeats() {
        Screen screen = new Screen(1, 10, 5, 5, Arrays.asList(new Seat(1, 1, SeatType.standard)));
        assertEquals(10, screen.getNrStandardSeats());
    }

    @Test
    void getNrVipSeats() {
        Screen screen = new Screen(1, 10, 5, 5, Arrays.asList(new Seat(1, 1, SeatType.vip)));
        assertEquals(5, screen.getNrVipSeats());
    }

    @Test
    void getNrPremiumSeats() {
        Screen screen = new Screen(1, 10, 5, 5, Arrays.asList(new Seat(1, 1, SeatType.premium)));
        assertEquals(5, screen.getNrPremiumSeats());
    }

    @Test
    void getSeats() {
        Seat seat1 = new Seat(1, 1, SeatType.standard);
        Seat seat2 = new Seat(2, 2, SeatType.vip);
        List<Seat> seats = Arrays.asList(seat1, seat2);
        Screen screen = new Screen(1, 10, 5, 5, seats);

        assertEquals(seats, screen.getSeats());
    }

    @Test
    void setSeats() {
        Seat seat1 = new Seat(1, 1, SeatType.standard);
        Seat seat2 = new Seat(2, 2, SeatType.vip);
        List<Seat> newSeats = Arrays.asList(seat1, seat2);
        Screen screen = new Screen(1, 10, 5, 5, Arrays.asList(new Seat(3, 3, SeatType.premium)));

        screen.setSeats(newSeats);

        assertEquals(newSeats, screen.getSeats());
    }

    @Test
    void toCSV() {
        Seat seat1 = new Seat(1, 1, SeatType.standard);
        Seat seat2 = new Seat(2, 2, SeatType.vip);
        List<Seat> seats = Arrays.asList(seat1, seat2);
        Screen screen = new Screen(1, 10, 5, 5, seats);

        String expectedCSV = "1,10,5,5,1,1,standard;2,2,vip";
        assertEquals(expectedCSV, screen.toCSV());
    }

    @Test
    void getHeader() {
        Screen screen = new Screen(1, 10, 5, 5, Arrays.asList(new Seat(1, 1, SeatType.standard)));
        String[] expectedHeader = {"id", "nrStandardSeats", "nrVipSeats", "nrPremiumSeats", "seats"};
        assertArrayEquals(expectedHeader, screen.getHeader());
    }

    @Test
    void fromCSV() {
        String csvLine = "1,10,5,5,1,1,standard;2,2,vip";
        Screen screen = Screen.fromCSV(csvLine);

        assertEquals(1, screen.getId());
        assertEquals(10, screen.getNrStandardSeats());
        assertEquals(5, screen.getNrVipSeats());
        assertEquals(5, screen.getNrPremiumSeats());

        List<Seat> expectedSeats = Arrays.asList(
                new Seat(1, 1, SeatType.standard),
                new Seat(2, 2, SeatType.vip)
        );
        assertEquals(expectedSeats, screen.getSeats());
    }
}
