package Service;

import Domain.*;
import Repo.InMemoryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CinemaService {
    InMemoryRepository<Customer> customerRepo = new InMemoryRepository<Customer>();
    InMemoryRepository<Staff> staffRepo = new InMemoryRepository<Staff>();
    InMemoryRepository<Movie> movieRepo = new InMemoryRepository<Movie>();
    InMemoryRepository<Showtime> showtimeRepo = new InMemoryRepository<Showtime>();
    InMemoryRepository<Screen> screenRepo = new InMemoryRepository<Screen>();
    InMemoryRepository<Seat> seatRepo = new InMemoryRepository<Seat>();
    InMemoryRepository<Booking> bookingRepo = new InMemoryRepository<Booking>();
    InMemoryRepository<BasicMembership> basicMembershipRepo = new InMemoryRepository<BasicMembership>();
    InMemoryRepository<PremiumMembership> premiumMembershipRepo = new InMemoryRepository<PremiumMembership>();

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

    public void addSeat(int seatNr,  SeatType type) {
        Seat seat = new Seat(seatNr,type);
        seatRepo.add(seat);
    }

    public void updateSeat(int id, int seatNr,  SeatType type) {
        Seat seat = new Seat(seatNr, type);
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

    public List<Showtime> displayShowtimes(Customer customer) {
        Map<Integer, Showtime> showtimes = showtimeRepo.getAll();
        List<Showtime> unfilteredShowtimes = new ArrayList<Showtime>(showtimeRepo.getAll().size());

        if(customer.getUnderaged()) {
            List<Showtime> filteredShowtimes = new ArrayList<>();
            for(Map.Entry<Integer, Showtime> entry : showtimes.entrySet()) {
                if(!movieRepo.read(entry.getValue().getMovieId()).getPg())
                    filteredShowtimes.add(entry.getValue());
            }
            return filteredShowtimes;
        }

        return unfilteredShowtimes;
    }

    public Customer findCustomerByEmail(String email){
        Map<Integer, Customer> customers = customerRepo.getAll();

        for(Map.Entry<Integer, Customer> entry : customers.entrySet()){
            if(entry.getValue().getEmail().equals(email))
                return entry.getValue();
        }

        return null;
    }

    public Staff findStaffByEmail(String email){
        Map<Integer, Staff> staffs = staffRepo.getAll();

        for(Map.Entry<Integer, Staff> entry : staffs.entrySet()){
            if(entry.getValue().getEmail().equals(email))
                return entry.getValue();
        }

        return null;
    }

}
