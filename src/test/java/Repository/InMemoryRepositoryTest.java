package Repository;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {
    InMemoryRepository<Customer> customerRepo = new InMemoryRepository<>();
    InMemoryRepository<Staff> staffRepo = new InMemoryRepository<>();
    InMemoryRepository<Booking> bookingRepo = new InMemoryRepository<>();
    InMemoryRepository<Showtime> showtimeRepo = new InMemoryRepository<>();
    InMemoryRepository<Movie> movieRepo = new InMemoryRepository<>();
    InMemoryRepository<Screen> screenRepo = new InMemoryRepository<>();
    InMemoryRepository<Seat> seatRepo = new InMemoryRepository<>();
    InMemoryRepository<Ticket> ticketRepo = new InMemoryRepository<>();
    InMemoryRepository<BasicMembership> basicMembershipRepo = new InMemoryRepository<>();
    InMemoryRepository<PremiumMembership> premiumMembershipRepo = new InMemoryRepository<>();

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
        assertEquals(1, customerRepo.generateNewId());
        customerRepo.add(customer1);
        assertEquals(2, customerRepo.generateNewId());
    }

    @Test
    void testAddCustomer() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        assertEquals(2, customerRepo.getAll().size());
        assertEquals(customer1, customerRepo.read(1));
        assertEquals(customer2, customerRepo.read(2));
    }

    @Test
    void testReadCustomer() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        assertEquals(customer1, customerRepo.read(1));
        assertEquals(customer2, customerRepo.read(2));
    }

    @Test
    void testDeleteCustomer() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        customerRepo.delete(1);
        assertEquals(1, customerRepo.getAll().size());
        assertNotSame(customerRepo.read(1), customer1);
    }

    @Test
    void testUpdateCustomer() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        Customer customer3 = new Customer(2, "John", "Doe", "john.doe@gamil.com", false, 2);
        customerRepo.update(customer3);
        assertEquals(customer3, customerRepo.read(2));
        assertNotSame(customer2, customerRepo.read(2));
    }

    @Test
    void testReadAllCustomer() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        assertEquals(2, customerRepo.getAll().size());
        assertEquals(customer1, customerRepo.getAll().get(1));
        assertEquals(customer2, customerRepo.getAll().get(2));
    }

    @Test
    void testGenerateIdStaff() {
        assertEquals(1, staffRepo.generateNewId());
        staffRepo.add(staff);
        assertEquals(2, staffRepo.generateNewId());
    }

    @Test
    void testAddStaff() {
        staffRepo.add(staff);
        assertEquals(1, staffRepo.getAll().size());
        assertEquals(staff, staffRepo.read(1));
    }

    @Test
    void testReadStaff() {
        staffRepo.add(staff);
        assertEquals(staff, staffRepo.read(1));
    }

    @Test
    void testDeleteStaff() {
        staffRepo.add(staff);
        staffRepo.delete(1);
        assertEquals(0, staffRepo.getAll().size());
        assertNotSame(staffRepo.read(1), staff);
    }

    @Test
    void testUpdateStaff() {
        staffRepo.add(staff);
        Staff staff2 = new Staff(1, "Jane", "Doe", "john.doe@gamil.com");
        staffRepo.update(staff2);
        assertEquals(staff2, staffRepo.read(1));
        assertNotSame(staff, staffRepo.read(1));
    }

    @Test
    void testReadAllStaff() {
        staffRepo.add(staff);
        assertEquals(1, staffRepo.getAll().size());
        assertEquals(staff, staffRepo.getAll().get(1));
    }

    @Test
    void testGenerateIdMovie() {
        assertEquals(1, movieRepo.generateNewId());
        movieRepo.add(movie);
        assertEquals(2, movieRepo.generateNewId());
    }

    @Test
    void testAddMovie() {
        movieRepo.add(movie);
        assertEquals(1, movieRepo.getAll().size());
        assertEquals(movie, movieRepo.read(1));
    }

    @Test
    void testReadMovie() {
        movieRepo.add(movie);
        assertEquals(movie, movieRepo.read(1));
    }

    @Test
    void testDeleteMovie() {
        movieRepo.add(movie);
        movieRepo.delete(1);
        assertEquals(0, movieRepo.getAll().size());
        assertNotSame(movieRepo.read(1), movie);
    }

    @Test
    void testUpdateMovie() {
        movieRepo.add(movie);
        Movie movie2 = new Movie(1, "Titanic", true, "drama", LocalDate.of(2003, 6,17));
        movieRepo.update(movie2);
        assertEquals(movie2, movieRepo.read(1));
        assertNotSame(movie, movieRepo.read(1));
    }

    @Test
    void testReadAllMovie() {
        movieRepo.add(movie);
        assertEquals(1,movieRepo.getAll().size());
        assertEquals(movie, movieRepo.getAll().get(1));
    }

    @Test
    void testGenerateIdScreen() {
        assertEquals(1, screenRepo.generateNewId());
        screenRepo.add(screen);
        assertEquals(2, screenRepo.generateNewId());
    }

    @Test
    void testAddScreen() {
        screenRepo.add(screen);
        assertEquals(1, screenRepo.getAll().size());
        assertEquals(screen, screenRepo.read(1));
    }

    @Test
    void testReadScreen() {
        screenRepo.add(screen);
        assertEquals(screen, screenRepo.read(1));
    }

    @Test
    void testDeleteScreen() {
        screenRepo.add(screen);
        screenRepo.delete(1);
        assertEquals(0, screenRepo.getAll().size());
        assertNotSame(screenRepo.read(1), screen);
    }

    @Test
    void testUpdateScreen() {
        screenRepo.add(screen);
        Screen screen2 = new Screen(1, 1, 0, 1, Arrays.asList(seat1,seat3));
        screenRepo.update(screen2);
        assertEquals(screen2, screenRepo.read(1));
        assertNotSame(screen, screenRepo.read(1));
    }

    @Test
    void testReadAllScreen() {
        screenRepo.add(screen);
        assertEquals(1,screenRepo.getAll().size());
        assertEquals(screen, screenRepo.getAll().get(1));
    }

    @Test
    void testGenerateIdSeat() {
        assertEquals(1, seatRepo.generateNewId());
        seatRepo.add(seat1);
        assertEquals(2, seatRepo.generateNewId());
    }

    @Test
    void testAddSeat() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        assertEquals(2, seatRepo.getAll().size());
        assertEquals(seat1, seatRepo.read(1));
        assertEquals(seat2, seatRepo.read(2));
    }

    @Test
    void testReadSeat() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        assertEquals(seat1, seatRepo.read(1));
        assertEquals(seat2, seatRepo.read(2));
    }

    @Test
    void testDeleteSeat() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.delete(1);
        assertEquals(1, seatRepo.getAll().size());
        assertNotSame(seatRepo.read(1), seat1);
    }

    @Test
    void testUpdateSeat() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        Seat seat4 = new Seat(2, 2, SeatType.premium);
        seatRepo.update(seat4);
        assertEquals(seat4, seatRepo.read(2));
        assertNotSame(seat2, seatRepo.read(2));
    }

    @Test
    void testReadAllSeat() {
        seatRepo.add(seat1);
        seatRepo.add(seat2);
        seatRepo.add(seat3);
        assertEquals(3, seatRepo.getAll().size());
        assertEquals(seat1, seatRepo.getAll().get(1));
        assertEquals(seat2, seatRepo.getAll().get(2));
        assertEquals(seat3, seatRepo.getAll().get(3));
    }

    @Test
    void testGenerateIdShowtime() {
        assertEquals(1, showtimeRepo.generateNewId());
        showtimeRepo.add(showtime);
        assertEquals(2, showtimeRepo.generateNewId());
    }

    @Test
    void testAddShowtime() {
        showtimeRepo.add(showtime);
        assertEquals(1, showtimeRepo.getAll().size());
        assertEquals(showtime, showtimeRepo.read(1));
    }

    @Test
    void testReadShowtime() {
        showtimeRepo.add(showtime);
        assertEquals(showtime, showtimeRepo.read(1));
    }

    @Test
    void testDeleteShowtime() {
        showtimeRepo.add(showtime);
        showtimeRepo.delete(1);
        assertEquals(0, showtimeRepo.getAll().size());
        assertNotSame(showtimeRepo.read(1), showtime);
    }

    @Test
    void testUpdateShowtime() {
        showtimeRepo.add(showtime);
        Showtime showtime2 = new Showtime(1, 1, 1, LocalDate.of(2024, 12, 26), LocalTime.of(20,0),115, Arrays.asList(seat1,seat2,seat3));
        showtimeRepo.update(showtime2);
        assertEquals(showtime2, showtimeRepo.read(1));
        assertNotSame(showtime, showtimeRepo.read(1));
    }

    @Test
    void testReadAllShowtime() {
        showtimeRepo.add(showtime);
        assertEquals(1, showtimeRepo.getAll().size());
        assertEquals(showtime, showtimeRepo.getAll().get(1));
    }

    @Test
    void testGenerateIdBooking() {
        assertEquals(1, bookingRepo.generateNewId());
        bookingRepo.add(booking);
        assertEquals(2, bookingRepo.generateNewId());
    }

    @Test
    void testAddBooking() {
        bookingRepo.add(booking);
        assertEquals(1, bookingRepo.getAll().size());
        assertEquals(booking, bookingRepo.read(1));
    }

    @Test
    void testReadBooking() {
        bookingRepo.add(booking);
        assertEquals(booking, bookingRepo.read(1));
    }

    @Test
    void testDeleteBooking() {
        bookingRepo.add(booking);
        bookingRepo.delete(1);
        assertEquals(0, bookingRepo.getAll().size());
        assertNotSame(bookingRepo.read(1), booking);
    }

    @Test
    void testUpdateBooking() {
        bookingRepo.add(booking);
        Booking booking2 = new Booking(1, 1, 1, LocalDate.of(2024, 12,26), 1, Arrays.asList(1));
        bookingRepo.update(booking2);
        assertEquals(booking2, bookingRepo.read(1));
        assertNotSame(booking, bookingRepo.read(1));
    }

    @Test
    void testReadAllBooking() {
        bookingRepo.add(booking);
        assertEquals(1, bookingRepo.getAll().size());
        assertEquals(booking, bookingRepo.getAll().get(1));
    }

    @Test
    void testGenerateIdTicket() {
        assertEquals(1, ticketRepo.generateNewId());
        ticketRepo.add(ticket1);
        assertEquals(2, ticketRepo.generateNewId());
    }

    @Test
    void testAddTicket() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(2, ticketRepo.getAll().size());
        assertEquals(ticket1, ticketRepo.read(1));
        assertEquals(ticket2, ticketRepo.read(2));
    }

    @Test
    void testReadTicket() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(ticket1, ticketRepo.read(1));
        assertEquals(ticket2, ticketRepo.read(2));
    }

    @Test
    void testDeleteTicket() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        ticketRepo.delete(2);
        assertEquals(1, ticketRepo.getAll().size());
        assertNotSame(ticketRepo.read(2), ticket2);
    }

    @Test
    void testUpdateTicket() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        Ticket ticket3 = new Ticket(2, 1, 1, 3, 50);
        ticketRepo.update(ticket3);
        assertEquals(ticket3, ticketRepo.read(2));
        assertNotSame(ticket2, ticketRepo.read(2));
    }

    @Test
    void testReadAllTicket() {
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);
        assertEquals(2, ticketRepo.getAll().size());
        assertEquals(ticket1, ticketRepo.getAll().get(1));
        assertEquals(ticket2, ticketRepo.getAll().get(2));
    }

    @Test
    void testGenerateIdBasicMembership() {
        assertEquals(1, basicMembershipRepo.generateNewId());
        basicMembershipRepo.add(basicMembership);
        assertEquals(2, basicMembershipRepo.generateNewId());
    }

    @Test
    void testAddBasicMembership() {
        basicMembershipRepo.add(basicMembership);
        assertEquals(1, basicMembershipRepo.getAll().size());
        assertEquals(basicMembership, basicMembershipRepo.read(1));
    }

    @Test
    void testReadBasicMembership() {
        basicMembershipRepo.add(basicMembership);
        assertEquals(basicMembership, basicMembershipRepo.read(1));
    }

    @Test
    void testDeleteBasicMembership() {
        basicMembershipRepo.add(basicMembership);
        basicMembershipRepo.delete(1);
        assertEquals(0, basicMembershipRepo.getAll().size());
        assertNotSame(basicMembershipRepo.read(1), basicMembership);
    }

    @Test
    void testUpdateBasicMembership() {
        basicMembershipRepo.add(basicMembership);
        BasicMembership basicMembership2 = new BasicMembership(1, 1, LocalDate.now(), LocalDate.now().plusDays(15));
        basicMembershipRepo.update(basicMembership2);
        assertEquals(basicMembership2, basicMembershipRepo.read(1));
        assertNotSame(basicMembership, basicMembershipRepo.read(1));
    }

    @Test
    void testReadAllBasicMembership() {
        basicMembershipRepo.add(basicMembership);
        assertEquals(1, basicMembershipRepo.getAll().size());
        assertEquals(basicMembership, basicMembershipRepo.getAll().get(1));
    }

    @Test
    void testGenerateIdPremiumMembership() {
        assertEquals(1, premiumMembershipRepo.generateNewId());
        premiumMembershipRepo.add(premiumMembership);
        assertEquals(2, premiumMembershipRepo.generateNewId());
    }

    @Test
    void testAddPremiumMembership() {
        premiumMembershipRepo.add(premiumMembership);
        assertEquals(1, premiumMembershipRepo.getAll().size());
        assertEquals(premiumMembership, premiumMembershipRepo.read(1));
    }

    @Test
    void testReadPremiumMembership() {
        premiumMembershipRepo.add(premiumMembership);
        assertEquals(premiumMembership, premiumMembershipRepo.read(1));
    }

    @Test
    void testDeletePremiumMembership() {
        premiumMembershipRepo.add(premiumMembership);
        premiumMembershipRepo.delete(1);
        assertEquals(0, premiumMembershipRepo.getAll().size());
        assertNotSame(premiumMembershipRepo.read(1), premiumMembership);
    }

    @Test
    void testUpdatePremiumMembership() {
        premiumMembershipRepo.add(premiumMembership);
        PremiumMembership premiumMembership2 = new PremiumMembership(1, 2, LocalDate.now(), LocalDate.now().plusDays(15));
        premiumMembershipRepo.update(premiumMembership2);
        assertEquals(premiumMembership2, premiumMembershipRepo.read(1));
        assertNotSame(premiumMembership, premiumMembershipRepo.read(1));
    }

    @Test
    void testReadAllPremiumMembership() {
        premiumMembershipRepo.add(premiumMembership);
        assertEquals(1, premiumMembershipRepo.getAll().size());
        assertEquals(premiumMembership, premiumMembershipRepo.getAll().get(1));
    }
}