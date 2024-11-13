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

/**
 * Service layer for cinema operations, handling the business logic for managing customers, staff, movies, showtimes,
 * screens, seats, bookings, tickets, and memberships.
 * This class acts as an interface between the domain entities and the repository layer.
 * Provides methods to add, get, update, and delete cinema-related entities.
 */
public class CinemaService {
    // Repositories for each domain entity
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

    /**
     * Adds a new customer.
     * @param firstName the first name of the customer
     * @param lastName  the last name of the customer
     * @param email     the customer's email
     * @param underage  indicates if the customer is underage
     */
    public void addCustomer(String firstName, String lastName, String email, boolean underage) {
        Customer customer = new Customer(firstName, lastName, email, underage);
        customerRepo.add(customer);
    }

    /**
     * Gets a customer by their ID.
     * @param id the customer ID
     * @return the Customer object
     */
    public Customer getCustomer(int id) {
        return customerRepo.read(id);
    }

    /**
     * Updates the details of an existing customer.
     * @param id        the ID of the customer to be updated
     * @param firstName the new first name of the customer
     * @param lastName  the new last name of the customer
     * @param email     the new email of the customer
     * @param underage  indicates if the customer is underage (true if underage, false otherwise)
     */
    public void updateCustomer(int id, String firstName, String lastName,String email, boolean underage) {
        Customer customer = new Customer(firstName, lastName, email, underage);
        customerRepo.update(id, customer);
    }

    /**
     * Adds a new staff member.
     * @param firstName the first name of the staff member
     * @param lastName  the last name of the staff member
     * @param email     the email address of the staff member
     */
    public void addStaff(String firstName, String lastName, String email) {
        Staff staff = new Staff(firstName, lastName, email);
        staffRepo.add(staff);
    }

    /**
     * Updates the details of an existing staff member.
     * @param id        the ID of the staff member to be updated
     * @param firstName the new first name of the staff member
     * @param lastName  the new last name of the staff member
     * @param email     the new email address of the staff member
     */
    public void updateStaff(int id, String firstName, String lastName, String email) {
        Staff staff = new Staff(firstName, lastName, email);
        staffRepo.update(id, staff);
    }

    /**
     * Adds a new movie to the system.
     * @param title      the title of the movie
     * @param pg         true if the movie is PG-rated, false otherwise
     * @param genre      the genre of the movie
     * @param releaseDate the release date of the movie
     */
    public void addMovie(String title, boolean pg, String genre, LocalDate releaseDate) {
        Movie movie = new Movie(title, pg, genre, releaseDate);
        movieRepo.add(movie);
    }

    /**
     * Gets a movie by its ID.
     * @param id the ID of the movie to be retrieved
     * @return the Movie object with the given ID
     */
    public Movie getMovie(int id) {
        return movieRepo.read(id);
    }

    /**
     * Updates the details of an existing movie.
     * @param id          the ID of the movie to be updated
     * @param title       the new title of the movie
     * @param pg          true if the movie is PG-rated, false otherwise
     * @param genre       the new genre of the movie
     * @param releaseDate the new release date of the movie
     */
    public void updateMovie(int id, String title, boolean pg, String genre, LocalDate releaseDate) {
        Movie movie = new Movie(title, pg, genre, releaseDate);
        movieRepo.update(id, movie);
    }

    /**
     * Deletes a movie from the system.
     * @param id the ID of the movie to be deleted
     */
    public void deleteMovie(int id) {
        movieRepo.delete(id);
    }

