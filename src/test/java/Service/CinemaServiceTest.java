package Service;

import Model.*;
import Repository.FileRepository;
import Repository.IRepository;
import Repository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class CinemaServiceTest {

    private CinemaService cinemaServiceInMemory;
    private CinemaService cinemaServiceFile;

    private IRepository<Customer> customerRepoInMemory;
    private IRepository<Staff> staffRepoInMemory;
    private IRepository<Movie> movieRepoInMemory;
    private IRepository<Showtime> showtimeRepoInMemory;
    private IRepository<Screen> screenRepoInMemory;
    private IRepository<Seat> seatRepoInMemory;
    private IRepository<Booking> bookingRepoInMemory;
    private IRepository<Ticket> ticketRepoInMemory;
    private IRepository<BasicMembership> basicMembershipRepoInMemory;
    private IRepository<PremiumMembership> premiumMembershipRepoInMemory;

    private IRepository<Customer> customerRepoFile;
    private IRepository<Staff> staffRepoFile;
    private IRepository<Movie> movieRepoFile;
    private IRepository<Showtime> showtimeRepoFile;
    private IRepository<Screen> screenRepoFile;
    private IRepository<Seat> seatRepoFile;
    private IRepository<Booking> bookingRepoFile;
    private IRepository<Ticket> ticketRepoFile;
    private IRepository<BasicMembership> basicMembershipRepoFile;
    private IRepository<PremiumMembership> premiumMembershipRepoFile;
    @BeforeEach
    void setUp() {
        String path = System.getProperty("user.dir") + "\\src\\test\\java\\Files\\";

        // În memorie
        customerRepoInMemory = new InMemoryRepository<>();
        staffRepoInMemory = new InMemoryRepository<>();
        movieRepoInMemory = new InMemoryRepository<>();
        showtimeRepoInMemory = new InMemoryRepository<>();
        screenRepoInMemory = new InMemoryRepository<>();
        seatRepoInMemory = new InMemoryRepository<>();
        bookingRepoInMemory = new InMemoryRepository<>();
        ticketRepoInMemory = new InMemoryRepository<>();
        basicMembershipRepoInMemory = new InMemoryRepository<>();
        premiumMembershipRepoInMemory = new InMemoryRepository<>();

        cinemaServiceInMemory = new CinemaService(
                customerRepoInMemory, staffRepoInMemory, movieRepoInMemory,
                showtimeRepoInMemory, screenRepoInMemory, seatRepoInMemory,
                bookingRepoInMemory, ticketRepoInMemory, basicMembershipRepoInMemory,
                premiumMembershipRepoInMemory
        );

        // Fișiere
        customerRepoFile = new FileRepository<>(path + "customerTest2.csv", Customer::fromCSV);
        staffRepoFile = new FileRepository<>(path + "staffTest2.csv", Staff::fromCSV);
        movieRepoFile = new FileRepository<>(path + "movieTest2.csv", Movie::fromCSV);
        showtimeRepoFile = new FileRepository<>(path + "showtimeTest2.csv", Showtime::fromCSV);
        screenRepoFile = new FileRepository<>(path + "screenTest2.csv", Screen::fromCSV);
        seatRepoFile = new FileRepository<>(path + "seatTest2.csv", Seat::fromCSV);
        bookingRepoFile = new FileRepository<>(path + "bookingTest2.csv", Booking::fromCSV);
        ticketRepoFile = new FileRepository<>(path + "ticketTest2.csv", Ticket::fromCSV);
        basicMembershipRepoFile = new FileRepository<>(path + "basicMembershipTest2.csv", BasicMembership::fromCSV);
        premiumMembershipRepoFile = new FileRepository<>(path + "premiumMembershipTest2.csv", PremiumMembership::fromCSV);

        cinemaServiceFile = new CinemaService(
                customerRepoFile, staffRepoFile, movieRepoFile,
                showtimeRepoFile, screenRepoFile, seatRepoFile,
                bookingRepoFile, ticketRepoFile, basicMembershipRepoFile,
                premiumMembershipRepoFile
        );
    }

    @Test
    void testAddCustomerInMemory() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        LocalDate birthday = LocalDate.of(2000, 1, 1);
        cinemaServiceInMemory.addCustomer(firstName, lastName, email, birthday);
        Customer customer = customerRepoInMemory.read(1);
        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals(1, customer.getId());
        assertFalse(customer.getUnderaged());
    }

