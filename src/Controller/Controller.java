package Controller;


import Domain.*;
import Service.CinemaService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Map;

public class Controller {
    CinemaService cinemaService = new CinemaService();

    public void add(){
        cinemaService.addCustomer("Miruna", "Marginean", "miruna", false);
        cinemaService.addScreen(5,2,3);
        cinemaService.addMovie("The Notebook", true, "romance", LocalDate.ofEpochDay(17-12-1998));
        cinemaService.addShowtime(1,1, LocalDate.now(), LocalTime.now(), 120);
    }

    int getAge(LocalDate birthday) {
        LocalDate today = LocalDate.now();
        return Period.between(birthday, today).getYears();
    }

    public void createCustomer(String firstname, String lastname, String email, LocalDate birthday) {
        boolean underaged = getAge(birthday) < 18;
        cinemaService.addCustomer(firstname, lastname, email, underaged);
    }

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

    public int getIdOfCustomer(Customer customer) {
        return cinemaService.getIdOfCustomer(customer);
    }

    public void createStaff(String firstname, String lastname, String email) {
        cinemaService.addStaff(firstname, lastname, email);
    }

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

    public void customerMenu() {
        System.out.println("1. View today's showtimes\n2. Create a booking\n3. Create a Membership\n4. Exit");
    }

    public void displayShowtimes(Customer customer) {
        Map<Integer, Showtime> showtimes = cinemaService.displayShowtimes(customer);

        for(Map.Entry<Integer, Showtime> entry : showtimes.entrySet()) {
            Movie movie = cinemaService.getMovie(entry.getValue().getMovieId());
            System.out.println("Showtime " + entry.getKey() + ":\n" + movie.getTitle() + "\n" + movie.getGenre() + "\n" + movie.getReleaseDate() + "\nScreen " + entry.getValue().getScreenId() + "\nStarts at " + entry.getValue().getStartTime() +  "\nDuration: " + entry.getValue().getDuration());
        }
    }

    public void displayAvailableSeats(int showtimeId) {
        Screen screen = cinemaService.getScreen(cinemaService.getShowtime(showtimeId).getScreenId());
        List<Seat> seats = screen.getSeats();

        for(Seat seat : seats) {
            System.out.println("Seat number " + seat.getSeatNr() + " type " + seat.getType() + " costs " + seat.getPrice());
        }
    }

    public void removeSeatsFromAvailable(int showtimeId, List<Integer> seats) {
        Showtime showtime = cinemaService.getShowtime(showtimeId);
        List<Seat> seatsAvailable = showtime.getSeats();

        for(Seat seat : seatsAvailable) {
            if(seats.contains(seat.getSeatNr()))
                seatsAvailable.remove(seat);
        }

        showtime.setSeats(seatsAvailable);
    }

    public int createBooking(int loggedCustomerId, int showtimeId, LocalDate date, int nrOfSeats) {
        return cinemaService.addBooking(loggedCustomerId, showtimeId, date, nrOfSeats);
    }

    public Booking getBooking(int bookingId) {
        return cinemaService.getBooking(bookingId);
    }

    public int createTicket(int bookingId, int seatNr) {
        Showtime showtime = cinemaService.getShowtime(cinemaService.getBooking(bookingId).getShowtimeId());
        Seat seat = cinemaService.findSeatBySeatNr(showtime.getScreenId(), seatNr);
        return cinemaService.addTicket(bookingId, showtime.getScreenId(), seatNr, seat.getPrice());
    }

    public void displayTickets(Customer customer, Booking booking, int ticketId) {
        String ticketInfo = "Booking made by " + customer.getFirstName() + " " + customer.getLastName() + " on " + booking.getDate().toString() + "\n";
        ticketInfo += "Movie " + cinemaService.getMovie(cinemaService.getShowtime(booking.getShowtimeId()).getMovieId()).getTitle() + "\n";
        ticketInfo += "Room " + cinemaService.getTicket(ticketId).getScreenId() + " seat number " + cinemaService.getTicket(ticketId).getSeatNr() + " type " + cinemaService.findSeatBySeatNr(cinemaService.getTicket(ticketId).getScreenId(), cinemaService.getTicket(ticketId).getSeatNr()).getType() + "\n";
        ticketInfo += "Price " + cinemaService.getTicket(ticketId).getPrice() + "\n";
        System.out.println(ticketInfo);
    }

    public void calculateTotalPrice(int loggedCustomerId, int currentBookingId) {
        int type = cinemaService.getMembershipType(loggedCustomerId);
        Membership membership;
        if(type == 1)
            membership = cinemaService.getBasicMembership(cinemaService.getCustomer(loggedCustomerId).getMembershipId());
        else
            membership = cinemaService.getPremiumMembership(cinemaService.getCustomer(loggedCustomerId).getMembershipId());

        List<Integer> tickets = cinemaService.getBooking(currentBookingId).getTickets();

        double totalPrice = 0;
        for(Integer ticket : tickets) {
            totalPrice += cinemaService.getTicket(ticket).getPrice();
        }
        double discountedPrice = cinemaService.calculateDiscountedPrice(totalPrice, membership);

        System.out.println("Price of tickets " + totalPrice + " lei\nDiscount " + (totalPrice - discountedPrice) + " lei\nTotal to pay " + discountedPrice + " lei\n");
    }

    public void staffMenu() {
        System.out.println("1. Modify movie\n2. Modify showtime\n3. Modify screen\n4. Back");
    }

    public void staffMenu2() {
        System.out.println("1. Add\n2. Update\n3. Delete");
    }

    public void addMovie(String title, boolean pg, String genre, LocalDate releaseDate) {
        cinemaService.addMovie(title, pg, genre, releaseDate);
    }

    public void updateMovie( String title, boolean pg, String genre, LocalDate releaseDate) {
        Integer movieId = cinemaService.findMovieIdByTitle(title);
        if (movieId != null) {
            cinemaService.updateMovie(movieId, title, pg, genre, releaseDate);
        } else {
            System.out.println("Movie not found with title: " + title);
        }
    }

    public void deleteMovie(String title){
        Integer movieId = cinemaService.findMovieIdByTitle(title);
        if (movieId != null) {
            cinemaService.deleteMovie(movieId);
        } else {
            System.out.println("Movie not found with title: " + title);
        }
    }
    public Integer findMovieIdByTitle(String title){
        return cinemaService.findMovieIdByTitle(title);
    }

    public void addShowtime(int screenId, int movieId, LocalDate date, LocalTime startTime, int duration) {
        cinemaService.addShowtime(screenId, movieId, date,startTime, duration);
    }

    public void updateShowtime(int id, int screenId, int movieId, LocalDate date, LocalTime startTime, int duration) {
        cinemaService.updateShowtime(id, screenId, movieId, date, startTime, duration, cinemaService.getShowtime(id).getSeats());
    }

    public void deleteShowtime(int id){
        cinemaService.deleteShowtime(id);
    }

    public void addScreen(int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        cinemaService.addScreen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
    }

    public void updateScreen(int id, int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        cinemaService.updateScreen(id, nrStandardSeats, nrVipSeats, nrPremiumSeats);
    }

    public void deleteScreen(int id) {
        cinemaService.deleteScreen(id);
    }
    public BasicMembership createBasicMembership(int customerId, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        return cinemaService.addBasicMembership(customerId, startDate, endDate, bookings);
    }
    public PremiumMembership createPremiumMembership(int customerId, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        return cinemaService.addPremiumMembership(customerId, startDate, endDate, bookings);
    }

}
