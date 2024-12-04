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

/**
 * The ConsoleApp class provides a command-line interface for managing a cinema booking system.
 * It interacts with the Controller to handle various operations, such as customer and staff
 * management, booking creation, membership handling, and showtime display.
 */
public class ConsoleApp {
    Controller controller;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructs a new ConsoleApp instance with the specified controller.
     *
     * @param controller the controller used to handle application logic
     */
    public ConsoleApp(Controller controller) {
        this.controller = controller;
    }

    /**
     * Runs the application by receiving user input and calling the appropriate methods from the Controller Layer.
     */
    public void run() {
        //controller.add();
        controller.terminateMemberships();
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter fmt2 = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("\n\u001B[1m" + "=======Welcome to the CinemApp!=======" + "\u001B[0m");
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.println("======================================");
            System.out.println("""
                    Please choose an option:
                    1. Customer
                    2. Staff
                    3. Exit
                    Enter your choice:
                    """);
            String userType = sc.nextLine();

            switch (userType) {
                case "1": {
                    Customer loggedCustomer;

                    boolean continueLoop2 = true;
                    while (continueLoop2) {
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
                                if(loggedCustomer != null)
                                    this.customerMenu(loggedCustomer);
                                break;
                            }
                            case "2": {
                                signUpCustomer();
                                loggedCustomer = this.logInCustomer();
                                if(loggedCustomer != null)
                                    this.customerMenu(loggedCustomer);
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
                    break;
                }
                case "2": {
                    Staff loggedStaff;

                    boolean continueLoop3 = true;
                    while (continueLoop3) {
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
                                loggedStaff = this.logInStaff();
                                this.staffMenu(loggedStaff);
                                break;
                            }
                            case "2": {
                                this.signUpStaff();
                                loggedStaff = this.logInStaff();
                                this.staffMenu(loggedStaff);
                                break;
                            }
                            case "3": {
                                continueLoop3 = false;
                                break;
                            }
                            default: {
                                System.out.println("Invalid input. Please try again.");
                                break;
                            }
                        }
                    }

                    break;
                }
                case "3": {
                    continueLoop = false;
                    break;
                }
                default: {
                    System.out.println("Invalid input. Please enter a number between 1-3!");
                    break;
                }
            }
        }

        System.out.println("Thank you for using CinemApp!");
    }

    /**
     * Prompts the customer to log in by entering their email address and retrieves the customer
     * details from the controller.
     *
     * @return the logged-in Customer instance
     */
    public Customer logInCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n================Log in================");

        String email = "";
        boolean isValid = false;

        while (!isValid) {
            System.out.println("Please enter your email: ");
            email = sc.nextLine();

            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Please try again.");
            } else {
                isValid = true;
            }
        }

        Customer loggedCustomer = controller.logCustomer(email);

        if (loggedCustomer == null) {
                System.out.println("No customer found with the provided email.");
                return null;
            }

        System.out.println("\nHello, " + loggedCustomer.getFirstName() + " " + loggedCustomer.getLastName() + ", you logged in successfully!");

        return loggedCustomer;
    }

    /**
     * Prompts a new customer to sign up by entering their details, such as name, email, and birthday,
     * and creates a customer account using the controller.
     */
    public void signUpCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===============Sign up================");

        String firstName = "";
        while (true) {
            System.out.println("Please enter your first name: ");
            firstName = sc.nextLine();
            if (firstName.isEmpty() || !firstName.matches("^[A-Za-z]+$")) {
                System.out.println("First name must contain only letters and cannot be empty. Please try again.");
            } else {
                break;
            }
        }

        String lastName = "";
        while (true) {
            System.out.println("Please enter your last name: ");
            lastName = sc.nextLine();
            if (lastName.isEmpty() || !lastName.matches("^[A-Za-z]+$")) {
                System.out.println("Last name must contain only letters and cannot be empty. Please try again.");
            } else {
                break;
            }
        }

        System.out.println("Please enter your email: ");
        String email = sc.nextLine();

        boolean invalidDate = true;
        while (invalidDate) {
            System.out.println("Please enter your birthday (dd-MM-yyyy): ");
            String date = sc.nextLine();

            try {
                LocalDate birthday = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                if (birthday.isAfter(LocalDate.now())) {
                    System.out.println("Birthdate cannot be in the future. Please try again.");
                } else {
                    controller.createCustomer(firstName, lastName, email, birthday);
                    System.out.println("\nAccount created successfully!");
                    invalidDate = false;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd-MM-yyyy.");
            }
        }
    }

    /**
     * Allows a logged-in customer to create a new booking by selecting a showtime, choosing tickets,
     * and selecting seats. Finalizes the booking and displays ticket details.
     *
     * @param loggedCustomer the currently logged-in customer
     */
    public void createBooking(Customer loggedCustomer) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n======================================");

        int showtimeId = -1;
        while (true) {
            System.out.println("\nPlease choose the Showtime number: ");
            if (sc.hasNextInt()) {
                showtimeId = sc.nextInt();
                sc.nextLine();
                if (controller.isShowtimeAvailable(showtimeId)) {
                    break;
                } else {
                    System.out.println("Invalid Showtime number. Please try again.");
                }
            } else {
                System.out.println("Please enter a valid number for Showtime.");
                sc.nextLine();
            }
        }

        int nrOfTickets = -1;
        while (true) {
            System.out.println("Number of tickets you want to buy: ");
            if (sc.hasNextInt()) {
                nrOfTickets = sc.nextInt();
                sc.nextLine();
                if (nrOfTickets > 0) {
                    break;
                } else {
                    System.out.println("Please enter a valid number of tickets (greater than 0).");
                }
            } else {
                System.out.println("Please enter a valid number for tickets.");
                sc.nextLine();
            }
        }

        int typeOfTickets = -1;
        while (true) {
            System.out.println("Type of tickets you want to buy \n 1. standard - 30 lei \n 2. vip - 40 lei \n 3. premium - 50 lei ");
            if (sc.hasNextInt()) {
                typeOfTickets = sc.nextInt();
                sc.nextLine();
                if (typeOfTickets >= 1 && typeOfTickets <= 3) {
                    break;
                } else {
                    System.out.println("Invalid ticket type. Please choose 1, 2, or 3.");
                }
            } else {
                System.out.println("Please enter a valid number for ticket type.");
                sc.nextLine();
            }
        }

        controller.displayAvailableSeats(showtimeId, typeOfTickets);

        List<Integer> seats = new ArrayList<>();
        System.out.println("\nPlease choose your seats: ");
        for (int i = 0; i < nrOfTickets; i++) {
            int seat = -1;
            while (true) {
                if (sc.hasNextInt()) {
                    seat = sc.nextInt();
                    sc.nextLine();
                    if (controller.isSeatAvailable(showtimeId, seat)) {
                        if (!seats.contains(seat)) {
                            seats.add(seat);
                            break;
                        } else {
                            System.out.println("Seat " + seat + " has already been selected. Please choose another one.");
                        }
                    } else {
                        System.out.println("Seat " + seat + " is not available. Please choose a different one.");
                    }
                } else {
                    System.out.println("Please enter a valid seat number.");
                    sc.nextLine();
                }
            }
        }

        controller.removeSeatsFromAvailable(showtimeId, seats);

        int currentBookingId = controller.createBooking(loggedCustomer.getId(), showtimeId, LocalDate.now(), nrOfTickets, seats);
        System.out.println("\nBooking created successfully!");

        controller.displayTickets(loggedCustomer, controller.getBooking(currentBookingId));
        controller.calculateTotalPrice(loggedCustomer.getId(), currentBookingId);
    }

    /**
     * Allows a logged-in customer to purchase a membership. Provides options for Basic or Premium
     * memberships and calculates the total price.
     *
     * @param loggedCustomer the currently logged-in customer
     */
    public void createMembership(Customer loggedCustomer) {
        Scanner sc = new Scanner(System.in);

        boolean invalidOption = true;
        while(invalidOption) {
            System.out.println("\n======================================");
            System.out.println("""
                Please choose the type of membership you want to purchase:
                1. Basic
                2. Premium
                Enter your choice:
                """);
            String type = sc.nextLine();

            switch (type) {
                case "1": {
                    BasicMembership membership = controller.createBasicMembership(loggedCustomer.getId(), LocalDate.now(), LocalDate.now().plusDays(30));
                    System.out.println("\nYour total is: " + membership.getPrice());
                    invalidOption = false;
                    break;
                }
                case "2": {
                    PremiumMembership membership = controller.createPremiumMembership(loggedCustomer.getId(), LocalDate.now(), LocalDate.now().plusDays(30));
                    System.out.println("\nYour total is: " + membership.getPrice());
                    invalidOption = false;
                    break;
                }
                default: {
                    System.out.println("Invalid input. Please enter a number between 1-3!");
                    break;
                }
            }
        }

        System.out.println("\nMembership created successfully! ");
    }

    /**
     * Displays showtimes based on user-selected filters or sorting criteria, such as date, movie title,
     * or duration.
     *
     * @param loggedCustomer the currently logged-in customer
     */
    public void displayShowtimes(Customer loggedCustomer) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=====================================");
        System.out.println("Display \n 1. all showtimes \n 2. showtimes filtered by date \n 3. showtimes filtered by movie \n 4. showtimes sorted by duration \n 5. showtimes sorted by date");

        Integer showtimesId = sc.nextInt();
        sc.nextLine();
        switch(showtimesId) {
            case 1: {
                controller.displayShowtimesFilteredByPg(loggedCustomer);
                break;
            }
            case 2: {
                System.out.println("Please enter a date (dd-MM-yyyy): ");
                String date = sc.nextLine();

                try {
                    LocalDate date1 = LocalDate.parse(date, dateFormatter);
                    controller.displayShowtimesFilteredByDate(loggedCustomer,date1);

                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                }
                break;
            }
            case 3:{
                System.out.println("Please enter the movie title: ");
                String movieTitle = sc.nextLine();
                controller.displayFilteredShowtimesByMovie(loggedCustomer, movieTitle);
                break;
            }
            case 4:{
                controller.displaySortedShowtimesByDuration(loggedCustomer);
                break;
            }
            case 5:{
                controller.displaySortedShowtimesByDateAsc(loggedCustomer);
                break;
            }
            default: {
                System.out.println("Invalid option. Please choose 1, 2, 3, 4 or 5.");
                break;
            }

        }

    }

    /**
     * Presents the customer menu, allowing the logged-in customer to perform actions such as
     * viewing showtimes, creating bookings, managing memberships, and viewing booking history.
     *
     * @param loggedCustomer the currently logged-in customer
     */
    public void customerMenu(Customer loggedCustomer) {
        Scanner sc = new Scanner(System.in);

        boolean continueLoop = true;
        while(continueLoop) {
            controller.customerMenu();
            String option = sc.nextLine();

            switch (option) {
                case "1": {
                    //display Showtimes
                    this.displayShowtimes(loggedCustomer);
                    break;
                }
                case "2": {
                    //create Booking
                    this.displayShowtimes(loggedCustomer);
                    this.createBooking(loggedCustomer);
                    break;
                }
                case "3": {
                    //create Membership
                    this.createMembership(loggedCustomer);
                    break;
                }
                case "4":{
                    //view Booking history
                    controller.displayBookingsWithShowtimes(loggedCustomer);
                    break;
                }
                case "5": {
                    //back
                    continueLoop = false;
                    break;
                }
                default: {
                    System.out.println("Invalid input. Please enter a number between 1-4!");
                    break;
                }
            }
        }
    }

    /**
     * Logs in a staff member by prompting for their email and retrieves their information.
     *
     * @return the logged-in Staff instance
     */
    public Staff logInStaff() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n================Log in================");

        System.out.println("Please enter your email: ");
        String email = sc.nextLine();

        Staff loggedStaff = controller.logStaff(email);
        System.out.println("\nHello, " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + ", you logged in successfully!");

        return loggedStaff;
    }

    /**
     * Signs up a new staff member by collecting their details and creating an account.
     * Prompts the user to enter their information.
     */
    public void signUpStaff() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===============Sign up================");

        System.out.println("Please enter your first name: ");
        String firstName = sc.next();

        System.out.println("Please enter your last name: ");
        String lastName = sc.next();

        System.out.println("Please enter your email: ");
        String email = sc.next();

        controller.createStaff(firstName, lastName, email);
        System.out.println("\nAccount created successfully!");
    }

    /**
     * Allows a logged-in staff member to modify movie details, including adding, updating, or deleting movies.
     * Provides options to perform each of these actions and handles input validation.
     *
     * @param loggedStaff the currently logged-in staff member
     */
    public void modifyMovie(Staff loggedStaff) {
        Scanner sc = new Scanner(System.in);

        boolean invalidOption = true;
        while(invalidOption) {
            controller.staffMenu2();
            String option = sc.nextLine();
            System.out.println("\n======================================");


            switch (option) {
                case "1": {
                    //add Movie
                    System.out.println("\nPlease enter movie title: ");
                    String title = sc.nextLine();

                    System.out.println("Please enter movie PG (true/false): ");
                    boolean pg = sc.nextBoolean();
                    sc.nextLine();

                    System.out.println("Please enter movie genre: ");
                    String genre = sc.nextLine();

                    boolean invalidTime = true;
                    while(invalidTime) {
                        System.out.println("Please enter movie release date: ");
                        String date = sc.nextLine();

                        try {
                            LocalDate releaseDate = LocalDate.parse(date, timeFormatter);
                            controller.addMovie(title, pg, genre, releaseDate);
                            System.out.println("\nMovie added successfully!");
                            invalidTime = false;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use HH:mm.");
                        }
                    }

                    invalidOption = false;
                    break;
                }
                case "2": {
                    //update Movie
                    controller.displayMoviesStaff();
                    System.out.println("\nPlease enter title of the movie you want to update: ");
                    String title = sc.nextLine();

                    System.out.println("Please enter new PG (true/false): ");
                    boolean newPg = sc.nextBoolean();
                    sc.nextLine();

                    System.out.println("Please enter new genre: ");
                    String newGenre = sc.nextLine();

                    boolean invalidTime = true;
                    while(invalidTime) {
                        System.out.println("Please enter new release date: ");
                        String date = sc.nextLine();

                        try {
                            LocalDate newReleaseDate = LocalDate.parse(date, timeFormatter);
                            controller.updateMovie(title, newPg, newGenre, newReleaseDate);
                            System.out.println("\nMovie updated successfully!");
                            invalidTime = false;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use HH:mm.");
                        }
                    }

                    invalidOption = false;
                    break;
                }
                case "3": {
                    //delete Movie
                    controller.displayMoviesStaff();
                    System.out.println("\nPlease enter title of the movie you want to delete: ");
                    String title = sc.nextLine();

                    controller.deleteMovie(title);
                    System.out.println("\nMovie deleted successfully!");

                    invalidOption = false;
                    break;
                }
                default: {
                    System.out.println("Invalid input. Please enter a number between 1-3!");
                    break;
                }
            }
        }
    }

    /**
     * Allows a logged-in staff member to modify screen details, including adding, updating, or deleting screens.
     * Provides options to perform each of these actions and handles input validation.
     *
     * @param loggedStaff the currently logged-in staff member
     */
    public void modifyScreen(Staff loggedStaff) {
        Scanner sc = new Scanner(System.in);

        boolean invalidOption = true;
        while(invalidOption) {
            controller.staffMenu2();
            String option = sc.nextLine();
            System.out.println("\n======================================");

            switch (option) {
                case "1": {
                    //add Screen
                    System.out.println("\nPlease enter the number of standard seats: ");
                    int nrStandardSeats = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Please enter the number of VIP seats: ");
                    int nrVipSeats = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Please enter the number of premium seats: ");
                    int nrPremiumSeats = sc.nextInt();
                    sc.nextLine();

                    controller.addScreen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
                    System.out.println("\nScreen added successfully!");

                    invalidOption = false;
                    break;
                }
                case "2": {
                    //update Screen
                    controller.displayScreensStaff();
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

                    List<Seat> seats = new ArrayList<>();
                    controller.updateScreen(id, newNrStandardSeats, newNrVipSeats, newNrPremiumSeats, seats);
                    System.out.println("\nScreen updated successfully!");

                    invalidOption = false;
                    break;
                }
                case "3": {
                    //delete Screen
                    controller.displayScreensStaff();
                    System.out.println("\nPlease enter the ID of the screen you want to delete: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    controller.deleteScreen(id);
                    System.out.println("\nScreen deleted successfully!");

                    invalidOption = false;
                    break;
                }
                default: {
                    System.out.println("Invalid input. Please enter a number between 1-3!");
                    break;
                }
            }
        }
    }

    /**
     * Allows a logged-in staff member to modify showtime details, including adding, updating, or deleting showtimes.
     * Provides options to perform each of these actions and handles input validation.
     *
     * @param loggedStaff the currently logged-in staff member
     */
    public void modifyShowtime(Staff loggedStaff) {
        Scanner sc = new Scanner(System.in);

        boolean invalidOption = true;
        while(invalidOption) {
            controller.staffMenu2();
            String option = sc.nextLine();
            System.out.println("\n=====================================");

            switch (option) {
                case "1": {
                    //add Showtime
                    System.out.println("\nPlease enter screen ID: ");
                    int screenId = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Please enter movie title: ");
                    String title = sc.nextLine();

                    int movieId = controller.findMovieIdByTitle(title);

                    LocalDate showtimeDate = LocalDate.now();
                    boolean invalidDate = true;
                    while(invalidDate) {
                        System.out.println("Please enter a date: ");
                        String date = sc.nextLine();

                        try {
                            showtimeDate = LocalDate.parse(date, dateFormatter);
                            invalidDate = false;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                        }
                    }

                    LocalTime startTime = LocalTime.now();
                    invalidDate = true;
                    while(invalidDate) {
                        System.out.println("Please enter a starting time: ");
                        String time = sc.nextLine();

                        try {
                            startTime = LocalTime.parse(time, timeFormatter);
                            invalidDate = false;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use HH:mm.");
                        }
                    }

                    System.out.println("Please enter duration: ");
                    int duration = sc.nextInt();
                    sc.nextLine();

                    controller.addShowtime(screenId, movieId, showtimeDate, startTime, duration);
                    System.out.println("\nShowtime added successfully!");

                    invalidOption = false;
                    break;
                }
                case "2": {
                    //update Showtime
                    controller.displayShowtimesStaff();
                    System.out.println("\nPlease enter the ID of the showtime you want to update: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Please enter new screen ID: ");
                    int newScreenId = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Please enter new movie title: ");
                    String title = sc.nextLine();

                    int newMovieId = controller.findMovieIdByTitle(title);

                    LocalDate newShowtimeDate = LocalDate.now();
                    boolean invalidDate = true;
                    while(invalidDate) {
                        System.out.println("Please enter a date: ");
                        String date = sc.nextLine();

                        try {
                            newShowtimeDate = LocalDate.parse(date, dateFormatter);
                            invalidDate = false;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                        }
                    }

                    LocalTime newStartTime = LocalTime.now();
                    invalidDate = true;
                    while(invalidDate) {
                        System.out.println("Please enter a starting time: ");
                        String time = sc.nextLine();

                        try {
                            newStartTime = LocalTime.parse(time, timeFormatter);
                            invalidDate = false;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use HH:mm.");
                        }
                    }

                    System.out.println("Please enter duration: ");
                    int newDuration = sc.nextInt();
                    sc.nextLine();

                    controller.updateShowtime(id, newScreenId, newMovieId, newShowtimeDate, newStartTime, newDuration);
                    System.out.println("\nShowtime updated successfully!");

                    invalidOption = false;
                    break;
                }
                case "3": {
                    //delete Showtime
                    controller.displayShowtimesStaff();
                    System.out.println("\nPlease enter the ID of the showtime you want to delete: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    controller.deleteShowtime(id);
                    System.out.println("\nShowtime deleted successfully!");

                    invalidOption = false;
                    break;
                }
                default: {
                    System.out.println("Invalid input. Please enter a number between 1-3!");
                    break;
                }
            }
        }
    }

    /**
     * Presents the staff menu, allowing the logged-in staff member to perform actions such as
     * modifying movies, showtimes, and screens, or going back to the previous menu.
     *
     * @param loggedStaff the currently logged-in staff member
     */
    public void staffMenu(Staff loggedStaff) {
        Scanner sc = new Scanner(System.in);

        boolean continueLoop = true;
        while(continueLoop) {
            controller.staffMenu();
            String option = sc.nextLine();

            switch (option) {
                case "1": {
                    //modify Movie
                    this.modifyMovie(loggedStaff);
                    break;
                }
                case "2": {
                    //modify Showtime
                    this.modifyShowtime(loggedStaff);
                    break;
                }
                case "3": {
                    //modify Screen
                    this.modifyScreen(loggedStaff);
                    break;
                }
                case "4": {
                    //back
                    continueLoop = false;
                    break;
                }
                default: {
                    System.out.println("Invalid input. Please enter a number between 1-3!");
                    break;
                }
            }
        }
    }
}