//    @Test
//    void testAddCustomerFile() {
//        String firstName = "John";
//        String lastName = "Doe";
//        String email = "john.doe@example.com";
//        LocalDate birthday = LocalDate.of(2000, 1, 1);
//        cinemaServiceFile.addCustomer(firstName, lastName, email, birthday);
//        Customer customer = customerRepoFile.read(1);
//        assertNotNull(customer);
//        assertEquals("John", customer.getFirstName());
//        assertEquals("Doe", customer.getLastName());
//        assertEquals("john.doe@example.com", customer.getEmail());
//        assertEquals(1, customer.getId());
//        assertFalse(customer.getUnderaged());
//    }



    @Test
    void testGetCustomerInMemory() {
        String firstName = "Alice";
        String lastName = "Smith";
        String email = "alice.smith@example.com";
        LocalDate birthday = LocalDate.of(2010, 5, 5);
        cinemaServiceInMemory.addCustomer(firstName, lastName, email, birthday);
        Customer customer = cinemaServiceInMemory.getCustomer(1);
        assertNotNull(customer);
        assertEquals("Alice", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("alice.smith@example.com", customer.getEmail());
        assertEquals(1, customer.getId());
        assertTrue(customer.getUnderaged());
    }

//    @Test
//    void testGetCustomerFile() {
//        String firstName = "Alice";
//        String lastName = "Smith";
//        String email = "alice.smith@example.com";
//        LocalDate birthday = LocalDate.of(2010, 5, 5);
//        cinemaServiceFile.addCustomer(firstName, lastName, email, birthday);
//        Customer customer = cinemaServiceFile.getCustomer(1);
//        assertNotNull(customer);
//        assertEquals("Alice", customer.getFirstName());
//        assertEquals("Smith", customer.getLastName());
//        assertEquals("alice.smith@example.com", customer.getEmail());
//        assertEquals(1, customer.getId());
//        assertTrue(customer.getUnderaged());
//    }



    @Test
    void testUpdateCustomerInMemory() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        LocalDate birthday = LocalDate.of(2000, 1, 1);
        cinemaServiceInMemory.addCustomer(firstName, lastName, email, birthday);
        String newFirstName = "Johnathan";
        String newLastName = "Does";
        String newEmail = "johnathan.does@example.com";
        boolean underaged = false;
        cinemaServiceInMemory.updateCustomer(1, newFirstName, newLastName, newEmail, underaged, -1);
        Customer updatedCustomer = customerRepoInMemory.read(1);
        assertNotNull(updatedCustomer);
        assertEquals("Johnathan", updatedCustomer.getFirstName());
        assertEquals("Does", updatedCustomer.getLastName());
        assertEquals("johnathan.does@example.com", updatedCustomer.getEmail());
        assertFalse(updatedCustomer.getUnderaged());
    }

//    @Test
//    void testUpdateCustomerFile() {
//        String firstName = "John";
//        String lastName = "Doe";
//        String email = "john.doe@example.com";
//        LocalDate birthday = LocalDate.of(2000, 1, 1);
//        cinemaServiceFile.addCustomer(firstName, lastName, email, birthday);
//        String newFirstName = "Johnathan";
//        String newLastName = "Does";
//        String newEmail = "johnathan.does@example.com";
//        boolean underaged = false;
//        cinemaServiceFile.updateCustomer(1, newFirstName, newLastName, newEmail, underaged, -1);
//        Customer updatedCustomer = customerRepoFile.read(1);
//        assertNotNull(updatedCustomer);
//        assertEquals("Johnathan", updatedCustomer.getFirstName());
//        assertEquals("Does", updatedCustomer.getLastName());
//        assertEquals("johnathan.does@example.com", updatedCustomer.getEmail());
//        assertFalse(updatedCustomer.getUnderaged());
//    }
//

    @Test
    void testAddStaffInMemory() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        cinemaServiceInMemory.addStaff(firstName, lastName, email);
        Staff staff = staffRepoInMemory.read(1);
        assertNotNull(staff);
        assertEquals("John", staff.getFirstName());
        assertEquals("Doe", staff.getLastName());
        assertEquals("john.doe@example.com", staff.getEmail());
    }

//    @Test
//    void testAddStaffFile() {
//        String firstName = "John";
//        String lastName = "Doe";
//        String email = "john.doe@example.com";
//        cinemaServiceFile.addStaff(firstName, lastName, email);
//        Staff staff = staffRepoFile.read(1);
//        assertNotNull(staff);
//        assertEquals("John", staff.getFirstName());
//        assertEquals("Doe", staff.getLastName());
//        assertEquals("john.doe@example.com", staff.getEmail());
//    }


    @Test
    void testUpdateStaffInMemory() {
        String firstName = "Alice";
        String lastName = "Smith";
        String email = "alice.smith@example.com";
        cinemaServiceInMemory.addStaff(firstName, lastName, email);
        String newFirstName = "Alicia";
        String newLastName = "Johnson";
        String newEmail = "alicia.johnson@example.com";
        cinemaServiceInMemory.updateStaff(1, newFirstName, newLastName, newEmail);
        Staff updatedStaff = staffRepoInMemory.read(1);
        assertNotNull(updatedStaff);
        assertEquals("Alicia", updatedStaff.getFirstName());
        assertEquals("Johnson", updatedStaff.getLastName());
        assertEquals("alicia.johnson@example.com", updatedStaff.getEmail());
    }

