package Controller;


import Domain.*;
import Service.CinemaService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

public class Controller {
    CinemaService cinemaService = new CinemaService();

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
        System.out.println("1. View today's showtimes\n2. Create a booking\n3. Create a Membership");
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

    public void createBooking(int loggedCustomerId, int showtimeId, LocalDate date, int nrOfSeats) {
        cinemaService.addBooking(loggedCustomerId, showtimeId, date, nrOfSeats);
    }

    public int getIdOfBooking(Booking booking) {
        return cinemaService.getIdOfBooking(booking);
    }

    public void createTickets(int bookingId, List<Integer> seats) {
        for(int i = 0; i < seats.size(); i++) {
            cinemaService.addTicket(bookingId, cinemaService.getSeat(seats.get(i)).getSeatNr(), cinemaService.getSeat(seats.get(i)).getPrice());
        }
    }

    public List<Ticket> currentBookingTickets(int bookingId) {
        return cinemaService.bookingTickets(bookingId);
    }

    public void staffMenu() {
        System.out.println("1. Modify movie\n2. Modify showtime\n3. Modify screen");
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

    public void addShowtime(int screenId, int movieId, LocalDate date, int startTime, double duration) {
        cinemaService.addShowtime(screenId, movieId, date,startTime, duration);
    }

    public void updateShowtime(int id, int screenId, int movieId, LocalDate date,int startTime, double duration) {
        cinemaService.updateShowtime(id, screenId, movieId, date, startTime, duration);
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
    public void deleteScreen(int id){
        cinemaService.deleteScreen(id);
    }






}
