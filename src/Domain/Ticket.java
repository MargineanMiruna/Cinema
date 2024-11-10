package Domain;

public class Ticket {
    int bookingId;
    int seatId;
    double price;

    public Ticket(int bookingId, int seatId, double price) {
        this.bookingId = bookingId;
        this.seatId = seatId;
        this.price = price;
    }

    public int getBookingId() { return bookingId; }

    public int getSeatId() {
        return seatId;
    }

    public double getPrice() {
        return price;
    }
}

