package Domain;

import java.time.LocalDate;
import java.util.List;

public abstract class Membership {
    int customerId;
    LocalDate startDate;
    LocalDate endDate;
    double discount;

    /**
     *
     * @param customerId the unique id of the customer
     * @param startDate start date of the membership
     * @param endDate end date of the membership ( a membership lasts a month)
     */
    public Membership(int customerId, LocalDate startDate, LocalDate endDate) {
        this.customerId = customerId;
        this.startDate = LocalDate.now();
        this.endDate = endDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public abstract double offerDiscount(double price);
}
