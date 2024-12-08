package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeatTest {

    @Test
    void getId() {
        Seat seat = new Seat(1, 5, SeatType.standard);
        assertEquals(1, seat.getId());
    }

    @Test
    void getSeatNr() {
        Seat seat = new Seat(1, 5, SeatType.standard);
        assertEquals(5, seat.getSeatNr());
    }

    @Test
    void getPrice() {
        Seat standardSeat = new Seat(1, 5, SeatType.standard);
        Seat vipSeat = new Seat(2, 10, SeatType.vip);
        Seat premiumSeat = new Seat(3, 15, SeatType.premium);

        assertEquals(30, standardSeat.getPrice());
        assertEquals(40, vipSeat.getPrice());
        assertEquals(50, premiumSeat.getPrice());
    }

    @Test
    void getType() {
        Seat seat = new Seat(1, 5, SeatType.standard);
        assertEquals(SeatType.standard, seat.getType());
    }

    @Test
    void toCSV() {
        Seat seat = new Seat(1, 5, SeatType.standard);
        String expectedCSV = "1,5,standard";
        assertEquals(expectedCSV, seat.toCSV());
    }

    @Test
    void getHeader() {
        Seat seat = new Seat(1, 5, SeatType.standard);
        String[] expectedHeader = {"id", "seatNr", "type"};
        assertArrayEquals(expectedHeader, seat.getHeader());
    }

    @Test
    void fromCSV() {
        String csvLine = "1,5,standard";
        Seat seat = Seat.fromCSV(csvLine);

        assertEquals(1, seat.getId());
        assertEquals(5, seat.getSeatNr());
        assertEquals(SeatType.standard, seat.getType());
    }
}
