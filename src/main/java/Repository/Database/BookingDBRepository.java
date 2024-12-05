package Repository.Database;

import Model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A repository class for managing Booking data in the database.
 * Extends the generic DataBaseRepository for Booking entities.
 */
public class BookingDBRepository extends DataBaseRepository<Booking> {
    /**
     * Default constructor for BookingDBRepository.
     * Calls the superclass constructor and ensures the necessary table for storing Booking data is created.
     */
    public BookingDBRepository(Connection connection) {
        super(connection);
        createTable();
    }

    /**
     * Creates the "Booking" table in the database if it does not already exist.
     *
     */
    private void createTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS Booking (id INT, customerId INT, showtimeId INT, bookingDate DATE, nrOfCustomers INT, PRIMARY KEY(id), FOREIGN KEY(customerId) REFERENCES Customer(id), FOREIGN KEY(showtimeId) REFERENCES Showtime(id));";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a new unique ID for a booking.
     * The ID is one greater than the last entry's ID, or 1 if the repository is empty.
     *
     * @return a unique integer ID for a new object
     */
    @Override
    public int generateNewId() {
        String lastEntrySQL = "SELECT id FROM Booking ORDER BY id DESC LIMIT 1;";
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
     * Adds booking to database
     * @param obj the object to be added to the database
     */
    @Override
    public void add(Booking obj) {
        String addSQL = "INSERT INTO Booking (id, customerId, showtimeId, bookingDate, nrOfCustomers) VALUES (" + obj.getId() + ", " + obj.getCustomerId() + ", " + obj.getShowtimeId() + ", " + obj.getDate() + ", " + obj.getNrOfCustomers() + ");";
        try {
            Statement addStatement = connection.createStatement();
            addStatement.executeUpdate(addSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads or retrieves a booking from the database by its ID.
     *
     * @param id the unique identifier of the object to retrieve
     * @return the object associated with the specified ID, or throws exception if no such object exists
     */
    @Override
    public Booking read(int id) {
        String readSQL = "SELECT * FROM Booking WHERE id = " + id + ";";
        String getTicketsSQL = "SELECT T.id FROM Tickets T " +
                "JOIN Booking B ON B.id = T.bookingId" +
                "WHERE B.id = " + id + ";";

        List<Integer> ticketIds = new ArrayList<>();
        try {
            Statement getTicketsStatement = connection.createStatement();
            ResultSet resultSet1 = getTicketsStatement.executeQuery(getTicketsSQL);
            while (resultSet1.next()) {
                ticketIds.add(resultSet1.getInt("id"));
            }

            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);

            Booking obj = new Booking(resultSet.getInt("id"), resultSet.getInt("customerId"), resultSet.getInt("showtimeId"), resultSet.getDate("bookingDate").toLocalDate(), resultSet.getInt("nrOfCustomers"), ticketIds);
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a booking from the repository by its ID.
     *
     * @param id the unique identifier of the object to be deleted
     */
    @Override
    public void delete(int id) {
        String deleteSQL = "DELETE FROM TABLE Booking WHERE id = " + id + ";";
        String deleteTicketsSQL = "DELETE FROM Tickets WHERE bookingId = " + id + ";";
        try {
            Statement deleteTicketsStatement = connection.createStatement();
            deleteTicketsStatement.executeUpdate(deleteTicketsSQL);
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeUpdate(deleteSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a booking in the database with a new object using the specified ID.
     *
     * @param obj the new object with which to update the existing object
     */
    @Override
    public void update(Booking obj) {
        String updateSQL = "UPDATE TABLE Booking SET customerId = " + obj.getCustomerId() + ", showtimeId = " + obj.getShowtimeId() + ", bookingDate = " + obj.getDate() + ", nrOfCustomers = " + obj.getNrOfCustomers() + " WHERE id = " + obj.getId() + " ;";
        try {
            Statement updateStatement = connection.createStatement();
            updateStatement.executeUpdate(updateSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all objects in the customer table as a map.
     *
     * @return a map containing all objects in the cusromer table,
     * with their IDs as keys and the objects as values
     */
    @Override
    public Map<Integer, Booking> getAll() {
        Map<Integer, Booking> objects = new HashMap<>();

        String readSQL = "SELECT * FROM Booking";

        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            while (resultSet.next()) {
                List<Integer> ticketIds = new ArrayList<>();
                String getTicketsSQL = "SELECT T.id FROM Tickets T " +
                        "JOIN Booking B ON B.id = T.bookingId" +
                        "WHERE B.id = " + resultSet.getInt("id") + ";";
                Statement getTicketsStatement = connection.createStatement();
                ResultSet resultSet1 = getTicketsStatement.executeQuery(getTicketsSQL);
                while (resultSet1.next()) {
                    ticketIds.add(resultSet1.getInt("id"));
                }

                Booking obj = new Booking(resultSet.getInt("id"), resultSet.getInt("customerId"), resultSet.getInt("showtimeId"), resultSet.getDate("bookingDate").toLocalDate(), resultSet.getInt("nrOfCustomers"), ticketIds);
                objects.put(obj.getId(), obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }
}
