package Repository.Database;

import Model.BasicMembership;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * A repository class for managing basic Membership data in the database.
 * Extends the generic DataBaseRepository for BasicMembership entities.
 */
public class BasicMembershipDBRepository extends DataBaseRepository<BasicMembership> {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Default constructor for BasicMembershipDBRepository.
     * Calls the superclass constructor and ensures the necessary table for storing BasicMembership data is created.
     */
    public BasicMembershipDBRepository(Connection connection) {
        super(connection);
        createTable();
    }

    /**
     * Creates the "BasicMembership" table in the database if it does not already exist.
     *
     */
    private void createTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS BasicMembership (id INT, customerId INT, startDate DATE, endDate DATE, PRIMARY KEY(id), FOREIGN KEY (customerId) REFERENCES Customer(id));";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a new unique ID for a basic membership.
     * The ID is one greater than the last entry's ID, or 1 if the repository is empty.
     *
     * @return a unique integer ID for a new object
     */
    @Override
    public int generateNewId() {
        String lastEntrySQL = "SELECT id FROM BasicMembership ORDER BY id DESC LIMIT 1;";
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
     * Adds basic membership to database
     * @param obj the object to be added to the database
     */
    @Override
    public void add(BasicMembership obj) {
        String addSQL = "INSERT INTO BasicMembership (id, customerId, startDate, endDate) VALUES (" + obj.getId() + ", " + obj.getCustomerId() + ", '" + obj.getStartDate() + "', '" + obj.getEndDate() + "');";
        try {
            Statement addStatement = connection.createStatement();
            addStatement.executeUpdate(addSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads or retrieves a basic membership from the database by its ID.
     *
     * @param id the unique identifier of the object to retrieve
     * @return the object associated with the specified ID, or throws exception if no such object exists
     */
    @Override
    public BasicMembership read(int id) {
        String readSQL = "SELECT * FROM BasicMembership WHERE id = " + id + ";";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            BasicMembership obj = new BasicMembership(resultSet.getInt("id"), resultSet.getInt("customerId"), LocalDate.parse(resultSet.getString("startDate"),dateFormatter), LocalDate.parse(resultSet.getString("endDate"),dateFormatter));
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a basic membership from the repository by its ID.
     *
     * @param id the unique identifier of the object to be deleted
     */
    @Override
    public void delete(int id) {
        String deleteSQL = "DELETE FROM BasicMembership WHERE id = " + id + ";";
        try {
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeUpdate(deleteSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a basic membership in the database with a new object using the specified ID.
     *
     * @param obj the new object with which to update the existing object
     */
    @Override
    public void update(BasicMembership obj) {
        String updateSQL = "UPDATE BasicMembership SET customerId = " + obj.getCustomerId() + ", startDate = '" + obj.getStartDate() + "', endDate = '" + obj.getEndDate() + "' WHERE id = " + obj.getId() + " ;";
        try {
            Statement updateStatement = connection.createStatement();
            updateStatement.executeUpdate(updateSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all objects in the BasicMembership table as a map.
     *
     * @return a map containing all objects in the BasicMembership table,
     * with their IDs as keys and the objects as values
     */
    @Override
    public Map<Integer, BasicMembership> getAll() {
        Map<Integer, BasicMembership> objects = new HashMap<>();

        String readSQL = "SELECT * FROM BasicMembership";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            while (resultSet.next()) {
                BasicMembership obj = new BasicMembership(resultSet.getInt("id"), resultSet.getInt("customerId"), LocalDate.parse(resultSet.getString("startDate"),dateFormatter), LocalDate.parse(resultSet.getString("endDate"),dateFormatter));
                objects.put(obj.getId(), obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }
}
