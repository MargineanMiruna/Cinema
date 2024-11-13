package Domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Represents a showtime for a specific movie, including details such as the screen, date, start time, duration and available seats.
 * A Showtime instance includes all the relevant information needed to schedule and display a showtime for a movie.
 * Each showtime is associated with a particular screen and includes a list of available seats.
 */
public class Showtime {
    private int screenId;
    private int movieId;
    private LocalDate date;
    private LocalTime startTime;
    private int duration;
    private List<Seat> seats;

    /**
     *Constructs a Showtime instance with the specified screen ID, movie ID, date, start time, duration, and seat list.
     *
     * @param screenId
     * @param movieId
     * @param date of the showtime
     * @param startTime of the showtime
     * @param duration of the showtime
     * @param seats list of available
     */

    public Showtime(int screenId, int movieId, LocalDate date, LocalTime startTime, int duration, List<Seat> seats) {
        this.screenId = screenId;
        this.movieId = movieId;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
        this.seats = seats;
    }

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
     * Sets a new screen ID for this showtime.
     *
     * @param screenId the new screen ID.
     */
    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    /**
     * Sets a new movie ID for this showtime.
     *
     * @param movieId the new movie ID.
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    /**
     * Sets a new start time for this showtime.
     *
     * @param startTime the new start time as a {@code LocalTime}.
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets a new duration for this showtime.
     *
     * @param duration the new duration in minutes.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Sets a new date for this showtime.
     *
     * @param date the new date as a {@code LocalDate}.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
}