//    @Test
//    void testUpdateStaffFile() {
//        String firstName = "Alice";
//        String lastName = "Smith";
//        String email = "alice.smith@example.com";
//        cinemaServiceFile.addStaff(firstName, lastName, email);
//        String newFirstName = "Alicia";
//        String newLastName = "Johnson";
//        String newEmail = "alicia.johnson@example.com";
//        cinemaServiceFile.updateStaff(1, newFirstName, newLastName, newEmail);
//        Staff updatedStaff = staffRepoFile.read(1);
//        assertNotNull(updatedStaff);
//        assertEquals("Alicia", updatedStaff.getFirstName());
//        assertEquals("Johnson", updatedStaff.getLastName());
//        assertEquals("alicia.johnson@example.com", updatedStaff.getEmail());
//    }

    @Test
    public void testAddMovieInMemory() {
        String title = "Inception";
        boolean pg = true;
        String genre = "Sci-Fi";
        LocalDate releaseDate = LocalDate.of(2010, 7, 16);
        cinemaServiceInMemory.addMovie(title, pg, genre, releaseDate);
        Movie movie = movieRepoInMemory.read(1);
        assertNotNull(movie);
        assertEquals("Inception", movie.getTitle());
        assertTrue(movie.getPg());
        assertEquals("Sci-Fi", movie.getGenre());
        assertEquals(releaseDate, movie.getReleaseDate());
    }

//    @Test
//    public void testAddMovieFile() {
//        String title = "Inception";
//        boolean pg = true;
//        String genre = "Sci-Fi";
//        LocalDate releaseDate = LocalDate.of(2010, 7, 16);
//        cinemaServiceFile.addMovie(title, pg, genre, releaseDate);
//        Movie movie = movieRepoFile.read(1);
//        assertNotNull(movie);
//        assertEquals("Inception", movie.getTitle());
//        assertTrue(movie.getPg());
//        assertEquals("Sci-Fi", movie.getGenre());
//        assertEquals(releaseDate, movie.getReleaseDate());
//    }

    @Test
    public void testUpdateMovieInMemory() {
        String title = "Interstellar";
        boolean pg = false;
        String genre = "Sci-Fi";
        LocalDate releaseDate = LocalDate.of(2014, 11, 7);
        cinemaServiceInMemory.addMovie(title, pg, genre, releaseDate);
        String newTitle = "Interstellar: Director's Cut";
        boolean newPg = true;
        String newGenre = "Sci-Fi, Drama";
        LocalDate newReleaseDate = LocalDate.of(2015, 11, 7);
        cinemaServiceInMemory.updateMovie(1, newTitle, newPg, newGenre, newReleaseDate);
        Movie updatedMovie = movieRepoInMemory.read(1);
        assertNotNull(updatedMovie);
        assertEquals("Interstellar: Director's Cut", updatedMovie.getTitle());
        assertTrue(updatedMovie.getPg());
        assertEquals("Sci-Fi, Drama", updatedMovie.getGenre());
        assertEquals(newReleaseDate, updatedMovie.getReleaseDate());
    }

//    @Test
//    public void testUpdateMovieFile() {
//        String title = "Interstellar";
//        boolean pg = false;
//        String genre = "Sci-Fi";
//        LocalDate releaseDate = LocalDate.of(2014, 11, 7);
//        cinemaServiceFile.addMovie(title, pg, genre, releaseDate);
//        String newTitle = "Interstellar: Director's Cut";
//        boolean newPg = true;
//        String newGenre = "Sci-Fi, Drama";
//        LocalDate newReleaseDate = LocalDate.of(2015, 11, 7);
//        cinemaServiceFile.updateMovie(1, newTitle, newPg, newGenre, newReleaseDate);
//        Movie updatedMovie = movieRepoFile.read(1);
//        assertNotNull(updatedMovie);
//        assertEquals("Interstellar: Director's Cut", updatedMovie.getTitle());
//        assertTrue(updatedMovie.getPg());
//        assertEquals("Sci-Fi, Drama", updatedMovie.getGenre());
//        assertEquals(newReleaseDate, updatedMovie.getReleaseDate());
//    }


    @Test
    public void testDeleteMovieInMemory() {
        String title = "Titanic";
        boolean pg = true;
        String genre = "Romance";
        LocalDate releaseDate = LocalDate.of(1997, 12, 19);
        cinemaServiceInMemory.addMovie(title, pg, genre, releaseDate);
        movieRepoInMemory.delete(1);
        Movie deletedMovie = movieRepoInMemory.read(1);
        assertNull(deletedMovie);
    }

//    @Test
//    public void testDeleteMovieFile() {
//        String title = "Titanic";
//        boolean pg = true;
//        String genre = "Romance";
//        LocalDate releaseDate = LocalDate.of(1997, 12, 19);
//        cinemaServiceFile.addMovie(title, pg, genre, releaseDate);
//        movieRepoFile.delete(1);
//        Movie deletedMovie = movieRepoFile.read(1);
//        assertNull(deletedMovie);
//    }

    @Test
    void findMovieIdByTitleInMemory() {
        Movie movie1 = new Movie(1, "Inception", false,"Sci-Fi", LocalDate.of(2010, 7, 16));
        movieRepoInMemory.add(movie1);

        Integer movieId = cinemaServiceInMemory.findMovieIdByTitle("Inception");
        assertNotNull(movieId);
        assertEquals(1, movieId);

        Integer movieId2 = cinemaServiceInMemory.findMovieIdByTitle("Avatar");
        assertEquals(-1, movieId2);

    }

    @Test
    public void testAddShowtimeInMemory() {
        int screenId = 1;
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            seats.add(new Seat(i, i, SeatType.standard));
        }
        for (int i = 2; i < 12; i++) {
            seats.add(new Seat(i, i, SeatType.vip));
        }
        for (int i = 10; i < 22; i++) {
            seats.add(new Seat(i, i, SeatType.premium));
        }
        Screen screen = new Screen(1, 2, 10, 10, seats);
        screenRepoInMemory.add(screen);
        int movieId = 1;
        Movie movie = new Movie(1, "Barbie", true, "action", LocalDate.of(2024, 12, 25));
        movieRepoInMemory.add(movie);
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime startTime = LocalTime.of(18, 30);
        int duration = 120;
        cinemaServiceInMemory.addShowtime(screenId, movieId, date, startTime, duration);
        Showtime showtime = showtimeRepoInMemory.read(1);
        assertNotNull(showtime);
        assertEquals(1, showtime.getScreenId());
        assertEquals(1, showtime.getMovieId());
        assertEquals(date, showtime.getDate());
        assertEquals(startTime, showtime.getStartTime());
        assertEquals(duration, showtime.getDuration());
    }

