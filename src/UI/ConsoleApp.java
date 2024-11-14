package UI;

import Controller.Controller;
import Model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    Controller controller;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ConsoleApp(Controller controller) {
        this.controller = controller;
    }

    /**
     * Runs the application by receiving user input and calling the appropriate methods from the Controller Layer.
     */
    public void run() {
        controller.add();
        controller.terminateMemberships();
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter fmt2 = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("\n\u001B[1m" + "=======Welcome to the CinemApp!=======" + "\u001B[0m");
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.println("======================================");
            System.out.println("Please choose an option:\n1. Customer\n2. Staff\n3. Exit\nEnter your choice: ");
            String userType = sc.nextLine();

            switch (userType) {
                case "1": {
                    Customer loggedCustomer;
                    boolean continueLoop2 = true;

                    while(continueLoop2) {
                        System.out.println("""
                                ======================================
                                Please choose an option:
                                1. Log in
                                2. Sign up
                                3. Back
                                Enter your choice:
                                """);

                        String opt = sc.nextLine();

                        switch (opt) {
                            case "1": {
                                loggedCustomer = this.logInCustomer();
                                break;
                            }
                            case "2": {
                                signUpCustomer();
                                loggedCustomer = this.logInCustomer();
                                break;
                            }
                            case "3": {
                                continueLoop2 = false;
                                break;
                            }
                            default: {
                                System.out.println("Invalid input. Please try again.");
                                break;
                            }
                        }
                    }

                    while(continueLoop2) {
                        controller.customerMenu();
                        String opt = sc.nextLine();

                        switch (opt) {
                            case "1": {
                                //display Showtimes
                                controller.displayShowtimes(loggedCustomer);
                                break;
                            }
                            case "2": {
                                //create Booking
                                controller.displayShowtimes(loggedCustomer);
                                this.createBooking(loggedCustomer);
                                break;
                            }
                            case 3: {
                                //create Membership
                                this.createMembership(loggedCustomer);
                                break;
                            }
                            case 4: {
                                System.exit(0);
                            }
                            default : {
                                System.out.println("Invalid input. Please try again.");
                            }
                        }
                    }                }
                case 2: {
                    System.out.println("\n======================================");
                    System.out.println("\nPlease choose an option:\n1. Log in\n2. Sign up\n3. Back\nEnter your choice: ");

                    int opt = sc.nextInt();
                    while(opt > 4) {
                        System.out.println("Invalid input. Please enter a number between 1-4.");
                        opt = sc.nextInt();
                    }
                    sc.nextLine();

                    if (opt == 3)
                        break;
                    if (opt == 2) {
                        System.out.println("\n===============Sign up================");
                        System.out.println("Please enter your first name: ");
                        String firstName = sc.next();
                            System.out.println("Please enter your last name: ");
                            String lastName = sc.next();
                            System.out.println("Please enter your email: ");
                            String email = sc.next();

                            ctrl.createStaff(firstName, lastName, email);
                            System.out.println("\nAccount created successfully!");
                        }

                        System.out.println("\n================Log in================");
                        System.out.println("Please enter your email: ");
                        String email = sc.next();
                        Staff loggedStaff = ctrl.logStaff(email);
                        System.out.println("\nHello, " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + ", you logged in successfully!");
                        boolean action = true;

                        while(action) {
                            ctrl.staffMenu();
                            opt = sc.nextInt();
                            sc.nextLine();

                            switch (opt) {
                                case 1: {
                                    //modify movie
                                    ctrl.staffMenu2();
                                    int opt2 = sc.nextInt();
                                    sc.nextLine();

                                    System.out.println("\n======================================");
                                    switch (opt2) {
                                        case 1: {
                                            //add movie

                                            System.out.println("\nPlease enter movie title: ");
                                            String title = sc.nextLine();

                                            System.out.println("Please enter movie PG (true/false): ");
                                            boolean pg = sc.nextBoolean();
                                            sc.nextLine();

                                            System.out.println("Please enter movie genre: ");
                                            String genre = sc.nextLine();

                                            System.out.println("Please enter movie release date: ");
                                            String date = sc.nextLine();
                                            LocalDate releaseDate = LocalDate.parse(date, fmt);

                                            ctrl.addMovie(title, pg, genre, releaseDate);
                                            System.out.println("\nMovie added successfully!");

                                            break;
                                        }
                                        case 2: {
                                            //update movie
                                            ctrl.displayMoviesStaff();
                                            System.out.println("\nPlease enter title of the movie you want to update: ");
                                            String title = sc.nextLine();

                                            System.out.println("Please enter new PG (true/false): ");
                                            boolean newpg = sc.nextBoolean();
                                            sc.nextLine();

                                            System.out.println("Please enter new genre: ");
                                            String newgenre = sc.nextLine();

                                            System.out.println("Please enter new release date: ");
                                            String date = sc.nextLine();
                                            LocalDate newreleaseDate = LocalDate.parse(date, fmt);

                                            ctrl.updateMovie(title, newpg, newgenre, newreleaseDate);
                                            System.out.println("\nMovie updated successfully!");

                                            break;
                                        }
                                        case 3: {
                                            //delete movie
                                            ctrl.displayMoviesStaff();
                                            System.out.println("\nPlease enter title of the movie you want to delete: ");
                                            String title = sc.nextLine();

                                            ctrl.deleteMovie(title);
                                            System.out.println("\nMovie deleted successfully!");

                                            break;
                                        }
                                        default: {
                                            System.out.println("Invalid input. Please enter a number between 1-3.");
                                            break;
                                        }
                                    }

                                    break;
                                }
                                case 2: {
                                    //modify showtime
                                    ctrl.staffMenu2();
                                    int opt2 = sc.nextInt();
                                    sc.nextLine();

                                    System.out.println("\n======================================");
                                    switch (opt2) {
                                        case 1: {
                                            // add showtime
                                            System.out.println("\nPlease enter screen ID: ");
                                            int screenId = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println("Please enter movie title: ");
                                            String title = sc.nextLine();

                                            int movieId = ctrl.findMovieIdByTitle(title);

                                            System.out.println("Please enter a date: ");
                                            String strDate = sc.nextLine();
                                            LocalDate date = LocalDate.parse(strDate, fmt);

                                            System.out.println("Please enter a starting time: ");
                                            String time = sc.nextLine();
                                            LocalTime startTime = LocalTime.parse(time, fmt2);

                                            System.out.println("Please enter duration: ");
                                            int duration = sc.nextInt();
                                            sc.nextLine();

                                            ctrl.addShowtime(screenId, movieId, date, startTime, duration);
                                            System.out.println("\nShowtime added successfully!");

                                            break;
                                        }
                                        case 2: {
                                            // update showtime
                                            ctrl.displayShowtimesStaff();
                                            System.out.println("\nPlease enter the ID of the showtime you want to update: ");
                                            int id = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println("Please enter new screen ID: ");
                                            int newScreenId = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println("Please enter new movie title: ");
                                            String title = sc.nextLine();

                                            int newMovieId = ctrl.findMovieIdByTitle(title);

                                            System.out.println("Please enter a date: ");
                                            String strDate = sc.nextLine();
                                            LocalDate newDate = LocalDate.parse(strDate, fmt);

                                            System.out.println("Please enter a starting time: ");
                                            String time = sc.nextLine();
                                            LocalTime newStartTime = LocalTime.parse(time, fmt2);

                                            System.out.println("Please enter duration: ");
                                            int newDuration = sc.nextInt();
                                            sc.nextLine();

                                            ctrl.updateShowtime(id, newScreenId, newMovieId, newDate, newStartTime, newDuration);
                                            System.out.println("\nShowtime updated successfully!");

                                            break;
                                        }
                                        case 3: {
                                            //delete showtime
                                            ctrl.displayShowtimesStaff();
                                            System.out.println("\nPlease enter the ID of the showtime you want to delete: ");
                                            int id = sc.nextInt();
                                            sc.nextLine();

                                            ctrl.deleteShowtime(id);
                                            System.out.println("\nShowtime deleted successfully!");

                                            break;
                                        }
                                        default: {
                                            System.out.println("Invalid input. Please enter a number between 1-3.");
                                        }
                                    }

                                    break;
                                }
                                case 3: {
                                    // Modify screen
                                    ctrl.staffMenu2();
                                    int opt2 = sc.nextInt();
                                    sc.nextLine();

                                    System.out.println("\n======================================");
                                    switch (opt2) {
                                        case 1: {
                                            // add screen
                                            System.out.println("\nPlease enter the number of standard seats: ");
                                            int nrStandardSeats = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println("Please enter the number of VIP seats: ");
                                            int nrVipSeats = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println("Please enter the number of premium seats: ");
                                            int nrPremiumSeats = sc.nextInt();
                                            sc.nextLine();

                                            ctrl.addScreen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
                                            System.out.println("\nScreen added successfully!");

                                            break;
                                        }
                                        case 2: {
                                            // update screen
                                            ctrl.displayScreensStaff();
                                            System.out.println("\nPlease enter the ID of the screen you want to update: ");
                                            int id = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println("Please enter new number of standard seats: ");
                                            int newNrStandardSeats = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println("Please enter new number of VIP seats: ");
                                            int newNrVipSeats = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println("Please enter new number of premium seats: ");
                                            int newNrPremiumSeats = sc.nextInt();
                                            sc.nextLine();

                                            ctrl.updateScreen(id, newNrStandardSeats, newNrVipSeats, newNrPremiumSeats);
                                            System.out.println("\nScreen updated successfully!");

                                            break;
                                        }
                                        case 3: {
                                            //delete screen
                                            ctrl.displayScreensStaff();
                                            System.out.println("\nPlease enter the ID of the screen you want to delete: ");
                                            int id = sc.nextInt();
                                            sc.nextLine();

                                            ctrl.deleteScreen(id);
                                            System.out.println("\nScreen deleted successfully!");

                                            break;
                                        }
                                        default: {
                                            System.out.println("Invalid input. Please enter a number between 1-3.");
                                            break;
                                        }
                                    }

                                    break;
                                }
                                case 4: {
                                    action = false;
                                    break;
                                }
                                default: {
                                    System.out.println("Invalid input. Please enter a number between 1-4.");
                                    break;
                                }
                            }
                        }
                        break;
                    }
                case 3: {
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Invalid input. Please enter a number between 1-3!");
                    break;
                }

            }
        }
    }

    public Customer logInCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n================Log in================");

        System.out.println("Please enter your email: ");
        String email = sc.nextLine();

        Customer loggedCustomer = controller.logCustomer(email);
        System.out.println("\nHello, " + loggedCustomer.getFirstName() + " " + loggedCustomer.getLastName() + ", you logged in successfully!");

        return loggedCustomer;
    }

    public void signUpCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===============Sign up================");

        System.out.println("Please enter your first name: ");
        String firstName = sc.nextLine();

        System.out.println("Please enter your last name: ");
        String lastName = sc.nextLine();

        System.out.println("Please enter your email: ");
        String email = sc.nextLine();

        System.out.println("Please enter your birthday (dd-MM-yyyy): ");
        String date = sc.nextLine();

        boolean invalidDate = true;
        while(invalidDate) {
            try {
                LocalDate birthday = LocalDate.parse(date, dateFormatter);
                controller.createCustomer(firstName, lastName, email, birthday);
                System.out.println("\nAccount created successfully!");
                invalidDate = false;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd-MM-yyyy.");
            }
        }
    }

    public void createBooking(Customer loggedCustomer) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n======================================");

        System.out.println("\nPlease choose the Showtime number: ");
        int showtimeId = sc.nextInt();
        sc.nextLine();

        System.out.println("Number of tickets you want to buy: ");
        int nrOfTickets = sc.nextInt();
        sc.nextLine();

        controller.displayAvailableSeats(showtimeId);
        System.out.println("\nPlease choose your seats: ");
        List<Integer> seats = new ArrayList<>();
        for(int i = 0; i < nrOfTickets; i++) {
            seats.add(sc.nextInt());
            sc.nextLine();
        }
        controller.removeSeatsFromAvailable(showtimeId, seats);

        int currentBookingId = controller.createBooking(loggedCustomer.getId(), showtimeId, LocalDate.now(), nrOfTickets);
        System.out.println("\nBooking created successfully!");

        List<Integer> tickets = new ArrayList<>();
        for (int i = 0; i < nrOfTickets; i++) {
            tickets.add(controller.createTicket(currentBookingId, seats.get(i)));
        }

        Booking currentBooking = controller.getBooking(currentBookingId);
        currentBooking.setTickets(tickets);

        System.out.println("\nYour tickets: ");
        for (int i = 0; i < nrOfTickets; i++) {
            controller.displayTickets(loggedCustomer, currentBooking, tickets.get(i));
        }

        System.out.println("\n======================================");
        System.out.println("\nYour total: ");
        controller.calculateTotalPrice(loggedCustomer.getId(), currentBookingId);
    }

    public void createMembership(Customer loggedCustomer) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n======================================");
        System.out.println("""
                Please choose the type of membership you want to purchase:
                1. Basic
                2. Premium
                Enter your choice:
                """);
        String type = sc.nextLine();
        LocalDate starDate = LocalDate.now();
        LocalDate endDate = starDate.plusDays(30);

        switch (type) {
            case "1": {
                BasicMembership membership = controller.createBasicMembership(loggedCustomer.getId(), starDate, endDate);
                System.out.println("\nYour total is: " + membership.getPrice());
                break;
            }
            case "2": {
                PremiumMembership membership = controller.createPremiumMembership(loggedCustomer.getId(), starDate, endDate);
                System.out.println("\nYour total is: " + membership.getPrice());
                break;
            }
        }

        System.out.println("\nMembership created successfully! ");
    }
}