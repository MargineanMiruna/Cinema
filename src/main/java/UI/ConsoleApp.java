package UI;

import Controller.Controller;
import Model.*;
import Exception.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            try {
                System.out.println("\n======================================");
                System.out.println("""
                        Please choose an option:
                        1. Customer
                        2. Staff
                        3. Exit
                        Enter your choice:""");
                String userType = sc.nextLine();

                switch (userType) {
                    case "1": {
                        Customer loggedCustomer;

                        boolean continueLoop2 = true;
                        while (continueLoop2) {
                            System.out.println("""
                                    \n======================================
                                    Please choose an option:
                                    1. Log in
                                    2. Sign up
                                    3. Back
                                    Enter your choice:""");
                            String opt = sc.nextLine();

                            switch (opt) {
                                case "1": {
                                    loggedCustomer = this.logInCustomer();
                                    if (loggedCustomer != null)
                                        this.customerMenu(loggedCustomer);
                                    break;
                                }
                                case "2": {
                                    signUpCustomer();
                                    loggedCustomer = this.logInCustomer();
                                    if (loggedCustomer != null)
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
                                    \n======================================
                                    Please choose an option:
                                    1. Log in
                                    2. Sign up
                                    3. Back
                                    Enter your choice:""");
                            String opt = sc.nextLine();

                            switch (opt) {
                                case "1": {
                                    loggedStaff = this.logInStaff();
                                    if (loggedStaff != null)
                                        this.staffMenu(loggedStaff);
                                    break;
                                }
                                case "2": {
                                    this.signUpStaff();
                                    loggedStaff = this.logInStaff();
                                    if (loggedStaff != null)
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
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
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
            try {
                System.out.println("Please enter your email: ");
                email = sc.nextLine();

                if (email.isEmpty()) {
                    throw new ValidationException("Email cannot be empty. Please try again.");
                }

                loggedCustomer = controller.logCustomer(email);

                if (loggedCustomer == null) {
                    throw new EntityNotFoundException("No account found with the email: " + email + ". Please try again.");
                }
            } catch (EntityNotFoundException | ValidationException e) {
                System.out.println(e.getMessage());
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
            try {
                System.out.println("Please enter your first name: ");
                firstName = sc.nextLine();
                if (firstName.isEmpty() || !firstName.matches("^[A-Za-z]+$")) {
                    throw new ValidationException("First name must contain only letters and cannot be empty. Please try again.");
                } else {
                    break;
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }

        String lastName = "";
        while (true) {
            try {
                System.out.println("Please enter your last name: ");
                lastName = sc.nextLine();
                if (lastName.isEmpty() || !lastName.matches("^[A-Za-z]+$")) {
                    throw new ValidationException("Last name must contain only letters and cannot be empty. Please try again.");
                } else {
                    break;
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }

        String email = "";
        while (true) {
            System.out.println("Please enter your email: ");
            email = sc.nextLine();
            try {
                if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    throw new ValidationException("Email must respect the email format and cannot be empty. Please try again.");
                } else {
                    break;
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }

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
            } catch (DateTimeParseException | ValidationException e) {
                System.out.println(e.getMessage());
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
            try {
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
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> allSelectedSeats = new ArrayList<>();
        int totalTickets = 0;

        while (true) {
            System.out.println("Type of tickets you want to buy \n 1. standard - 30 lei \n 2. vip - 40 lei \n 3. premium - 50 lei \n 0. Finish");
            int typeOfTickets = -1;
            while (true) {
                try {
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
                } catch (ValidationException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (typeOfTickets == 0) {
                break;
            }

            List<Integer> availableSeats = new ArrayList<>(controller.displayAvailableSeats(showtimeId, typeOfTickets));
            try {
                if (availableSeats.isEmpty()) {
                    throw new EntityNotFoundException("No seats available for this ticket type. Please choose a different type.");
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e.getMessage());
                continue;
            }

            int nrOfTickets = -1;
            while (true) {
                try {
                    System.out.println("Number of tickets you want to buy for this type: ");
                    if (sc.hasNextInt()) {
                        nrOfTickets = sc.nextInt();
                        sc.nextLine();
                        if (nrOfTickets > 0) {
                            if (nrOfTickets <= availableSeats.size()) {
                                totalTickets += nrOfTickets;
                                break;
                            } else {
                                throw new ValidationException("Only " + availableSeats.size() + " seats are available for this type. Please choose fewer tickets.");
                            }
                        } else {
                            throw new ValidationException("Please enter a valid number of tickets (greater than 0).");
                        }
                    } else {
                        sc.nextLine();
                        throw new ValidationException("Please enter a valid number for tickets.");
                    }
                } catch (ValidationException e) {
                    System.out.println(e.getMessage());
                }
            }

            List<Integer> seats = new ArrayList<>();
            System.out.println("\nPlease choose your seats for " + nrOfTickets + " tickets:");
            for (int i = 0; i < nrOfTickets; i++) {
                int seat = -1;
                while (true) {
                    try {
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
                                    throw new ValidationException("Seat " + seat + " has already been selected. Please choose another one.");
                                }
                            } else {
                                throw new ValidationException("Seat " + seat + " is not available in the list of available seats. Please choose a valid seat.");
                            }
                        } else {
                            sc.nextLine();
                            throw new ValidationException("Please enter a valid seat number.");
                        }
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            controller.removeSeatsFromAvailable(showtimeId, seats);
        }

        try {
            if (allSelectedSeats.isEmpty()) {
                throw new ValidationException("You have not selected any seats. Booking cannot be created.");
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
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
                Enter your choice:""");

                if (!sc.hasNextInt()) {
                    throw new ValidationException("Please enter a valid number (1 or 2).");
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
                        throw new ValidationException("Invalid input. Please enter a number between 1 and 2.");
                    }
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
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

        int showtimesDisplayOption = 1;
        while (true) {
            System.out.println("\n=====================================");
            System.out.println("""
                        Display:
                        1. All showtimes
                        2. Showtimes filtered by date
                        3. Showtimes filtered by movie
                        4. Showtimes sorted by duration
                        5. Showtimes sorted by date
                        Enter your choice:""");

            try {
                if (!sc.hasNextInt()) {
                    throw new ValidationException("Invalid input. Please enter a number between 1 and 5.");
                } else {
                    showtimesDisplayOption = sc.nextInt();
                    sc.nextLine();
                    break;
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }

        try {
            switch (showtimesDisplayOption) {
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
                            if(date1.isBefore(LocalDate.now())) {
                                throw new ValidationException("Date is in the past. Please enter a future date.");
                            }
                            if (controller.displayShowtimesFilteredByDate(loggedCustomer, date1).isEmpty()) {
                                throw new EntityNotFoundException("Please try another date.");
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                        } catch (EntityNotFoundException | ValidationException e) {
                            System.out.println(e.getMessage());
                            date1 = null;
                        }
                    }
                    break;
                }
                case 3: {
                    String movieTitle;
                    while (true) {
                        System.out.println("Please enter the movie title: ");
                        movieTitle = sc.nextLine();
                        try {
                            if (controller.doesMovieExist(movieTitle)) {
                                if (controller.displayFilteredShowtimesByMovie(loggedCustomer, movieTitle).isEmpty()) {
                                    throw new EntityNotFoundException("Please try another movie.");
                                }
                                break;
                            } else {
                                throw new EntityNotFoundException("The movie \"" + movieTitle + "\" does not exist. Please try again.");
                            }
                        } catch (EntityNotFoundException e) {
                            System.out.println(e.getMessage());
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
                    throw new ValidationException("Invalid input. Please enter a number between 1 and 5.");
                }
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
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

            try {
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
                    case "4": {
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
                        throw new ValidationException("Invalid input. Please enter a number between 1 and 5.");
                    }
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
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

            try {
                if (email.isEmpty()) {
                    throw new ValidationException("Email cannot be empty. Please try again.");
                }

                loggedStaff = controller.logStaff(email);

                if (loggedStaff == null) {
                    throw new EntityNotFoundException("No staff member found with the email: " + email + ". Please try again.");
                }
            } catch (ValidationException | EntityNotFoundException e) {
                System.out.println(e.getMessage());
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
            try {
                if (firstName.isEmpty() || !firstName.matches("^[A-Za-z]+$")) {
                    throw new ValidationException("First name must contain only letters and cannot be empty. Please try again.");
                } else {
                    break;
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }

        String lastName = "";
        while (true) {
            System.out.println("Please enter your last name: ");
            lastName = sc.nextLine();
            try {
                if (lastName.isEmpty() || !lastName.matches("^[A-Za-z]+$")) {
                    throw new ValidationException("Last name must contain only letters and cannot be empty. Please try again.");
                } else {
                    break;
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }

        String email = "";
        while (true) {
            System.out.println("Please enter your email: ");
            email = sc.nextLine();
            try {
                if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    throw new ValidationException("Email must respect the email format and cannot be empty. Please try again.");
                } else {
                    controller.createStaff(firstName, lastName, email);
                    System.out.println("\nAccount created successfully!");
                    break;
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
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

            try {
                switch (option) {
                    case "1": {
                        // Adaugare Film

                        String title = "";
                        while (true) {
                            System.out.println("\nPlease enter movie title: ");
                            title = sc.nextLine().trim();
                            try {
                                if (title.isEmpty()) {
                                    throw new ValidationException("Movie title cannot be empty. Please try again.");
                                } else break;
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        boolean pg = false;
                        boolean validPg = false;
                        while (!validPg) {
                            System.out.println("Please enter movie PG (true/false): ");
                            String pgInput = sc.nextLine().trim().toLowerCase();
                            try {
                                if (pgInput.equals("true")) {
                                    pg = true;
                                    validPg = true;
                                } else if (pgInput.equals("false")) {
                                    pg = false;
                                    validPg = true;
                                } else {
                                    throw new ValidationException("Invalid PG value. Please enter 'true' or 'false'.");
                                }
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        String genre = "";
                        while (true) {
                            System.out.println("Please enter movie genre: ");
                            genre = sc.nextLine().trim();
                            try {
                                if (genre.isEmpty()) {
                                    throw new ValidationException("Movie genre cannot be empty. Please try again.");
                                } else break;
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        boolean invalidTime = true;
                        LocalDate releaseDate = null;
                        while (invalidTime) {
                            System.out.println("Please enter movie release date (dd-MM-yyyy): ");
                            String date = sc.nextLine().trim();
                            try {
                                releaseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                                invalidTime = false;

                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                            }
                        }

                        controller.addMovie(title, pg, genre, releaseDate);
                        System.out.println("\nMovie added successfully by " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + " on " + LocalDate.now());
                        invalidOption = false;
                        break;
                    }
                    case "2": {
                        //modificare film
                        controller.displayMoviesStaff();

                        String title = "";
                        while (true) {
                            System.out.println("\nPlease enter title of the movie you want to update: ");
                            title = sc.nextLine().trim();
                            try {
                                if (!controller.doesMovieExist(title)) {
                                    throw new EntityNotFoundException("No movie found with that title. Please try again.");
                                } else {
                                    int movieId = controller.findMovieIdByTitle(title);
                                    if (controller.hasAssignedShowtimesforMovie(movieId)) {
                                        throw new BusinessLogicException("This movie has showtimes already scheduled. Cannot be updated.");
                                    } else break;
                                }
                            } catch (EntityNotFoundException e) {
                                System.out.println(e.getMessage());
                            } catch (BusinessLogicException e) {
                                System.out.println(e.getMessage());
                                return;
                            }
                        }

                        boolean newPg = false;
                        boolean validPg = false;
                        while (!validPg) {
                            System.out.println("Please enter new PG (true/false): ");
                            String pgInput = sc.nextLine().trim().toLowerCase();
                            try {
                                if (pgInput.equals("true")) {
                                    newPg = true;
                                    validPg = true;
                                } else if (pgInput.equals("false")) {
                                    newPg = false;
                                    validPg = true;
                                } else {
                                    throw new ValidationException("Invalid PG value. Please enter 'true' or 'false'.");
                                }
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        String newGenre = "";
                        while (true) {
                            System.out.println("Please enter new genre: ");
                            newGenre = sc.nextLine().trim();
                            try {
                                if (newGenre.isEmpty()) {
                                    throw new ValidationException("Genre cannot be empty. Please try again.");
                                } else break;
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        boolean invalidDate = true;
                        LocalDate newReleaseDate = null;
                        while (invalidDate) {
                            System.out.println("Please enter new release date (dd-MM-yyyy): ");
                            String date = sc.nextLine().trim();
                            try {
                                newReleaseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                                invalidDate = false;

                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                            }
                        }

                        controller.updateMovie(title, newPg, newGenre, newReleaseDate);
                        System.out.println("\nMovie updated successfully by " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + " on " + LocalDate.now() + ".");
                        invalidOption = false;
                        break;
                    }
                    case "3": {
                        //stergere film
                        controller.displayMoviesStaff();

                        String title = "";
                        while (true) {
                            System.out.println("\nPlease enter title of the movie you want to delete: ");
                            title = sc.nextLine().trim();
                            try {
                                if (!controller.doesMovieExist(title)) {
                                    throw new EntityNotFoundException("No movie found with that title. Please try again.");
                                } else {
                                    int movieId = controller.findMovieIdByTitle(title);
                                    if (controller.hasAssignedShowtimesforMovie(movieId)) {
                                        throw new BusinessLogicException("This movie has showtimes already scheduled. Cannot be updated.");
                                    } else {
                                        controller.deleteMovie(title);
                                        System.out.println("\nMovie deleted successfully by " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + " on " + LocalDate.now() + ".");
                                        break;
                                    }
                                }
                            } catch (EntityNotFoundException e) {
                                System.out.println(e.getMessage());
                            } catch (BusinessLogicException e) {
                                System.out.println(e.getMessage());
                                return;
                            }
                        }

                        invalidOption = false;
                        break;
                    }
                    default: {
                        throw new ValidationException("Invalid input. Please enter a number between 1-3!");
                    }
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
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

            try {
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
                            try {
                                if ((nrStandardSeats > 0 || nrVipSeats > 0 || nrPremiumSeats > 0) &&
                                        nrStandardSeats <= 199 && nrVipSeats <= 199 && nrPremiumSeats <= 199) {
                                    controller.addScreen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
                                    System.out.println("\nScreen added successfully by " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + " on " + LocalDate.now() + ".");
                                    break;
                                } else {
                                    throw new ValidationException("Invalid input. At least one seat type must have more than 0 seats, and all must be below 200.");
                                }
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

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
                            try {
                                if (controller.doesScreenExist(id)) {
                                    if (controller.hasAssignedShowtimesForScreen(id)) {
                                        throw new BusinessLogicException("This screen has showtimes scheduled. Cannot be updated.");
                                    }
                                    break;
                                } else {
                                    throw new EntityNotFoundException("No screen found with that ID. Please try again.");
                                }
                            } catch (EntityNotFoundException e) {
                                System.out.println(e.getMessage());
                            } catch (BusinessLogicException e) {
                                System.out.println(e.getMessage());
                                return;
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
                            try {
                                if ((newNrStandardSeats > 0 || newNrVipSeats > 0 || newNrPremiumSeats > 0) &&
                                        newNrStandardSeats <= 199 && newNrVipSeats <= 199 && newNrPremiumSeats <= 199) {
                                    List<Seat> seats = new ArrayList<>();
                                    controller.updateScreen(id, newNrStandardSeats, newNrVipSeats, newNrPremiumSeats, seats);
                                    System.out.println("\nScreen updated successfully by " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + " on " + LocalDate.now() + ".");
                                    break;
                                } else {
                                    throw new ValidationException("Invalid input. At least one seat type must have more than 0 seats, and all must be below 200.");
                                }
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

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
                            try {
                                if (!controller.doesScreenExist(id)) {
                                    throw new EntityNotFoundException("No screen found with that ID. Please try again.");
                                } else if (controller.hasAssignedShowtimesForScreen(id)) {
                                    throw new BusinessLogicException("This screen has showtimes already scheduled. Cannot be deleted.");
                                } else {
                                    controller.deleteScreen(id);
                                    System.out.println("\nScreen deleted successfully by " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + " on " + LocalDate.now() + ".");
                                    break;
                                }
                            } catch (EntityNotFoundException e) {
                                System.out.println(e.getMessage());
                            } catch (BusinessLogicException e) {
                                System.out.println(e.getMessage());
                                return;
                            }
                        }

                        invalidOption = false;
                        break;
                    }
                    default: {
                        throw new ValidationException("Invalid input. Please enter a number between 1-3!");
                    }
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
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

            try {
                switch (option) {
                    case "1": { // Add Showtime
                        int screenId = 0;
                        while (true) {
                            System.out.println("\nPlease enter screen ID: ");
                            screenId = sc.nextInt();
                            sc.nextLine();
                            try {
                                if (!controller.doesScreenExist(screenId)) {
                                    throw new EntityNotFoundException("No screen found with that ID. Please try again.");
                                } else break;
                            } catch (EntityNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        String title = "";
                        int movieId;
                        while (true) {
                            System.out.println("Please enter movie title: ");
                            title = sc.nextLine();
                            try {
                                if (title.isEmpty()) {
                                    throw new ValidationException("Movie title cannot be empty. Please try again.");
                                }
                                else {
                                    movieId = controller.findMovieIdByTitle(title);
                                    if (movieId == -1) {
                                        throw new EntityNotFoundException("No movie found with that title. Please try again.");
                                    } else break;
                                }
                            } catch (EntityNotFoundException | ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        LocalDate showtimeDate = null;
                        boolean invalidDate = true;
                        while (invalidDate) {
                            System.out.println("Please enter a date (dd-MM-yyyy): ");
                            String date = sc.nextLine();
                            try {
                                showtimeDate = LocalDate.parse(date, dateFormatter);
                                if (showtimeDate.isBefore(LocalDate.now())) {
                                    throw new ValidationException("Date must be in the future. Please try again.");
                                } else {
                                    invalidDate = false;
                                }
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
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
                                    throw new ValidationException("Start time must be between 06:00 and 24:00. Please try again.");
                                } else {
                                    invalidTime = false;
                                }
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid time format. Please use HH:mm.");
                            }
                        }

                        int duration;
                        while(true) {
                            System.out.println("Please enter duration (in minutes): ");
                            duration = sc.nextInt();
                            sc.nextLine();
                            try {
                                if (duration <= 0 || duration > 200) {
                                    throw new ValidationException("Duration must be greater than 0 and less than or equal to 200. Please try again.");
                                } else break;
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        controller.addShowtime(screenId, movieId, showtimeDate, startTime, duration);
                        System.out.println("\nShowtime added successfully by " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + " on " + LocalDate.now() + ".");
                        invalidOption = false;
                        break;
                    }
                    case "2": { // Update Showtime
                        controller.displayShowtimesStaff();

                        int id;
                        while (true) {
                            System.out.println("\nPlease enter the ID of the showtime you want to update: ");
                            id = sc.nextInt();
                            sc.nextLine();
                            try {
                                if (!controller.doesShowtimeExist(id)) {
                                    throw new ValidationException("No showtime found with that ID. Please try again.");
                                } else if (controller.hasBookingsForShowtime(id)) {
                                    throw new BusinessLogicException("Cannot update a showtime with existing bookings. Please try again.");
                                } else break;
                            } catch (ValidationException | BusinessLogicException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        int newScreenId;
                        while (true) {
                            System.out.println("Please enter new screen ID: ");
                            newScreenId = sc.nextInt();
                            sc.nextLine();
                            try {
                                if (!controller.doesScreenExist(newScreenId)) {
                                    throw new ValidationException("No screen found with that ID. Please try again.");
                                } else break;
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        String title = "";
                        int newMovieId;
                        while (true) {
                            System.out.println("Please enter new movie title: ");
                            title = sc.nextLine();
                            try {
                                if (title.isEmpty()) {
                                    throw new ValidationException("Movie title cannot be empty. Please try again.");
                                } else {
                                    newMovieId = controller.findMovieIdByTitle(title);
                                    if (newMovieId == -1) {
                                        throw new EntityNotFoundException("No movie found with that title. Please try again.");
                                    } else break;
                                }
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            } catch (EntityNotFoundException e) {
                                System.out.println(e.getMessage());
                                return;
                            }
                        }

                        LocalDate newShowtimeDate = null;
                        boolean invalidDate = true;
                        while (invalidDate) {
                            System.out.println("Please enter a new date (dd-MM-yyyy): ");
                            String date = sc.nextLine();
                            try {
                                newShowtimeDate = LocalDate.parse(date, dateFormatter);
                                if (newShowtimeDate.isBefore(LocalDate.now())) {
                                    throw new ValidationException("Date must be in the future. Please try again.");
                                } else {
                                    invalidDate = false;
                                }
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
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
                                    throw new ValidationException("Start time must be between 06:00 and 24:00. Please try again.");
                                } else {
                                    invalidTime = false;
                                }
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid time format. Please use HH:mm.");
                            }
                        }

                        int newDuration;
                        while (true) {
                            System.out.println("Please enter a new duration (in minutes): ");
                            newDuration = sc.nextInt();
                            sc.nextLine();
                            try {
                                if (newDuration <= 0 || newDuration > 200) {
                                    throw new ValidationException("Duration must be greater than 0 and less than or equal to 200. Please try again.");
                                } else break;
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        controller.updateShowtime(id, newScreenId, newMovieId, newShowtimeDate, newStartTime, newDuration);
                        System.out.println("\nShowtime updated successfully by " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + " on " + LocalDate.now() + ".");
                        invalidOption = false;
                        break;
                    }
                    case "3": { // Delete Showtime
                        controller.displayShowtimesStaff();
                        int id;
                        while (true) {
                            System.out.println("\nPlease enter the ID of the showtime you want to delete: ");
                            id = sc.nextInt();
                            sc.nextLine();
                            try {
                                if (!controller.doesShowtimeExist(id)) {
                                    throw new ValidationException("No showtime found with that ID. Please try again.");
                                } else if (controller.hasBookingsForShowtime(id)) {
                                    throw new BusinessLogicException("Cannot delete a showtime with existing bookings. Please try again.");
                                } else {
                                    controller.deleteShowtime(id);
                                    System.out.println("\nShowtime deleted successfully by " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + " on " + LocalDate.now() + ".");
                                    break;
                                }
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            } catch (BusinessLogicException e) {
                                System.out.println(e.getMessage());
                                return;
                            }
                        }

                        invalidOption = false;
                        break;
                    }
                    default: {
                        throw new ValidationException("Invalid option. Please enter a number between 1-3!");
                    }
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
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

            try {
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
                        throw new ValidationException("Invalid option. Please enter a number between 1-4!");
                    }
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}