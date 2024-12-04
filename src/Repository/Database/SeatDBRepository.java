package Repository.Database;

import Model.Seat;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import Model.SeatType;

/**
 * A repository class for managing Seat data in the database.
 * Extends the generic DataBaseRepository for Seat entities.
 */
public class SeatDBRepository extends DataBaseRepository<Seat> {
    /**
     * Default constructor for SeatDBRepository.
     * Calls the superclass constructor and ensures the necessary table for storing Seat data is created.
     */
    public SeatDBRepository() {
        super();
        createTable();
    }

    /**
     * Creates the "Seat" table in the database if it does not already exist.
     *
     */
    private void createTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS Seat (id INT, seatNr INT, seatType VARCHAR(10));";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a new unique ID for a seat.
     * The ID is one greater than the last entry's ID, or 1 if the repository is empty.
     *
     * @return a unique integer ID for a new object
     */
    @Override
    public int generateNewId() {
        String lastEntrySQL = "SELECT id FROM Seat ORDER BY id DESC LIMIT 1;";
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
     * Adds seat to database
     * @param obj the object to be added to the database
     */
    @Override
    public void add(Seat obj) {
        String addSQL = "INSERT INTO Seat (id, seatNr, seatType) VALUES (" + obj.getId() + ", " + obj.getSeatNr() + ", '" + obj.getType() + "');";
        try {
            Statement addStatement = connection.createStatement();
            addStatement.executeUpdate(addSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads or retrieves a seat from the database by its ID.
     *
     * @param id the unique identifier of the object to retrieve
     * @return the object associated with the specified ID, or throws exception if no such object exists
     */
    @Override
    public Seat read(int id) {
        String readSQL = "SELECT * FROM Seat WHERE id = " + id + ";";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            Seat obj = new Seat(resultSet.getInt("id"), resultSet.getInt("seatNr"), SeatType.valueOf(resultSet.getString("seatType")));
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a seat from the repository by its ID.
     *
     * @param id the unique identifier of the object to be deleted
     */
    @Override
    public void delete(int id) {
        String deleteSQL = "DELETE FROM TABLE Seat WHERE id = " + id + ";";
        try {
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeUpdate(deleteSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a seat in the database with a new object using the specified ID.
     *
     * @param obj the new object with which to update the existing object
     */
    @Override
    public void update(Seat obj) {
        String updateSQL = "UPDATE TABLE Seat SET seatNr = " + obj.getSeatNr() + ", seatType = " + obj.getType() + " WHERE id = " + obj.getId() + " ;";
        try {
            Statement updateStatement = connection.createStatement();
            updateStatement.executeUpdate(updateSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all objects in the seat table as a map.
     *
     * @return a map containing all objects in the cusromer table,
     * with their IDs as keys and the objects as values
     */
    @Override
    public Map<Integer, Seat> getAll() {
        Map<Integer, Seat> objects = new HashMap<>();

        String readSQL = "SELECT * FROM Seat";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            while (resultSet.next()) {
                Seat obj = new Seat(resultSet.getInt("id"), resultSet.getInt("seatNr"), SeatType.valueOf(resultSet.getString("seatType")));
                objects.put(obj.getId(), obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }
}
