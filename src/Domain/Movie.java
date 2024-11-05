package Domain;
import java.time.LocalDate;

public class Movie {
    private int id;
    private String title;
    private boolean pg;
    private String genre;
    private LocalDate releaseDate;

    public Movie(int id, String title, boolean pg, String genre, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.pg = pg;
        this.genre = genre;
        this.releaseDate = releaseDate;

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getPg() {
        return pg;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPg(boolean pg) {
        this.pg = pg;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
