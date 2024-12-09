package Repository;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FileRepositoryTest {
    String path = System.getProperty("user.dir") + "\\src\\test\\java\\Files\\";
    FileRepository<Customer> customerRepo = new FileRepository<>(path + "customerTest.csv", Customer::fromCSV);
    FileRepository<Staff> staffRepo = new FileRepository<>(path + "staffTest.csv", Staff::fromCSV);
    FileRepository<Movie> movieRepo = new FileRepository<>(path + "movieTest.csv", Movie::fromCSV);
    FileRepository<Booking> bookingRepo = new FileRepository<>(path + "bookingTest.csv", Booking::fromCSV);
    FileRepository<Showtime> showtimeRepo = new FileRepository<>(path + "showtimeTest.csv", Showtime::fromCSV);
    FileRepository<Screen> screenRepo = new FileRepository<>(path + "screenTest.csv", Screen::fromCSV);
    FileRepository<Seat> seatRepo = new FileRepository<>(path + "seatTest.csv", Seat::fromCSV);
    FileRepository<Ticket> ticketRepo = new FileRepository<>(path + "ticketTest.csv", Ticket::fromCSV);
    FileRepository<BasicMembership> basicMembershipRepo = new FileRepository<>(path + "basicMembershipTest.csv", BasicMembership::fromCSV);
    FileRepository<PremiumMembership> premiumMembershipRepo = new FileRepository<>(path + "premiumMembershipTest.csv", PremiumMembership::fromCSV);

    Customer customer1;
    Customer customer2;
    Staff staff;
    Booking booking;
    Showtime showtime;
    Movie movie;
    Screen screen;
    Seat seat1;
    Seat seat2;
    Seat seat3;
    Ticket ticket1;
    Ticket ticket2;
    BasicMembership basicMembership;
    PremiumMembership premiumMembership;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(1, "John", "Doe", "john.doe@gamil.com", false, 1);
        customer2 = new Customer(2, "Jane", "Doe", "jane.doe@gamil.com", true, 2);
        staff = new Staff(1, "John", "Doe", "john.doe@gmail.com");
        booking = new Booking(1, 1, 1, LocalDate.of(2025,1,1), 2, Arrays.asList(1,2));
        seat1 = new Seat(1, 1, SeatType.standard);
        seat2 = new Seat(2, 2, SeatType.vip);
        seat3 = new Seat(3, 3, SeatType.premium);
        screen = new Screen(1, 1,1,1, Arrays.asList(seat1,seat2,seat3));
        movie = new Movie(1, "Harry Potter", true, "adventure", LocalDate.of(2001, 10, 31));
        showtime = new Showtime(1, 1, 1, LocalDate.of(2025, 1, 7), LocalTime.of(14,30), 120, Arrays.asList(seat1,seat2,seat3));
        ticket1 = new Ticket(1, 1,1,1, 30);
        ticket2 = new Ticket(2, 1, 1, 2, 40);
        basicMembership = new BasicMembership(1, 1, LocalDate.now(), LocalDate.now().plusDays(30));
        premiumMembership = new PremiumMembership(1, 2, LocalDate.now(), LocalDate.now().plusDays(30));
    }

    @Test
    void testGenerateIdCustomer() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        assertEquals(3, customerRepo.generateNewId());
    }

    @Test
    void testAddCustomer() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        assertEquals(2, customerRepo.getAll().size());
        assertEquals(customer1.getId(), customerRepo.read(1).getId());
        assertEquals(customer1.getFirstName(), customerRepo.read(1).getFirstName());
        assertEquals(customer1.getLastName(), customerRepo.read(1).getLastName());
        assertEquals(customer1.getEmail(), customerRepo.read(1).getEmail());
        assertEquals(customer1.getUnderaged(), customerRepo.read(1).getUnderaged());
        assertEquals(customer1.getMembershipId(), customerRepo.read(1).getMembershipId());
        assertEquals(customer2.getId(), customerRepo.read(2).getId());
        assertEquals(customer2.getFirstName(), customerRepo.read(2).getFirstName());
        assertEquals(customer2.getLastName(), customerRepo.read(2).getLastName());
        assertEquals(customer2.getEmail(), customerRepo.read(2).getEmail());
        assertEquals(customer2.getUnderaged(), customerRepo.read(2).getUnderaged());
        assertEquals(customer2.getMembershipId(), customerRepo.read(2).getMembershipId());
    }

    @Test
    void testReadCustomer() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        assertEquals(customer1.getId(), customerRepo.read(1).getId());
        assertEquals(customer1.getFirstName(), customerRepo.read(1).getFirstName());
        assertEquals(customer1.getLastName(), customerRepo.read(1).getLastName());
        assertEquals(customer1.getEmail(), customerRepo.read(1).getEmail());
        assertEquals(customer1.getUnderaged(), customerRepo.read(1).getUnderaged());
        assertEquals(customer1.getMembershipId(), customerRepo.read(1).getMembershipId());
        assertEquals(customer2.getId(), customerRepo.read(2).getId());
        assertEquals(customer2.getFirstName(), customerRepo.read(2).getFirstName());
        assertEquals(customer2.getLastName(), customerRepo.read(2).getLastName());
        assertEquals(customer2.getEmail(), customerRepo.read(2).getEmail());
        assertEquals(customer2.getUnderaged(), customerRepo.read(2).getUnderaged());
        assertEquals(customer2.getMembershipId(), customerRepo.read(2).getMembershipId());
    }

    @Test
    void testDeleteCustomer() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        customerRepo.delete(1);
        assertEquals(1, customerRepo.getAll().size());
        assertNull(customerRepo.read(1));
        assertNotNull(customerRepo.read(2));
    }

    @Test
    void testUpdateCustomer() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        Customer customer3 = new Customer(2, "John", "Doe", "john.doe@gamil.com", false, 2);
        customerRepo.update(customer3);
        assertEquals(customer3.getFirstName(), customerRepo.read(2).getFirstName());
        assertEquals(customer3.getLastName(), customerRepo.read(2).getLastName());
        assertEquals(customer3.getEmail(), customerRepo.read(2).getEmail());
        assertEquals(customer3.getUnderaged(), customerRepo.read(2).getUnderaged());
        assertEquals(customer3.getMembershipId(), customerRepo.read(2).getMembershipId());
        assertNotSame(customer2.getFirstName(), customerRepo.read(2).getFirstName());
        assertEquals(customer2.getLastName(), customerRepo.read(2).getLastName());
        assertNotSame(customer2.getEmail(), customerRepo.read(2).getEmail());
        assertNotSame(customer2.getUnderaged(), customerRepo.read(2).getUnderaged());
        assertEquals(customer2.getMembershipId(), customerRepo.read(2).getMembershipId());
    }

    @Test
    void testReadAllCustomer() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        assertEquals(2, customerRepo.getAll().size());
        assertEquals(customer1.getId(), customerRepo.getAll().get(1).getId());
        assertEquals(customer1.getFirstName(), customerRepo.getAll().get(1).getFirstName());
        assertEquals(customer1.getLastName(), customerRepo.getAll().get(1).getLastName());
        assertEquals(customer1.getEmail(), customerRepo.getAll().get(1).getEmail());
        assertEquals(customer1.getUnderaged(), customerRepo.getAll().get(1).getUnderaged());
        assertEquals(customer1.getMembershipId(), customerRepo.getAll().get(1).getMembershipId());
        assertEquals(customer2.getId(), customerRepo.getAll().get(2).getId());
        assertEquals(customer2.getFirstName(), customerRepo.getAll().get(2).getFirstName());
        assertEquals(customer2.getLastName(), customerRepo.getAll().get(2).getLastName());
        assertEquals(customer2.getEmail(), customerRepo.getAll().get(2).getEmail());
        assertEquals(customer2.getUnderaged(), customerRepo.getAll().get(2).getUnderaged());
        assertEquals(customer2.getMembershipId(), customerRepo.getAll().get(2).getMembershipId());

    }

    @Test
    void testGenerateIdStaff() {
        staffRepo.add(staff);
        assertEquals(2, staffRepo.generateNewId());
    }

    @Test
    void testAddStaff() {
        staffRepo.add(staff);
        assertEquals(1, staffRepo.getAll().size());
        assertEquals(staff.getId(), staffRepo.read(1).getId());
        assertEquals(staff.getFirstName(), staffRepo.read(1).getFirstName());
        assertEquals(staff.getLastName(), staffRepo.read(1).getLastName());
        assertEquals(staff.getEmail(), staffRepo.read(1).getEmail());
    }

    @Test
    void testReadStaff() {
        staffRepo.add(staff);
        assertEquals(staff.getId(), staffRepo.read(1).getId());
        assertEquals(staff.getFirstName(), staffRepo.read(1).getFirstName());
        assertEquals(staff.getLastName(), staffRepo.read(1).getLastName());
        assertEquals(staff.getEmail(), staffRepo.read(1).getEmail());
    }

    @Test
    void testDeleteStaff() {
        staffRepo.add(staff);
        staffRepo.delete(1);
        assertEquals(0, staffRepo.getAll().size());
        assertNull(staffRepo.read(1));
    }

    @Test
    void testUpdateStaff() {
        staffRepo.add(staff);
        Staff staff2 = new Staff(1, "Jane", "Doe", "john.doe@gamil.com");
        staffRepo.update(staff2);
        assertEquals(staff2.getFirstName(), staffRepo.read(1).getFirstName());
        assertEquals(staff2.getLastName(), staffRepo.read(1).getLastName());
        assertEquals(staff2.getEmail(), staffRepo.read(1).getEmail());
        assertNotSame(staff.getFirstName(), staffRepo.read(1).getFirstName());
        assertNotSame(staff.getLastName(), staffRepo.read(1).getLastName());
        assertNotSame(staff.getEmail(), staffRepo.read(1).getEmail());
    }

    @Test
    void testReadAllStaff() {
        staffRepo.add(staff);
        assertEquals(1, staffRepo.getAll().size());
        assertEquals(staff.getId(), staffRepo.getAll().get(1).getId());
        assertEquals(staff.getFirstName(), staffRepo.getAll().get(1).getFirstName());
        assertEquals(staff.getLastName(), staffRepo.getAll().get(1).getLastName());
        assertEquals(staff.getEmail(), staffRepo.getAll().get(1).getEmail());
    }

    @Test
    void testGenerateIdMovie() {
        movieRepo.add(movie);
        assertEquals(2, movieRepo.generateNewId());
    }

    @Test
    void testAddMovie() {
        movieRepo.add(movie);
        assertEquals(1, movieRepo.getAll().size());
        assertEquals(movie.getId(), movieRepo.read(1).getId());
        assertEquals(movie.getTitle(), movieRepo.read(1).getTitle());
        assertEquals(movie.getPg(), movieRepo.read(1).getPg());
        assertEquals(movie.getGenre(), movieRepo.read(1).getGenre());
        assertEquals(movie.getReleaseDate(), movieRepo.read(1).getReleaseDate());
    }

    @Test
    void testReadMovie() {
        movieRepo.add(movie);
        assertEquals(movie.getId(), movieRepo.read(1).getId());
        assertEquals(movie.getTitle(), movieRepo.read(1).getTitle());
        assertEquals(movie.getPg(), movieRepo.read(1).getPg());
        assertEquals(movie.getGenre(), movieRepo.read(1).getGenre());
        assertEquals(movie.getReleaseDate(), movieRepo.read(1).getReleaseDate());
    }

    @Test
    void testDeleteMovie() {
        movieRepo.add(movie);
        movieRepo.delete(1);
        assertEquals(0, movieRepo.getAll().size());
        assertNull(movieRepo.read(1));
    }

    @Test
    void testUpdateMovie() {
        movieRepo.add(movie);
        Movie movie2 = new Movie(1, "Titanic", true, "drama", LocalDate.of(2003, 6,17));
        movieRepo.update(movie2);
        assertEquals(movie2.getTitle(), movieRepo.read(1).getTitle());
        assertEquals(movie2.getPg(), movieRepo.read(1).getPg());
        assertEquals(movie2.getGenre(), movieRepo.read(1).getGenre());
        assertEquals(movie2.getReleaseDate(), movieRepo.read(1).getReleaseDate());
        assertNotSame(movie.getTitle(), movieRepo.read(1).getTitle());
        assertEquals(movie.getPg(), movieRepo.read(1).getPg());
        assertNotSame(movie.getGenre(), movieRepo.read(1).getGenre());
        assertNotSame(movie.getReleaseDate(), movieRepo.read(1).getReleaseDate());
    }

    @Test
    void testReadAllMovie() {
        movieRepo.add(movie);
        assertEquals(1, movieRepo.getAll().size());
        assertEquals(movie.getId(), movieRepo.getAll().get(1).getId());
        assertEquals(movie.getTitle(), movieRepo.getAll().get(1).getTitle());
        assertEquals(movie.getPg(), movieRepo.getAll().get(1).getPg());
        assertEquals(movie.getGenre(), movieRepo.getAll().get(1).getGenre());
        assertEquals(movie.getReleaseDate(), movieRepo.getAll().get(1).getReleaseDate());
    }

    @Test
    void testGenerateIdScreen() {
        screenRepo.add(screen);
        assertEquals(2, screenRepo.generateNewId());
    }

    @Test
    void testAddScreen() {
        screenRepo.add(screen);
        assertEquals(1, screenRepo.getAll().size());
        assertEquals(screen.getId(), screenRepo.read(1).getId());
        assertEquals(screen.getNrStandardSeats(), screenRepo.read(1).getNrStandardSeats());
        assertEquals(screen.getNrVipSeats(), screenRepo.read(1).getNrVipSeats());
        assertEquals(screen.getNrPremiumSeats(), screenRepo.read(1).getNrPremiumSeats());
        assertEquals(screen.getSeats(), screenRepo.read(1).getSeats());
    }

    @Test
    void testReadScreen() {
        screenRepo.add(screen);
        assertEquals(screen.getId(), screenRepo.read(1).getId());
        assertEquals(screen.getNrStandardSeats(), screenRepo.read(1).getNrStandardSeats());
        assertEquals(screen.getNrVipSeats(), screenRepo.read(1).getNrVipSeats());
        assertEquals(screen.getNrPremiumSeats(), screenRepo.read(1).getNrPremiumSeats());
        assertEquals(screen.getSeats(), screenRepo.read(1).getSeats());
    }

    @Test
    void testDeleteScreen() {
        screenRepo.add(screen);
        screenRepo.delete(1);
        assertEquals(0, screenRepo.getAll().size());
        assertNull(screenRepo.read(1));
    }

    @Test
    void testUpdateScreen() {
        screenRepo.add(screen);
        Screen screen2 = new Screen(1, 1, 0, 1, Arrays.asList(seat1,seat3));
        screenRepo.update(screen2);
        assertEquals(screen2.getNrStandardSeats(), screenRepo.read(1).getNrStandardSeats());
        assertEquals(screen2.getNrVipSeats(), screenRepo.read(1).getNrVipSeats());
        assertEquals(screen2.getNrPremiumSeats(), screenRepo.read(1).getNrPremiumSeats());
        assertEquals(screen2.getSeats(), screenRepo.read(1).getSeats());
        assertEquals(screen.getNrStandardSeats(), screenRepo.read(1).getNrStandardSeats());
        assertNotSame(screen.getNrVipSeats(), screenRepo.read(1).getNrVipSeats());
        assertEquals(screen.getNrPremiumSeats(), screenRepo.read(1).getNrPremiumSeats());
        assertNotSame(screen.getSeats(), screenRepo.read(1).getSeats());
    }

    @Test
    void testReadAllScreen() {
        screenRepo.add(screen);
        assertEquals(1,screenRepo.getAll().size());
        assertEquals(screen.getId(), screenRepo.getAll().get(1).getId());
        assertEquals(screen.getNrStandardSeats(), screenRepo.getAll().get(1).getNrStandardSeats());
        assertEquals(screen.getNrVipSeats(), screenRepo.getAll().get(1).getNrVipSeats());
        assertEquals(screen.getNrPremiumSeats(), screenRepo.getAll().get(1).getNrPremiumSeats());
        assertEquals(screen.getSeats(), screenRepo.getAll().get(1).getSeats());
    }

    @Test
    void testGenerateIdSeat() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.add(seat3);
        assertEquals(4, seatRepo.generateNewId());
    }

    @Test
    void testAddSeat() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.add(seat3);
        assertEquals(3, seatRepo.getAll().size());
        assertEquals(seat1.getId(), seatRepo.read(1).getId());
        assertEquals(seat1.getSeatNr(), seatRepo.read(1).getSeatNr());
        assertEquals(seat1.getType(), seatRepo.read(1).getType());
        assertEquals(seat2.getId(), seatRepo.read(2).getId());
        assertEquals(seat2.getSeatNr(), seatRepo.read(2).getSeatNr());
        assertEquals(seat2.getType(), seatRepo.read(2).getType());
        assertEquals(seat3.getId(), seatRepo.read(3).getId());
        assertEquals(seat3.getSeatNr(), seatRepo.read(3).getSeatNr());
        assertEquals(seat3.getType(), seatRepo.read(3).getType());
    }

    @Test
    void testReadSeat() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.add(seat3);
        assertEquals(seat1.getId(), seatRepo.read(1).getId());
        assertEquals(seat1.getSeatNr(), seatRepo.read(1).getSeatNr());
        assertEquals(seat1.getType(), seatRepo.read(1).getType());
        assertEquals(seat2.getId(), seatRepo.read(2).getId());
        assertEquals(seat2.getSeatNr(), seatRepo.read(2).getSeatNr());
        assertEquals(seat2.getType(), seatRepo.read(2).getType());
        assertEquals(seat3.getId(), seatRepo.read(3).getId());
        assertEquals(seat3.getSeatNr(), seatRepo.read(3).getSeatNr());
        assertEquals(seat3.getType(), seatRepo.read(3).getType());
    }

    @Test
    void testDeleteSeat() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.add(seat3);
        seatRepo.delete(1);
        assertEquals(2, seatRepo.getAll().size());
        assertNull(seatRepo.read(1));
        assertNotNull(seatRepo.read(2));
        assertNotNull(seatRepo.read(3));
    }

    @Test
    void testUpdateSeat() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.add(seat3);
        Seat seat4 = new Seat(2, 2, SeatType.premium);
        seatRepo.update(seat4);
        assertEquals(seat4.getSeatNr(), seatRepo.read(2).getSeatNr());
        assertEquals(seat4.getType(), seatRepo.read(2).getType());
        assertEquals(seat2.getSeatNr(), seatRepo.read(2).getSeatNr());
        assertNotSame(seat2.getType(), seatRepo.read(2).getType());
    }

    @Test
    void testReadAllSeat() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.add(seat3);
        assertEquals(3, seatRepo.getAll().size());
        assertEquals(seat1.getId(), seatRepo.getAll().get(1).getId());
        assertEquals(seat1.getSeatNr(), seatRepo.getAll().get(1).getSeatNr());
        assertEquals(seat1.getType(), seatRepo.getAll().get(1).getType());
        assertEquals(seat2.getId(), seatRepo.getAll().get(2).getId());
        assertEquals(seat2.getSeatNr(), seatRepo.getAll().get(2).getSeatNr());
        assertEquals(seat2.getType(), seatRepo.getAll().get(2).getType());
        assertEquals(seat3.getId(), seatRepo.getAll().get(3).getId());
        assertEquals(seat3.getSeatNr(), seatRepo.getAll().get(3).getSeatNr());
        assertEquals(seat3.getType(), seatRepo.getAll().get(3).getType());
    }

    @Test
    void testGenerateIdShowtime() {
        showtimeRepo.add(showtime);
        assertEquals(2, showtimeRepo.generateNewId());
    }

    @Test
    void testAddShowtime() {
        showtimeRepo.add(showtime);
        assertEquals(1, showtimeRepo.getAll().size());
        assertEquals(showtime.getId(), showtimeRepo.read(1).getId());
        assertEquals(showtime.getScreenId(), showtimeRepo.read(1).getScreenId());
        assertEquals(showtime.getMovieId(), showtimeRepo.read(1).getMovieId());
        assertEquals(showtime.getDate(), showtimeRepo.read(1).getDate());
        assertEquals(showtime.getStartTime(), showtimeRepo.read(1).getStartTime());
        assertEquals(showtime.getDuration(), showtimeRepo.read(1).getDuration());
        assertEquals(showtime.getSeats(), showtimeRepo.read(1).getSeats());
    }

    @Test
    void testReadShowtime() {
        showtimeRepo.add(showtime);
        assertEquals(showtime.getId(), showtimeRepo.read(1).getId());
        assertEquals(showtime.getScreenId(), showtimeRepo.read(1).getScreenId());
        assertEquals(showtime.getMovieId(), showtimeRepo.read(1).getMovieId());
        assertEquals(showtime.getDate(), showtimeRepo.read(1).getDate());
        assertEquals(showtime.getStartTime(), showtimeRepo.read(1).getStartTime());
        assertEquals(showtime.getDuration(), showtimeRepo.read(1).getDuration());
        assertEquals(showtime.getSeats(), showtimeRepo.read(1).getSeats());
    }

    @Test
    void testDeleteShowtime() {
        showtimeRepo.add(showtime);
        showtimeRepo.delete(1);
        assertEquals(0, showtimeRepo.getAll().size());
        assertNull(showtimeRepo.read(1));
    }

    @Test
    void testUpdateShowtime() {
        showtimeRepo.add(showtime);
        Showtime showtime2 = new Showtime(1, 1, 1, LocalDate.of(2024, 12, 26), LocalTime.of(20,0),115, Arrays.asList(seat1,seat2,seat3));
        showtimeRepo.update(showtime2);
        assertEquals(showtime2.getScreenId(), showtimeRepo.read(1).getScreenId());
        assertEquals(showtime2.getMovieId(), showtimeRepo.read(1).getMovieId());
        assertEquals(showtime2.getDate(), showtimeRepo.read(1).getDate());
        assertEquals(showtime2.getStartTime(), showtimeRepo.read(1).getStartTime());
        assertEquals(showtime2.getDuration(), showtimeRepo.read(1).getDuration());
        assertEquals(showtime2.getSeats(), showtimeRepo.read(1).getSeats());
        assertEquals(showtime.getScreenId(), showtimeRepo.read(1).getScreenId());
        assertEquals(showtime.getMovieId(), showtimeRepo.read(1).getMovieId());
        assertNotSame(showtime.getDate(), showtimeRepo.read(1).getDate());
        assertNotSame(showtime.getStartTime(), showtimeRepo.read(1).getStartTime());
        assertNotSame(showtime.getDuration(), showtimeRepo.read(1).getDuration());
        assertEquals(showtime.getSeats(), showtimeRepo.read(1).getSeats());
    }

    @Test
    void testReadAllShowtime() {
        showtimeRepo.add(showtime);
        assertEquals(1, showtimeRepo.getAll().size());
        assertEquals(showtime.getId(), showtimeRepo.getAll().get(1).getId());
        assertEquals(showtime.getScreenId(), showtimeRepo.getAll().get(1).getScreenId());
        assertEquals(showtime.getMovieId(), showtimeRepo.getAll().get(1).getMovieId());
        assertEquals(showtime.getDate(), showtimeRepo.getAll().get(1).getDate());
        assertEquals(showtime.getStartTime(), showtimeRepo.getAll().get(1).getStartTime());
        assertEquals(showtime.getDuration(), showtimeRepo.getAll().get(1).getDuration());
        assertEquals(showtime.getSeats(), showtimeRepo.getAll().get(1).getSeats());
    }

    @Test
    void testGenerateIdBooking() {
        bookingRepo.add(booking);
        assertEquals(2, bookingRepo.generateNewId());
    }

    @Test
    void testAddBooking() {
        bookingRepo.add(booking);
        assertEquals(1, bookingRepo.getAll().size());
        assertEquals(booking.getId(), bookingRepo.read(1).getId());
        assertEquals(booking.getCustomerId(), bookingRepo.read(1).getCustomerId());
        assertEquals(booking.getShowtimeId(), bookingRepo.read(1).getShowtimeId());
        assertEquals(booking.getDate(), bookingRepo.read(1).getDate());
        assertEquals(booking.getNrOfCustomers(), bookingRepo.read(1).getNrOfCustomers());
        assertEquals(booking.getTickets(), bookingRepo.read(1).getTickets());
    }

    @Test
    void testReadBooking() {
        bookingRepo.add(booking);
        assertEquals(booking.getId(), bookingRepo.read(1).getId());
        assertEquals(booking.getCustomerId(), bookingRepo.read(1).getCustomerId());
        assertEquals(booking.getShowtimeId(), bookingRepo.read(1).getShowtimeId());
        assertEquals(booking.getDate(), bookingRepo.read(1).getDate());
        assertEquals(booking.getNrOfCustomers(), bookingRepo.read(1).getNrOfCustomers());
        assertEquals(booking.getTickets(), bookingRepo.read(1).getTickets());
    }

    @Test
    void testDeleteBooking() {
        bookingRepo.add(booking);
        bookingRepo.delete(1);
        assertEquals(0, bookingRepo.getAll().size());
        assertNull(bookingRepo.read(1));
    }

    @Test
    void testUpdateBooking() {
        bookingRepo.add(booking);
        Booking booking2 = new Booking(1, 1, 1, LocalDate.of(2024, 12,26), 1, Arrays.asList(1));
        bookingRepo.update(booking2);
        assertEquals(booking2.getCustomerId(), bookingRepo.read(1).getCustomerId());
        assertEquals(booking2.getShowtimeId(), bookingRepo.read(1).getShowtimeId());
        assertEquals(booking2.getDate(), bookingRepo.read(1).getDate());
        assertEquals(booking2.getNrOfCustomers(), bookingRepo.read(1).getNrOfCustomers());
        assertEquals(booking2.getTickets(), bookingRepo.read(1).getTickets());
        assertEquals(booking.getCustomerId(), bookingRepo.read(1).getCustomerId());
        assertEquals(booking.getShowtimeId(), bookingRepo.read(1).getShowtimeId());
        assertNotSame(booking.getDate(), bookingRepo.read(1).getDate());
        assertNotSame(booking.getNrOfCustomers(), bookingRepo.read(1).getNrOfCustomers());
        assertNotSame(booking.getTickets(), bookingRepo.read(1).getTickets());
    }

    @Test
    void testReadAllBooking() {
        bookingRepo.add(booking);
        assertEquals(1, bookingRepo.getAll().size());
        assertEquals(booking.getId(), bookingRepo.getAll().get(1).getId());
        assertEquals(booking.getCustomerId(), bookingRepo.getAll().get(1).getCustomerId());
        assertEquals(booking.getShowtimeId(), bookingRepo.getAll().get(1).getShowtimeId());
        assertEquals(booking.getDate(), bookingRepo.getAll().get(1).getDate());
        assertEquals(booking.getNrOfCustomers(), bookingRepo.getAll().get(1).getNrOfCustomers());
        assertEquals(booking.getTickets(), bookingRepo.getAll().get(1).getTickets());
    }

    @Test
    void testGenerateIdTicket() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(3, ticketRepo.generateNewId());
    }

    @Test
    void testAddTicket() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(2, ticketRepo.getAll().size());
        assertEquals(ticket1.getId(), ticketRepo.read(1).getId());
        assertEquals(ticket1.getScreenId(), ticketRepo.read(1).getScreenId());
        assertEquals(ticket1.getBookingId(), ticketRepo.read(1).getBookingId());
        assertEquals(ticket1.getSeatNr(), ticketRepo.read(1).getSeatNr());
        assertEquals(ticket1.getPrice(), ticketRepo.read(1).getPrice());
        assertEquals(ticket2.getId(), ticketRepo.read(2).getId());
        assertEquals(ticket2.getScreenId(), ticketRepo.read(2).getScreenId());
        assertEquals(ticket2.getBookingId(), ticketRepo.read(2).getBookingId());
        assertEquals(ticket2.getSeatNr(), ticketRepo.read(2).getSeatNr());
        assertEquals(ticket2.getPrice(), ticketRepo.read(2).getPrice());
    }

    @Test
    void testReadTicket() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(ticket1.getId(), ticketRepo.read(1).getId());
        assertEquals(ticket1.getScreenId(), ticketRepo.read(1).getScreenId());
        assertEquals(ticket1.getBookingId(), ticketRepo.read(1).getBookingId());
        assertEquals(ticket1.getSeatNr(), ticketRepo.read(1).getSeatNr());
        assertEquals(ticket1.getPrice(), ticketRepo.read(1).getPrice());
        assertEquals(ticket2.getId(), ticketRepo.read(2).getId());
        assertEquals(ticket2.getScreenId(), ticketRepo.read(2).getScreenId());
        assertEquals(ticket2.getBookingId(), ticketRepo.read(2).getBookingId());
        assertEquals(ticket2.getSeatNr(), ticketRepo.read(2).getSeatNr());
        assertEquals(ticket2.getPrice(), ticketRepo.read(2).getPrice());
    }

    @Test
    void testDeleteTicket() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        ticketRepo.delete(2);
        assertEquals(1, ticketRepo.getAll().size());
        assertNull(ticketRepo.read(2));
        assertNotNull(ticketRepo.read(1));
    }

    @Test
    void testUpdateTicket() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        Ticket ticket3 = new Ticket(2, 1, 1, 3, 50);
        ticketRepo.update(ticket3);
        assertEquals(ticket3.getScreenId(), ticketRepo.read(2).getScreenId());
        assertEquals(ticket3.getBookingId(), ticketRepo.read(2).getBookingId());
        assertEquals(ticket3.getSeatNr(), ticketRepo.read(2).getSeatNr());
        assertEquals(ticket3.getPrice(), ticketRepo.read(2).getPrice());
        assertEquals(ticket2.getScreenId(), ticketRepo.read(2).getScreenId());
        assertEquals(ticket2.getBookingId(), ticketRepo.read(2).getBookingId());
        assertNotSame(ticket2.getSeatNr(), ticketRepo.read(2).getSeatNr());
        assertNotSame(ticket2.getPrice(), ticketRepo.read(2).getPrice());
    }

    @Test
    void testReadAllTicket() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(2, ticketRepo.getAll().size());
        assertEquals(ticket1.getId(), ticketRepo.getAll().get(1).getId());
        assertEquals(ticket1.getScreenId(), ticketRepo.getAll().get(1).getScreenId());
        assertEquals(ticket1.getBookingId(), ticketRepo.getAll().get(1).getBookingId());
        assertEquals(ticket1.getSeatNr(), ticketRepo.getAll().get(1).getSeatNr());
        assertEquals(ticket1.getPrice(), ticketRepo.getAll().get(1).getPrice());
        assertEquals(ticket2.getId(), ticketRepo.getAll().get(2).getId());
        assertEquals(ticket2.getScreenId(), ticketRepo.getAll().get(2).getScreenId());
        assertEquals(ticket2.getBookingId(), ticketRepo.getAll().get(2).getBookingId());
        assertEquals(ticket2.getSeatNr(), ticketRepo.getAll().get(2).getSeatNr());
        assertEquals(ticket2.getPrice(), ticketRepo.getAll().get(2).getPrice());
    }

    @Test
    void testGenerateIdBasicMembership() {
        basicMembershipRepo.add(basicMembership);
        assertEquals(2, basicMembershipRepo.generateNewId());
    }

    @Test
    void testAddBasicMembership() {
        basicMembershipRepo.add(basicMembership);
        assertEquals(1, basicMembershipRepo.getAll().size());
        assertEquals(basicMembership.getId(), basicMembershipRepo.read(1).getId());
        assertEquals(basicMembership.getCustomerId(), basicMembershipRepo.read(1).getCustomerId());
        assertEquals(basicMembership.getStartDate(), basicMembershipRepo.read(1).getStartDate());
        assertEquals(basicMembership.getEndDate(), basicMembershipRepo.read(1).getEndDate());
    }

    @Test
    void testReadBasicMembership() {
        basicMembershipRepo.add(basicMembership);
        assertEquals(basicMembership.getId(), basicMembershipRepo.read(1).getId());
        assertEquals(basicMembership.getCustomerId(), basicMembershipRepo.read(1).getCustomerId());
        assertEquals(basicMembership.getStartDate(), basicMembershipRepo.read(1).getStartDate());
        assertEquals(basicMembership.getEndDate(), basicMembershipRepo.read(1).getEndDate());
    }

    @Test
    void testDeleteBasicMembership() {
        basicMembershipRepo.add(basicMembership);
        basicMembershipRepo.delete(1);
        assertEquals(0, basicMembershipRepo.getAll().size());
        assertNull(basicMembershipRepo.read(1));
    }

    @Test
    void testUpdateBasicMembership() {
        basicMembershipRepo.add(basicMembership);
        BasicMembership basicMembership2 = new BasicMembership(1, 1, LocalDate.now(), LocalDate.now().plusDays(15));
        basicMembershipRepo.update(basicMembership2);
        assertEquals(basicMembership2.getCustomerId(), basicMembershipRepo.read(1).getCustomerId());
        assertEquals(basicMembership2.getStartDate(), basicMembershipRepo.read(1).getStartDate());
        assertEquals(basicMembership2.getEndDate(), basicMembershipRepo.read(1).getEndDate());
        assertEquals(basicMembership.getCustomerId(), basicMembershipRepo.read(1).getCustomerId());
        assertEquals(basicMembership.getStartDate(), basicMembershipRepo.read(1).getStartDate());
        assertNotSame(basicMembership.getEndDate(), basicMembershipRepo.read(1).getEndDate());
    }

    @Test
    void testReadAllBasicMembership() {
        basicMembershipRepo.add(basicMembership);
        assertEquals(1, basicMembershipRepo.getAll().size());
        assertEquals(basicMembership.getId(), basicMembershipRepo.getAll().get(1).getId());
        assertEquals(basicMembership.getCustomerId(), basicMembershipRepo.getAll().get(1).getCustomerId());
        assertEquals(basicMembership.getStartDate(), basicMembershipRepo.getAll().get(1).getStartDate());
        assertEquals(basicMembership.getEndDate(), basicMembershipRepo.getAll().get(1).getEndDate());
    }

    @Test
    void testGenerateIdPremiumMembership() {
        premiumMembershipRepo.add(premiumMembership);
        assertEquals(2, premiumMembershipRepo.generateNewId());
    }

    @Test
    void testAddPremiumMembership() {
        premiumMembershipRepo.add(premiumMembership);
        assertEquals(1, premiumMembershipRepo.getAll().size());
        assertEquals(premiumMembership.getId(), premiumMembershipRepo.read(1).getId());
        assertEquals(premiumMembership.getCustomerId(), premiumMembershipRepo.read(1).getCustomerId());
        assertEquals(premiumMembership.getStartDate(), premiumMembershipRepo.read(1).getStartDate());
        assertEquals(premiumMembership.getEndDate(), premiumMembershipRepo.read(1).getEndDate());
    }

    @Test
    void testReadPremiumMembership() {
        premiumMembershipRepo.add(premiumMembership);
        assertEquals(premiumMembership.getId(), premiumMembershipRepo.read(1).getId());
        assertEquals(premiumMembership.getCustomerId(), premiumMembershipRepo.read(1).getCustomerId());
        assertEquals(premiumMembership.getStartDate(), premiumMembershipRepo.read(1).getStartDate());
        assertEquals(premiumMembership.getEndDate(), premiumMembershipRepo.read(1).getEndDate());

    }

    @Test
    void testDeletePremiumMembership() {
        premiumMembershipRepo.add(premiumMembership);
        premiumMembershipRepo.delete(1);
        assertEquals(0, premiumMembershipRepo.getAll().size());
        assertNull(premiumMembershipRepo.read(1));
    }

    @Test
    void testUpdatePremiumMembership() {
        premiumMembershipRepo.add(premiumMembership);
        PremiumMembership premiumMembership2 = new PremiumMembership(1, 2, LocalDate.now(), LocalDate.now().plusDays(15));
        premiumMembershipRepo.update(premiumMembership2);
        assertEquals(premiumMembership2.getCustomerId(), premiumMembershipRepo.read(1).getCustomerId());
        assertEquals(premiumMembership2.getStartDate(), premiumMembershipRepo.read(1).getStartDate());
        assertEquals(premiumMembership2.getEndDate(), premiumMembershipRepo.read(1).getEndDate());
        assertEquals(premiumMembership.getCustomerId(), premiumMembershipRepo.read(1).getCustomerId());
        assertEquals(premiumMembership.getStartDate(), premiumMembershipRepo.read(1).getStartDate());
        assertNotSame(premiumMembership.getEndDate(), premiumMembershipRepo.read(1).getEndDate());
    }

    @Test
    void testReadAllPremiumMembership() {
        premiumMembershipRepo.add(premiumMembership);
        assertEquals(1, premiumMembershipRepo.getAll().size());
        assertEquals(premiumMembership.getId(), premiumMembershipRepo.getAll().get(1).getId());
        assertEquals(premiumMembership.getCustomerId(), premiumMembershipRepo.getAll().get(1).getCustomerId());
        assertEquals(premiumMembership.getStartDate(), premiumMembershipRepo.getAll().get(1).getStartDate());
        assertEquals(premiumMembership.getEndDate(), premiumMembershipRepo.getAll().get(1).getEndDate());
    }
}