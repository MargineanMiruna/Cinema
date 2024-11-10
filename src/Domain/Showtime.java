package Domain;

import java.time.LocalDate;

public class Showtime {
    private int screenId;
    private int movieId;
    private LocalDate date;
    private int startTime;
    private double duration;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }


    public Showtime(int screenId, int movieId, LocalDate date, int startTime, double duration) {
        this.screenId = screenId;
        this.movieId = movieId;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
    }

    public int getScreenId() {
        return screenId;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getStartTime() {
        return startTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
