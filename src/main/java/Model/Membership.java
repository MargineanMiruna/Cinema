package Model;

import java.time.LocalDate;

/**
 * The abstract Membership class represents a membership for a customer, with a specific start date, end date, and discount.
 * This class is intended to be extended by specific types of memberships.
 */
public abstract class Membership implements HasId {
    protected int id;
    protected int customerId;
    protected LocalDate startDate;
    protected LocalDate endDate;

    /**
     * Constructs a Membership with the specified customer ID, start date, and end date.
     * Note that the start date is set to the current date when creating the membership.
     *
     * @param id The unique identifier of the membership
     * @param customerId The unique ID of the customer
     * @param startDate The start date of the membership
     * @param endDate The end date of the membership; each membership lasts one month
     */
    public Membership(int id, int customerId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.customerId = customerId;
        this.startDate = LocalDate.now();
        this.endDate = endDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() { return id; }

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
