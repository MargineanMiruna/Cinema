package Controller;


import Model.*;
import Service.CinemaService;
import Exception.EntityNotFoundException;

import java.sql.SQLOutput;
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
        cinemaService.addCustomer("Miruna", "Marginean", "miruna.marginean.2018@gmail.com", LocalDate.of(2004,5,10));
        cinemaService.addCustomer("Tea", "Nicola", "tea.nicola1104@yahoo.ro", LocalDate.of(2004,11,11));
        cinemaService.addCustomer("Bence", "Molnar", "mbence@outlook.com", LocalDate.of(2009,9,24));

        cinemaService.addStaff("Alexandra","Olah","aleolah3110@gmail.com");
        cinemaService.addStaff("Klara","Orban","oklara@gmail.com");

        cinemaService.addScreen(6,10,7);
        cinemaService.addScreen(12,4,8);
        cinemaService.addScreen(10,5,2);

        cinemaService.addMovie("The Notebook", true, "romance", LocalDate.of(2004,6,25));
        cinemaService.addMovie("Barbie", false, "comedy", LocalDate.of(2023, 7, 21));
        cinemaService.addMovie("Joker", true, "thriller", LocalDate.of(2019, 10, 4));

        cinemaService.addShowtime(1,2, LocalDate.of(2025, 1, 5), LocalTime.of(12,45), 120);
        cinemaService.addShowtime(2,3, LocalDate.of(2024,12,24), LocalTime.of(20, 30), 135);
        cinemaService.addShowtime(2,1, LocalDate.of(2024,12,14), LocalTime.of(16,20), 130);

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
        if(cinemaService.findCustomerByEmail(email) != null) {
            return cinemaService.findCustomerByEmail(email);
        } else {
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
        if(cinemaService.findStaffByEmail(email) != null) {
            return cinemaService.findStaffByEmail(email);
        } else {
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
                4. View booking history
                5. Back
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
            System.out.println("\nShowtime " + entry.getKey() + ":\n\tMovie details:\n\t\tTitle: " + movie.getTitle() + "\n\t\tGenre: " + movie.getGenre() + "\n\t\tRealease date: " + movie.getReleaseDate() + "\n\tDate: " + entry.getValue().getDate() + "\n\tRoom " + entry.getValue().getScreenId() + "\n\tStarts at: " + entry.getValue().getStartTime() +  "\n\tDuration: " + entry.getValue().getDuration()+ " min");
        }
    }

    /**
     * Displays the available seats for a specific showtime and ticket type.
     * This method retrieves the showtime associated with the given `showtimeId` and
     * filters the available seats based on the specified `typeOfTickets`. It then prints
     * the available seats on the same line, with spaces between each seat.
     *
     * @param showtimeId The identifier of the showtime for which available seats will be displayed.
     * @param typeOfTickets The type of tickets for which seats are being filtered ( standard, premium, etc.).
     */
    public List<Integer> displayAvailableSeats(int showtimeId,int typeOfTickets) {
        List<Integer> seats = cinemaService.filterSeatsByType(showtimeId, typeOfTickets);

        System.out.println("\n======================================");
        for (Integer seat : seats) {
            System.out.print(seat + " ");
        }
        System.out.println("\n======================================");
        return seats;
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
    public int createBooking(int loggedCustomerId, int showtimeId, LocalDate date, int nrOfSeats, List<Integer> seats) {
        return cinemaService.addBooking(loggedCustomerId, showtimeId, date, nrOfSeats, seats);
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
    public void updateMovie(String title, boolean pg, String genre, LocalDate releaseDate) {
        Integer movieId = cinemaService.findMovieIdByTitle(title);
        cinemaService.updateMovie(movieId, title, pg, genre, releaseDate);
    }

    /**
     * Deletes a movie by title, including all associated showtimes.
     * @param title The title of the movie to delete.
     */
    public void deleteMovie(String title){
        Integer movieId = cinemaService.findMovieIdByTitle(title);
        cinemaService.deleteMovie(movieId);
        cinemaService.deleteShowtimesByMovieId(movieId);
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
    public void updateScreen(int id, int nrStandardSeats, int nrVipSeats, int nrPremiumSeats, List<Seat> seats) {
        cinemaService.updateScreen(id, nrStandardSeats, nrVipSeats, nrPremiumSeats, seats);
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
            System.out.println("\nShowtime " + entry.getKey() + ":\n\tMovie details:\n\t\tTitle: " + movie.getTitle() + "\n\t\tGenre: " + movie.getGenre() + "\n\t\tRealease date: " + movie.getReleaseDate() + "\n\tDate: " + entry.getValue().getDate() + "\n\tRoom " + entry.getValue().getScreenId() + "\n\tStarts at: " + entry.getValue().getStartTime() +  "\n\tDuration: " + entry.getValue().getDuration() + " min");
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

    /**
     * Displays the showtimes filtered by the specified date.
     * This method filters the showtimes based on the provided date and retrieves the corresponding movie details
     * for each filtered showtime. It then displays information about each showtime, including the movie title, genre,
     * release date, showtime date, screen ID, start time, and duration.
     *
     * @param date The date by which the showtimes will be filtered.
     */
    public void displayShowtimesFilteredByDate (Customer customer, LocalDate date){
        Map<Integer,Showtime> filteredShowtimes = cinemaService.filerShowtimesByDate(customer, date);

        try {
            if (filteredShowtimes.isEmpty()) {
                throw new EntityNotFoundException("No showtimes available for the selected date.");
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        for(Map.Entry<Integer, Showtime> entry : filteredShowtimes.entrySet()) {
            Movie movie = cinemaService.getMovie(entry.getValue().getMovieId());
            System.out.println("\n======================================");
            System.out.println("\nShowtime " + entry.getKey() + ":\n\tMovie details:\n\t\tTitle: " + movie.getTitle() + "\n\t\tGenre: " + movie.getGenre() + "\n\t\tRealease date: " + movie.getReleaseDate() + "\n\tDate: " + entry.getValue().getDate() + "\n\tRoom " + entry.getValue().getScreenId() + "\n\tStarts at: " + entry.getValue().getStartTime() +  "\n\tDuration: " + entry.getValue().getDuration());
        }

    }

    /**
     * Displays showtimes for a specific customer, sorted by date in ascending order.
     * For each showtime, details of the associated movie (title, genre, release date) and showtime
     * (date, screen, start time, duration) are printed.
     *
     * @param customer The customer whose showtimes are displayed.
     */
    public void displaySortedShowtimesByDateAsc(Customer customer){
        Map<Integer, Showtime> sortedShowtimes = cinemaService.sortShowtimesByDateAsc(customer);

        for(Map.Entry<Integer, Showtime> entry : sortedShowtimes.entrySet()) {
            Movie movie = cinemaService.getMovie(entry.getValue().getMovieId());
            System.out.println("\n======================================");
            System.out.println("\nShowtime " + entry.getKey() + ":\n\tMovie details:\n\t\tTitle: " + movie.getTitle() + "\n\t\tGenre: " + movie.getGenre() + "\n\t\tRealease date: " + movie.getReleaseDate() + "\n\tDate: " + entry.getValue().getDate() + "\n\tRoom " + entry.getValue().getScreenId() + "\n\tStarts at: " + entry.getValue().getStartTime() +  "\n\tDuration: " + entry.getValue().getDuration());
        }

    }

    /**
     * Displays showtimes for a specific customer that match the given movie title.
     * The method filters the showtimes by movie title and displays details of the matching
     * showtimes, including movie information (title, genre, release date) and showtime
     * details (date, screen, start time, duration). If no showtimes are found, a message
     * indicating this is displayed.
     *
     * @param customer   The customer whose showtimes are filtered and displayed.
     * @param movieTitle The title of the movie used to filter the showtimes.
     */
    public void displayFilteredShowtimesByMovie(Customer customer, String movieTitle){
        Map<Integer, Showtime> filteredShowtimes = cinemaService.filterShowtimesByMovie(customer,movieTitle);

        try {
            if (filteredShowtimes.isEmpty()) {
                throw new EntityNotFoundException("No showtimes found for the movie \"" + movieTitle + "\".");
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        for(Map.Entry<Integer, Showtime> entry : filteredShowtimes.entrySet()) {
            Movie movie = cinemaService.getMovie(entry.getValue().getMovieId());
            System.out.println("\n======================================");
            System.out.println("\nShowtime " + entry.getKey() + ":\n\tMovie details:\n\t\tTitle: " + movie.getTitle() + "\n\t\tGenre: " + movie.getGenre() + "\n\t\tRealease date: " + movie.getReleaseDate() + "\n\tDate: " + entry.getValue().getDate() + "\n\tRoom " + entry.getValue().getScreenId() + "\n\tStarts at: " + entry.getValue().getStartTime() +  "\n\tDuration: " + entry.getValue().getDuration());
        }

    }

    /**
     * Displays the showtimes for a specific customer, sorted in ascending order by their duration.
     * For each showtime, the method fetches and displays details about the associated movie, including:
     * title, genre, release date, and showtime details such as date, room, start time, and duration.
     *
     * @param customer The customer whose showtimes are to be sorted and displayed.
     */
    public void displaySortedShowtimesByDuration(Customer customer){
        Map<Integer, Showtime> sortedShowtimes = cinemaService.sortShowtimesByDuration(customer);

        for(Map.Entry<Integer, Showtime> entry : sortedShowtimes.entrySet()) {
            Movie movie = cinemaService.getMovie(entry.getValue().getMovieId());
            System.out.println("\n======================================");
            System.out.println("\nShowtime " + entry.getKey() + ":\n\tMovie details:\n\t\tTitle: " + movie.getTitle() + "\n\t\tGenre: " + movie.getGenre() + "\n\t\tRealease date: " + movie.getReleaseDate() + "\n\tDate: " + entry.getValue().getDate() + "\n\tRoom " + entry.getValue().getScreenId() + "\n\tStarts at: " + entry.getValue().getStartTime() +  "\n\tDuration: " + entry.getValue().getDuration());
        }
    }

    /**
     * Displays all bookings made by a customer, including associated showtime details.
     *
     * @param customer The customer whose bookings are to be displayed.
     *                 This parameter is used to retrieve the relevant bookings.
     * If no bookings are found for the customer, a message will be printed indicating this.
     */
    public void displayBookingsWithShowtimes(Customer customer) {
        Map<Integer, Map.Entry<Booking, Showtime>> customerBookings = cinemaService.getBookingsByCustomer(customer);

        try {
            if (customerBookings.isEmpty()) {
                throw new EntityNotFoundException("No bookings found for customer: " + customer.getFirstName() + " " + customer.getLastName());
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }

        for (Map.Entry<Integer, Map.Entry<Booking, Showtime>> entry : customerBookings.entrySet()) {
            Booking booking = entry.getValue().getKey();
            Showtime showtime = entry.getValue().getValue();

            System.out.println("\nBooking ID: " + entry.getKey());
            System.out.println("\tShowtime ID: " + showtime.getId());
            System.out.println("\tMovie : " + cinemaService.getMovie(showtime.getMovieId()).getTitle());
            System.out.println("\tDate: " + showtime.getDate());
            System.out.println("\tStart Time: " + showtime.getStartTime());
            System.out.println("\tDuration: " + showtime.getDuration() + " minutes");
            System.out.println("\tNumber of Customers: " + booking.getNrOfCustomers());
        }
    }

    /**
     * Checks if a Showtime with the given ID exists.
     *
     * @param showtimeId The ID of the Showtime to check.
     * @return true if the Showtime exists, false otherwise.
     */
    public boolean isShowtimeAvailable(int showtimeId) {
         return cinemaService.isShowtimeAvailable(showtimeId);
    }


    /**
     * Checks if a specific seat is available for a given showtime by delegating the check to the cinema service.
     *
     * @param showtimeId the ID of the showtime
     * @param seat the seat number to check
     * @return true if the seat is available, false otherwise
     */
    public boolean isSeatAvailable(int showtimeId, int seat) {
        return cinemaService.isSeatAvailable(showtimeId, seat);
    }

    /**
     * Checks if a movie with the given title exists by delegating to the cinema service.
     *
     * @param movieTitle The title of the movie to check.
     * @return {@code true} if the movie exists, {@code false} otherwise.
     */
    public boolean doesMovieExist(String movieTitle) {
        return cinemaService.doesMovieExist(movieTitle);
    }

    /**
     * Checks if a screen exists in the repository by its ID.
     *
     * @param id the unique identifier of the screen to check
     * @return true if the screen exists, false otherwise
     */
    public boolean doesScreenExist(int id) {
       return  cinemaService.doesScreenExist(id);
    }

    /**
     * Checks if a screen with the given ID has showtimes scheduled for the future.
     *
     * @param id the unique identifier of the screen
     * @return true if the screen has future showtimes, false otherwise
     */
    public boolean hasFutureShowtimes(int id) {
        return cinemaService.hasFutureShowtimes(id);
    }

    /**
     * Checks if a showtime exists in the repository by its ID.
     *
     * @param id the unique identifier of the showtime to check
     * @return true if the showtime exists, false otherwise
     */
    public boolean doesShowtimeExist(int id) {
        return cinemaService.doesShowtimeExist(id);
    }

    /**
     * Checks if there are any bookings associated with a given showtime ID.
     *
     * @param id the ID of the showtime to check for bookings
     * @return true if there are bookings for the given showtime ID, false otherwise
     */
    public boolean hasBookingsForShowtime(int id) {
        return cinemaService.hasBookingsForShowtime(id);
    }
}