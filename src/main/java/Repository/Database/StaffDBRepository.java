package Repository.Database;

import Model.Staff;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A repository class for managing Staff data in the database.
 * Extends the generic DataBaseRepository for Staff entities.
 */
public class StaffDBRepository extends DataBaseRepository<Staff> {
    public StaffDBRepository(Connection connection) {
        super(connection);
        createTable();
    }

    /**
     * Creates the "Staff" table in the database if it does not already exist.
     *
     */
     private void createTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS Staff (id INT, firstName VARCHAR(100), lastName VARCHAR(100), email VARCHAR(100), PRIMARY KEY(id));";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a new unique ID for an object.
     * The ID is one greater than the last entry's ID, or 1 if the repository is empty.
     *
     * @return a unique integer ID for a new object
     */
    @Override
    public int generateNewId() {
        String lastEntrySQL = "SELECT id FROM Staff ORDER BY id DESC LIMIT 1;";
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
     * Adds an object to the repository.
     *
     * @param obj the object to be added to the repository
     */
    @Override
    public void add(Staff obj) {
        String addSQL = "INSERT INTO Staff (id, firstName, lastName, email) VALUES (" + obj.getId() + ", '" + obj.getFirstName() + "', '" + obj.getLastName() + "', '" + obj.getEmail() + "');";
        try {
            Statement addStatement = connection.createStatement();
            addStatement.executeUpdate(addSQL);
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
    public Staff read(int id) {
        String readSQL = "SELECT * FROM Staff WHERE id = " + id + ";";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            Staff obj = new Staff(resultSet.getInt("id"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"));
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
        String deleteSQL = "DELETE FROM Staff WHERE id = " + id + ";";
        try {
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeUpdate(deleteSQL);
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
    public void update(Staff obj) {
        String updateSQL = "UPDATE Staff SET firstName = '" + obj.getFirstName() + "', lastName = '" + obj.getLastName() + "', email = '" + obj.getEmail() + "' WHERE id = " + obj.getId() + ";";
        try {
            Statement updateStatement = connection.createStatement();
            updateStatement.executeUpdate(updateSQL);
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
    public Map<Integer, Staff> getAll() {
        Map<Integer, Staff> objects = new HashMap<>();

        String readSQL = "SELECT * FROM Staff";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            while (resultSet.next()) {
                Staff obj = new Staff(resultSet.getInt("id"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"));
                objects.put(obj.getId(), obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }
}
