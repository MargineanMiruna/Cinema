package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Screen(int id, int nrStandardSeats, int nrVipSeats, int nrPremiumSeats, List<Seat> seats) {
        this.id = id;
        this.nrStandardSeats = nrStandardSeats;
        this.nrVipSeats = nrVipSeats;
        this.nrPremiumSeats = nrPremiumSeats;
        this.seats = seats;
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

    /**
     * Converts the screen to a CSV format string.
     * The seats are serialized as a semicolon-separated list of seat IDs.
     */
    @Override
    public String toCSV() {
        String seatsCSV = seats.stream().map(Seat::toCSV).collect(Collectors.joining(";"));
        return String.join(",", String.valueOf(id), String.valueOf(nrStandardSeats), String.valueOf(nrVipSeats), String.valueOf(nrPremiumSeats), seatsCSV);
    }

    /**
     * Returns the header for a CSV file representing screens.
     */
    @Override
    public String[] getHeader() {
        return new String[]{"id", "nrStandardSeats", "nrVipSeats", "nrPremiumSeats", "seats"};
    }

    /**
     * Creates a Screen object from a CSV line.
     * Assumes the seats are serialized as a semicolon-separated list of seat CSV strings.
     */
    public static Screen fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", 5);

        List<Seat> seats = new ArrayList<>();
        if (!parts[4].isEmpty()) {
            String[] seatParts = parts[4].split(";");
            for (String seatCSV : seatParts) {
                seats.add(Seat.fromCSV(seatCSV));
            }
        }

        Screen screen = new Screen(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), seats);
        return screen;
    }
}
