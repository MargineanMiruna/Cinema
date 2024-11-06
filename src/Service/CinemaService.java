package Service;

import Domain.*;
import Repo.InMemoryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public void addCustomer(String firstName, String lastName, String email, boolean underage) {
        Customer customer = new Customer(firstName, lastName, email, underage);
        customerRepo.add(customer);
    }

    public void updateCustomer(int id, String firstName, String lastName,String email, boolean underage) {
        Customer customer = new Customer(firstName, lastName, email, underage);
        customerRepo.update(id, customer);
    }

    public void addStaff(String firstName, String lastName, String email) {
        Staff staff = new Staff(firstName, lastName, email);
        staffRepo.add(staff);
    }

    public void updateStaff(int id, String firstName, String lastName, String email) {
        Staff staff = new Staff(firstName, lastName, email);
        staffRepo.update(id, staff);
    }

    public void addMovie(String title, boolean pg, String genre, LocalDate releaseDate) {
        Movie movie = new Movie(title, pg, genre, releaseDate);
        movieRepo.add(movie);
    }

    public void updateMovie(int id, String title, boolean pg, String genre, LocalDate releaseDate) {
        Movie movie = new Movie(title, pg, genre, releaseDate);
        movieRepo.update(id, movie);
    }

    public void addShowtime(int screenId, int movieId, int startTime, double duration) {
        Showtime showtime = new Showtime(screenId, movieId, startTime, duration);
        showtimeRepo.add(showtime);
    }

    public void updateShowtime(int id, int screenId, int movieId, int startTime, double duration) {
        Showtime showtime = new Showtime(screenId, movieId, startTime, duration);
        showtimeRepo.update(id, showtime);
    }

    public void addScreen(int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        Screen screen = new Screen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
        screenRepo.add(screen);
    }

    public void updateScreen(int id, int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        Screen screen = new Screen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
        screenRepo.update(id, screen);
    }

    public void addSeat(int seatNr, boolean booked, SeatType type) {
        Seat seat = new Seat(seatNr,booked,type);
        seatRepo.add(seat);
    }

    public void updateSeat(int id, int seatNr, boolean booked, SeatType type) {
        Seat seat = new Seat(seatNr,booked,type);
        seatRepo.update(id, seat);
    }

    public void addBooking(int customerId, int bookingId, LocalDate date, int nrOfCustomers, List<Seat> chosenSeats) {
        Booking booking = new Booking(customerId, bookingId, date, nrOfCustomers, chosenSeats);
        bookingRepo.add(booking);
    }

    public void updateBooking(int id, int customerId, int bookingId, LocalDate date, int nrOfCustomers, List<Seat> chosenSeats) {
        Booking booking = new Booking(customerId, bookingId, date, nrOfCustomers, chosenSeats);
        bookingRepo.update(id, booking);
    }

    public void addBasicMembership(Customer customer, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        BasicMembership basicMembership = new BasicMembership(customer, startDate,endDate, bookings);
        basicMembershipRepo.add(basicMembership);
    }

    public void updateBasicMembership(int id, Customer customer, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        BasicMembership basicMembership = new BasicMembership(customer, startDate,endDate, bookings);
        basicMembershipRepo.update(id, basicMembership);
    }

    public void addPremiumMembership(Customer customer, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        PremiumMembership premiumMembership = new PremiumMembership(customer, startDate,endDate, bookings);
        premiumMembershipRepo.add(premiumMembership);
    }

    public void updatePremiumMembership(int id, Customer customer, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        PremiumMembership premiumMembership = new PremiumMembership(customer, startDate,endDate, bookings);
        premiumMembershipRepo.update(id, premiumMembership);
    }

    public List displayShowtimes(Customer customer) {
        if (customer.getUnderaged()) {
            List<Showtime> showtimes = showtimeRepo.getAll();
            List<Showtime> filteredShowtimes = new ArrayList<>();
            for(Showtime showtime : showtimes)
            {
                if(!movieRepo.read(showtime.getMovieId()).getPg())
                    filteredShowtimes.add(showtime);
            }
            return filteredShowtimes;
        }
        return showtimeRepo.getAll();
    }

    public Customer findCustomerByEmail(String email){
        List<Customer> customers = customerRepo.getAll();
        for(Customer customer : customers){
            if ( customer.getEmail().equals(email))
                return customer;
        }
        return null;
    }

}