//    @Test
//    public void testAddShowtimeFile() {
//        int screenId = 1;
//        List<Seat> seats = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            seats.add(new Seat(i, i, SeatType.standard));
//        }
//        for (int i = 2; i < 12; i++) {
//            seats.add(new Seat(i, i, SeatType.vip));
//        }
//        for (int i = 10; i < 22; i++) {
//            seats.add(new Seat(i, i, SeatType.premium));
//        }
//        Screen screen = new Screen(1, 2, 10, 10, seats);
//        screenRepoFile.add(screen);
//        int movieId = 1;
//        Movie movie = new Movie(1, "Barbie", true, "action", LocalDate.of(2024, 12, 25));
//        movieRepoFile.add(movie);
//        LocalDate date = LocalDate.of(2024, 12, 25);
//        LocalTime startTime = LocalTime.of(18, 30);
//        int duration = 120;
//        cinemaServiceFile.addShowtime(screenId, movieId, date, startTime, duration);
//        Showtime showtime = showtimeRepoFile.read(1);
//        assertNotNull(showtime);
//        assertEquals(1, showtime.getScreenId());
//        assertEquals(1, showtime.getMovieId());
//        assertEquals(date, showtime.getDate());
//        assertEquals(startTime, showtime.getStartTime());
//        assertEquals(duration, showtime.getDuration());
//    }
//
//
@Test
void getShowtimeInMemory() {
    int screenId = 1;
    List<Seat> seats = new ArrayList<>();
    for (int i = 0; i < 2; i++) seats.add(new Seat(i, i, SeatType.standard));
    for (int i = 2; i < 12; i++) seats.add(new Seat(i, i, SeatType.vip));
    for (int i = 10; i < 22; i++) seats.add(new Seat(i, i, SeatType.premium));

    Screen screen = new Screen(screenId, 2, 10, 10, seats);
    screenRepoInMemory.add(screen);

    int movieId = 1;
    Movie movie = new Movie(movieId, "Barbie", true, "action", LocalDate.of(2024, 12, 25));
    movieRepoInMemory.add(movie);

    LocalDate date = LocalDate.of(2024, 12, 25);
    LocalTime startTime = LocalTime.of(18, 30);
    int duration = 120;

    cinemaServiceInMemory.addShowtime(screenId, movieId, date, startTime, duration);

    Showtime showtime = showtimeRepoInMemory.read(1);

    assertNotNull(showtime);
    assertEquals(1, showtime.getId());
    assertEquals(screenId, showtime.getScreenId());
    assertEquals(movieId, showtime.getMovieId());
    assertEquals(date, showtime.getDate());
    assertEquals(startTime, showtime.getStartTime());
    assertEquals(duration, showtime.getDuration());
}

    @Test
    public void testUpdateShowtimeInMemory() {
        int screenId = 1;
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            seats.add(new Seat(i, i, SeatType.standard));
        }
        for (int i = 2; i < 12; i++) {
            seats.add(new Seat(i, i, SeatType.vip));
        }
        for (int i = 10; i < 22; i++) {
            seats.add(new Seat(i, i, SeatType.premium));
        }
        Screen screen = new Screen(1, 2, 10, 10, seats);
        screenRepoInMemory.add(screen);
        int movieId = 1;
        Movie movie = new Movie(1, "Barbie", true, "action", LocalDate.of(2024, 12, 25));
        movieRepoInMemory.add(movie);
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime startTime = LocalTime.of(18, 30);
        int duration = 120;
        cinemaServiceInMemory.addShowtime(screenId, movieId, date, startTime, duration);
        LocalTime newStartTime = LocalTime.of(20, 0);
        int newDuration = 150;
        cinemaServiceInMemory.updateShowtime(1, screenId, movieId, date, newStartTime, newDuration, seats);
        Showtime updatedShowtime = showtimeRepoInMemory.read(1);
        assertNotNull(updatedShowtime);
        assertEquals(newStartTime, updatedShowtime.getStartTime());
        assertEquals(newDuration, updatedShowtime.getDuration());
    }

