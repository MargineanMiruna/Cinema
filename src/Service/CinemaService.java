package Service;

import Domain.*;
import Repo.InMemoryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
    InMemoryRepository<Ticket> ticketRepo = new InMemoryRepository<Ticket>();
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

    public Integer findMovieIdByTitle(String title) {
        for (Map.Entry<Integer, Movie> entry : movieRepo.getAll().entrySet()) {
            if (entry.getValue().getTitle().equalsIgnoreCase(title)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Movie getMovie(int id) {
        return movieRepo.read(id);
    }

    public void updateMovie(int id, String title, boolean pg, String genre, LocalDate releaseDate) {
        Movie movie = new Movie(title, pg, genre, releaseDate);
        movieRepo.update(id, movie);
    }
    public void deleteMovie(int id) {
       movieRepo.delete(id);
    }

    public void addShowtime(int screenId, int movieId, LocalDate date, int startTime, double duration) {
        Showtime showtime = new Showtime(screenId, movieId, date,startTime, duration);
        showtimeRepo.add(showtime);
    }

    public void deleteShowtime(int id){
        showtimeRepo.delete(id);
    }

    public Showtime getShowtime(int id) {
        return showtimeRepo.read(id);
    }

    public void updateShowtime(int id, int screenId, int movieId, LocalDate date, int startTime, double duration) {
        Showtime showtime = new Showtime(screenId, movieId, date, startTime, duration);
        showtimeRepo.update(id, showtime);
    }

    public void addScreen(int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        Screen screen = new Screen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
        screenRepo.add(screen);
    }
    public void deleteScreen(int id){
        screenRepo.delete(id);
    }

    public Screen getScreen(int id) {
        return screenRepo.read(id);
    }

    public void updateScreen(int id, int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        Screen screen = new Screen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
        screenRepo.update(id, screen);
    }

    public void addSeat(int seatNr,  SeatType type) {
        Seat seat = new Seat(seatNr,type);
        seatRepo.add(seat);
    }

    public Seat getSeat(int id) {
        return seatRepo.read(id);
    }

    public void updateSeat(int id, int seatNr,  SeatType type) {
        Seat seat = new Seat(seatNr, type);
        seatRepo.update(id, seat);
    }

    public void addBooking(int customerId, int showtimeId, LocalDate date, int nrOfCustomers) {
        Booking booking = new Booking(customerId, showtimeId, date, nrOfCustomers);
        bookingRepo.add(booking);
    }

    public void updateBooking(int id, int customerId, int showtimeId, LocalDate date, int nrOfCustomers) {
        Booking booking = new Booking(customerId, showtimeId, date, nrOfCustomers);
        bookingRepo.update(id, booking);
    }

    public void addTicket(int bookingId, int seatId, double price) {
        Ticket ticket = new Ticket(bookingId, seatId, price);
        ticketRepo.add(ticket);
    }

    public void updateTicket(int id, int bookingId, int seatId, double price) {
        Ticket ticket = new Ticket(bookingId, seatId, price);
        ticketRepo.update(id, ticket);
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

    public Map<Integer, Showtime> displayShowtimes(Customer customer) {
        Map<Integer, Showtime> unfilteredShowtimes = showtimeRepo.getAll();

        if(customer.getUnderaged()) {
            Map<Integer, Showtime> filteredShowtimes = new HashMap<>();
            for(Map.Entry<Integer, Showtime> entry : unfilteredShowtimes.entrySet()) {
                if(!movieRepo.read(entry.getValue().getMovieId()).getPg())
                    filteredShowtimes.put(entry.getKey(), entry.getValue());
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

    public int getIdOfCustomer(Customer customer){
        Map<Integer, Customer> customers = customerRepo.getAll();

        for(Map.Entry<Integer, Customer> entry : customers.entrySet()){
            if(entry.getValue().equals(customer))
                return entry.getKey();
        }

        return 0;
    }

    public int getIdOfBooking(Booking booking) {
        Map<Integer, Booking> bookings = bookingRepo.getAll();

        for(Map.Entry<Integer, Booking> entry : bookings.entrySet()){
            if(entry.getValue().equals(bookings))
                return entry.getKey();
        }

        return 0;
    }

    public List<Ticket> bookingTickets(int bookingId) {
        Map<Integer, Ticket> tickets = ticketRepo.getAll();
        List<Ticket> bookingTickets = new ArrayList<>();

        for(Map.Entry<Integer, Ticket> entry : tickets.entrySet()){
            if(entry.getValue().getBookingId() == bookingId)
                bookingTickets.add(entry.getValue());
        }

        return bookingTickets;
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