    /**
     * Finds a movie by its title.
     * @param title the movie title to search for
     * @return the movie ID if found; null otherwise
     */
    public Integer findMovieIdByTitle(String title) {
        for (Map.Entry<Integer, Movie> entry : movieRepo.getAll().entrySet()) {
            if (entry.getValue().getTitle().equalsIgnoreCase(title)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Adds a new showtime for a given screen and movie.
     * @param screenId  the ID of the screen
     * @param movieId   the ID of the movie
     * @param date      the date of the showtime
     * @param startTime the start time of the showtime
     * @param duration  the duration of the showtime in minutes
     */
    public void addShowtime(int screenId, int movieId, LocalDate date, LocalTime startTime, int duration) {
        Screen screen = getScreen(screenId);
        List<Seat> seatsCopy = new ArrayList<>();
        for (Seat seat : screen.getSeats()) {
            seatsCopy.add(new Seat(seat.getSeatNr(), seat.getType()));
        }
        Showtime showtime = new Showtime(screenId, movieId, date,startTime, duration, seatsCopy);
        showtimeRepo.add(showtime);
    }

    /**
     * Gets a showtime by its ID.
     * @param id the ID of the showtime
     * @return the Showtime with the given ID
     */
    public Showtime getShowtime(int id) {
        return showtimeRepo.read(id);
    }

    /**
     * Updates the details of an existing showtime.
     * @param id        the ID of the showtime to be updated
     * @param screenId  the new screen ID
     * @param movieId   the new movie ID
     * @param date      the new date of the showtime
     * @param startTime the new start time of the showtime
     * @param duration  the new duration of the showtime
     * @param seats     the list of seats for the showtime
     */
    public void updateShowtime(int id, int screenId, int movieId, LocalDate date, LocalTime startTime, int duration, List<Seat> seats) {
        Showtime showtime = new Showtime(screenId, movieId, date, startTime, duration, seats);
        showtimeRepo.update(id, showtime);
    }

    /**
     * Deletes a showtime by its ID.
     * @param id the ID of the showtime to be deleted
     */
    public void deleteShowtime(int id) {
        showtimeRepo.delete(id);
    }

    /**
     * Adds a new screen with the specified number of seats.
     * @param nrStandardSeats the number of standard seats in the screen
     * @param nrVipSeats      the number of VIP seats in the screen
     * @param nrPremiumSeats  the number of premium seats in the screen
     */
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

    /**
     * Gets a screen by its ID.
     * @param id the ID of the screen
     * @return the Screen with the given ID
     */
    public Screen getScreen(int id) {
        return screenRepo.read(id);
    }

    /**
     * Updates the details of an existing screen.
     * @param id             the ID of the screen to be updated
     * @param nrStandardSeats the new number of standard seats
     * @param nrVipSeats      the new number of VIP seats
     * @param nrPremiumSeats  the new number of premium seats
     */
    public void updateScreen(int id, int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        Screen screen = new Screen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
        screenRepo.update(id, screen);
    }

    /**
     * Deletes a screen by its ID.
     * @param id the ID of the screen to be deleted
     */
    public void deleteScreen(int id){
        screenRepo.delete(id);
    }

    /**
     * Adds a new seat with the specified seat number and type.
     * @param seatNr the seat number
     * @param type   the type of the seat
     */
    public void addSeat(int seatNr,  SeatType type) {
        Seat seat = new Seat(seatNr,type);
        seatRepo.add(seat);
    }

    /**
     * Gets a seat by its ID.
     * @param id the ID of the seat
     * @return the {@link Seat} with the given ID
     */
    public Seat getSeat(int id) {
        return seatRepo.read(id);
    }

    /**
     * Updates the details of an existing seat.
     * @param id     the ID of the seat to be updated
     * @param seatNr the new seat number
     * @param type   the new type of the seat
     */
    public void updateSeat(int id, int seatNr,  SeatType type) {
        Seat seat = new Seat(seatNr, type);
        seatRepo.update(id, seat);
    }

    /**
     * Finds a seat by its seat number and screen ID.
     * @param screenId the ID of the screen
     * @param seatNr   the seat number
     * @return the Seat if found, or null if not
     */
    public Seat findSeatBySeatNr(int screenId, int seatNr) {
        Screen screen = screenRepo.read(screenId);

        for(Seat seat : screen.getSeats()) {
            if(seat.getSeatNr() == seatNr) {
                return seat;
            }
        }

        return null;
    }

    /**
     * Adds a new booking for a customer.
     * @param customerId    the ID of the customer making the booking
     * @param showtimeId    the ID of the showtime for the booking
     * @param date          the date of the booking
     * @param nrOfCustomers the number of customers in the booking
     * @return the booking ID
     */
    public int addBooking(int customerId, int showtimeId, LocalDate date, int nrOfCustomers) {
        Booking booking = new Booking(customerId, showtimeId, date, nrOfCustomers);
        return bookingRepo.add(booking);
    }

    /**
     * Retrieves a booking by its ID.
     * @param id the ID of the booking
     * @return the Booking with the given ID
     */
    public Booking getBooking(int id) {
        return bookingRepo.read(id);
    }

    /**
     * Updates the details of an existing booking.
     * @param id            the ID of the booking to be updated
     * @param customerId    the new customer ID
     * @param showtimeId    the new showtime ID
     * @param date          the new booking date
     * @param nrOfCustomers the new number of customers
     */
    public void updateBooking(int id, int customerId, int showtimeId, LocalDate date, int nrOfCustomers) {
        Booking booking = new Booking(customerId, showtimeId, date, nrOfCustomers);
        bookingRepo.update(id, booking);
    }

    /**
     * Adds a new ticket for a booking.
     * @param bookingId the ID of the booking the ticket is associated with
     * @param screenId  the ID of the screen
     * @param seatId    the ID of the seat
     * @param price     the price of the ticket
     * @return the ticket ID
     */
    public int addTicket(int bookingId, int screenId, int seatId, double price) {
        Ticket ticket = new Ticket(bookingId, screenId, seatId, price);
        return ticketRepo.add(ticket);
    }

    /**
     * Gets a ticket by its ID.
     * @param id the ID of the ticket
     * @return the Ticket with the given ID
     */
    public Ticket getTicket(int id) {
        return ticketRepo.read(id);
    }

    /**
     * Updates the details of an existing ticket.
     * @param id        the ID of the ticket to be updated
     * @param bookingId the new booking ID
     * @param screenId  the new screen ID
     * @param seatId    the new seat ID
     * @param price     the new price of the ticket
     */
    public void updateTicket(int id, int bookingId, int screenId, int seatId, double price) {
        Ticket ticket = new Ticket(bookingId, screenId, seatId, price);
        ticketRepo.update(id, ticket);
    }

    /**
     * Adds a new basic membership for a customer.
     * @param customerId the ID of the customer
     * @param startDate  the start date of the membership
     * @param endDate    the end date of the membership
     * @return the BasicMembership object
     */
    public BasicMembership addBasicMembership(int customerId, LocalDate startDate, LocalDate endDate) {
        BasicMembership basicMembership = new BasicMembership(customerId, startDate,endDate);
        Customer customer = getCustomer(customerId);
        customer.setMembershipId(basicMembershipRepo.add(basicMembership));
        return basicMembership;

    }

    /**
     * Gets a basic membership by its ID.
     * @param id the ID of the basic membership
     * @return the BasicMembership with the given ID
     */
    public BasicMembership getBasicMembership(int id) {
        return basicMembershipRepo.read(id);
    }

    /**
     * Updates the details of an existing basic membership.
     * @param id        the ID of the basic membership to be updated
     * @param customerId the new customer ID
     * @param startDate  the new start date of the membership
     * @param endDate    the new end date of the membership
     */
    public void updateBasicMembership(int id, int customerId, LocalDate startDate, LocalDate endDate) {
        BasicMembership basicMembership = new BasicMembership(customerId, startDate,endDate);
        basicMembershipRepo.update(id, basicMembership);
    }

    /**
     * Deletes a basic membership by its ID.
     * @param id the ID of the basic membership to be deleted
     */
    public void deleteBasicMembership(int id) {
        basicMembershipRepo.delete(id);
    }

    /**
     * Adds a new premium membership for a customer.
     * @param customerId the ID of the customer
     * @param startDate  the start date of the membership
     * @param endDate    the end date of the membership
     * @return the PremiumMembership object
     */
    public PremiumMembership addPremiumMembership(int customerId, LocalDate startDate, LocalDate endDate) {
        PremiumMembership premiumMembership = new PremiumMembership(customerId, startDate,endDate);
        Customer customer = getCustomer(customerId);
        customer.setMembershipId(premiumMembershipRepo.add(premiumMembership));
        return premiumMembership;
    }

    /**
     * Gets a premium membership by its ID.
     * @param id the ID of the premium membership to get
     * @return the PremiumMembership object with the given ID
     */
    public PremiumMembership getPremiumMembership(int id) {
        return premiumMembershipRepo.read(id);
    }

    /**
     * Updates the details of a premium membership.
     * @param id the ID of the premium membership to update
     * @param customerId the ID of the customer associated with the membership
     * @param startDate the start date of the membership
     * @param endDate the end date of the membership
     */
    public void updatePremiumMembership(int id, int customerId, LocalDate startDate, LocalDate endDate) {
        PremiumMembership premiumMembership = new PremiumMembership(customerId, startDate,endDate);
        premiumMembershipRepo.update(id, premiumMembership);
    }

    /**
     * Deletes a premium membership by its ID.
     * @param id the ID of the premium membership to delete
     */
    public void deletePremiumMembership(int id) {
        premiumMembershipRepo.delete(id);
    }

    /**
     * Determines the type of membership a customer has.
     * 1 = basic membership, 2 = premium membership, 0 = no membership.
     * @param customerId the ID of the customer
     * @return the type of membership (1, 2, or 0)
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
     * Returns a list of showtimes that the customer can watch depending on their age status.
     * If the customer is underaged, only non-PG movies are returned.
     * @param customer the customer whose showtimes to display
     * @return a map of showtimes available for the customer
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

    /**
     * Gets all movies available for the staff to view.
     * @return a map of all movies
     */
    public Map<Integer, Movie> displayMoviesStaff(){
        Map<Integer, Movie> allMovies = movieRepo.getAll();
        return allMovies;
    }

    /**
     * Gets all showtimes available for the staff to view.
     * @return a map of all showtimes
     */
    public Map<Integer, Showtime> displayShowtimesStaff(){
        Map<Integer, Showtime> allshowtimes = showtimeRepo.getAll();
        return allshowtimes;
    }

    /**
     * Gets all screens available for the staff to view.
     * @return a map of all screens
     */
    public Map<Integer, Screen> displayScreensStaff(){
        Map<Integer, Screen> allscreens= screenRepo.getAll();
        return allscreens;
    }

    /**
     * Finds a customer by their email address.
     * @param email the email address of the customer to find
     * @return the Customer object matching the given email, or null if no such customer exists
     */
    public Customer findCustomerByEmail(String email){
        Map<Integer, Customer> customers = customerRepo.getAll();

        for(Map.Entry<Integer, Customer> entry : customers.entrySet()){
            if(entry.getValue().getEmail().equals(email))
                return entry.getValue();
        }

        return null;
    }

    /**
     * Gets the ID of the given customer.
     * @param customer the customer to get the ID for
     * @return the ID of the given customer, or 0 if the customer does not exist
     */
    public int getIdOfCustomer(Customer customer){
        Map<Integer, Customer> customers = customerRepo.getAll();

        for(Map.Entry<Integer, Customer> entry : customers.entrySet()){
            if(entry.getValue().equals(customer))
                return entry.getKey();
        }

        return 0;
    }

    /**
     * Gets the ID of the given booking.
     * @param booking the booking to get the ID for
     * @return the ID of the given booking, or 0 if the booking does not exist
     */
    public int getIdOfBooking(Booking booking) {
        Map<Integer, Booking> bookings = bookingRepo.getAll();

        for(Map.Entry<Integer, Booking> entry : bookings.entrySet()){
            if(entry.getValue().equals(bookings))
                return entry.getKey();
        }

        return 0;
    }

    /**
     * Gets a list of tickets associated with a given booking ID.
     * @param bookingId the ID of the booking for which to get tickets
     * @return a list of tickets associated with the given booking ID
     */
    public List<Ticket> bookingTickets(int bookingId) {
        Map<Integer, Ticket> tickets = ticketRepo.getAll();
        List<Ticket> bookingTickets = new ArrayList<>();

        for(Map.Entry<Integer, Ticket> entry : tickets.entrySet()){
            if(entry.getValue().getBookingId() == bookingId)
                bookingTickets.add(entry.getValue());
        }

        return bookingTickets;
    }

    /**
     * Finds a staff member by their email address.
     * @param email the email address of the staff member to find
     * @return the Staff object matching the given email, or null if no such staff exists
     */
    public Staff findStaffByEmail(String email) {
        Map<Integer, Staff> staffs = staffRepo.getAll();

        for(Map.Entry<Integer, Staff> entry : staffs.entrySet()){
            if(entry.getValue().getEmail().equals(email))
                return entry.getValue();
        }

        return null;
    }

    /**
     * Deletes all showtimes associated with a given movie ID.
     * @param movieId the ID of the movie for which to delete all related showtimes
     */
    public void deleteShowtimesByMovieId(int movieId) {
        Map<Integer, Showtime> allShowtimes = showtimeRepo.getAll();
        List<Integer> showtimeIdsToDelete = new ArrayList<>();

        for (Map.Entry<Integer, Showtime> entry : allShowtimes.entrySet()) {
            if (entry.getValue().getMovieId() == movieId) {
                showtimeIdsToDelete.add(entry.getKey());
            }
        }

        for (Integer showtimeId : showtimeIdsToDelete) {
            showtimeRepo.delete(showtimeId);
        }
    }


    /**
     * Deletes all showtimes associated with a given screen ID.
     * @param screenId the ID of the screen for which to delete all related showtimes
     */
    public void deleteShowtimesByScreenId(int screenId) {
        Map<Integer, Showtime> allShowtimes = showtimeRepo.getAll();
        List<Integer> showtimeIdsToDelete = new ArrayList<>();

        for (Map.Entry<Integer, Showtime> entry : allShowtimes.entrySet()) {
            if (entry.getValue().getScreenId() == screenId) {
                showtimeIdsToDelete.add(entry.getKey());
            }
        }

        for (Integer showtimeId : showtimeIdsToDelete) {
            showtimeRepo.delete(showtimeId);
        }
    }


    /**
     * Calculates the discounted price for a given membership type.
     * @param price the original price to calculate the discounted price for
     * @param membership the membership providing the discount
     * @return the discounted price based on the membership's offer
     */
    public double calculateDiscountedPrice(double price, Membership membership) {
        return membership.offerDiscount(price);
    }

    /**
     * Terminates memberships that have expired ( 30 days have passed since the end date).
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