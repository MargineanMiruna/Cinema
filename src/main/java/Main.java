import Controller.Controller;
import Model.*;
import Repository.Database.*;
import Repository.FileRepository;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.CinemaService;
import UI.ConsoleApp;
import Exception.ValidationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean invalid = true;
        while (invalid) {
            try {
                System.out.println("""
                            Choose an option:
                            1. In memory repository
                            2. File repository
                            3. Database repository""");

                String option = sc.nextLine();

                switch (option) {
                    case "1": {
                        InMemoryOption();
                        invalid = false;
                        break;
                    }
                    case "2": {
                        FileOption();
                        invalid = false;
                        break;
                    }
                    case "3": {
                        DatabaseOption();
                        invalid = false;
                        break;
                    }
                    default: {
                        throw new ValidationException("Invalid option. Please try again.");
                    }
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void InMemoryOption() {
        IRepository<Customer> customerRepo = new InMemoryRepository();
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

    public static void FileOption() {
        IRepository<Customer> customerRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\main\\java\\Files\\customer.csv", Customer::fromCSV);
        IRepository<Staff> staffRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\main\\java\\Files\\staff.csv", Staff::fromCSV);
        IRepository<Booking> bookingRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\main\\java\\Files\\booking.csv", Booking::fromCSV);
        IRepository<Showtime> showtimeRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\main\\java\\Files\\showtime.csv", Showtime::fromCSV);
        IRepository<Movie> movieRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\main\\java\\Files\\movie.csv", Movie::fromCSV);
        IRepository<Screen> screenRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\main\\java\\Files\\screen.csv", Screen::fromCSV);
        IRepository<Seat> seatRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\main\\java\\Files\\seat.csv", Seat::fromCSV);
        IRepository<Ticket> ticketRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\main\\java\\Files\\ticket.csv", Ticket::fromCSV);
        IRepository<BasicMembership> basicMembershipRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\main\\java\\Files\\basicMembership.csv", BasicMembership::fromCSV);
        IRepository<PremiumMembership> premiumMembershipRepo = new FileRepository<>(System.getProperty("user.dir") + "\\src\\main\\java\\Files\\premiumMembership.csv", PremiumMembership::fromCSV);

        CinemaService cinemaService = new CinemaService(customerRepo, staffRepo, movieRepo, showtimeRepo, screenRepo, seatRepo, bookingRepo, ticketRepo, basicMembershipRepo, premiumMembershipRepo);
        Controller controller = new Controller(cinemaService);

        ConsoleApp app = new ConsoleApp(controller);
        app.run();
    }

    public static void DatabaseOption() {
        Connection connection;
        final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/java/Files/cinemaDB.db";

        try {
            connection = DriverManager.getConnection(DB_URL, "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        IRepository<Customer> customerRepo = new CustomerDBRepository(connection);
        IRepository<Staff> staffRepo = new StaffDBRepository(connection);
        IRepository<Booking> bookingRepo = new BookingDBRepository(connection);
        IRepository<Showtime> showtimeRepo = new ShowtimeDBRepository(connection);
        IRepository<Movie> movieRepo = new MovieDBRepository(connection);
        IRepository<Screen> screenRepo = new ScreenDBRepository(connection);
        IRepository<Seat> seatRepo = new SeatDBRepository(connection);
        IRepository<Ticket> ticketRepo = new TicketDBRepository(connection);
        IRepository<BasicMembership> basicMembershipRepo = new BasicMembershipDBRepository(connection);
        IRepository<PremiumMembership> premiumMembershipRepo = new PremiumMembershipDBRepository(connection);

        CinemaService cinemaService = new CinemaService(customerRepo, staffRepo, movieRepo, showtimeRepo, screenRepo, seatRepo, bookingRepo, ticketRepo, basicMembershipRepo, premiumMembershipRepo);
        Controller controller = new Controller(cinemaService);

        ConsoleApp app = new ConsoleApp(controller);
        app.run();
    }
}