package Repository.Database;

import Model.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * A repository class for managing Movie data in the database.
 * Extends the generic DataBaseRepository for Movie entities.
 */
public class MovieDBRepository extends DataBaseRepository<Movie> {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Default constructor for MovieDBRepository.
     * Calls the superclass constructor and ensures the necessary table for storing Movie data is created.
     */
    public MovieDBRepository(Connection connection) {
        super(connection);
        createTable();
    }

    /**
     * Creates the "Movie" table in the database if it does not already exist.
     *
     */
    private void createTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS Movie (id INT, title VARCHAR(100), pg INT, genre VARCHAR(100), releaseDate DATE, PRIMARY KEY(id));";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a new unique ID for a movie.
     * The ID is one greater than the last entry's ID, or 1 if the repository is empty.
     *
     * @return a unique integer ID for a new object
     */
    @Override
    public int generateNewId() {
        String lastEntrySQL = "SELECT id FROM Movie ORDER BY id DESC LIMIT 1;";
        int lastId = 0;
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(lastEntrySQL);
            lastId = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lastId + 1;
    }

    /**
     * Adds movie to database
     * @param obj the object to be added to the database
     */
    @Override
    public void add(Movie obj) {
        String addSQL = "INSERT INTO Movie (id, title, pg, genre, releaseDate) VALUES (" + obj.getId() + ", '" + obj.getTitle() + "', " + obj.getPg() + ", '" + obj.getGenre() + "', '" + obj.getReleaseDate() + "');";
        try {
            Statement addStatement = connection.createStatement();
            addStatement.executeUpdate(addSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads or retrieves a movie from the database by its ID.
     *
     * @param id the unique identifier of the object to retrieve
     * @return the object associated with the specified ID, or throws exception if no such object exists
     */
    @Override
    public Movie read(int id) {
        String readSQL = "SELECT * FROM Movie WHERE id = " + id + ";";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            Movie obj = new Movie(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getBoolean("pg"), resultSet.getString("genre"), LocalDate.parse(resultSet.getString("releaseDate"),dateFormatter));
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a movie from the repository by its ID.
     *
     * @param id the unique identifier of the object to be deleted
     */
    @Override
    public void delete(int id) {
        String deleteSQL = "DELETE FROM TABLE Movie WHERE id = " + id + ";";
        try {
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeUpdate(deleteSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a movie in the database with a new object using the specified ID.
     *
     * @param obj the new object with which to update the existing object
     */
    @Override
    public void update(Movie obj) {
        String updateSQL = "UPDATE TABLE Movie SET title = '" + obj.getTitle() + "', pg = " + obj.getPg() + ", genre = '" + obj.getGenre() + "', releaseDate = '" + obj.getReleaseDate() + "' WHERE id = " + obj.getId() + " ;";
        try {
            Statement updateStatement = connection.createStatement();
            updateStatement.executeUpdate(updateSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all objects in the movie table as a map.
     *
     * @return a map containing all objects in the cusromer table,
     * with their IDs as keys and the objects as values
     */
    @Override
    public Map<Integer, Movie> getAll() {
        Map<Integer, Movie> objects = new HashMap<>();

        String readSQL = "SELECT * FROM Movie";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            while (resultSet.next()) {
                Movie obj = new Movie(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getBoolean("pg"), resultSet.getString("genre"), LocalDate.parse(resultSet.getString("releaseDate"),dateFormatter));
                objects.put(obj.getId(), obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }
}
