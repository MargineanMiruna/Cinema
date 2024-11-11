package Domain;

public class Ticket {
    int bookingId;
    int screenId;
    int seatNr;
    double price;

    /**
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

    public int getBookingId() { return bookingId; }

    public int getScreenId() { return screenId; }

    public int getSeatNr() {
        return seatNr;
    }

    public double getPrice() {
        return price;
    }
}

