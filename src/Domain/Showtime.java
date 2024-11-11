package Domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Showtime {
    int screenId;
    int movieId;
    LocalDate date;
    LocalTime startTime;
    int duration;
    List<Seat> seats;

    /**
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

    public int getScreenId() {
        return screenId;
    }

    public int getMovieId() {
        return movieId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Seat> getSeats() { return seats; }

    public void setSeats(List<Seat> seats) { this.seats = seats; }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
