package Domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Booking represents a reservation made by a customer for a specific showtime.
 * It contains information about the customer, showtime, booking date, and the number of tickets.
 */
public class Booking {
    private int customerId;
    private int showtimeId;
    private LocalDate date;
    private int nrOfCustomers;
    private List<Integer> tickets;

    /**
     * Constructs a Booking with the specified customer ID, showtime ID, booking date, and number of customers.
     * @param customerId the unique identifier of the customer making the booking
     * @param showtimeId the unique identifier of the showtime for the booking
     * @param date the date of the booking
     * @param nrOfCustomers the number of customers included in the booking
     */
    public Booking(int customerId, int showtimeId, LocalDate date, int nrOfCustomers) {
        this.customerId = customerId;
        this.showtimeId = showtimeId;
        this.date = date;
        this.nrOfCustomers = nrOfCustomers;
    }

    /**
     * Sets the customer ID for the booking.
     * @param customerId the new customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Sets the date of the booking.
     * @param date the new booking date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Sets the number of customers for this booking.
     * @param nrOfCustomers the new number of customers
     */
    public void setNrOfCustomers(int nrOfCustomers) {
        this.nrOfCustomers = nrOfCustomers;
    }

    /**
     * Sets the list of tickets associated with this booking.
     * @param tickets a list of ticket IDs
     */
    public void setTickets(List<Integer> tickets) {
        this.tickets = tickets;
    }

    /**
     * Gets the customer ID associated with this booking.
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Gets the showtime ID associated with this booking.
     * @return the showtime ID
     */
    public int getShowtimeId() {
        return showtimeId;
    }

    /**
     * Sets the showtime ID for this booking.
     * @param showtimeId the new showtime ID
     */
    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    /**
     * Gets the date of the booking.
     * @return the booking date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the number of customers included in this booking.
     * @return the number of customers
     */
    public int getNrOfCustomers() {
        return nrOfCustomers;
    }

    /**
     * Gets the list of ticket IDs associated with this booking.
     * @return a list of ticket IDs
     */
    public List<Integer> getTickets() {
        return tickets;
    }
}
