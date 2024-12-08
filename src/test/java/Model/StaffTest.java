package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaffTest {

    @Test
    void toCSV() {
        Staff staff = new Staff(1, "Alexandra", "Olah", "alexandra");
        String expectedCSV = "1,Alexandra,Olah,alexandra";
        assertEquals(expectedCSV, staff.toCSV());
    }

    @Test
    void getHeader() {
        Staff staff = new Staff(1, "Alexandra", "Olah", "alexandra");
        String expectedHeader = "id,firstName,lastName,email";
        assertEquals(expectedHeader, String.join(",", staff.getHeader()));

    }

    @Test
    void fromCSV() {
        String csvLine = "1,Alexandra,Olah,alexandra";
        Staff staff = Staff.fromCSV(csvLine);

        assertEquals(1, staff.getId());
        assertEquals("Alexandra", staff.getFirstName());
        assertEquals("Olah", staff.getLastName());
        assertEquals("alexandra", staff.getEmail());
    }
}