package UI;

import Controller.Controller;
import Model.*;
import Exception.*;

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
                                throw new ValidationException("Invalid input. Please try again.");
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
                                if (loggedStaff!= null)
                                    this.staffMenu(loggedStaff);
                                break;
                            }
                            case "2": {
                                this.signUpStaff();
                                loggedStaff = this.logInStaff();
                                if (loggedStaff!= null)
                                    this.staffMenu(loggedStaff);
                                break;
                            }
                            case "3": {
                                continueLoop3 = false;
                                break;
                            }
                            default: {
                                throw new ValidationException("Invalid input. Please try again.");
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
                    throw new ValidationException("Invalid input. Please enter a number between 1-3!");
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
        Customer loggedCustomer = null;

        while (loggedCustomer == null) {
            System.out.println("Please enter your email: ");
            email = sc.nextLine();

            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Please try again.");
                continue;
            }

            loggedCustomer = controller.logCustomer(email);

            if (loggedCustomer == null) {
                throw new EntityNotFoundException("No account found with the email: " + email + ". Please try again.");
            }
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
                throw new ValidationException("First name must contain only letters and cannot be empty. Please try again.");
            } else {
                break;
            }
        }

        String lastName = "";
        while (true) {
            System.out.println("Please enter your last name: ");
            lastName = sc.nextLine();
            if (lastName.isEmpty() || !lastName.matches("^[A-Za-z]+$")) {
                throw new ValidationException("Last name must contain only letters and cannot be empty. Please try again.");
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
                LocalDate birthday = LocalDate.parse(date, dateFormatter);
                if (birthday.isAfter(LocalDate.now())) {
                    throw new ValidationException("Birthdate cannot be in the future. Please try again.");
                } else if (birthday.getYear() < 1900) {
                    throw  new ValidationException("Birth year cannot be earlier than 1900. Please try again.");
                } else {
                    controller.createCustomer(firstName, lastName, email, birthday);
                    System.out.println("\nAccount created successfully!");
                    invalidDate = false;
                }
            } catch (DateTimeParseException e) {
                throw new ValidationException("Invalid date format. Please use dd-MM-yyyy.");
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

        // Selectarea showtime-ului
        int showtimeId = -1;
        while (true) {
            System.out.println("\nPlease choose the Showtime number: ");
            if (sc.hasNextInt()) {
                showtimeId = sc.nextInt();
                sc.nextLine();
                if (controller.isShowtimeAvailable(showtimeId)) {
                    break;
                } else {
                    throw new ValidationException("Invalid Showtime number. Please try again.");
                }
            } else {
                sc.nextLine();
                throw new ValidationException("Please enter a valid number for Showtime.");
            }
        }

        List<Integer> allSelectedSeats = new ArrayList<>();
        int totalTickets = 0;

        while (true) {
            System.out.println("Type of tickets you want to buy \n 1. standard - 30 lei \n 2. vip - 40 lei \n 3. premium - 50 lei \n 0. Finish");
            int typeOfTickets = -1;
            while (true) {
                if (sc.hasNextInt()) {
                    typeOfTickets = sc.nextInt();
                    sc.nextLine();
                    if (typeOfTickets == 0) {
                        break;
                    } else if (typeOfTickets >= 1 && typeOfTickets <= 3) {
                        break;
                    } else {
                        throw new ValidationException("Invalid ticket type. Please choose 1, 2, 3, or 0 to finish.");
                    }
                } else {
                    sc.nextLine();
                    throw new ValidationException("Please enter a valid number for ticket type.");
                }
            }

            if (typeOfTickets == 0) {
                break;
            }

            List<Integer> availableSeats = new ArrayList<>(controller.displayAvailableSeats(showtimeId, typeOfTickets));
            if (availableSeats.isEmpty()) {
                System.out.println("No seats available for this ticket type. Please choose a different type.");
                continue;
            }

            int nrOfTickets = -1;
            while (true) {
                System.out.println("Number of tickets you want to buy for this type: ");
                if (sc.hasNextInt()) {
                    nrOfTickets = sc.nextInt();
                    sc.nextLine();
                    if (nrOfTickets > 0) {
                        if (nrOfTickets <= availableSeats.size()) {
                            totalTickets += nrOfTickets;
                            break;
                        } else {
                            System.out.println("Only " + availableSeats.size() + " seats are available for this type. Please choose fewer tickets.");
                        }
                    } else {
                        System.out.println("Please enter a valid number of tickets (greater than 0).");
                    }
                } else {
                    System.out.println("Please enter a valid number for tickets.");
                    sc.nextLine();
                }
            }

            List<Integer> seats = new ArrayList<>();
            System.out.println("\nPlease choose your seats for " + nrOfTickets + " tickets:");
            for (int i = 0; i < nrOfTickets; i++) {
                int seat = -1;
                while (true) {
                    if (sc.hasNextInt()) {
                        seat = sc.nextInt();
                        sc.nextLine();
                        if (availableSeats.contains(seat)) {
                            if (!seats.contains(seat)) {
                                seats.add(seat);
                                allSelectedSeats.add(seat);
                                availableSeats.remove(Integer.valueOf(seat));
                                break;
                            } else {
                                System.out.println("Seat " + seat + " has already been selected. Please choose another one.");
                            }
                        } else {
                            System.out.println("Seat " + seat + " is not available in the list of available seats. Please choose a valid seat.");
                        }
                    } else {
                        System.out.println("Please enter a valid seat number.");
                        sc.nextLine();
                    }
                }
            }

            controller.removeSeatsFromAvailable(showtimeId, seats);
        }

        if (allSelectedSeats.isEmpty()) {
            System.out.println("You have not selected any seats. Booking cannot be created.");
            return;
        }

        int currentBookingId = controller.createBooking(loggedCustomer.getId(), showtimeId, LocalDate.now(), totalTickets, allSelectedSeats);
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
        while (invalidOption) {
            try {
                System.out.println("\n======================================");
                System.out.println("""
                Please choose the type of membership you want to purchase:
                1. Basic
                2. Premium
                Enter your choice:
                """);

                if (!sc.hasNextInt()) {
                    throw new IllegalArgumentException("Please enter a valid number (1 or 2).");
                }

                int type = sc.nextInt();
                sc.nextLine();

                switch (type) {
                    case 1: {
                        BasicMembership membership = controller.createBasicMembership(
                                loggedCustomer.getId(),
                                LocalDate.now(),
                                LocalDate.now().plusDays(30)
                        );
                        System.out.println("\nYour total is: " + membership.getPrice());
                        invalidOption = false;
                        break;
                    }
                    case 2: {
                        PremiumMembership membership = controller.createPremiumMembership(
                                loggedCustomer.getId(),
                                LocalDate.now(),
                                LocalDate.now().plusDays(30)
                        );
                        System.out.println("\nYour total is: " + membership.getPrice());
                        invalidOption = false;
                        break;
                    }
                    default: {
                        System.out.println("Invalid input. Please enter a number between 1 and 2.");
                        break;
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        System.out.println("\nMembership created successfully!");
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
        System.out.println("""
        Display:
        1. All showtimes
        2. Showtimes filtered by date
        3. Showtimes filtered by movie
        4. Showtimes sorted by duration
        5. Showtimes sorted by date
        """);

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            sc.nextLine();
            return;
        }

        int showtimesId = sc.nextInt();
        sc.nextLine();

        switch (showtimesId) {
            case 1: {
                controller.displayShowtimesFilteredByPg(loggedCustomer);
                break;
            }
            case 2: {
                LocalDate date1 = null;
                while (date1 == null) {
                    System.out.println("Please enter a date (dd-MM-yyyy): ");
                    String date = sc.nextLine();
                    try {
                        date1 = LocalDate.parse(date, dateFormatter); // Parsăm data
                        controller.displayShowtimesFilteredByDate(loggedCustomer, date1);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                    }
                }
                break;
            }
            case 3: {
                String movieTitle = null;
                while (true) {
                    System.out.println("Please enter the movie title: ");
                    movieTitle = sc.nextLine();
                    if (controller.doesMovieExist(movieTitle)) {
                        controller.displayFilteredShowtimesByMovie(loggedCustomer, movieTitle);
                        break;
                    } else {
                        System.out.println("The movie \"" + movieTitle + "\" does not exist. Please try again.");
                    }
                }
                break;
            }
            case 4: {
                controller.displaySortedShowtimesByDuration(loggedCustomer);
                break;
            }
            case 5: {
                controller.displaySortedShowtimesByDateAsc(loggedCustomer);
                break;
            }
            default: {
                System.out.println("Invalid option. Please choose 1, 2, 3, 4, or 5.");
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
     * Logs in a staff member by verifying the email and checking if a staff member with the given email exists.
     *
     * @return The logged-in staff member if the email is valid and exists, or throws an exception otherwise.
     */
    public Staff logInStaff() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n================Log in================");

        String email = "";
        Staff loggedStaff = null;

        while (loggedStaff == null) {
            System.out.println("Please enter your email: ");
            email = sc.nextLine().trim();  // Remove any leading/trailing spaces

            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Please try again.");
                continue;
            }

            loggedStaff = controller.logStaff(email);

            if (loggedStaff == null) {
                System.out.println("No staff member found with the email: " + email + ". Please try again.");
            }
        }

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
                } else if (birthday.getYear() < 1900) {
                    System.out.println("Birth year cannot be earlier than 1900. Please try again.");
                } else {
                    controller.createStaff(firstName, lastName, email);
                    System.out.println("\nAccount created successfully!");
                    invalidDate = false;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd-MM-yyyy.");
            }
        }
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
        while (invalidOption) {
            controller.staffMenu2();
            String option = sc.nextLine();
            System.out.println("\n======================================");

            switch (option) {
                case "1": {
                    // Adaugare Film
                    System.out.println("\nPlease enter movie title: ");
                    String title = sc.nextLine().trim();

                    if (title.isEmpty()) {
                        System.out.println("Title cannot be empty. Please try again.");
                        break;
                    }

                    System.out.println("Please enter movie PG (true/false): ");
                    boolean pg = false;
                    boolean validPg = false;
                    while (!validPg) {
                        String pgInput = sc.nextLine().trim().toLowerCase();
                        if (pgInput.equals("true")) {
                            pg = true;
                            validPg = true;
                        } else if (pgInput.equals("false")) {
                            pg = false;
                            validPg = true;
                        } else {
                            System.out.println("Invalid PG value. Please enter 'true' or 'false'.");
                        }
                    }

                    System.out.println("Please enter movie genre: ");
                    String genre = sc.nextLine().trim();

                    if (genre.isEmpty()) {
                        System.out.println("Genre cannot be empty. Please try again.");
                        break;
                    }

                    boolean invalidTime = true;
                    while (invalidTime) {
                        System.out.println("Please enter movie release date (dd-MM-yyyy): ");
                        String date = sc.nextLine().trim();

                        try {
                            LocalDate releaseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            controller.addMovie(title, pg, genre, releaseDate);
                            System.out.println("\nMovie added successfully!");
                            invalidTime = false;

                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                        }
                    }

                    invalidOption = false;
                    break;
                }
                case "2": {

                    controller.displayMoviesStaff();
                    System.out.println("\nPlease enter title of the movie you want to update: ");
                    String title = sc.nextLine().trim();

                    if (!controller.doesMovieExist(title)) {
                        System.out.println("No movie found with that title. Please try again.");
                        break;
                    }

                    System.out.println("Please enter new PG (true/false): ");
                    boolean newPg = false;
                    boolean validPg = false;
                    while (!validPg) {
                        String pgInput = sc.nextLine().trim().toLowerCase();
                        if (pgInput.equals("true")) {
                            newPg = true;
                            validPg = true;
                        } else if (pgInput.equals("false")) {
                            newPg = false;
                            validPg = true;
                        } else {
                            System.out.println("Invalid PG value. Please enter 'true' or 'false'.");
                        }
                    }

                    System.out.println("Please enter new genre: ");
                    String newGenre = sc.nextLine().trim();

                    if (newGenre.isEmpty()) {
                        System.out.println("Genre cannot be empty. Please try again.");
                        break;
                    }

                    boolean invalidTime = true;
                    while (invalidTime) {
                        System.out.println("Please enter new release date (dd-MM-yyyy): ");
                        String date = sc.nextLine().trim();

                        try {
                            LocalDate newReleaseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            controller.updateMovie(title, newPg, newGenre, newReleaseDate);
                            System.out.println("\nMovie updated successfully!");
                            invalidTime = false;

                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                        }
                    }

                    invalidOption = false;
                    break;
                }
                case "3": {

                    controller.displayMoviesStaff();
                    System.out.println("\nPlease enter title of the movie you want to delete: ");
                    String title = sc.nextLine().trim();

                    if (!controller.doesMovieExist(title)) {
                        System.out.println("No movie found with that title. Please try again.");
                        break;
                    }

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
        while (invalidOption) {
            controller.staffMenu2();
            String option = sc.nextLine();
            System.out.println("\n======================================");

            switch (option) {
                case "1": { // Add Screen
                    int nrStandardSeats, nrVipSeats, nrPremiumSeats;

                    while (true) {
                        System.out.println("\nPlease enter the number of standard seats (0-199): ");
                        nrStandardSeats = sc.nextInt();
                        System.out.println("Please enter the number of VIP seats (0-199): ");
                        nrVipSeats = sc.nextInt();
                        System.out.println("Please enter the number of premium seats (0-199): ");
                        nrPremiumSeats = sc.nextInt();
                        sc.nextLine();

                        if ((nrStandardSeats > 0 || nrVipSeats > 0 || nrPremiumSeats > 0) &&
                                nrStandardSeats <= 199 && nrVipSeats <= 199 && nrPremiumSeats <= 199) {
                            break;
                        } else {
                            System.out.println("Invalid input. At least one seat type must have more than 0 seats, and all must be below 200.");
                        }
                    }

                    controller.addScreen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
                    System.out.println("\nScreen added successfully!");
                    invalidOption = false;
                    break;
                }
                case "2": { // Update Screen
                    controller.displayScreensStaff();
                    int id;

                    while (true) {
                        System.out.println("\nPlease enter the ID of the screen you want to update: ");
                        id = sc.nextInt();
                        sc.nextLine();

                        if (controller.doesScreenExist(id)) {
                            break;
                        } else {
                            System.out.println("Invalid screen ID. Please try again.");
                        }
                    }

                    int newNrStandardSeats, newNrVipSeats, newNrPremiumSeats;
                    while (true) {
                        System.out.println("Please enter new number of standard seats (0-199): ");
                        newNrStandardSeats = sc.nextInt();
                        System.out.println("Please enter new number of VIP seats (0-199): ");
                        newNrVipSeats = sc.nextInt();
                        System.out.println("Please enter new number of premium seats (0-199): ");
                        newNrPremiumSeats = sc.nextInt();
                        sc.nextLine();

                        if ((newNrStandardSeats > 0 || newNrVipSeats > 0 || newNrPremiumSeats > 0) &&
                                newNrStandardSeats <= 199 && newNrVipSeats <= 199 && newNrPremiumSeats <= 199) {
                            break;
                        } else {
                            System.out.println("Invalid input. At least one seat type must have more than 0 seats, and all must be below 200.");
                        }
                    }

                    List<Seat> seats = new ArrayList<>(); // Assuming this is managed elsewhere
                    controller.updateScreen(id, newNrStandardSeats, newNrVipSeats, newNrPremiumSeats, seats);
                    System.out.println("\nScreen updated successfully!");
                    invalidOption = false;
                    break;
                }
                case "3": { // Delete Screen
                    controller.displayScreensStaff();
                    int id;

                    while (true) {
                        System.out.println("\nPlease enter the ID of the screen you want to delete: ");
                        id = sc.nextInt();
                        sc.nextLine();

                        if (!controller.doesScreenExist(id)) {
                            System.out.println("Invalid screen ID. Please try again.");
                        } else if (controller.hasFutureShowtimes(id)) {
                            System.out.println("This screen has showtimes scheduled in the future. Cannot delete until they are completed.");
                        } else {
                            break;
                        }
                    }

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
     * Validates input to ensure screen ID, movie title, date, time, and duration meet required conditions.
     * Prevents updating or deleting a showtime with existing bookings.
     *
     * @param loggedStaff the currently logged-in staff member
     */
    public void modifyShowtime(Staff loggedStaff) {
        Scanner sc = new Scanner(System.in);

        boolean invalidOption = true;
        while (invalidOption) {
            controller.staffMenu2();
            String option = sc.nextLine();
            System.out.println("\n=====================================");

            switch (option) {
                case "1": { // Add Showtime
                    System.out.println("\nPlease enter screen ID: ");
                    int screenId = sc.nextInt();
                    sc.nextLine();

                    if (!controller.doesScreenExist(screenId)) {
                        System.out.println("Screen ID does not exist. Please try again.");
                        break;
                    }

                    System.out.println("Please enter movie title: ");
                    String title = sc.nextLine();

                    int movieId = controller.findMovieIdByTitle(title);
                    if (movieId == -1) {
                        System.out.println("Movie title does not exist. Please try again.");
                        break;
                    }

                    LocalDate showtimeDate = null;
                    boolean invalidDate = true;
                    while (invalidDate) {
                        System.out.println("Please enter a date (dd-MM-yyyy): ");
                        String date = sc.nextLine();

                        try {
                            showtimeDate = LocalDate.parse(date, dateFormatter);
                            if (showtimeDate.isBefore(LocalDate.now())) {
                                System.out.println("Date must be in the future. Please try again.");
                            } else {
                                invalidDate = false;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                        }
                    }

                    LocalTime startTime = null;
                    boolean invalidTime = true;
                    while (invalidTime) {
                        System.out.println("Please enter a starting time (HH:mm): ");
                        String time = sc.nextLine();

                        try {
                            startTime = LocalTime.parse(time, timeFormatter);
                            if (startTime.isBefore(LocalTime.of(6, 0)) || startTime.isAfter(LocalTime.of(23, 59))) {
                                System.out.println("Start time must be between 06:00 and 24:00. Please try again.");
                            } else {
                                invalidTime = false;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid time format. Please use HH:mm.");
                        }
                    }

                    System.out.println("Please enter duration (in minutes): ");
                    int duration = sc.nextInt();
                    sc.nextLine();

                    if (duration <= 0 || duration > 200) {
                        System.out.println("Duration must be greater than 0 and less than or equal to 200. Please try again.");
                        break;
                    }

                    controller.addShowtime(screenId, movieId, showtimeDate, startTime, duration);
                    System.out.println("\nShowtime added successfully!");
                    invalidOption = false;
                    break;
                }
                case "2": { // Update Showtime
                    controller.displayShowtimesStaff();
                    System.out.println("\nPlease enter the ID of the showtime you want to update: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    if (!controller.doesShowtimeExist(id)) {
                        System.out.println("Showtime ID does not exist. Please try again.");
                        break;
                    }

                    if (controller.hasBookingsForShowtime(id)) {
                        System.out.println("Cannot update a showtime with existing bookings. Please try again.");
                        break;
                    }

                    System.out.println("Please enter new screen ID: ");
                    int newScreenId = sc.nextInt();
                    sc.nextLine();

                    if (!controller.doesScreenExist(newScreenId)) {
                        System.out.println("Screen ID does not exist. Please try again.");
                        break;
                    }

                    System.out.println("Please enter new movie title: ");
                    String title = sc.nextLine();

                    int newMovieId = controller.findMovieIdByTitle(title);
                    if (newMovieId == -1) {
                        System.out.println("Movie title does not exist. Please try again.");
                        break;
                    }

                    LocalDate newShowtimeDate = null;
                    boolean invalidDate = true;
                    while (invalidDate) {
                        System.out.println("Please enter a new date (dd-MM-yyyy): ");
                        String date = sc.nextLine();

                        try {
                            newShowtimeDate = LocalDate.parse(date, dateFormatter);
                            if (newShowtimeDate.isBefore(LocalDate.now())) {
                                System.out.println("Date must be in the future. Please try again.");
                            } else {
                                invalidDate = false;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                        }
                    }

                    LocalTime newStartTime = null;
                    boolean invalidTime = true;
                    while (invalidTime) {
                        System.out.println("Please enter a new starting time (HH:mm): ");
                        String time = sc.nextLine();

                        try {
                            newStartTime = LocalTime.parse(time, timeFormatter);
                            if (newStartTime.isBefore(LocalTime.of(6, 0)) || newStartTime.isAfter(LocalTime.of(23, 59))) {
                                System.out.println("Start time must be between 06:00 and 24:00. Please try again.");
                            } else {
                                invalidTime = false;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid time format. Please use HH:mm.");
                        }
                    }

                    System.out.println("Please enter a new duration (in minutes): ");
                    int newDuration = sc.nextInt();
                    sc.nextLine();

                    if (newDuration <= 0 || newDuration > 200) {
                        System.out.println("Duration must be greater than 0 and less than or equal to 200. Please try again.");
                        break;
                    }

                    controller.updateShowtime(id, newScreenId, newMovieId, newShowtimeDate, newStartTime, newDuration);
                    System.out.println("\nShowtime updated successfully!");
                    invalidOption = false;
                    break;
                }
                case "3": { // Delete Showtime
                    controller.displayShowtimesStaff();
                    System.out.println("\nPlease enter the ID of the showtime you want to delete: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    if (!controller.doesShowtimeExist(id)) {
                        System.out.println("Showtime ID does not exist. Please try again.");
                        break;
                    }

                    if (controller.hasBookingsForShowtime(id)) {
                        System.out.println("Cannot delete a showtime with existing bookings. Please try again.");
                        break;
                    }

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