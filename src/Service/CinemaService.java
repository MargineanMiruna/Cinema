package Service;

import Domain.*;
import Repo.InMemoryRepository;

import java.lang.reflect.Member;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public Customer getCustomer(int id) {
        return customerRepo.read(id);
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

    public Integer findMovieIdByTitle(String title) {
        for (Map.Entry<Integer, Movie> entry : movieRepo.getAll().entrySet()) {
            if (entry.getValue().getTitle().equalsIgnoreCase(title)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void addShowtime(int screenId, int movieId, LocalDate date, LocalTime startTime, int duration) {
        Screen screen = getScreen(screenId);
        List<Seat> seatsCopy = new ArrayList<>();
        for (Seat seat : screen.getSeats()) {
            seatsCopy.add(new Seat(seat.getSeatNr(), seat.getType()));
        }
        Showtime showtime = new Showtime(screenId, movieId, date,startTime, duration, seatsCopy);
        showtimeRepo.add(showtime);
    }

    public Showtime getShowtime(int id) {
        return showtimeRepo.read(id);
    }

    public void updateShowtime(int id, int screenId, int movieId, LocalDate date, LocalTime startTime, int duration, List<Seat> seats) {
        Showtime showtime = new Showtime(screenId, movieId, date, startTime, duration, seats);
        showtimeRepo.update(id, showtime);
    }

    public void deleteShowtime(int id) {
        showtimeRepo.delete(id);
    }

    public void addScreen(int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        Screen screen = new Screen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
        screenRepo.add(screen);

        for (int i = 1; i <= nrStandardSeats; i++) {
            seatRepo.add(new Seat(i, SeatType.standard));
        }
        for (int i = 1 + nrStandardSeats; i <= nrVipSeats + nrStandardSeats; i++) {
            seatRepo.add(new Seat(i, SeatType.vip));
        }
        for(int i = 1 + nrStandardSeats + nrVipSeats; i <=nrPremiumSeats + nrVipSeats + nrStandardSeats; i++) {
            seatRepo.add(new Seat(i, SeatType.premium));
        }
    }

    public Screen getScreen(int id) {
        return screenRepo.read(id);
    }

    public void updateScreen(int id, int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        Screen screen = new Screen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
        screenRepo.update(id, screen);
    }

    public void deleteScreen(int id){
        screenRepo.delete(id);
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

    public Seat findSeatBySeatNr(int screenId, int seatNr) {
        Screen screen = screenRepo.read(screenId);

        for(Seat seat : screen.getSeats()) {
            if(seat.getSeatNr() == seatNr) {
                return seat;
            }
        }

        return null;
    }

    public int addBooking(int customerId, int showtimeId, LocalDate date, int nrOfCustomers) {
        Booking booking = new Booking(customerId, showtimeId, date, nrOfCustomers);
        return bookingRepo.add(booking);
    }

    public Booking getBooking(int id) {
        return bookingRepo.read(id);
    }

    public void updateBooking(int id, int customerId, int showtimeId, LocalDate date, int nrOfCustomers) {
        Booking booking = new Booking(customerId, showtimeId, date, nrOfCustomers);
        bookingRepo.update(id, booking);
    }

    public int addTicket(int bookingId, int screenId, int seatId, double price) {
        Ticket ticket = new Ticket(bookingId, screenId, seatId, price);
        return ticketRepo.add(ticket);
    }

    public Ticket getTicket(int id) {
        return ticketRepo.read(id);
    }

    public void updateTicket(int id, int bookingId, int screenId, int seatId, double price) {
        Ticket ticket = new Ticket(bookingId, screenId, seatId, price);
        ticketRepo.update(id, ticket);
    }

    public BasicMembership addBasicMembership(int customerId, LocalDate startDate, LocalDate endDate) {
        BasicMembership basicMembership = new BasicMembership(customerId, startDate,endDate);
        Customer customer = getCustomer(customerId);
        customer.setMembershipId(basicMembershipRepo.add(basicMembership));
        return basicMembership;

    }

    public BasicMembership getBasicMembership(int id) {
        return basicMembershipRepo.read(id);
    }

    public void updateBasicMembership(int id, int customerId, LocalDate startDate, LocalDate endDate) {
        BasicMembership basicMembership = new BasicMembership(customerId, startDate,endDate);
        basicMembershipRepo.update(id, basicMembership);
    }

    public void deleteBasicMembership(int id) {
        basicMembershipRepo.delete(id);
    }

    public PremiumMembership addPremiumMembership(int customerId, LocalDate startDate, LocalDate endDate) {
        PremiumMembership premiumMembership = new PremiumMembership(customerId, startDate,endDate);
        Customer customer = getCustomer(customerId);
        customer.setMembershipId(premiumMembershipRepo.add(premiumMembership));
        return premiumMembership;
    }

    public PremiumMembership getPremiumMembership(int id) {
        return premiumMembershipRepo.read(id);
    }

    public void updatePremiumMembership(int id, int customerId, LocalDate startDate, LocalDate endDate) {
        PremiumMembership premiumMembership = new PremiumMembership(customerId, startDate,endDate);
        premiumMembershipRepo.update(id, premiumMembership);
    }

    public void deletePremiumMembership(int id) {
        premiumMembershipRepo.delete(id);
    }

    /**
     *
     * @param customerId
     * @return nr which coresponds to thr type of membership the customer with customerId has
     * 1 = basic membership
     * 2 = premium membership
     */
    public int getMembershipType(int customerId) {
        Map<Integer, BasicMembership> basicMemberships = basicMembershipRepo.getAll();
        Map<Integer, PremiumMembership> premiumMemberships = premiumMembershipRepo.getAll();

        for(BasicMembership basicMembership : basicMemberships.values()) {
            if(basicMembership.getCustomerId() == customerId) {
                return 1;
            }
        }

        for(PremiumMembership premiumMembership : premiumMemberships.values()) {
            if(premiumMembership.getCustomerId() == customerId) {
                return 2;
            }
        }

        return 0;
    }

    /**
     *
     * @param customer
     * @return a list of showtimes the customer can watch depending if he is underaged or not
     */
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

    public Map<Integer, Movie> displayMoviesStaff(){
        Map<Integer, Movie> allMovies = movieRepo.getAll();
        return allMovies;
    }
    public Map<Integer, Showtime> displayShowtimesStaff(){
        Map<Integer, Showtime> allshowtimes = showtimeRepo.getAll();
        return allshowtimes;
    }
    public Map<Integer, Screen> displayScreensStaff(){
        Map<Integer, Screen> allscreens= screenRepo.getAll();
        return allscreens;
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

    public Staff findStaffByEmail(String email) {
        Map<Integer, Staff> staffs = staffRepo.getAll();

        for(Map.Entry<Integer, Staff> entry : staffs.entrySet()){
            if(entry.getValue().getEmail().equals(email))
                return entry.getValue();
        }

        return null;
    }

    /**
     *
     * @param movieId
     * if we delete a movie we must delete all showtimes that contain this movie
     */
    public void deleteShowtimesByMovieId(int movieId) {
        Map<Integer,Showtime> allshowtimes = showtimeRepo.getAll();

        for(Map.Entry<Integer,Showtime> entry : allshowtimes.entrySet()){
            if(entry.getValue().getMovieId() == movieId)
                showtimeRepo.delete(entry.getKey());
        }
    }

    public double calculateDiscountedPrice(double price, Membership membership) {
        return membership.offerDiscount(price);
    }

    /**
     * after a month ( 30 days the membership terminates itself ( it expires)
     */

    public void terminateMemberships() {
        Map<Integer, BasicMembership> basicMembershipMap = basicMembershipRepo.getAll();

        for(Map.Entry<Integer, BasicMembership> entry : basicMembershipMap.entrySet()){
            if(entry.getValue().getEndDate() == LocalDate.now()) {
                getCustomer(entry.getValue().getCustomerId()).setMembershipId(0);
                deleteBasicMembership(entry.getKey());
            }
        }

        Map<Integer, PremiumMembership> premiumMembershipMap = premiumMembershipRepo.getAll();

        for(Map.Entry<Integer, PremiumMembership> entry : premiumMembershipMap.entrySet()){
            if(entry.getValue().getEndDate() == LocalDate.now()) {
                getCustomer(entry.getValue().getCustomerId()).setMembershipId(0);
                deleteBasicMembership(entry.getKey());
            }
        }
    }

}