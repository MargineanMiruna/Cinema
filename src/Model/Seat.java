package Model;

/**
 * The seat class represents a seat within a screening environment, with a seat number and seat type.
 */
public class Seat implements HasId{
    private int id;
    private int seatNr;
    private SeatType type;

    /**
     * Constructs a Seat instance with the specified seat number and seat type.
     *
     * @param id The unique identifier of the seat
     * @param seatNr The number of the seat
     * @param type The type of the seat, which determines its price
     */
    public Seat(int id, int seatNr, SeatType type) {
        this.id = id;
        this.seatNr = seatNr;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() { return id; }

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
}
