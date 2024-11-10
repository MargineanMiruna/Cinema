package Domain;

public class Ticket {
    int bookingId;
    int screenId;
    int seatNr;
    double price;

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

