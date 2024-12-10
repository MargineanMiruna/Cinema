package Repository.Database;

import Model.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieDBRepositoryTest {
    Connection connection;
    //final String DB_URL = "jdbc:sqlite:D:/Facultate/Semestrul3/MAP/Cinema-Management-Project/src/test/java/Files/cinemaDBTest.db";
    final String DB_URL = "jdbc:sqlite:C:/Users/aleol/Facultate/Sem3/FP/Cinema-Management-Project/src/test/java/Files/cinemaDBTest.db";
    MovieDBRepository movieRepo;
    Movie movie1;
    Movie movie2;

    @BeforeEach
    void setUp() {
        try {
            connection = DriverManager.getConnection(DB_URL, "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        movieRepo = new MovieDBRepository(connection);
        movie1 = new Movie(1, "Harry Potter", false, "adventure", LocalDate.of(2001,10,30));
        movie2 = new Movie(2, "Titanic", true, "drama", LocalDate.of(2004,4,21));
    }

    @AfterEach
    void tearDown() throws SQLException {
        String createSQL = "DELETE FROM Movie;";
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
        assertEquals(1, movieRepo.generateNewId());
        movieRepo.add(movie1);
        assertEquals(2, movieRepo.generateNewId());
    }

    @Test
    void testAdd() {
        movieRepo.add(movie1);
        movieRepo.add(movie2);
        assertEquals(2, movieRepo.getAll().size());
        assertEquals(movie1.getId(), movieRepo.read(1).getId());
        assertEquals(movie1.getTitle(), movieRepo.read(1).getTitle());
        assertEquals(movie1.getPg(), movieRepo.read(1).getPg());
        assertEquals(movie1.getGenre(), movieRepo.read(1).getGenre());
        assertEquals(movie1.getReleaseDate(), movieRepo.read(1).getReleaseDate());
        assertEquals(movie2.getId(), movieRepo.read(2).getId());
        assertEquals(movie2.getTitle(), movieRepo.read(2).getTitle());
        assertEquals(movie2.getPg(), movieRepo.read(2).getPg());
        assertEquals(movie2.getGenre(), movieRepo.read(2).getGenre());
        assertEquals(movie2.getReleaseDate(), movieRepo.read(2).getReleaseDate());
    }

    @Test
    void testRead() {
        movieRepo.add(movie1);
        movieRepo.add(movie2);
        assertEquals(movie1.getId(), movieRepo.read(1).getId());
        assertEquals(movie1.getTitle(), movieRepo.read(1).getTitle());
        assertEquals(movie1.getPg(), movieRepo.read(1).getPg());
        assertEquals(movie1.getGenre(), movieRepo.read(1).getGenre());
        assertEquals(movie1.getReleaseDate(), movieRepo.read(1).getReleaseDate());
        assertEquals(movie2.getId(), movieRepo.read(2).getId());
        assertEquals(movie2.getTitle(), movieRepo.read(2).getTitle());
        assertEquals(movie2.getPg(), movieRepo.read(2).getPg());
        assertEquals(movie2.getGenre(), movieRepo.read(2).getGenre());
        assertEquals(movie2.getReleaseDate(), movieRepo.read(2).getReleaseDate());
    }

    @Test
    void testDelete() {
        movieRepo.add(movie1);
        movieRepo.add(movie2);
        movieRepo.delete(1);
        assertEquals(1, movieRepo.getAll().size());
        assertEquals(movieRepo.read(2).getId(), movie2.getId());
    }

    @Test
    void testUpdate() {
        movieRepo.add(movie1);
        movieRepo.add(movie2);
        Movie movie3 = new Movie(2, "Frozen", false, "cartoon", LocalDate.of(2014, 12,28));
        movieRepo.update(movie3);
        assertEquals(movie3.getTitle(), movieRepo.read(2).getTitle());
        assertEquals(movie3.getPg(), movieRepo.read(2).getPg());
        assertEquals(movie3.getGenre(), movieRepo.read(2).getGenre());
        assertEquals(movie3.getReleaseDate(), movieRepo.read(2).getReleaseDate());
        assertNotSame(movie2.getTitle(), movieRepo.read(2).getTitle());
        assertNotSame(movie2.getPg(), movieRepo.read(2).getPg());
        assertNotSame(movie2.getGenre(), movieRepo.read(2).getGenre());
        assertNotSame(movie2.getReleaseDate(), movieRepo.read(2).getReleaseDate());
    }

    @Test
    void testGetAll() {
        movieRepo.add(movie1);
        movieRepo.add(movie2);
        assertEquals(2, movieRepo.getAll().size());
        assertEquals(movie1.getId(), movieRepo.getAll().get(1).getId());
        assertEquals(movie1.getTitle(), movieRepo.getAll().get(1).getTitle());
        assertEquals(movie1.getPg(), movieRepo.getAll().get(1).getPg());
        assertEquals(movie1.getGenre(), movieRepo.getAll().get(1).getGenre());
        assertEquals(movie1.getReleaseDate(), movieRepo.getAll().get(1).getReleaseDate());
        assertEquals(movie2.getId(), movieRepo.getAll().get(2).getId());
        assertEquals(movie2.getTitle(), movieRepo.getAll().get(2).getTitle());
        assertEquals(movie2.getPg(), movieRepo.getAll().get(2).getPg());
        assertEquals(movie2.getGenre(), movieRepo.getAll().get(2).getGenre());
        assertEquals(movie2.getReleaseDate(), movieRepo.getAll().get(2).getReleaseDate());
    }
}