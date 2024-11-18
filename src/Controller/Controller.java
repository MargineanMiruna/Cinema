package Controller;


import Model.*;
import Service.CinemaService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller class that provides methods for managing customer and
 * staff accounts, movies, showtimes, seats, bookings, tickets,
 * and memberships through interaction with the CinemaService layer.
 */
public class Controller {
    CinemaService cinemaService;

    public Controller(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    /**
     * default entry point to initialize sample data for testing
     */
    public void add(){
        cinemaService.addCustomer("Miruna", "Marginean", "miruna", LocalDate.of(2004,5,10));
        cinemaService.addCustomer("Tea", "Nicola", "tea", LocalDate.of(2004,11,11));
        cinemaService.addCustomer("Bence", "Molnar", "bence", LocalDate.of(2009,9,24));

        cinemaService.addStaff("Alexandra","Olah","alexandra");
        cinemaService.addStaff("Klara","Orban","klara");

        cinemaService.addScreen(6,10,7);
        cinemaService.addScreen(12,4,8);
        cinemaService.addScreen(10,5,2);

        cinemaService.addMovie("The Notebook", true, "romance", LocalDate.of(1998,11,17));
        cinemaService.addMovie("Barbie", false, "comedy", LocalDate.of(2024, 4, 25));
        cinemaService.addMovie("Joker", true, "thriller", LocalDate.of(2019, 10, 14));

        cinemaService.addShowtime(1,2, LocalDate.of(2025, 1, 5), LocalTime.of(12,45), 120);
        cinemaService.addShowtime(2,3, LocalDate.of(2024,12,24), LocalTime.of(20, 30), 210);
        cinemaService.addShowtime(3,1, LocalDate.of(2024,12,14), LocalTime.of(16,20), 190);

    }

    /**
     * Creates a new customer based on provided details.
     * Sets customer as underaged if their age is under 18.
     * @param firstname The first name of the customer.
     * @param lastname  The last name of the customer.
     * @param email     The email of the customer.
     * @param birthday  The birth date of the customer.
     */
    public void createCustomer(String firstname, String lastname, String email, LocalDate birthday) {
        cinemaService.addCustomer(firstname, lastname, email, birthday);
    }

    /**
     * Logs in a customer by finding their account via email.
     * @param email The email of the customer to log in.
     * @return The Customer object if found, otherwise null.
     */
    public Customer logCustomer(String email) {
        try {
            if(cinemaService.findCustomerByEmail(email) != null) {
                return cinemaService.findCustomerByEmail(email);
            }
            else {
                System.out.println("No account by this email!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred: ");
            return null;
        }
    }

    /**
     * Creates a new staff member with the given information.
     * @param firstname The first name of the staff member.
     * @param lastname  The last name of the staff member.
     * @param email     The email of the staff member.
     */
    public void createStaff(String firstname, String lastname, String email) {
        cinemaService.addStaff(firstname, lastname, email);
    }

    /**
     * Logs in a staff member by their email.
     * @param email The email of the staff member.
     * @return The Staff object if found, otherwise null.
     */
    public Staff logStaff(String email) {
        try {
            if(cinemaService.findStaffByEmail(email) != null) {
                return cinemaService.findStaffByEmail(email);
            }
            else {
                System.out.println("No account by this email!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred: ");
            return null;
        }
    }

    /**
     * Displays the customer menu options.
     */
    public void customerMenu() {
        System.out.println("""
                =================Menu=================
                1. View showtimes
                2. Create a booking
                3. Create a membership
                4. Back
                Enter your choice:""");
    }

    /**
     * Displays showtimes for a given customer.
     * @param customer The customer viewing the showtimes.
     */
    public void displayShowtimesFilteredByPg(Customer customer) {
        Map<Integer, Showtime> showtimes = cinemaService.filterShowtimesByPg(customer);

        for(Map.Entry<Integer, Showtime> entry : showtimes.entrySet()) {
            Movie movie = cinemaService.getMovie(entry.getValue().getMovieId());
            System.out.println("\n======================================");
            System.out.println("\nShowtime " + entry.getKey() + ":\n\tMovie details:\n\t\tTitle: " + movie.getTitle() + "\n\t\tGenre: " + movie.getGenre() + "\n\t\tRealease date: " + movie.getReleaseDate() + "\n\tDate: " + entry.getValue().getDate() + "\n\tRoom " + entry.getValue().getScreenId() + "\n\tStarts at: " + entry.getValue().getStartTime() +  "\n\tDuration: " + entry.getValue().getDuration());
        }
    }

    /**
     * Displays available seats for a given showtime.
     * @param showtimeId The ID of the showtime.
     */
    public void displayAvailableSeats(int showtimeId) {
        Showtime showtime= cinemaService.getShowtime(showtimeId);
        List<Seat> seats = showtime.getSeats();

        System.out.println("\n======================================");
        System.out.printf("%-5s %-13s %-10s%n", "No", "Type", "Price");
        for(Seat seat : seats) {
            System.out.printf("%-5d %-13s %-10s%n", seat.getSeatNr(), seat.getType(), seat.getPrice() + " lei");
        }
        System.out.println("\n======================================");
    }

    /**
     * Removes booked seats from the list of available seats for a showtime.
     * @param showtimeId The ID of the showtime.
     * @param seats The list of seat numbers to be booked.
     */
    public void removeSeatsFromAvailable(int showtimeId, List<Integer> seats) {
        cinemaService.removeSeatsFromAvailable(showtimeId, seats);
    }

    /**
     * Creates a new booking for a customer.
     * @param loggedCustomerId The ID of the customer.
     * @param showtimeId The ID of the showtime.
     * @param date The date of the booking.
     * @param nrOfSeats The number of seats booked.
     * @return The ID of the created booking.
     */
    public int createBooking(int loggedCustomerId, int showtimeId, LocalDate date, int nrOfSeats) {
        return cinemaService.addBooking(loggedCustomerId, showtimeId, date, nrOfSeats);
    }

    /**
     * Gets a booking by its ID.
     * @param bookingId The ID of the booking.
     * @return The Booking object.
     */
    public Booking getBooking(int bookingId) {
        return cinemaService.getBooking(bookingId);
    }

    /**
     * Creates the tickets for all seats in a booking and adds them to the booking.
     * @param bookingId The ID of the booking.
     * @param seats The list of seat numbers booked.
     */
    public void createTickets(int bookingId, List<Integer> seats) {
        List<Integer> tickets = new ArrayList<>();
        for(int i = 0; i < seats.size(); i++) {
            Showtime showtime = cinemaService.getShowtime(cinemaService.getBooking(bookingId).getShowtimeId());
            Seat seat = cinemaService.findSeatBySeatNr(showtime.getScreenId(), seats.get(i));
            tickets.add(cinemaService.addTicket(bookingId, showtime.getScreenId(), seats.get(i), seat.getPrice()));
        }

        Booking currentBooking = this.getBooking(bookingId);
        currentBooking.setTickets(tickets);
    }

    /**
     * Displays tickets details for a booking.
     * @param customer The customer who made the booking.
     * @param booking The booking containing the tickets.
     */
    public void displayTickets(Customer customer, Booking booking) {
        System.out.println("\nYour tickets: ");
        for(int i = 0; i < booking.getTickets().size(); i++) {
            System.out.println("\n======================================");
            String ticketInfo = "\nBooking made by " + customer.getFirstName() + " " + customer.getLastName() + " on " + booking.getDate().toString() + "\n";
            ticketInfo += "Movie " + cinemaService.getMovie(cinemaService.getShowtime(booking.getShowtimeId()).getMovieId()).getTitle() + "\n";
            ticketInfo += "Room " + cinemaService.getTicket(booking.getTickets().get(i)).getScreenId() + " seat number " + cinemaService.getTicket(booking.getTickets().get(i)).getSeatNr() + " type " + cinemaService.findSeatBySeatNr(cinemaService.getTicket(booking.getTickets().get(i)).getScreenId(), cinemaService.getTicket(booking.getTickets().get(i)).getSeatNr()).getType() + "\n";
            ticketInfo += "Price " + cinemaService.getTicket(booking.getTickets().get(i)).getPrice();
            System.out.println(ticketInfo);
        }
    }


    public void calculateTotalPrice(int loggedCustomerId, int currentBookingId) {
        double totalPrice = cinemaService.calculateTotalPrice(currentBookingId);
        double discountedPrice = cinemaService.calculateDiscountedPrice(loggedCustomerId, currentBookingId);

        System.out.println("\n======================================");
        System.out.println("\nYour total: ");
        System.out.println("Price of tickets " + totalPrice + " lei\nDiscount " + (totalPrice - discountedPrice) + " lei\nTotal to pay " + discountedPrice + " lei");
    }

    /**
     * Displays the staff menu options.
     */
    public void staffMenu() {
        System.out.println("""
                =================Menu=================
                1. Modify movie
                2. Modify showtime
                3. Modify screen
                4. Back
                Enter your choice:""");
    }

    /**
     * Displays additional staff menu options.
     */
    public void staffMenu2() {
        System.out.println("\n======================================");
        System.out.println("1. Add\n2. Update\n3. Delete\nEnter your choice: ");
    }

    /**
     * Adds a new movie to the cinema's list.
     * @param title The title of the movie.
     * @param pg Parental guidance flag (true if PG).
     * @param genre The genre of the movie.
     * @param releaseDate The release date of the movie.
     */
    public void addMovie(String title, boolean pg, String genre, LocalDate releaseDate) {
        cinemaService.addMovie(title, pg, genre, releaseDate);
    }

    /**
     * Updates an existing movie's details.
     * @param title The title of the movie.
     * @param pg Parental guidance flag (true if PG).
     * @param genre The genre of the movie.
     * @param releaseDate The release date of the movie.
     */
    public void updateMovie( String title, boolean pg, String genre, LocalDate releaseDate) {
        Integer movieId = cinemaService.findMovieIdByTitle(title);
        if (movieId != null) {
            cinemaService.updateMovie(movieId, title, pg, genre, releaseDate);
        } else {
            System.out.println("Movie not found with title: " + title);
        }
    }

    /**
     * Deletes a movie by title, including all associated showtimes.
     * @param title The title of the movie to delete.
     */
    public void deleteMovie(String title){
        Integer movieId = cinemaService.findMovieIdByTitle(title);
        if (movieId != null) {
            cinemaService.deleteMovie(movieId);
            cinemaService.deleteShowtimesByMovieId(movieId);

        } else {
            System.out.println("Movie not found with title: " + title);
        }
    }

    /**
     * Finds the ID of a movie by its title.
     * @param title The title of the movie.
     * @return The ID of the movie, or null if not found.
     */
    public int findMovieIdByTitle(String title){
        return cinemaService.findMovieIdByTitle(title);
    }

    /**
     * Adds a new showtime to a specified screen and movie.
     * @param screenId The ID of the screen.
     * @param movieId The ID of the movie.
     * @param date The date of the showtime.
     * @param startTime The start time of the showtime.
     * @param duration The duration of the showtime.
     */
    public void addShowtime(int screenId, int movieId, LocalDate date, LocalTime startTime, int duration) {
        cinemaService.addShowtime(screenId, movieId, date,startTime, duration);
    }

    /**
     * Updates an existing showtime.
     * @param id The ID of the showtime.
     * @param screenId The screen ID.
     * @param movieId The movie ID.
     * @param date The date of the showtime.
     * @param startTime The start time.
     * @param duration The duration of the showtime.
     */
    public void updateShowtime(int id, int screenId, int movieId, LocalDate date, LocalTime startTime, int duration) {
        cinemaService.updateShowtime(id, screenId, movieId, date, startTime, duration, cinemaService.getShowtime(id).getSeats());
    }

    /**
     * Deletes a showtime by ID.
     * @param id The ID of the showtime to delete.
     */
    public void deleteShowtime(int id){
        cinemaService.deleteShowtime(id);
    }

    /**
     * Adds a new screen with specified seat types.
     * @param nrStandardSeats The number of standard seats.
     * @param nrVipSeats The number of VIP seats.
     * @param nrPremiumSeats The number of premium seats.
     */
    public void addScreen(int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        cinemaService.addScreen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
    }

    /**
     * Updates an existing screen's seat configuration.
     * @param id The ID of the screen.
     * @param nrStandardSeats The number of standard seats.
     * @param nrVipSeats The number of VIP seats.
     * @param nrPremiumSeats The number of premium seats.
     */
    public void updateScreen(int id, int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        cinemaService.updateScreen(id, nrStandardSeats, nrVipSeats, nrPremiumSeats);
    }

    /**
     * Deletes a screen by its ID.
     * @param id The ID of the screen to delete.
     */
    public void deleteScreen(int id) {
        cinemaService.deleteScreen(id);
        cinemaService.deleteShowtimesByScreenId(id);
    }

    /**
     * Creates a basic membership for a customer with a specified start and end date.
     * @param customerId The ID of the customer to whom the membership will be assigned.
     * @param startDate  The start date of the basic membership.
     * @param endDate    The end date of the basic membership.
     * @return A BasicMembership object representing the created membership.
     */
    public BasicMembership createBasicMembership(int customerId, LocalDate startDate, LocalDate endDate) {
        return cinemaService.addBasicMembership(customerId, startDate, endDate);
    }

    /**
     * Creates a premium membership for a customer with a specified start and end date.
     * @param customerId The ID of the customer to whom the membership will be assigned.
     * @param startDate  The start date of the premium membership.
     * @param endDate    The end date of the premium membership.
     * @return A PremiumMembership object representing the created membership.
     */
    public PremiumMembership createPremiumMembership(int customerId, LocalDate startDate, LocalDate endDate) {
        return cinemaService.addPremiumMembership(customerId, startDate, endDate);
    }

    /**
     * Terminates all expired memberships by updating their status within the system.
     * This method identifies memberships whose end date has passed and processes them
     * to deactivate or remove benefits as per the application's logic.
     */
    public void terminateMemberships() {
        cinemaService.terminateMemberships();
    }

    /**
     * Displays a list of all showtimes for staff, including movie details and screening information.
     */
    public void displayShowtimesStaff() {
        Map<Integer, Showtime> showtimes = cinemaService.displayShowtimesStaff();

        for(Map.Entry<Integer, Showtime> entry : showtimes.entrySet()) {
            Movie movie = cinemaService.getMovie(entry.getValue().getMovieId());
            System.out.println("\n======================================");
            System.out.println("\nShowtime " + entry.getKey() + ":\n\tMovie details:\n\t\tTitle: " + movie.getTitle() + "\n\t\tGenre: " + movie.getGenre() + "\n\t\tRealease date: " + movie.getReleaseDate() + "\n\tDate: " + entry.getValue().getDate() + "\n\tRoom " + entry.getValue().getScreenId() + "\n\tStarts at: " + entry.getValue().getStartTime() +  "\n\tDuration: " + entry.getValue().getDuration());
        }
    }

    /**
     * Displays a list of all screens for staff.
     */
    public void displayScreensStaff() {
        Map<Integer, Screen> screens = cinemaService.displayScreensStaff();

        for(Map.Entry<Integer, Screen> entry : screens.entrySet()) {
            System.out.println("\nScreen " + entry.getKey());
        }
    }

    /**
     * Displays a list of all screens for staff.
     */
    public void displayMoviesStaff() {
        Map<Integer, Movie> movies = cinemaService.displayMoviesStaff();

        for(Map.Entry<Integer, Movie> entry : movies.entrySet()) {
            System.out.println("\nMovie " + entry.getKey() + ":\nTitle: " + entry.getValue().getTitle() + "\n\t\tGenre: " + entry.getValue().getGenre() + "\n\t\tRealease date: " + entry.getValue().getReleaseDate());
        }
    }

}