package Repository;

import Model.Customer;
import Model.Screen;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import Model.Staff;
import org.sqlite.SQLiteDataSource;

public class ScreenDBRepository extends DataBaseRepository<Screen> {
    public ScreenDBRepository() {
        super();
        createTable();

    }

    /**
     * Creates the "Screen" table in the database if it does not already exist.
     *
     */
    private void createTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS Screen (id INT, nrStandardSeats INT, nrVipSeats INT, nrPremiumSeats INT, PRIMARY KEY(id));";
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
        String lastEntrySQL = "SELECT id FROM Screen ORDER BY id DESC LIMIT 1;";
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
    public void add(Screen obj) {
        String addSQL = "INSERT INTO TABLE Screen(id,nrStandardSeats,nrVipSeats, nrPremiumSeats) VALUES (" + obj.getId() + ", " + obj.getNrStandardSeats() + ", " + obj.getNrVipSeats() + ", " + obj.getNrPremiumSeats()  + ");";
        try {
            Statement addStatement = connection.createStatement();
            addStatement.executeQuery(addSQL);
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
    public Screen read(int id) {
        String readSQL = "SELECT * FROM Screen WHERE id = " + id + ";";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            Screen obj = new Screen(resultSet.getInt("id"), resultSet.getInt("nrStandardSeats"), resultSet.getInt("nrVipSeats"), resultSet.getInt("nrPremiumSeats"));
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
        String deleteSQL = "DELETE FROM TABLE Screen WHERE id = " + id + ";";
        try {
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeQuery(deleteSQL);
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
    public void update(Screen obj) {
        String updateSQL = "UPDATE TABLE Screen SET nrStandardSeats = " + obj.getNrStandardSeats() + ", nrVipSeats = " + obj.getNrVipSeats()+ ", nrPremiumSeats = " + obj.getNrPremiumSeats() + " WHERE id = " + obj.getId() + " ;";
        try {
            Statement updateStatement = connection.createStatement();
            updateStatement.executeQuery(updateSQL);
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
    public Map<Integer, Screen> getAll() {
        Map<Integer, Screen> objects = new HashMap<>();

        String readSQL = "SELECT * FROM Screen";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            while (resultSet.next()) {
                Screen obj = new Screen(resultSet.getInt("id"), resultSet.getInt("nrStandardSeats"), resultSet.getInt("nrVipSeats"), resultSet.getInt("nrPremiumSeats") );
                objects.put(obj.getId(), obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }

}
