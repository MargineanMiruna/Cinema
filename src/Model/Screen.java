package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * The screen class represents a screen in a cinema, including a specified number of standard, VIP, and premium seats.
 */
public class Screen implements HasId {
    private int id;
    private int nrStandardSeats;
    private int nrVipSeats;
    private int nrPremiumSeats;
    private List<Seat> seats;

    /**
     * Constructs a Screen instance with the specified number of standard, VIP, and premium seats.
     * The seats are initialized and assigned seat numbers starting from 1, with standard seats followed by VIP and premium seats.
     *
     * @param id The unique identifier of the screen
     * @param nrStandardSeats The number of standard seats
     * @param nrVipSeats The number of VIP seats
     * @param nrPremiumSeats The number of premium seats
     */
    public Screen(int id, int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        this.id = id;
        this.nrStandardSeats = nrStandardSeats;
        this.nrVipSeats = nrVipSeats;
        this.nrPremiumSeats = nrPremiumSeats;
        this.seats = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() { return id; }

    /**
     * Gets the number of standard seats in the screen.
     *
     * @return the number of standard seats as an integer.
     */
    public int getNrStandardSeats() {
        return nrStandardSeats;
    }

    /**
     * Gets the number of VIP seats in the screen.
     *
     * @return the number of VIP seats as an integer.
     */
    public int getNrVipSeats() {
        return nrVipSeats;
    }

    /**
     * Gets the number of premium seats in the screen.
     *
     * @return the number of premium seats as an integer.
     */
    public int getNrPremiumSeats() {
        return nrPremiumSeats;
    }

    /**
     * Gets a list of all the seats in the screen.
     *
     * @return a list of all the seats as a list of Seat.
     */
    public List<Seat> getSeats() {
        return seats;
    }

    /**
     * Sets a list of the seats for the screen.
     *
     * @param seats a list of all the seats as a list of Seat
     */
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
