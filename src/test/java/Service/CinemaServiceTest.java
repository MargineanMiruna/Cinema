package Service;

import Model.*;
import Repository.IRepository;
import Repository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class CinemaServiceTest {

    private CinemaService cinemaService;
    private IRepository<Customer> customerRepo;
    private  IRepository<Staff> staffRepo;
    private  IRepository<Movie> movieRepo;
    private IRepository<Showtime> showtimeRepo;
    private  IRepository<Screen> screenRepo;
    private  IRepository<Seat> seatRepo;
    private  IRepository<Booking> bookingRepo;
    private  IRepository<Ticket> ticketRepo;
    private  IRepository<BasicMembership> basicMembershipRepo;
    private  IRepository<PremiumMembership> premiumMembershipRepo;

    @BeforeEach
    void setUp() {
        customerRepo = new InMemoryRepository<>();
        staffRepo = new InMemoryRepository<>();
        movieRepo = new InMemoryRepository<>();
        screenRepo = new InMemoryRepository<>();
        seatRepo = new InMemoryRepository<>();
        bookingRepo = new InMemoryRepository<>();
        ticketRepo = new InMemoryRepository<>();
        basicMembershipRepo = new InMemoryRepository<>();
        premiumMembershipRepo = new InMemoryRepository<>();
        cinemaService = new CinemaService( customerRepo,  staffRepo,  movieRepo,
                 showtimeRepo,  screenRepo,  seatRepo,
                 bookingRepo, ticketRepo,  basicMembershipRepo,
                 premiumMembershipRepo);


    }


    @Test
    void testAddCustomer() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        LocalDate birthday = LocalDate.of(2000, 1, 1);

        cinemaService.addCustomer(firstName, lastName, email, birthday);

        Customer customer = customerRepo.read(1);
        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals(1, customer.getId());
        assertFalse(customer.getUnderaged());

    }

    @Test
    public void testGetCustomer() {
        String firstName = "Alice";
        String lastName = "Smith";
        String email = "alice.smith@example.com";
        LocalDate birthday = LocalDate.of(2010, 5, 5);
        cinemaService.addCustomer(firstName, lastName, email, birthday);
        Customer customer = cinemaService.getCustomer(1);
        assertNotNull(customer);
        assertEquals("Alice", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("alice.smith@example.com", customer.getEmail());
        assertEquals(1, customer.getId());
        assertTrue(customer.getUnderaged());
    }

    @Test
    public void testUpdateCustomer() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        LocalDate birthday = LocalDate.of(2000, 1, 1);
        cinemaService.addCustomer(firstName, lastName, email, birthday);
        String newFirstName = "Johnathan";
        String newLastName = "Does";
        String newEmail = "johnathan.does@example.com";
        boolean underaged = false;
        cinemaService.updateCustomer(1, newFirstName, newLastName, newEmail, underaged, -1);
        Customer updatedCustomer = customerRepo.read(1);
        assertNotNull(updatedCustomer);
        assertEquals("Johnathan", updatedCustomer.getFirstName());
        assertEquals("Does", updatedCustomer.getLastName());
        assertEquals("johnathan.does@example.com", updatedCustomer.getEmail());
        assertFalse(updatedCustomer.getUnderaged());
    }

    @Test
    public void testAddStaff() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        cinemaService.addStaff(firstName, lastName, email);
        Staff staff = staffRepo.read(1);
        assertNotNull(staff);
        assertEquals("John", staff.getFirstName());
        assertEquals("Doe", staff.getLastName());
        assertEquals("john.doe@example.com", staff.getEmail());
    }

    @Test
    public void testUpdateStaff() {
        String firstName = "Alice";
        String lastName = "Smith";
        String email = "alice.smith@example.com";
        cinemaService.addStaff(firstName, lastName, email);
        String newFirstName = "Alicia";
        String newLastName = "Johnson";
        String newEmail = "alicia.johnson@example.com";
        cinemaService.updateStaff(1, newFirstName, newLastName, newEmail);
        Staff updatedStaff = staffRepo.read(1);
        assertNotNull(updatedStaff);
        assertEquals("Alicia", updatedStaff.getFirstName());
        assertEquals("Johnson", updatedStaff.getLastName());
        assertEquals("alicia.johnson@example.com", updatedStaff.getEmail());
    }

    @Test
    public void testAddMovie() {
        String title = "Inception";
        boolean pg = true;
        String genre = "Sci-Fi";
        LocalDate releaseDate = LocalDate.of(2010, 7, 16);
        cinemaService.addMovie(title, pg, genre, releaseDate);
        Movie movie = movieRepo.read(1);
        assertNotNull(movie);
        assertEquals("Inception", movie.getTitle());
        assertTrue(movie.getPg());
        assertEquals("Sci-Fi", movie.getGenre());
        assertEquals(releaseDate, movie.getReleaseDate());
    }

    @Test
    public void testUpdateMovie() {
        String title = "Interstellar";
        boolean pg = false;
        String genre = "Sci-Fi";
        LocalDate releaseDate = LocalDate.of(2014, 11, 7);
        cinemaService.addMovie(title, pg, genre, releaseDate);
        String newTitle = "Interstellar: Director's Cut";
        boolean newPg = true;
        String newGenre = "Sci-Fi, Drama";
        LocalDate newReleaseDate = LocalDate.of(2015, 11, 7);
        cinemaService.updateMovie(1, newTitle, newPg, newGenre, newReleaseDate);
        Movie updatedMovie = movieRepo.read(1);
        assertNotNull(updatedMovie);
        assertEquals("Interstellar: Director's Cut", updatedMovie.getTitle());
        assertTrue(updatedMovie.getPg());
        assertEquals("Sci-Fi, Drama", updatedMovie.getGenre());
        assertEquals(newReleaseDate, updatedMovie.getReleaseDate());
    }

    @Test
    public void testDeleteMovie() {
        String title = "Titanic";
        boolean pg = true;
        String genre = "Romance";
        LocalDate releaseDate = LocalDate.of(1997, 12, 19);
        cinemaService.addMovie(title, pg, genre, releaseDate);
        movieRepo.delete(1);
        Movie deletedMovie = movieRepo.read(1);
        assertNull(deletedMovie);
    }

    @Test
    void findMovieIdByTitle() {
    }

    @Test
    public void testAddShowtime() {
        int screenId = 1;
        int movieId = 1;
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime startTime = LocalTime.of(18, 30);
        int duration = 120;
        cinemaService.addShowtime(screenId, movieId, date, startTime, duration);
        Showtime showtime = showtimeRepo.read(1);
        assertNotNull(showtime);
        assertEquals(1, showtime.getScreenId());
        assertEquals(1, showtime.getMovieId());
        assertEquals(date, showtime.getDate());
        assertEquals(startTime, showtime.getStartTime());
        assertEquals(duration, showtime.getDuration());
    }

    @Test
    void getShowtime() {
    }

    @Test
    public void testUpdateShowtime() {
        int screenId = 1;
        int movieId = 1;
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime startTime = LocalTime.of(18, 30);
        int duration = 120;
        cinemaService.addShowtime(screenId, movieId, date, startTime, duration);
        LocalTime newStartTime = LocalTime.of(20, 0);
        int newDuration = 150;
        cinemaService.updateShowtime(1, screenId, movieId, date, newStartTime, newDuration, List.of());
        Showtime updatedShowtime = showtimeRepo.read(1);
        assertNotNull(updatedShowtime);
        assertEquals(newStartTime, updatedShowtime.getStartTime());
        assertEquals(newDuration, updatedShowtime.getDuration());
    }

    @Test
    public void testDeleteShowtime() {
        int screenId = 1;
        int movieId = 1;
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime startTime = LocalTime.of(18, 30);
        int duration = 120;
        cinemaService.addShowtime(screenId, movieId, date, startTime, duration);
        showtimeRepo.delete(1);
        Showtime deletedShowtime = showtimeRepo.read(1);
        assertNull(deletedShowtime);
    }

    @Test
    void addScreen() {
    }

    @Test
    void getScreen() {
    }

    @Test
    void updateScreen() {
    }

    @Test
    void deleteScreen() {
    }

    @Test
    void getSeat() {
    }

    @Test
    void updateSeat() {
    }

    @Test
    void findSeatBySeatNr() {
    }

    @Test
    void addBooking() {
    }

    @Test
    void getBooking() {
    }

    @Test
    void updateBooking() {
    }

    @Test
    void addTicket() {
    }

    @Test
    void getTicket() {
    }

    @Test
    void updateTicket() {
    }

    @Test
    void addBasicMembership() {
    }

    @Test
    void getBasicMembership() {
    }

    @Test
    void updateBasicMembership() {
    }

    @Test
    void deleteBasicMembership() {
    }

    @Test
    void addPremiumMembership() {
    }

    @Test
    void getPremiumMembership() {
    }

    @Test
    void updatePremiumMembership() {
    }

    @Test
    void deletePremiumMembership() {
    }

    @Test
    void getMembershipType() {
    }

    @Test
    void filterShowtimesByPg() {
    }

    @Test
    void displayMoviesStaff() {
    }

    @Test
    void displayShowtimesStaff() {
    }

    @Test
    void displayScreensStaff() {
    }

    @Test
    void findCustomerByEmail() {
    }

    @Test
    void removeSeatsFromAvailable() {
    }

    @Test
    void findStaffByEmail() {
    }

    @Test
    void deleteShowtimesByMovieId() {
    }

    @Test
    void deleteShowtimesByScreenId() {
    }

    @Test
    void calculateTotalPrice() {
    }

    @Test
    void calculateDiscountedPrice() {
    }

    @Test
    void terminateMemberships() {
    }

    @Test
    void filterSeatsByType() {
    }

    @Test
    void filerShowtimesByDate() {
    }

    @Test
    void sortShowtimesByDateAsc() {
    }

    @Test
    void filterShowtimesByMovie() {
    }

    @Test
    void sortShowtimesByDuration() {
    }

    @Test
    void getBookingsByCustomer() {
    }

    @Test
    void isShowtimeAvailable() {
    }

    @Test
    void isSeatAvailable() {
    }

    @Test
    void doesMovieExist() {
    }

    @Test
    void doesScreenExist() {
    }

    @Test
    void hasFutureShowtimes() {
    }

    @Test
    void doesShowtimeExist() {
    }

    @Test
    void hasBookingsForShowtime() {
    }
}