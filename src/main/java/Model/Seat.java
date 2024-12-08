package Model;

import java.util.Objects;

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

    /**
     * Converts the seat to a CSV format string.
     */
    @Override
    public String toCSV() {
        return String.join(",", String.valueOf(id), String.valueOf(seatNr), String.valueOf(type));
    }

    /**
     * Returns the header for the CSV file representing seats.
     */
    @Override
    public String[] getHeader() {
        return new String[] {"id", "seatNr", "type"};
    }

    /**
     * Creates a Seat object from a CSV line.
     */
    public static Seat fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        return new Seat(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), SeatType.valueOf(parts[2]));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Seat seat = (Seat) obj;
        return id == seat.id &&
                seatNr == seat.seatNr &&
                type == seat.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seatNr, type);
    }



}
