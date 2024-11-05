package Domain;

public class Showtime {
    private int screenId;
    private int movieId;
    private int startTime;
    private int endTime;

    public Showtime(int screenId, int movieId, int startTime, double duration) {
        this.screenId = screenId;
        this.movieId = movieId;
        this.startTime = startTime;
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

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
