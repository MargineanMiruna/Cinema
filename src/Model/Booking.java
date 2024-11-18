package Model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

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

    /**
     * Convert the booking to a CSV format string.
     * The tickets list is serialized as a semicolon-separated string.
     */
    @Override
    public String toCSV() {
        String ticketsCSV = "";
        if(tickets != null)
             ticketsCSV = tickets.stream().map(String::valueOf).collect(Collectors.joining(";"));

        return String.join(",", String.valueOf(id), String.valueOf(customerId), String.valueOf(showtimeId), String.valueOf(date), String.valueOf(nrOfCustomers), ticketsCSV);
    }

    /**
     * Return the header for a CSV file representing bookings.
     */
    @Override
    public String[] getHeader() {
        return new String[]{"id", "customerId", "showtimeId", "date", "nrOfCustomers", "tickets"};
    }

    /**
     * Create a Booking object from a CSV line.
     */
    @Override
    public Booking fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        List<Integer> tickets = null;
        if(parts.length > 5 && !parts[5].isEmpty())
            tickets = Arrays.stream(parts[5].split(";")).map(Integer::parseInt).collect(Collectors.toList());

        Booking booking = new Booking(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), LocalDate.parse(parts[3]), Integer.parseInt(parts[4]));
        booking.setTickets(tickets);
        return booking;
    }
}
