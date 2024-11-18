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

    /**
     * Converts the Ticket object to a CSV format string.
     */
    @Override
    public String toCSV() {
        return String.join(",", String.valueOf(getId()), String.valueOf(getBookingId()), String.valueOf(getScreenId()), String.valueOf(getSeatNr()), String.valueOf(getPrice()));
    }

    /**
     * Returns the header for the CSV file representing tickets.
     */
    @Override
    public String[] getHeader() {
        return new String[]{"id", "bookingId", "screenId", "seatNr", "price"};
    }

    /**
     * Creates a Ticket object from a CSV line.
     */
    @Override
    public Ticket fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        return new Ticket(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Double.parseDouble(parts[4]));
    }
}

