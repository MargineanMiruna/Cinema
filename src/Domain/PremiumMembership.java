package Domain;

import java.time.LocalDate;
import java.util.List;

public class PremiumMembership extends Membership{
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Booking> bookings;
    private double price = 100;
    private int discount = 20;

    public PremiumMembership(Customer customer, LocalDate startDate, LocalDate endDate, List<Booking> bookings) {
        super(customer, startDate, endDate);

    }

    public double getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
