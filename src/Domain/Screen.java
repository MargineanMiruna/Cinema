package Domain;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a screen in a cinema or theater, including a specified number of standard, VIP, and premium seats.
 * The Screen class manages seat initialization and categorizes seats based on seat type.
 *
 * Each Screen instance contains a list of Seat objects, automatically populated based on
 * the specified number of each seat type upon construction.
 *
 *
 */
public class Screen {
    private int nrStandardSeats;
    private int nrVipSeats;
    private int nrPremiumSeats;
    private List<Seat> seats;

    /**
     * Constructs a Screen instance with the specified number of standard, VIP, and premium seats.
     * The seats are initialized and assigned seat numbers starting from 1, with standard seats followed by VIP and premium seats.
     *
     * @param nrStandardSeats the number of standard seats
     * @param nrVipSeats the number of VIP seats
     * @param nrPremiumSeats the number of premium seats
     */
    public Screen(int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        this.nrStandardSeats = nrStandardSeats;
        this.nrVipSeats = nrVipSeats;
        this.nrPremiumSeats = nrPremiumSeats;

        this.seats = new ArrayList<Seat>();
        for (int i = 1; i <= nrStandardSeats; i++) {
            seats.add(new Seat(i, SeatType.standard));
        }
        for (int i = 1 + nrStandardSeats; i <= nrVipSeats + nrStandardSeats; i++) {
            seats.add(new Seat(i, SeatType.vip));
        }
        for( int i = 1 + nrStandardSeats + nrVipSeats; i <=nrPremiumSeats + nrVipSeats + nrStandardSeats; i++) {
            seats.add(new Seat(i, SeatType.premium));
        }
    }
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
     * Sets a new number of VIP seats for the screen.
     *
     * @param nrVipSeats the new number of VIP seats.
     */
    public void setNrVipSeats(int nrVipSeats) {
        this.nrVipSeats = nrVipSeats;
    }
    /**
     * Sets a new number of standard seats for the screen.
     *
     * @param nrStandardSeats the new number of standard seats.
     */
    public void setNrStandardSeats(int nrStandardSeats) {
        this.nrStandardSeats = nrStandardSeats;
    }
    /**
     * Sets a new number of premium seats for the screen.
     *
     * @param nrPremiumSeats the new number of premium seats.
     */
    public void setNrPremiumSeats(int nrPremiumSeats) {
        this.nrPremiumSeats = nrPremiumSeats;
    }
    /**
     * Gets the list of all seats in the screen, including standard, VIP, and premium seats.
     *
     * @return a List<Seat> representing all seats in the screen.
     */
    public List<Seat> getSeats() {
        return seats;
    }
}
