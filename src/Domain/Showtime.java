package Domain;

public class Showtime {
    int screenId;
    int movieId;
    int startTime;
    double duration;

    public Showtime(int screenId, int movieId, int startTime, double duration) {
        this.screenId = screenId;
        this.movieId = movieId;
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
