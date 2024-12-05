package Repository.Database;

import Model.Screen;
import Model.Showtime;
import Model.Seat;
import Model.SeatType;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A repository class for managing the relationship between showtimes and seats in the database.
 * Provides functionality to add, retrieve, and delete seat associations for a showtime.
 */
public class AvailableSeatsDBRepository {
    private Connection connection;

    /**
     * Constructor for the AvailableSeatsDBRepository.
     * Initializes the database connection and ensures the required table is created.
     *
     * @param connection the database connection to be used by the repository.
     */
    public AvailableSeatsDBRepository(Connection connection) {
        this.connection = connection;
        createTable();
    }

    /**
     * Creates the "AvailableSeats" table in the database if it does not already exist.
     * This table represents the many-to-many relationship between showtimes and seats.
     */
    private void createTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS AvailableSeats (showtimeId INT, seatId INT, PRIMARY KEY(showtimeId, seatId), FOREIGN KEY (showtimeId) REFERENCES Showtime(id),FOREIGN KEY (seatId) REFERENCES Seat(id));";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds an association between a showtime and a seat in the database.
     *
     * @param showtime the screen object to associate.
     * @param seat   the seat object to associate with the screen.
     */
    public void add(Showtime showtime, Seat seat) {
        String addSQL = "INSERT INTO AvailableSeats (showtimeId, seatId) VALUES (" + showtime.getId() + ", " + seat.getId() + ");";
        try {
            Statement addStatement = connection.createStatement();
            addStatement.executeUpdate(addSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes all seat associations for a specific showtime from the database.
     *
     * @param showtimeId the ID of the screen whose seat associations are to be removed.
     */
    public void removeAllSeatsFromShowtime(int showtimeId) {
        String deleteSQL = "DELETE FROM TABLE SeatLocation WHERE showtimeId = " + showtimeId + ";";
        try {
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeUpdate(deleteSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all seats associated with a specific showtime.
     *
     * @param showtimeId the ID of the screen whose associated seats are to be retrieved.
     * @return a list of Seat objects associated with the specified screen.
     */
    public List<Seat> getSeatsForShowtime(int showtimeId) {
        List<Seat> seats = new ArrayList<>();
        String readSQL = "SELECT ST.seatId, ST.seatNr, ST.seatType FROM Seat ST " +
                "JOIN AvailableSeats A ON A.seatId = ST.id" +
                " WHERE A.showtimeId = " + showtimeId+ ";";

        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            while (resultSet.next()) {
                Seat obj = new Seat(resultSet.getInt("id"), resultSet.getInt("seatNr"), SeatType.valueOf(resultSet.getString("seatType")));
                seats.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seats;
    }

}
