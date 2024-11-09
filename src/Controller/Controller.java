package Controller;


import Domain.*;
import Service.CinemaService;

import java.time.LocalDate;
import java.time.Period;

public class Controller {
    CinemaService cinemaService = new CinemaService();

    int getAge(LocalDate birthday) {
        LocalDate today = LocalDate.now();
        return Period.between(birthday, today).getYears();
    }

    public void createCustomer(String firstname, String lastname, String email, LocalDate birthday) {
        boolean underaged = getAge(birthday) < 18;
        cinemaService.addCustomer(firstname, lastname, email, underaged);
    }

    public Customer logCustomer(String email) {
        try {
            if(cinemaService.findCustomerByEmail(email) != null) {
                return cinemaService.findCustomerByEmail(email);
            }
            else {
                System.out.println("No account by this email!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred: ");
            return null;

        }
    }

    public void createStaff(String firstname, String lastname, String email) {
        cinemaService.addStaff(firstname, lastname, email);
    }

    public Staff logStaff(String email) {
        try {
            if(cinemaService.findStaffByEmail(email) != null) {
                return cinemaService.findStaffByEmail(email);
            }
            else {
                System.out.println("No account by this email!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred: ");
            return null;

        }
    }
}