//    @Test
//    public void testUpdateShowtimeFile() {
//        int screenId = 1;
//        List<Seat> seats = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            seats.add(new Seat(i, i, SeatType.standard));
//        }
//        for (int i = 2; i < 12; i++) {
//            seats.add(new Seat(i, i, SeatType.vip));
//        }
//        for (int i = 10; i < 22; i++) {
//            seats.add(new Seat(i, i, SeatType.premium));
//        }
//        Screen screen = new Screen(1, 2, 10, 10, seats);
//        screenRepoFile.add(screen);
//        int movieId = 1;
//        Movie movie = new Movie(1, "Barbie", true, "action", LocalDate.of(2024, 12, 25));
//        movieRepoFile.add(movie);
//        LocalDate date = LocalDate.of(2024, 12, 25);
//        LocalTime startTime = LocalTime.of(18, 30);
//        int duration = 120;
//        cinemaServiceFile.addShowtime(screenId, movieId, date, startTime, duration);
//        LocalTime newStartTime = LocalTime.of(20, 0);
//        int newDuration = 150;
//        cinemaServiceFile.updateShowtime(1, screenId, movieId, date, newStartTime, newDuration, seats);
//        Showtime updatedShowtime = showtimeRepoFile.read(1);
//        assertNotNull(updatedShowtime);
//        assertEquals(newStartTime, updatedShowtime.getStartTime());
//        assertEquals(newDuration, updatedShowtime.getDuration());
//    }

    @Test
    public void testDeleteShowtimeInMemory() {
        int screenId = 1;
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            seats.add(new Seat(i, i, SeatType.standard));
        }
        for (int i = 2; i < 12; i++) {
            seats.add(new Seat(i, i, SeatType.vip));
        }
        for (int i = 10; i < 22; i++) {
            seats.add(new Seat(i, i, SeatType.premium));
        }
        Screen screen = new Screen(1, 2, 10, 10, seats);
        screenRepoInMemory.add(screen);
        int movieId = 1;
        Movie movie = new Movie(1, "Barbie", true, "action", LocalDate.of(2024, 12, 25));
        movieRepoInMemory.add(movie);
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime startTime = LocalTime.of(18, 30);
        int duration = 120;
        cinemaServiceInMemory.addShowtime(screenId, movieId, date, startTime, duration);
        showtimeRepoInMemory.delete(1);
        Showtime deletedShowtime = showtimeRepoInMemory.read(1);
        assertNull(deletedShowtime);
    }

