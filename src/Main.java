import Controller.Controller;
import Model.*;
import Repository.FileRepository;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.CinemaService;
import UI.ConsoleApp;

import java.awt.print.Book;

class Main {
    public static void main(String[] args) {
        IRepository<Customer> customerRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\customer.csv", Customer::fromCSV);
        //IRepository<Customer> customerRepo = new InMemoryRepository<>();

        IRepository<Staff> staffRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\staff.csv", Staff::fromCSV);
        //IRepository<Staff> staffRepo = new InMemoryRepository<>();

        IRepository<Booking> bookingRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\booking.csv", Booking::fromCSV);
        //IRepository<Booking> bookingRepo = new InMemoryRepository<>();

        IRepository<Showtime> showtimeRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\showtime.csv", Showtime::fromCSV);
        //IRepository<Showtime> showtimeRepo = new InMemoryRepository<>();

        IRepository<Movie> movieRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\movie.csv", Movie::fromCSV);
        //IRepository<Movie> movieRepo = new InMemoryRepository<>();

        IRepository<Screen> screenRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\screen.csv", Screen::fromCSV);
        //IRepository<Screen> screenRepo = new InMemoryRepository<>();

        IRepository<Seat> seatRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\seat.csv", Seat::fromCSV);
        //IRepository<Seat> seatRepo = new InMemoryRepository<>();

        IRepository<Ticket> ticketRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\ticket.csv", Ticket::fromCSV);
        //IRepository<Ticket> ticketRepo = new InMemoryRepository<>();

        IRepository<BasicMembership> basicMembershipRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\basicMembership.csv", BasicMembership::fromCSV);
        //IRepository<BasicMembership> basicMembershipRepo = new InMemoryRepository<>();

        IRepository<PremiumMembership> premiumMembershipRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\premiumMembership.csv", PremiumMembership::fromCSV);
        //IRepository<PremiumMembership> premiumMembershipRepo = new InMemoryRepository<>();

        CinemaService cinemaService = new CinemaService(customerRepo, staffRepo, movieRepo, showtimeRepo, screenRepo, seatRepo, bookingRepo, ticketRepo, basicMembershipRepo, premiumMembershipRepo);
        Controller controller = new Controller(cinemaService);

        ConsoleApp app = new ConsoleApp(controller);
        app.run();
    }
}