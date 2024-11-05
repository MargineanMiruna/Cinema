package Domain;

public class Seat {
    private int seatNr;
    private boolean booked;
    private SeatType type;

    public Seat(int seatNr, boolean booked, SeatType type) {
        this.seatNr = seatNr;
        this.booked = booked;
        this.type = type;
    }

    public int getSeatNr() {
        return seatNr;
    }

    public boolean isBooked() {
        return booked;
    }

    public int getPrice(){
        return type.getPrice();
    }

    public SeatType getType() {
        return type;
    }

    public void setSeatNr(int seatNr) {
        this.seatNr = seatNr;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public void setType(SeatType type) {
        this.type = type;
    }
}
