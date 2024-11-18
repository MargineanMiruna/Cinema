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
        IRepository<Customer> customerRepo = new FileRepository<>("D:\\Facultate\\Semestrul3\\MAP\\Cinema-Management-Project\\src\\Files\\customers.csv");
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