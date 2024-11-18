package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The showtime class represents a showtime for a specific movie.
 */
public class Showtime implements HasId {
    private int id;
    private int screenId;
    private int movieId;
    private LocalDate date;
    private LocalTime startTime;
    private int duration;
    private List<Seat> seats;

    /**
     *Constructs a Showtime instance with the specified screen ID, movie ID, date, start time, duration, and seat list.
     *
     * @param id The unique identifier of the showtime
     * @param screenId The ID of the screen
     * @param movieId The ID of the movie
     * @param date The date of the showtime
     * @param startTime The start time of the showtime
     * @param duration The duration of the showtime
     * @param seats A list of available seats for the showtime
     */

    public Showtime(int id, int screenId, int movieId, LocalDate date, LocalTime startTime, int duration, List<Seat> seats) {
        this.id = id;
        this.screenId = screenId;
        this.movieId = movieId;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
        this.seats = seats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() { return id; }

    /**
     * Gets the screen ID associated with this showtime.
     *
     * @return the screen ID as an integer.
     */
    public int getScreenId() {
        return screenId;
    }

    /**
     * Gets the movie ID associated with this showtime.
     *
     * @return the movie ID as an integer.
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Gets the date of this showtime.
     *
     * @return the date of the showtime as a {@code LocalDate}.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the start time of this showtime.
     *
     * @return the start time as a {@code LocalTime}.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the duration of the showtime in minutes.
     *
     * @return the duration in minutes as an integer.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the list of seats available for this showtime.
     *
     * @return a {@code List<Seat>} representing the available seats.
     */
    public List<Seat> getSeats() {
        return seats;
    }

    /**
     * Sets a new list of seats for this showtime.
     *
     * @param seats the updated list of seats.
     */
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    /**
     * Converts the showtime to a CSV format string.
     * The seats are serialized as a semicolon-separated list of seat CSV strings.
     */
    @Override
    public String toCSV() {
        String seatsCSV = seats.stream().map(Seat::toCSV).collect(Collectors.joining(";")); // Combine with semicolon separator
        return String.join(",", String.valueOf(id), String.valueOf(screenId), String.valueOf(movieId), date.toString(), startTime.toString(), String.valueOf(duration), seatsCSV);
    }

    /**
     * Returns the header for a CSV file representing showtimes.
     */
    @Override
    public String[] getHeader() {
        return new String[]{"id", "screenId", "movieId", "date", "startTime", "duration", "seats"};
    }

    /**
     * Creates a Showtime object from a CSV line.
     * Assumes the seats are serialized as a semicolon-separated list of seat CSV strings.
     */
    public static Showtime fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", 7);

        List<Seat> seats = new ArrayList<>();
        if (!parts[6].isEmpty()) {
            String[] seatParts = parts[6].split(";");
            for (String seatCSV : seatParts) {
                seats.add(Seat.fromCSV(seatCSV));
            }
        }

        return new Showtime(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), LocalDate.parse(parts[3]), LocalTime.parse(parts[4]), Integer.parseInt(parts[5]), seats);
    }
}