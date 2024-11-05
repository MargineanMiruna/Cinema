package Service;

import Domain.*;
import Repo.InMemoryRepository;

import java.time.LocalDate;
import java.util.List;

public class CinemaService {
    InMemoryRepository<Customer> customerRepo;
    InMemoryRepository<Staff> staffRepo;
    InMemoryRepository<Movie> movieRepo;
    InMemoryRepository<Showtime> showtimeRepo;
    InMemoryRepository<Screen> screenRepo;
    InMemoryRepository<Seat> seatRepo;
    InMemoryRepository<Booking> bookingRepo;
    InMemoryRepository<BasicMembership> basicMembershipRepo;
    InMemoryRepository<PremiumMembership> premiumMembershipRepo;

    public CinemaService() {}

    void addCustomer(String firstName, String lastName, boolean underage) {
        Customer customer = new Customer(firstName, lastName, underage);
        customerRepo.add(customer);
    }

    void updateCustomer(int id, String firstName, String lastName, boolean underage) {
        Customer customer = new Customer(firstName, lastName, underage);
        customerRepo.update(id, customer);
    }

    void addStaff(String firstName, String lastName) {
        Staff staff = new Staff(firstName, lastName);
        staffRepo.add(staff);
    }

    void updateStaff(int id, String firstName, String lastName) {
        Staff staff = new Staff(firstName, lastName);
        staffRepo.update(id, staff);
    }

    void addMovie(String title, boolean pg, String genre, LocalDate releaseDate) {
        Movie movie = new Movie(title, pg, genre, releaseDate);
        movieRepo.add(movie);
    }

    void updateMovie(int id, String title, boolean pg, String genre, LocalDate releaseDate) {
        Movie movie = new Movie(title, pg, genre, releaseDate);
        movieRepo.update(id, movie);
    }

    void addShowtime(int screenId, int movieId, int startTime, double duration) {
        Showtime showtime = new Showtime(screenId, movieId, startTime, duration);
        showtimeRepo.add(showtime);
    }

    void updateShowtime(int id, int screenId, int movieId, int startTime, double duration) {
        Showtime showtime = new Showtime(screenId, movieId, startTime, duration);
        showtimeRepo.update(id, showtime);
    }

    void addScreen(int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        Screen screen = new Screen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
        screenRepo.add(screen);
    }

    void updateScreen(int id, int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        Screen screen = new Screen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
        screenRepo.update(id, screen);
    }

    void addSeat(int seatNr, boolean booked, SeatType type) {
        Seat seat = new Seat(seatNr,booked,type);
        seatRepo.add(seat);
    }

    void updateSeat(int id, int seatNr, boolean booked, SeatType type) {
        Seat seat = new Seat(seatNr,booked,type);
        seatRepo.update(id, seat);
    }

    void addBooking(int customerId, int bookingId, LocalDate date, int nrOfCustomers, List<Seat> chosenSeats) {
        Booking booking = new Booking(customerId, bookingId, date, nrOfCustomers, chosenSeats);
        bookingRepo.add(booking);
    }

    void updateBooking(int id, int customerId, int bookingId, LocalDate date, int nrOfCustomers, List<Seat> chosenSeats) {
        Booking booking = new Booking(customerId, bookingId, date, nrOfCustomers, chosenSeats);
        bookingRepo.update(id, booking);
    }

    void addBasicMembership(Customer customer, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        BasicMembership basicMembership = new BasicMembership(customer, startDate,endDate, bookings);
        basicMembershipRepo.add(basicMembership);
    }

    void updateBasicMembership(int id, Customer customer, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        BasicMembership basicMembership = new BasicMembership(customer, startDate,endDate, bookings);
        basicMembershipRepo.update(id, basicMembership);
    }

    void addPremiumMembership(Customer customer, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        PremiumMembership premiumMembership = new PremiumMembership(customer, startDate,endDate, bookings);
        premiumMembershipRepo.add(premiumMembership);
    }

    void updatePremiumMembership(int id, Customer customer, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        PremiumMembership premiumMembership = new PremiumMembership(customer, startDate,endDate, bookings);
        premiumMembershipRepo.update(id, premiumMembership);
    }
}
