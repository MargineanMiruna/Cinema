package Repository;

import Model.Ticket;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import org.sqlite.SQLiteDataSource;

/**
 * A repository class for managing Ticket data in the database.
 * Extends the generic DataBaseRepository for Ticket entities.
 */
public class TicketDBRepository extends DataBaseRepository<Ticket> {
    private Connection connection;

    /**
     * Default constructor for TicketDBRepository.
     * Calls the superclass constructor and ensures the necessary table for storing Ticket data is created.
     */
    public TicketDBRepository() {
        super();
        createTable();
    }

    /**
     * Creates the "Ticket" table in the database if it does not already exist.
     *
     */
    private void createTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS Ticket (id INT, bookingId INT, screenId INT, seatNr INT, price FLOAT, PRIMARY KEY(id), FOREIGN KEY (bookingId) REFERENCES Booking(id), FOREIGN KEY (screenId) REFERENCES Screen(id), FOREIGN KEY (seatNr) REFERENCES Seat(seatNr));";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a new unique ID for a ticket.
     * The ID is one greater than the last entry's ID, or 1 if the repository is empty.
     *
     * @return a unique integer ID for a new object
     */
    @Override
    public int generateNewId() {
        String lastEntrySQL = "SELECT id FROM Ticket ORDER BY id DESC LIMIT 1;";
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
     * Adds ticket to database
     * @param obj the object to be added to the database
     */
    @Override
    public void add(Ticket obj) {
        String addSQL = "INSERT INTO TABLE Ticket (id, bookingId, screenId, seatNr, price) VALUES (" + obj.getId() + ", " + obj.getBookingId() + ", " + obj.getScreenId() + ", " + obj.getSeatNr() + ", " + obj.getPrice() + ");";
        try {
            Statement addStatement = connection.createStatement();
            addStatement.executeQuery(addSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads or retrieves a ticket from the database by its ID.
     *
     * @param id the unique identifier of the object to retrieve
     * @return the object associated with the specified ID, or throws exception if no such object exists
     */
    @Override
    public Ticket read(int id) {
        String readSQL = "SELECT * FROM Ticket WHERE id = " + id + ";";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            Ticket obj = new Ticket(resultSet.getInt("id"), resultSet.getInt("bookingId"), resultSet.getInt("screenId"), resultSet.getInt("seatNr"), resultSet.getFloat("price"));
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a ticket from the repository by its ID.
     *
     * @param id the unique identifier of the object to be deleted
     */
    @Override
    public void delete(int id) {
        String deleteSQL = "DELETE FROM TABLE Ticket WHERE id = " + id + ";";
        try {
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeQuery(deleteSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a ticket in the database with a new object using the specified ID.
     *
     * @param obj the new object with which to update the existing object
     */
    @Override
    public void update(Ticket obj) {
        String updateSQL = "UPDATE TABLE Ticket SET bookingId = " + obj.getBookingId() + ", screenId = " + obj.getScreenId() + ", seatNr = " + obj.getSeatNr() + ", price = " + obj.getPrice() + " WHERE id = " + obj.getId() + " ;";
        try {
            Statement updateStatement = connection.createStatement();
            updateStatement.executeQuery(updateSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all objects in the Ticket table as a map.
     *
     * @return a map containing all objects in the Ticket table,
     * with their IDs as keys and the objects as values
     */
    @Override
    public Map<Integer, Ticket> getAll() {
        Map<Integer, Ticket> objects = new HashMap<>();

        String readSQL = "SELECT * FROM Ticket";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            while (resultSet.next()) {
                Ticket obj = new Ticket(resultSet.getInt("id"), resultSet.getInt("bookingId"), resultSet.getInt("screenId"), resultSet.getInt("seatNr"), resultSet.getFloat("price"));
                objects.put(obj.getId(), obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }
}
