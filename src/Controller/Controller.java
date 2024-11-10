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
}
