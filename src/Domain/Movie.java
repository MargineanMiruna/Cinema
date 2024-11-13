package Domain;
import java.time.LocalDate;

/**
 * Represents a movie in the cinema system with details about its title, age restrictions, genre, and release date.
 */
public class Movie {
    private String title;
    /** The parental guidance status of the movie.
     *  If true, only customers who are not underaged can watch the movie.
     *  If false, any customer can watch the movie.
     */
    private boolean pg;
    private String genre;
    private LocalDate releaseDate;

    /**
     * Constructs a Movie with the specified title, parental guidance restriction, genre, and release date.
     * @param title the title of the movie
     * @param pg true if only customers who are not underaged can watch the movie, false otherwise
     * @param genre the genre of the movie
     * @param releaseDate the release date of the movie
     */
    public Movie(String title, boolean pg, String genre, LocalDate releaseDate) {
        this.title = title;
        this.pg = pg;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    /**
     * Gets the title of the movie.
     * @return the movie title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the parental guidance restriction status of the movie.
     * @return true if the movie is restricted to non-underaged customers, false otherwise
     */
    public boolean getPg() {
        return pg;
    }

    /**
     * Gets the genre of the movie.
     * @return the genre of the movie
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Gets the release date of the movie.
     * @return the release date of the movie
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the title of the movie.
     * @param title the new title of the movie
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the parental guidance restriction of the movie.
     * @param pg the new parental guidance restriction; true if only non-underaged customers can watch, false otherwise
     */
    public void setPg(boolean pg) {
        this.pg = pg;
    }

    /**
     * Sets the genre of the movie.
     * @param genre the new genre of the movie
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Sets the release date of the movie.
     * @param releaseDate the new release date of the movie
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
