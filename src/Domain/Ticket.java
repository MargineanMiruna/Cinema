package Domain;

/**
 * Represents a ticket for a booking in a screening system.
 * Each ticket includes details such as the booking ID, screen ID, seat number, and price.
 *
 * This class is used to store and retrieve information about individual tickets.
 */
public class Ticket {
    private int bookingId;
    private int screenId;
    private int seatNr;
    private double price;

    /**
     * Constructs a Ticket instance with specified booking ID, screen ID, seat number, and price.
     *
     * @param bookingId the id of the booking
     * @param screenId the id of the screen
     * @param seatNr the nr of the seat
     * @param price the price of the ticket
     */

    public Ticket(int bookingId, int screenId, int seatNr, double price) {
        this.bookingId = bookingId;
        this.screenId = screenId;
        this.seatNr = seatNr;
        this.price = price;
    }

    /**
     * Gets the booking ID associated with this ticket.
     * @return the booking ID as an integer.
     */
    public int getBookingId() { return bookingId; }

    /**
     * Gets the screen ID associated with this ticket.
     * @return the screen ID as an integer.
     */
    public int getScreenId() { return screenId; }

    /**
     * Gets the seat number associated with this ticket.
     * @return the seat number as an integer.
     */
    public int getSeatNr() {
        return seatNr;
    }

    /**
     *Gets the price of the ticket.
     * @return the price as a double.
     */

    public double getPrice() {
        return price;
    }
}

