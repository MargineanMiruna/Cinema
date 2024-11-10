package UI;

import Controller.Controller;
import Domain.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsoleApp {
    Controller ctrl = new Controller();

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the CinemApp!\n1. Customer\n2. Staff\n3. Exit");
        while(true) {
            int userType = sc.nextInt();
            sc.nextLine();
            switch(userType) {
                case 1: {
                    System.out.println("1. Log in\n2. Sign up\n3. Back");
                    int opt = sc.nextInt();
                    sc.nextLine();

                    if(opt == 3)
                        break;
                    if(opt == 2) {
                        System.out.println("Please enter your first name: ");
                        String firstName = sc.nextLine();
                        System.out.println("Please enter your last name: ");
                        String lastName = sc.nextLine();
                        System.out.println("Please enter your email: ");
                        String email = sc.nextLine();
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        System.out.println("Please enter your birthday: ");
                        String date = sc.nextLine();
                        LocalDate birthday = LocalDate.parse(date, fmt);
                        ctrl.createCustomer(firstName, lastName, email, birthday);
                        System.out.println("Account created successfully!");
                    }

                    System.out.println("Log in");
                    System.out.println("Please enter your email: ");
                    String email = sc.next();
                    Customer loggedCustomer = ctrl.logCustomer(email);
                    System.out.println("Hello, " + loggedCustomer.getFirstName() + " " + loggedCustomer.getLastName() + ", you logged in successfully");

                    ctrl.customerMenu();
                    opt = sc.nextInt();
                    sc.nextLine();
                    switch(opt) {
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
                            int nrOfCustomers = sc.nextInt();
                            sc.nextLine();
                            ctrl.displayAvailableSeats(showtimeId);
                            System.out.println("Please choose your seats: ");
                            //List<Seat> seats = ctrl.chooseSeats();
                            LocalDate today = LocalDate.now();
                            //ctrl.createBooking(loggedCustomer, showtimeId, LocalDate.now(), nrOfCustomers, seats);
                            break;
                        }
                        case 3: {
                            //create Membership
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    System.out.println("1. Log in\n2. Sign up\n3. Back");
                    int opt = sc.nextInt();
                    if(opt == 3)
                        break;
                    if(opt == 2) {
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

                    break;
                }
                case 3:
                {
                    System.exit(0);
                    break;
                }
            }
        }

    }
}
