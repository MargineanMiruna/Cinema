package Model;
import java.time.LocalDate;

/**
 * The movie class represents a movie in the cinema system.
 */
public class Movie implements HasId {
    private int id;
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
     *
     * @param id The unoque identifier of the movie
     * @param title The title of the movie
     * @param pg true if only customers who are not underaged can watch the movie, false otherwise
     * @param genre The genre of the movie
     * @param releaseDate The release date of the movie
     */
    public Movie(int id, String title, boolean pg, String genre, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.pg = pg;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() { return id; }

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
     * Converts the movie to a CSV format string.
     */
    @Override
    public String toCSV() {
        return String.join(",", String.valueOf(id), title, String.valueOf(pg), genre, String.valueOf(releaseDate));
    }

    /**
     * Returns the header for a CSV file representing movies.
     */
    @Override
    public String[] getHeader() {
        return new String[]{"id", "title", "pg", "genre", "releaseDate"};
    }

    /**
     * Creates a Movie object from a CSV line.
     */
    public static Movie fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        return new Movie(Integer.parseInt(parts[0]), parts[1], Boolean.parseBoolean(parts[2]), parts[3], LocalDate.parse(parts[4]));
    }
}
