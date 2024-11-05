package Domain;

import java.time.LocalDate;
import java.util.List;

public class BasicMembership extends Membership{
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Booking> bookings;
    private double price = 50;
    private int discount = 10;

    public BasicMembership(Customer customer, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        super(customer, startDate, endDate);
    }

    public double getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

}