//    @Test
//    public void testDeleteShowtimeFile() {
//        int screenId = 1;
//        List<Seat> seats = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            seats.add(new Seat(i, i, SeatType.standard));
//        }
//        for (int i = 2; i < 12; i++) {
//            seats.add(new Seat(i, i, SeatType.vip));
//        }
//        for (int i = 10; i < 22; i++) {
//            seats.add(new Seat(i, i, SeatType.premium));
//        }
//
//        Screen screen = new Screen(1, 2, 10, 10, seats);
//        screenRepoFile.add(screen);
//
//        int movieId = 1;
//        Movie movie = new Movie(1, "Barbie", true, "action", LocalDate.of(2024, 12, 25));
//        movieRepoFile.add(movie);
//
//        LocalDate date = LocalDate.of(2024, 12, 25);
//        LocalTime startTime = LocalTime.of(18, 30);
//        int duration = 120;
//        cinemaServiceFile.addShowtime(screenId, movieId, date, startTime, duration);
//
//        Showtime showtime = showtimeRepoFile.read(1);
//        assertNotNull(showtime);
//        assertEquals(1, showtime.getScreenId());
//        assertEquals(1, showtime.getMovieId());
//        assertEquals(date, showtime.getDate());
//        assertEquals(startTime, showtime.getStartTime());
//        assertEquals(duration, showtime.getDuration());
//
//        showtimeRepoFile.delete(1);
//
//        Showtime deletedShowtime = showtimeRepoFile.read(1);
//        assertNull(deletedShowtime);
//    }


    @Test
    void testAddScreenInMemory() {
        cinemaServiceInMemory.addScreen(10, 5, 3);

        Screen screen = screenRepoInMemory.read(1);
        assertNotNull(screen);
        assertEquals(10, screen.getNrStandardSeats());
        assertEquals(5, screen.getNrVipSeats());
        assertEquals(3, screen.getNrPremiumSeats());
        assertEquals(18, screen.getSeats().size());
    }

    @Test
    void testGetScreenInMemory() {
        cinemaServiceInMemory.addScreen(10, 5, 3);

        Screen screen = cinemaServiceInMemory.getScreen(1);
        assertNotNull(screen);
        assertEquals(10, screen.getNrStandardSeats());
        assertEquals(5, screen.getNrVipSeats());
        assertEquals(3, screen.getNrPremiumSeats());
    }

    @Test
    void testUpdateScreenInMemory() {
        cinemaServiceInMemory.addScreen(10, 5, 3);

        List<Seat> updatedSeats = new ArrayList<>();
        cinemaServiceInMemory.updateScreen(1, 15, 8, 5, updatedSeats);

        Screen updatedScreen = screenRepoInMemory.read(1);
        assertNotNull(updatedScreen);
        assertEquals(15, updatedScreen.getNrStandardSeats());
        assertEquals(8, updatedScreen.getNrVipSeats());
        assertEquals(5, updatedScreen.getNrPremiumSeats());
        assertEquals(28, updatedScreen.getSeats().size());
    }

    @Test
    void testDeleteScreenInMemory() {
        cinemaServiceInMemory.addScreen(10, 5, 3);

        cinemaServiceInMemory.deleteScreen(1);

        Screen deletedScreen = screenRepoInMemory.read(1);
        assertNull(deletedScreen);
    }

    @Test
    void testGetSeatInMemory() {
        Seat seat = new Seat(1, 1, SeatType.standard);
        seatRepoInMemory.add(seat);
        Seat fetchedSeat = cinemaServiceInMemory.getSeat(1);
        assertNotNull(fetchedSeat);
        assertEquals(seat, fetchedSeat);
    }

    @Test
    void testUpdateSeatInMemory() {
        Seat seat = new Seat(1, 1, SeatType.standard);
        seatRepoInMemory.add(seat);
        cinemaServiceInMemory.updateSeat(1, 2, SeatType.vip);
        Seat updatedSeat = seatRepoInMemory.read(1);
        assertEquals(2, updatedSeat.getSeatNr());
        assertEquals(SeatType.vip, updatedSeat.getType());
    }


    @Test
    void testFindSeatBySeatNrInMemory() {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1, 1, SeatType.standard));
        seats.add(new Seat(2, 2, SeatType.vip));
        Screen screen = new Screen(1, 1, 1, 0, seats);
        screenRepoInMemory.add(screen);

        Seat foundSeat = cinemaServiceInMemory.findSeatBySeatNr(1, 2);
        assertNotNull(foundSeat);
        assertEquals(2, foundSeat.getSeatNr());
        assertEquals(SeatType.vip, foundSeat.getType());
    }

    @Test
    void testAddBookingInMemory() {
        int screenId = 1;
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            seats.add(new Seat(i, i, SeatType.standard));
        }
        for (int i = 2; i < 12; i++) {
            seats.add(new Seat(i, i, SeatType.vip));
        }
        for (int i = 10; i < 22; i++) {
            seats.add(new Seat(i, i, SeatType.premium));
        }
        Screen screen = new Screen(1, 2, 10, 10, seats);
        screenRepoInMemory.add(screen);
        int movieId = 1;
        Movie movie = new Movie(1, "Barbie", true, "action", LocalDate.of(2024, 12, 25));
        movieRepoInMemory.add(movie);

        Showtime showtime = new Showtime(1, 1, 1, LocalDate.now(), LocalTime.of(1,30), 120,seats);
        showtimeRepoInMemory.add(showtime);

        Customer customer = new Customer(1, "John", "Doe", "john.doe@example.com", false, 0);
        customerRepoInMemory.add(customer);

        List<Integer> seatNumbers = List.of(1, 2);
        int bookingId = cinemaServiceInMemory.addBooking(1, 1, LocalDate.now(), 2, seatNumbers);

        Booking booking = bookingRepoInMemory.read(bookingId);
        assertNotNull(booking);
        assertEquals(2, booking.getNrOfCustomers());
        assertEquals(2, booking.getTickets().size());
    }

    @Test
    void testGetBookingInMemory() {
        Booking booking = new Booking(1, 1, 1, LocalDate.now(), 2, List.of(1, 2));
        bookingRepoInMemory.add(booking);
        Booking fetchedBooking = cinemaServiceInMemory.getBooking(1);
        assertNotNull(fetchedBooking);
        assertEquals(booking, fetchedBooking);
    }

    @Test
    void testUpdateBookingInMemory() {
        Booking booking = new Booking(1, 1, 1, LocalDate.now(), 2, List.of(1, 2));
        bookingRepoInMemory.add(booking);

        cinemaServiceInMemory.updateBooking(1, 2, 2, LocalDate.now().plusDays(1), 3, List.of(3, 4));
        Booking updatedBooking = bookingRepoInMemory.read(1);

        assertEquals(2, updatedBooking.getCustomerId());
        assertEquals(3, updatedBooking.getNrOfCustomers());
    }

    @Test
    void addTicket() {
        int screenId = 1;
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            seats.add(new Seat(i + 1, i + 1, SeatType.standard));
        }
        for (int i = 5; i < 8; i++) {
            seats.add(new Seat(i + 1, i + 1, SeatType.vip));
        }
        for (int i = 8; i < 10; i++) {
            seats.add(new Seat(i + 1, i + 1, SeatType.premium));
        }
        Screen screen = new Screen(screenId, 5, 3, 2, seats);
        screenRepoInMemory.add(screen);

        int bookingId = 1;
        LocalDate bookingDate = LocalDate.now();
        List<Integer> ticketIds = new ArrayList<>();
        Booking booking = new Booking(bookingId, 1, 1, bookingDate, 2, ticketIds);
        bookingRepoInMemory.add(booking);

        int seatId = seats.get(0).getId();
        double price = seats.get(0).getPrice();

        int ticketId = cinemaServiceInMemory.addTicket(bookingId, screenId, seatId, price);

        Ticket ticket = ticketRepoInMemory.read(ticketId);
        assertNotNull(ticket);
        assertEquals(bookingId, ticket.getBookingId());
        assertEquals(screenId, ticket.getScreenId());
        assertEquals(price, ticket.getPrice());
    }


    @Test
    void getTicket() {
        Ticket ticket = new Ticket(1, 1, 2, 3, 15.0);
        ticketRepoInMemory.add(ticket);

        Ticket fetchedTicket = cinemaServiceInMemory.getTicket(1);
        assertNotNull(fetchedTicket);
        assertEquals(ticket, fetchedTicket);
    }

    @Test
    void updateTicket() {
        int screenId = 1;
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            seats.add(new Seat(i + 1, i + 1, SeatType.standard));
        }
        for (int i = 5; i < 8; i++) {
            seats.add(new Seat(i + 1, i + 1, SeatType.vip));
        }
        for (int i = 8; i < 10; i++) {
            seats.add(new Seat(i + 1, i + 1, SeatType.premium));
        }
        Screen screen = new Screen(screenId, 5, 3, 2, seats);
        screenRepoInMemory.add(screen);

        int bookingId = 1;
        LocalDate bookingDate = LocalDate.now();
        List<Integer> ticketIds = new ArrayList<>();
        Booking booking = new Booking(bookingId, 1, 1, bookingDate, 2, ticketIds);
        bookingRepoInMemory.add(booking);

        int seatId = seats.get(0).getId();
        double price = seats.get(0).getPrice();

        int ticketId = cinemaServiceInMemory.addTicket(bookingId, screenId, seatId, price);

        cinemaServiceInMemory.updateTicket(1, 2, 3, 4, 20.0);

        Ticket updatedTicket = ticketRepoInMemory.read(1);
        assertNotNull(updatedTicket);
        assertEquals(2, updatedTicket.getBookingId());
        assertEquals(3, updatedTicket.getScreenId());
        assertEquals(20.0, updatedTicket.getPrice());
    }

    @Test
    void testAddBasicMembershipInMemory() {
        Customer customer = new Customer(1, "John", "Doe", "john.doe@example.com", false, 0);
        customerRepoInMemory.add(customer);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        BasicMembership basicMembership = cinemaServiceInMemory.addBasicMembership(1, startDate, endDate);
        assertNotNull(basicMembership);

        Customer updatedCustomer = customerRepoInMemory.read(1);
        assertEquals(basicMembership.getId(), updatedCustomer.getMembershipId());
    }


    @Test
    void getBasicMembershipInMemory() {
        int id = 1;
        int customerId = 1;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 1);
        BasicMembership basicMembership = new BasicMembership(id, customerId, startDate, endDate);
        basicMembershipRepoInMemory.add(basicMembership);

        BasicMembership fetchedMembership = basicMembershipRepoInMemory.getAll().get(id);

        assertNotNull(fetchedMembership);
        assertEquals(customerId, fetchedMembership.getCustomerId());
        assertEquals(startDate, fetchedMembership.getStartDate());
        assertEquals(endDate, fetchedMembership.getEndDate());
    }

    @Test
    void testUpdateBasicMembershipInMemory() {
        int id = 1;
        int customerId = 1;
        LocalDate oldStartDate = LocalDate.of(2024, 1, 1);
        LocalDate oldEndDate = LocalDate.of(2025, 1, 1);
        LocalDate newStartDate = LocalDate.of(2024, 2, 1);
        LocalDate newEndDate = LocalDate.of(2025, 2, 1);
        BasicMembership basicMembership = new BasicMembership(id, customerId, oldStartDate, oldEndDate);
        basicMembershipRepoInMemory.add(basicMembership);

        cinemaServiceInMemory.updateBasicMembership(id, customerId, newStartDate, newEndDate);

        BasicMembership updatedMembership = basicMembershipRepoInMemory.read(id);
        assertNotNull(updatedMembership);
        assertEquals(newStartDate, updatedMembership.getStartDate());
        assertEquals(newEndDate, updatedMembership.getEndDate());
    }


    @Test
    void testDeleteBasicMembershipInMemory() {
        int id = 1;
        int customerId = 1;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 1);
        BasicMembership basicMembership = new BasicMembership(id, customerId, startDate, endDate);
        basicMembershipRepoInMemory.add(basicMembership);

        cinemaServiceInMemory.deleteBasicMembership(id);

        BasicMembership deletedMembership = basicMembershipRepoInMemory.read(id);
        assertNull(deletedMembership);
    }

    @Test
    void testAddPremiumMembershipInMemory() {
        Customer customer = new Customer(1, "John", "Doe", "john.doe@example.com", false, 0);
        customerRepoInMemory.add(customer);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        PremiumMembership premiumMembership = cinemaServiceInMemory.addPremiumMembership(1, startDate, endDate);
        assertNotNull(premiumMembership);

        Customer updatedCustomer = customerRepoInMemory.read(1);
        assertEquals(premiumMembership.getId(), updatedCustomer.getMembershipId());
    }

    @Test
    void testGetPremiumMembershipInMemory() {
        int id = 1;
        int customerId = 1;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 1);
        PremiumMembership premiumMembership = new PremiumMembership(id, customerId, startDate, endDate);
        premiumMembershipRepoInMemory.add(premiumMembership);

        PremiumMembership fetchedMembership = premiumMembershipRepoInMemory.getAll().get(id);

        assertNotNull(fetchedMembership);
        assertEquals(customerId, fetchedMembership.getCustomerId());
        assertEquals(startDate, fetchedMembership.getStartDate());
        assertEquals(endDate, fetchedMembership.getEndDate());
    }


    @Test
    void testUpdatePremiumMembershipInMemory() {
        int id = 1;
        int customerId = 1;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 1);
        PremiumMembership premiumMembership = new PremiumMembership(id, customerId, startDate, endDate);
        premiumMembershipRepoInMemory.add(premiumMembership);

        LocalDate newStartDate = LocalDate.of(2025, 2, 1);
        LocalDate newEndDate = LocalDate.of(2026, 2, 1);
        PremiumMembership updatedMembership = new PremiumMembership(id, customerId, newStartDate, newEndDate);
        premiumMembershipRepoInMemory.update(updatedMembership);

        PremiumMembership fetchedMembership = premiumMembershipRepoInMemory.getAll().get(id);

        assertNotNull(fetchedMembership);
        assertEquals(customerId, fetchedMembership.getCustomerId());
        assertEquals(newStartDate, fetchedMembership.getStartDate());
        assertEquals(newEndDate, fetchedMembership.getEndDate());
    }


    @Test
    void testDeletePremiumMembershipInMemory() {
        int id = 1;
        int customerId = 1;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 1);
        PremiumMembership premiumMembership = new PremiumMembership(id, customerId, startDate, endDate);
        premiumMembershipRepoInMemory.add(premiumMembership);

        premiumMembershipRepoInMemory.delete(id);

        PremiumMembership fetchedMembership = premiumMembershipRepoInMemory.getAll().get(id);

        assertNull(fetchedMembership);
    }


    @Test
    void testGetMembershipTypeInMemory() {
        int customerIdWithBasic = 1;
        int customerIdWithPremium = 2;
        int customerIdWithoutMembership = 3;

        BasicMembership basicMembership = new BasicMembership(1, customerIdWithBasic, LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1));
        basicMembershipRepoInMemory.add(basicMembership);

        PremiumMembership premiumMembership = new PremiumMembership(2, customerIdWithPremium, LocalDate.of(2024, 2, 1), LocalDate.of(2025, 2, 1));
        premiumMembershipRepoInMemory.add(premiumMembership);

        assertEquals(1, cinemaServiceInMemory.getMembershipType(customerIdWithBasic));
        assertEquals(2, cinemaServiceInMemory.getMembershipType(customerIdWithPremium));
        assertEquals(0, cinemaServiceInMemory.getMembershipType(customerIdWithoutMembership));
    }


    @Test
    void testFilterShowtimesByPgUnderagedCustomer() {
        Customer underagedCustomer = new Customer(1, "Alice", "Smith", "alice@example.com", true, 0);
        customerRepoInMemory.add(underagedCustomer);

        Movie pgMovie = new Movie(1, "PG-13 Movie", true, "Action", LocalDate.of(2023, 6, 15));
        Movie nonPgMovie = new Movie(2, "Family Movie", false, "Comedy", LocalDate.of(2023, 8, 20));
        movieRepoInMemory.add(pgMovie);
        movieRepoInMemory.add(nonPgMovie);

        Showtime showtime1 = new Showtime(1, 1, 1, LocalDate.of(2024, 1, 1), LocalTime.of(18, 0), 120, new ArrayList<>());
        Showtime showtime2 = new Showtime(2, 1, 2, LocalDate.of(2024, 1, 1), LocalTime.of(20, 0), 150, new ArrayList<>());
        showtimeRepoInMemory.add(showtime1);
        showtimeRepoInMemory.add(showtime2);

        Map<Integer, Showtime> filteredShowtimes = cinemaServiceInMemory.filterShowtimesByPg(underagedCustomer);
        assertNotNull(filteredShowtimes);
        assertEquals(1, filteredShowtimes.size());
        assertTrue(filteredShowtimes.containsKey(2));
    }

    @Test
    void testFilterShowtimesByPgAdultCustomer() {
        Customer adultCustomer = new Customer(2, "Bob", "Jones", "bob@example.com", false, 0);
        customerRepoInMemory.add(adultCustomer);

        Movie pgMovie = new Movie(1, "PG-13 Movie", true, "Action", LocalDate.of(2023, 6, 15));
        Movie nonPgMovie = new Movie(2, "Family Movie", false, "Comedy", LocalDate.of(2023, 8, 20));
        movieRepoInMemory.add(pgMovie);
        movieRepoInMemory.add(nonPgMovie);

        Showtime showtime1 = new Showtime(1, 1, 1, LocalDate.of(2024, 1, 1), LocalTime.of(18, 0), 120, new ArrayList<>());
        Showtime showtime2 = new Showtime(2, 1, 2, LocalDate.of(2024, 1, 1), LocalTime.of(20, 0), 150, new ArrayList<>());
        showtimeRepoInMemory.add(showtime1);
        showtimeRepoInMemory.add(showtime2);

        Map<Integer, Showtime> allShowtimes = cinemaServiceInMemory.filterShowtimesByPg(adultCustomer);
        assertNotNull(allShowtimes);
        assertEquals(2, allShowtimes.size());
        assertTrue(allShowtimes.containsKey(1));
        assertTrue(allShowtimes.containsKey(2));
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