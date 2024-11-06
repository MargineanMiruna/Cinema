package Domain;

public class Seat {
    private int seatNr;
    private SeatType type;

    public Seat(int seatNr, SeatType type) {
        this.seatNr = seatNr;
        this.type = type;
    }

    public int getSeatNr() {
        return seatNr;
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

    public void setType(SeatType type) {
        this.type = type;
    }
}
