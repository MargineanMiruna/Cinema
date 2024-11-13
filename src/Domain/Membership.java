package Domain;

import java.time.LocalDate;
import java.util.List;

/**
 * The abstract Membership class represents a membership for a customer, with a specific start date, end date, and discount.
 * This class is intended to be extended by specific types of memberships.
 */
public abstract class Membership {
    int customerId;
    LocalDate startDate;
    LocalDate endDate;
    double discount;

    /**
     * Constructs a Membership with the specified customer ID, start date, and end date.
     * Note that the start date is set to the current date when creating the membership.
     * @param customerId the unique ID of the customer
     * @param startDate the start date of the membership
     * @param endDate the end date of the membership; each membership lasts one month
     */
    public Membership(int customerId, LocalDate startDate, LocalDate endDate) {
        this.customerId = customerId;
        this.startDate = LocalDate.now();
        this.endDate = endDate;
    }

    /**
     * Gets the customer ID associated with this membership.
     * @return the unique customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Gets the start date of the membership.
     * @return the start date of the membership
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date of the membership.
     * @return the end date of the membership
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Abstract method to apply a discount to a given price based on the membership type.
     * Each specific membership type will provide its own implementation of this discount calculation.
     * @param price the original price before discount
     * @return the price after applying the membership discount
     */
    public abstract double offerDiscount(double price);
}
