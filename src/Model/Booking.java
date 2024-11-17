package Model;

import java.time.LocalDate;
import java.util.List;

/**
 * The booking class represents a reservation made by a customer for a specific showtime.
 */
public class Booking implements HasId {
    private int id;
    private int customerId;
    private int showtimeId;
    private LocalDate date;
    private int nrOfCustomers;
    private List<Integer> tickets;

    /**
     * Constructs a Booking with the specified customer ID, showtime ID, booking date, and number of customers.
     *
     * @param id The unique identifier of the booking
     * @param customerId The ID of the customer making the booking
     * @param showtimeId The ID of the showtime for the booking
     * @param date The date of the booking
     * @param nrOfCustomers The number of customers included in the booking
     */
    public Booking(int id, int customerId, int showtimeId, LocalDate date, int nrOfCustomers) {
        this.id = id;
        this.customerId = customerId;
        this.showtimeId = showtimeId;
        this.date = date;
        this.nrOfCustomers = nrOfCustomers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() { return id; }

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

    /**
     * Sets the list of tickets associated with this booking.
     * @param tickets a list of ticket IDs
     */
    public void setTickets(List<Integer> tickets) {
        this.tickets = tickets;
    }
}
