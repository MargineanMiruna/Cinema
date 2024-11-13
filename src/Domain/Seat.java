package Domain;
/**
 * Represents a seat within a screening environment, with a seat number and seat type.
 * Each Seat has a type associated with it (such as standard, VIP, or premium),
 * which determines the price of the seat.
 *
 * The class provides methods to retrieve and set the seat number, seat type, and price.
 *
 * */
public class Seat {
    private int seatNr;
    private SeatType type;
    /**
     * Constructs a Seat instance with the specified seat number and seat type.
     *
     * @param seatNr the number of the seat
     * @param type the type of the seat, which determines its price
     */
    public Seat(int seatNr, SeatType type) {
        this.seatNr = seatNr;
        this.type = type;
    }
    /**
     * Gets the seat number.
     *
     * @return the seat number as an integer.
     */
    public int getSeatNr() {
        return seatNr;
    }
    /**
     * Gets the price of the seat based on its type.
     *
     * @return the price of the seat as an integer.
     */
    public int getPrice(){
        return type.getPrice();
    }
    /**
     * Gets the type of the seat.
     *
     * @return the {@code SeatType} of the seat.
     */
    public SeatType getType() {
        return type;
    }
    /**
     * Sets a new seat number for this seat.
     *
     * @param seatNr the new seat number as an integer.
     */
    public void setSeatNr(int seatNr) {
        this.seatNr = seatNr;
    }
    /**
     * Sets a new seat type for this seat.
     *
     * @param type the new {@code SeatType} for the seat.
     */
    public void setType(SeatType type) {
        this.type = type;
    }
}
