package Domain;

import java.time.LocalDate;
import java.util.List;

public abstract class Membership {
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Booking> bookings;

    public Membership(Customer customer, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.startDate = LocalDate.now();
        this.endDate = endDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
