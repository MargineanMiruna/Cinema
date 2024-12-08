package Model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void getId() {
        Movie movie = new Movie(1, "The Dark Knight", true, "Action", LocalDate.of(2008, 7, 18));
        assertEquals(1, movie.getId());
    }

    @Test
    void getTitle() {
        Movie movie = new Movie(1, "The Dark Knight", true, "Action", LocalDate.of(2008, 7, 18));
        assertEquals("The Dark Knight", movie.getTitle());
    }

    @Test
    void getPg() {
        Movie movie = new Movie(1, "The Dark Knight", true, "Action", LocalDate.of(2008, 7, 18));
        assertTrue(movie.getPg());

        Movie movie2 = new Movie(2, "The Lion King", false, "Animation", LocalDate.of(1994, 6, 24));
        assertFalse(movie2.getPg());
    }

    @Test
    void getGenre() {
        Movie movie = new Movie(1, "The Dark Knight", true, "Action", LocalDate.of(2008, 7, 18));
        assertEquals("Action", movie.getGenre());
    }

    @Test
    void getReleaseDate() {
        Movie movie = new Movie(1, "The Dark Knight", true, "Action", LocalDate.of(2008, 7, 18));
        assertEquals(LocalDate.of(2008, 7, 18), movie.getReleaseDate());
    }

    @Test
    void toCSV() {
        Movie movie = new Movie(1, "The Dark Knight", true, "Action", LocalDate.of(2008, 7, 18));
        String expectedCSV = "1,The Dark Knight,true,Action,2008-07-18";
        assertEquals(expectedCSV, movie.toCSV());
    }

    @Test
    void getHeader() {
        Movie movie = new Movie(1, "The Dark Knight", true, "Action", LocalDate.of(2008, 7, 18));
        String[] expectedHeader = {"id", "title", "pg", "genre", "releaseDate"};
        assertArrayEquals(expectedHeader, movie.getHeader());
    }

    @Test
    void fromCSV() {
        String csvLine = "1,The Dark Knight,true,Action,2008-07-18";
        Movie movie = Movie.fromCSV(csvLine);

        assertEquals(1, movie.getId());
        assertEquals("The Dark Knight", movie.getTitle());
        assertTrue(movie.getPg());
        assertEquals("Action", movie.getGenre());
        assertEquals(LocalDate.of(2008, 7, 18), movie.getReleaseDate());
    }
}
