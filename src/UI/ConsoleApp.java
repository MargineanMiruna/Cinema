package UI;

import Controller.Controller;
import Domain.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    Controller ctrl = new Controller();

    public void run() {
        ctrl.add();
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter fmt2 = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("Welcome to the CinemApp!");

        while (true) {
            System.out.println("1. Customer\n2. Staff\n3. Exit\nPlease enter your choice: ");
            int userType = 0;
            try {
                userType = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (userType) {
                case 1: {
                    System.out.println("1. Log in\n2. Sign up\n3. Back");
                    int opt = sc.nextInt();
                    sc.nextLine();

                    if (opt == 3)
                        break;
                    if (opt == 2) {
                        System.out.println("Please enter your first name: ");
                        String firstName = sc.nextLine();
                        System.out.println("Please enter your last name: ");
                        String lastName = sc.nextLine();
                        System.out.println("Please enter your email: ");
                        String email = sc.nextLine();
                        System.out.println("Please enter your birthday (dd-MM-yyyy): ");
                        String date = sc.nextLine();

                        try {
                            LocalDate birthday = LocalDate.parse(date, fmt);
                            ctrl.createCustomer(firstName, lastName, email, birthday);
                            System.out.println("Account created successfully!");
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                        }
                    }

                    System.out.println("Log in");
                    System.out.println("Please enter your email: ");
                    String email = sc.next();
                    Customer loggedCustomer = ctrl.logCustomer(email);
                    int loggedCustomerId = ctrl.getIdOfCustomer(loggedCustomer);
                    System.out.println("Hello, " + loggedCustomer.getFirstName() + " " + loggedCustomer.getLastName() + ", you logged in successfully");

                    while(true) {
                        ctrl.customerMenu();
                        opt = sc.nextInt();
                        sc.nextLine();
                        switch (opt) {
                            case 1: {
                                //display Showtimes
                                ctrl.displayShowtimes(loggedCustomer);
                                break;
                            }
                            case 2: {
                                //create Booking
                                ctrl.displayShowtimes(loggedCustomer);

                                System.out.println("Please choose the Showtime number: ");
                                int showtimeId = sc.nextInt();
                                sc.nextLine();

                                System.out.println("Number of tickets you want to buy: ");
                                int nrOfTickets = sc.nextInt();
                                sc.nextLine();

                                ctrl.displayAvailableSeats(showtimeId);
                                System.out.println("Please choose your seats: ");
                                List<Integer> seats = new ArrayList<>();
                                for(int i = 0; i < nrOfTickets; i++) {
                                    seats.add(sc.nextInt());
                                    sc.nextLine();
                                }
                                ctrl.removeSeatsFromAvailable(showtimeId, seats);

                                int currentBookingId = ctrl.createBooking(loggedCustomerId, showtimeId, LocalDate.now(), nrOfTickets);
                                System.out.println("Booking successfully created!");

                                List<Integer> tickets = new ArrayList<>();
                                for(int i = 0; i < nrOfTickets; i++) {
                                    tickets.add(ctrl.createTicket(currentBookingId, seats.get(i)));
                                }

                                Booking currentBooking = ctrl.getBooking(currentBookingId);
                                currentBooking.setTickets(tickets);

                                System.out.println("Your tickets: ");
                                for(int i = 0; i < nrOfTickets; i++) {
                                    ctrl.displayTickets(loggedCustomer, currentBooking, tickets.get(i));
                                }

                                System.out.println("Your total: ");
                                ctrl.calculateTotalPrice(loggedCustomerId, currentBookingId);

                                break;
                            }
                            case 3: {
                                //create Membership
                                System.out.println("Please choose the type of membership you want to have(1.basic /2.premium): ");
                                int type = sc.nextInt();
                                LocalDate starDate = LocalDate.now();
                                LocalDate endDate = starDate.plusDays(30);

                                if (type == 1) {
                                    BasicMembership membership = ctrl.createBasicMembership(loggedCustomerId,starDate,endDate);
                                    System.out.println("Your total is: " + membership.getPrice() );
                                } else if (type == 2) {
                                    PremiumMembership membership = ctrl.createPremiumMembership(loggedCustomerId,starDate,endDate);
                                    System.out.println("Your total is: " + membership.getPrice() );
                                }
                                System.out.println("Membership successfully created! ");
                                break;
                            }
                            case 4: {
                                System.exit(0);
                            }
                        }
                    }
                }
                case 2: {
                    System.out.println("1. Log in\n2. Sign up\n3. Back");
                    int opt = sc.nextInt();
                    if (opt == 3)
                        break;
                    if (opt == 2) {
                        System.out.println("Please enter your first name: ");
                        String firstName = sc.next();
                        System.out.println("Please enter your last name: ");
                        String lastName = sc.next();
                        System.out.println("Please enter your email: ");
                        String email = sc.next();

                        ctrl.createStaff(firstName, lastName, email);
                        System.out.println("Account created successfully!");
                    }

                    System.out.println("Log in");
                    System.out.println("Please enter your email: ");
                    String email = sc.next();
                    Staff loggedStaff = ctrl.logStaff(email);
                    System.out.println("Hello, " + loggedStaff.getFirstName() + " " + loggedStaff.getLastName() + ", you logged in successfully!");
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

                                switch (opt2) {
                                    case 1: {
                                        //add movie
                                        System.out.println("Please enter movie title: ");
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
                                        System.out.println("Movie added successfully!");

                                        break;
                                    }
                                    case 2: {
                                        //update movie
                                        System.out.println("Please enter title of the movie you want to update: ");
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
                                        System.out.println("Movie updated successfully!");

                                        break;
                                    }
                                    case 3: {
                                        //delete movie
                                        System.out.println("Please enter title of the movie you want to delete: ");
                                        String title = sc.nextLine();

                                        ctrl.deleteMovie(title);
                                        System.out.println("Movie deleted successfully!");

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

                                switch (opt2) {
                                    case 1: {
                                        // add showtime
                                        System.out.println("Please enter screen ID: ");
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
                                        System.out.println("Showtime added successfully!");

                                        break;
                                    }
                                    case 2: {
                                        // update showtime
                                        System.out.println("Please enter the ID of the showtime you want to update: ");
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
                                        System.out.println("Showtime updated successfully!");

                                        break;
                                    }
                                    case 3: {
                                        //delete showtime
                                        System.out.println("Please enter the ID of the showtime you want to delete: ");
                                        int id = sc.nextInt();
                                        sc.nextLine();

                                        ctrl.deleteShowtime(id);
                                        System.out.println("Showtime deleted successfully!");

                                        break;
                                    }
                                }

                                break;
                            }
                            case 3: {
                                // Modify screen
                                ctrl.staffMenu2();
                                int opt2 = sc.nextInt();
                                sc.nextLine();

                                switch (opt2) {
                                    case 1: {
                                        // add screen
                                        System.out.println("Please enter the number of standard seats: ");
                                        int nrStandardSeats = sc.nextInt();
                                        sc.nextLine();

                                        System.out.println("Please enter the number of VIP seats: ");
                                        int nrVipSeats = sc.nextInt();
                                        sc.nextLine();

                                        System.out.println("Please enter the number of premium seats: ");
                                        int nrPremiumSeats = sc.nextInt();
                                        sc.nextLine();

                                        ctrl.addScreen(nrStandardSeats, nrVipSeats, nrPremiumSeats);
                                        System.out.println("Screen added successfully!");

                                        break;
                                    }
                                    case 2: {
                                        // update screen
                                        System.out.println("Please enter the ID of the screen you want to delete: ");
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
                                        System.out.println("Screen updated successfully!");

                                        break;
                                    }
                                    case 3: {
                                        //delete screen
                                        System.out.println("Please enter the ID of the screen you want to delete: ");
                                        int id = sc.nextInt();
                                        sc.nextLine();

                                        ctrl.deleteScreen(id);
                                        System.out.println("Screen deleted successfully!");

                                        break;
                                    }
                                }

                                break;
                            }
                            case 4: {
                                action = false;
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
            }
        }
    }
}
