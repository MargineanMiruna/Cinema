package Model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The basic membership class represents a basic membership with a fixed price and discount rate.
 * It provides basic discounts to customers based on membership status.
 */
public class BasicMembership extends Membership {
    private final double price = 50;
    private final double discount = 10;

    /**
     * Constructs a BasicMembership with specified customer ID, start date, and end date.
     *
     * @param id The unique identifier of the membership
     * @param customerId The unique identifier of the customer
     * @param startDate The starting date of the membership
     * @param endDate The ending date of the membership
     */
    public BasicMembership(int id, int customerId, LocalDate startDate, LocalDate endDate) {
        super(id, customerId, startDate, endDate);
    }

    /**
     * Gets the price of the basic membership.
     * @return the price of the membership
     */
    public double getPrice() { return price; }


    /**
     * Applies the membership discount to the given price.
     * @param price the original price before discount
     * @return the price after applying the basic membership discount
     */
    @Override
    public double offerDiscount(double price) {
        return price - price * (discount / 100);
    }

    /**
     * Convert the basic membership to a CSV format string.
     */
    @Override
    public String toCSV() {
        return String.join(",", String.valueOf(id), String.valueOf(customerId), String.valueOf(startDate), String.valueOf(endDate));
    }

    /**
     * Return the header for a CSV file representing basic membership.
     */
    @Override
    public String[] getHeader() {
        return new String[]{"id", "customerId", "startDate", "endDate"};
    }

    /**
     * Create a Basic Membership object from a CSV line.
     */
    public static BasicMembership fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        return new BasicMembership(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), LocalDate.parse(parts[2]), LocalDate.parse(parts[3]));
    }
}
