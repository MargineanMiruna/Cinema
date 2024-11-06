package UI;

import Controller.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsoleApp {
    Controller ctrl = new Controller();

    public void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the CinemApp!\n1. Customer\n2. Staff\n3. Exit");
        int opt = sc.nextInt();

        while(true) {
            switch(opt) {
                case 1: {
                    System.out.println("1. Log in\n2. Sign up\n3. Exit");
                    opt = sc.nextInt();
                    if(opt == 3)
                        break;
                    if(opt == 2) {
                        System.out.println("Please enter your first name: ");
                        String firstName = sc.next();
                        System.out.println("Please enter your last name: ");
                        String lastName = sc.next();
                        System.out.println("Please enter your email: ");
                        String email = sc.next();
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
                    ctrl.logCustomer(email);
                    break;
                }
                case 2: {
                    System.out.println("1. Log in\n2. Sign up\n3. Exit");
                    opt = sc.nextInt();
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
                    //ctrl.logStaff(email);
                    break;
                }
                case 3: break;
            }
        }

    }
}
