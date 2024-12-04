package Repository.Database;

import Model.Booking;
import Model.Screen;
import Model.Seat;
import Model.SeatType;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A repository class for managing the relationship between screens and seats in the database.
 * Provides functionality to add, retrieve, and delete seat associations for a screen.
 */
public class SeatLocationDBRepository {
    private Connection connection;

    /**
     * Constructor for the SeatLocationDBRepository.
     * Initializes the database connection and ensures the required table is created.
     *
     * @param connection the database connection to be used by the repository.
     */
    public SeatLocationDBRepository(Connection connection) {
        this.connection = connection;
        createTable();
    }

    /**
     * Creates the "SeatLocation" table in the database if it does not already exist.
     * This table represents the many-to-many relationship between screens and seats.
     */
    private void createTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS SeatLocation (screenId INT, seatId INT, PRIMARY KEY(screenId, seatId), FOREIGN KEY (screenId) REFERENCES Screen(id),FOREIGN KEY (seatId) REFERENCES Seat(id));";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds an association between a screen and a seat in the database.
     *
     * @param screen the screen object to associate.
     * @param seat   the seat object to associate with the screen.
     */
    public void add(Screen screen, Seat seat) {
        String addSQL = "INSERT INTO TABLE SeatLocation (screenId, seatId) VALUES (" + screen.getId() + ", " + seat.getId() + ");";
        try {
            Statement addStatement = connection.createStatement();
            addStatement.executeQuery(addSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes all seat associations for a specific screen from the database.
     *
     * @param screenId the ID of the screen whose seat associations are to be removed.
     */
    public void removeAllSeatsFromScreen(int screenId) {
        String deleteSQL = "DELETE FROM TABLE SeatLocation WHERE screenId = " + screenId + ";";
        try {
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeQuery(deleteSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all seats associated with a specific screen.
     *
     * @param screenId the ID of the screen whose associated seats are to be retrieved.
     * @return a list of Seat objects associated with the specified screen.
     */
    public List<Seat> getSeatsForScreen(int screenId) {
        List<Seat> seats = new ArrayList<>();
        String readSQL = "SELECT ST.seatId, ST.seatNr, ST.seatType FROM Seat ST " +
                "JOIN SeatLocation SL ON SL.seatId = ST.id" +
                " WHERE screenId = " + screenId + ";";

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
