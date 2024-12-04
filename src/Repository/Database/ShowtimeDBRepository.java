package Repository.Database;
import Model.Showtime;
import Model.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A repository class for managing Showtime data in the database.
 * Extends the generic DataBaseRepository for Showtime entities.
 */
public class ShowtimeDBRepository extends DataBaseRepository<Showtime>{
    AvailableSeatsDBRepository AvailableSeats;

    /**
     * Default constructor for ShowtimeDBRepository.
     * Calls the superclass constructor and ensures the necessary table for storing Showtime data is created.
     */
    public ShowtimeDBRepository() {
        super();
        createTable();
        AvailableSeats = new AvailableSeatsDBRepository(connection);
    }
    /**
     * Creates the "Showtime" table in the database if it does not already exist.
     * This table includes columns for id, screenId, movieId, date, startTime, duration, and seats.
     *
     * @throws RuntimeException if an {@link SQLException} occurs while executing the SQL statement.
     */
    private void createTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS Showtime (" +
                "id INT PRIMARY KEY, " +
                "screenId INT, " +
                "movieId INT, " +
                "date DATE, " +
                "startTime TIME, " +
                "duration INT ," +
                " FOREIGN KEY (screenId) REFERENCES Screen(id), " +
                "FOREIGN KEY (movieId) REFERENCES Movie(id))";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Generates a new unique ID for a Showtime.
     * The ID is one greater than the last entry's ID, or 1 if the repository is empty.
     *
     * @return a unique integer ID for a new Showtime
     */
    @Override
    public int generateNewId() {
        String lastEntrySQL = "SELECT id FROM Showtime ORDER BY id DESC LIMIT 1;";
        int lastId = 0;
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(lastEntrySQL);
            if (resultSet.next()) {
                lastId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lastId + 1;
    }

    /**
     * Adds an object to the repository.
     *
     * @param obj the object to be added to the repository
     */
    @Override
    public void add(Showtime obj) {
        String addSQL = "INSERT INTO Showtime (id, screenId, movieId, date, startTime, duration, seats) VALUES ("
                + obj.getId() + ", "
                + obj.getScreenId() + ", "
                + obj.getMovieId() + ", '"
                + obj.getDate() + "', '"
                + obj.getStartTime() + "', "
                + obj.getDuration() + ";";
        try {
            Statement addStatement = connection.createStatement();
            addStatement.executeUpdate(addSQL);
            for(Seat seat : obj.getSeats())
                AvailableSeats.add(obj, seat);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Reads or retrieves an object from the repository by its ID.
     *
     * @param id the unique identifier of the object to retrieve
     * @return the object associated with the specified ID, or {@code null} if no such object exists
     */
    @Override
    public Showtime read(int id) {
        String readSQL = "SELECT * FROM Showtime WHERE id = " + id + ";";
        List<Seat> seatList;
        try {
            Statement readStatement = connection.createStatement();
            seatList = AvailableSeats.getSeatsForShowtime(id);
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            Showtime obj = new Showtime(
                    resultSet.getInt("id"),
                    resultSet.getInt("screenId"),
                    resultSet.getInt("movieId"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("startTime").toLocalTime(),
                    resultSet.getInt("duration"),
                    seatList
            );
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes an object from the repository by its ID.
     *
     * @param id the unique identifier of the object to be deleted
     */
    @Override
    public void delete(int id) {
        String deleteSQL = "DELETE FROM Showtime WHERE id = " + id + ";";
        try {
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeUpdate(deleteSQL);
            AvailableSeats.removeAllSeatsFromShowtime(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Updates an object in the repository with a new object using the specified ID.
     *
     * @param obj the new object with which to update the existing object
     */
    @Override
    public void update(Showtime obj) {
        String updateSQL = "UPDATE Showtime SET screenId = " + obj.getScreenId() + ", " +
                "movieId = " + obj.getMovieId() + ", " +
                "date = '" + obj.getDate() + "', " +
                "startTime = '" + obj.getStartTime() + "', " +
                "duration = " + obj.getDuration() + ", " +
                "WHERE id = " + obj.getId() + ";";

        try {
            Statement updateStatement = connection.createStatement();
            updateStatement.executeUpdate(updateSQL);
            AvailableSeats.removeAllSeatsFromShowtime(obj.getId());
            for(Seat seat : obj.getSeats())
                AvailableSeats.add(obj, seat);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Retrieves all objects in the repository as a map.
     *
     * @return a map containing all objects in the repository,
     * with their IDs as keys and the objects as values
     */
    @Override
    public Map<Integer, Showtime> getAll() {
        Map<Integer, Showtime> objects = new HashMap<>();

        String readSQL = "SELECT * FROM Showtime";

        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            while (resultSet.next()) {
                List<Seat> seats = AvailableSeats.getSeatsForShowtime(resultSet.getInt("id"));
                Showtime obj = new Showtime(resultSet.getInt("id"),
                        resultSet.getInt("screenId"),
                        resultSet.getInt("movieId"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("startTime").toLocalTime(),
                        resultSet.getInt("duration"),
                        seats
                        );

                objects.put(obj.getId(), obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }


}
