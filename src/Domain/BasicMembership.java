package Domain;

import java.time.LocalDate;
import java.util.List;

/**
 * BasicMembership represents a basic membership with a fixed price and discount rate.
 * It provides basic discounts to customers based on membership status.
 */
public class BasicMembership extends Membership{
    private int customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price = 50;
    private double discount = 10;

    /**
     * Constructs a BasicMembership with specified customer ID, start date, and end date.
     * @param customerId the unique identifier of the customer
     * @param startDate the starting date of the membership
     * @param endDate the ending date of the membership
     */
    public BasicMembership(int customerId, LocalDate startDate, LocalDate endDate) {
        super(customerId, startDate, endDate);
    }

    /**
     * Gets the price of the basic membership.
     * @return the price of the membership
     */
    public double getPrice() { return price; }

    /**
     * Sets a new price for the basic membership.
     * @param price the new price to be set for the membership
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Applies the membership discount to the given price.
     * @param price the original price before discount
     * @return the price after applying the basic membership discount
     */
    @Override
    public double offerDiscount(double price) {
        return price - price * (discount / 100);
    }
}
