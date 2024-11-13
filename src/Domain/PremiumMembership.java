package Domain;

import java.time.LocalDate;
import java.util.List;
/**
 * Represents a premium membership plan for a customer, which includes benefits such as a fixed membership price
 * and a discount on purchases.
 *
 * The PremiumMembership class extends the Membership class, inheriting the membership's start and end dates,
 * and customer ID. This membership type offers a customizable discount percentage on applicable purchases.
 *
 */
public class PremiumMembership extends Membership{
    int customerId;
    LocalDate startDate;
    LocalDate endDate;
    double price = 100;
    double discount = 20;

    /**
     * Constructs a PremiumMembership with the specified customer ID, start date, and end date.
     * The membership duration is typically set for a period of 30 days from the start date.
     *
     * @param customerId customerId the ID of the customer associated with this membership
     * @param startDate startDate the start date of the membership
     * @param endDate endDate the end date of the membership, usually 30 days after the start date
     */

    public PremiumMembership(int customerId, LocalDate startDate, LocalDate endDate) {
        super(customerId, startDate, endDate);
    }
    /**
     * Gets the fixed price of the premium membership.
     *
     * @return the price of the membership as a double.
     */
    public double getPrice() {
        return price;
    }
    /**
     * Sets a new price for the premium membership.
     *
     * @param price the new price as a double.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Applies the discount to a given price, reducing it by a specified percentage.
     *
     * @param price the original price on which the discount is applied
     * @return the discounted price as a double.
     */
    @Override
    public double offerDiscount(double price) {
        return price - price * (discount / 100);
    }
}
