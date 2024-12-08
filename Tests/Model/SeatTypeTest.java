package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeatTypeTest {

    @Test
    void getPrice() {
        assertEquals(30, SeatType.standard.getPrice());
        assertEquals(40, SeatType.vip.getPrice());
        assertEquals(50, SeatType.premium.getPrice());
    }

    @Test
    void values() {
        SeatType[] seatTypes = SeatType.values();
        assertEquals(3, seatTypes.length);
        assertEquals(SeatType.standard, seatTypes[0]);
        assertEquals(SeatType.vip, seatTypes[1]);
        assertEquals(SeatType.premium, seatTypes[2]);
    }

    @Test
    void valueOf() {
        assertEquals(SeatType.standard, SeatType.valueOf("standard"));
        assertEquals(SeatType.vip, SeatType.valueOf("vip"));
        assertEquals(SeatType.premium, SeatType.valueOf("premium"));
    }
}
