import Controller.Controller;
import Model.*;
import Repository.Database.*;
import Repository.FileRepository;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.CinemaService;
import UI.ConsoleApp;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Main {
    public static void main(String[] args) {
//        Connection connection;
//        final String DB_URL = "jdbc:sqlite:D:/Facultate/Semestrul3/MAP/Cinema-Management-Project/cinemaDB.db";
//        final String DB_URL = "jdbc:sqlite:C:/Users/aleol/Facultate/Sem3/FP/Cinema-Management-Project/cinemaDB.db";
//
//        try {
//            connection = DriverManager.getConnection(DB_URL, "user", "password");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//       // Database Repository
//        IRepository<Customer> customerRepo = new CustomerDBRepository(connection);
//        IRepository<Staff> staffRepo = new StaffDBRepository(connection);
//        IRepository<Booking> bookingRepo = new BookingDBRepository(connection);
//        IRepository<Showtime> showtimeRepo = new ShowtimeDBRepository(connection);
//        IRepository<Movie> movieRepo = new MovieDBRepository(connection);
//        IRepository<Screen> screenRepo = new ScreenDBRepository(connection);
//        IRepository<Seat> seatRepo = new SeatDBRepository(connection);
//        IRepository<Ticket> ticketRepo = new TicketDBRepository(connection);
//        IRepository<BasicMembership> basicMembershipRepo = new BasicMembershipDBRepository(connection);
//        IRepository<PremiumMembership> premiumMembershipRepo = new PremiumMembershipDBRepository(connection);

//        //File Repository
//        IRepository<Customer> customerRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\customer.csv", Customer::fromCSV);
//        IRepository<Staff> staffRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\staff.csv", Staff::fromCSV);
//        IRepository<Booking> bookingRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\booking.csv", Booking::fromCSV);
//        IRepository<Showtime> showtimeRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\showtime.csv", Showtime::fromCSV);
//        IRepository<Movie> movieRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\movie.csv", Movie::fromCSV);
//        IRepository<Screen> screenRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\screen.csv", Screen::fromCSV);
//        IRepository<Seat> seatRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\seat.csv", Seat::fromCSV);
//        IRepository<Ticket> ticketRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\ticket.csv", Ticket::fromCSV);
//        IRepository<BasicMembership> basicMembershipRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\basicMembership.csv", BasicMembership::fromCSV);
//        IRepository<PremiumMembership> premiumMembershipRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\Files\\premiumMembership.csv", PremiumMembership::fromCSV);


        //In-Memory Repository
        IRepository<Customer> customerRepo = new InMemoryRepository<>();
        IRepository<Staff> staffRepo = new InMemoryRepository<>();
        IRepository<Booking> bookingRepo = new InMemoryRepository<>();
        IRepository<Showtime> showtimeRepo = new InMemoryRepository<>();
        IRepository<Movie> movieRepo = new InMemoryRepository<>();
        IRepository<Screen> screenRepo = new InMemoryRepository<>();
        IRepository<Seat> seatRepo = new InMemoryRepository<>();
        IRepository<Ticket> ticketRepo = new InMemoryRepository<>();
        IRepository<BasicMembership> basicMembershipRepo = new InMemoryRepository<>();
        IRepository<PremiumMembership> premiumMembershipRepo = new InMemoryRepository<>();

        CinemaService cinemaService = new CinemaService(customerRepo, staffRepo, movieRepo, showtimeRepo, screenRepo, seatRepo, bookingRepo, ticketRepo, basicMembershipRepo, premiumMembershipRepo);
        Controller controller = new Controller(cinemaService);

        ConsoleApp app = new ConsoleApp(controller);
        app.run();
    }
}