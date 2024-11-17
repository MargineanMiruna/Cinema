package Model;

/**
 * The ticket class represents a ticket for a booking in a screening system.
 */
public class Ticket implements HasId {
    private int id;
    private int bookingId;
    private int screenId;
    private int seatNr;
    private double price;

    /**
     * Constructs a Ticket instance with specified booking ID, screen ID, seat number, and price.
     *
     * @param id The unique identifier of the ticket
     * @param bookingId The id of the booking
     * @param screenId The id of the screen
     * @param seatNr The nr of the seat
     * @param price The price of the ticket
     */
    public Ticket(int id, int bookingId, int screenId, int seatNr, double price) {
        this.id = id;
        this.bookingId = bookingId;
        this.screenId = screenId;
        this.seatNr = seatNr;
        this.price = price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() { return id; }

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
     * Gets the price of the ticket.
     * @return the price as a double.
     */
    public double getPrice() {
        return price;
    }
}

