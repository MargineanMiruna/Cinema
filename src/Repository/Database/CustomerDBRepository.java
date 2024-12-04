package Repository.Database;

import Model.Customer;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A repository class for managing Customer data in the database.
 * Extends the generic DataBaseRepository for Customer entities.
 */
public class CustomerDBRepository extends DataBaseRepository<Customer> {
    private Connection connection;

    /**
     * Default constructor for CustomerDBRepository.
     * Calls the superclass constructor and ensures the necessary table for storing Customer data is created.
     */
    public CustomerDBRepository() {
        super();
        createTable();
    }

    /**
     * Creates the "Customer" table in the database if it does not already exist.
     *
     */
     private void createTable() {
         String createSQL = "CREATE TABLE IF NOT EXISTS Customer (id INT, firstName VARCHAR(100), lastName VARCHAR(100), email VARCHAR(100), underage INT, membershipId INT, PRIMARY KEY(id));";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a new unique ID for a customer.
     * The ID is one greater than the last entry's ID, or 1 if the repository is empty.
     *
     * @return a unique integer ID for a new object
     */
    @Override
    public int generateNewId() {
        String lastEntrySQL = "SELECT id FROM Customer ORDER BY id DESC LIMIT 1;";
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
     * Adds customer to database
     * @param obj the object to be added to the database
     */
    @Override
    public void add(Customer obj) {
        String addSQL = "INSERT INTO TABLE Customer (id, firstName, lastName, email, underage, membershipId) VALUES (" + obj.getId() + ", " + obj.getFirstName() + ", " + obj.getLastName() + ", " + obj.getEmail() + ", " + obj.getUnderaged() + ", " + obj.getMembershipId() + ");";
        try {
            Statement addStatement = connection.createStatement();
            addStatement.executeQuery(addSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads or retrieves a customer from the database by its ID.
     *
     * @param id the unique identifier of the object to retrieve
     * @return the object associated with the specified ID, or throws exception if no such object exists
     */
    @Override
    public Customer read(int id) {
        String readSQL = "SELECT * FROM Customer WHERE id = " + id + ";";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            Customer obj = new Customer(resultSet.getInt("id"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"), resultSet.getBoolean("underage"), resultSet.getInt("membershipId"));
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a customer from the repository by its ID.
     *
     * @param id the unique identifier of the object to be deleted
     */
    @Override
    public void delete(int id) {
        String deleteSQL = "DELETE FROM TABLE Customer WHERE id = " + id + ";";
        try {
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeQuery(deleteSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a customer in the database with a new object using the specified ID.
     *
     * @param obj the new object with which to update the existing object
     */
    @Override
    public void update(Customer obj) {
        String updateSQL = "UPDATE TABLE Customer SET firstName = " + obj.getFirstName() + ", lastName = " + obj.getLastName() + ", email = " + obj.getEmail() + ", underage = " + obj.getUnderaged() + ", membershipId = " + obj.getMembershipId() + " WHERE id = " + obj.getId() + " ;";
        try {
            Statement updateStatement = connection.createStatement();
            updateStatement.executeQuery(updateSQL);
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
    public Map<Integer, Customer> getAll() {
        Map<Integer, Customer> objects = new HashMap<>();

        String readSQL = "SELECT * FROM Customer";
        try {
            Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readSQL);
            while (resultSet.next()) {
                Customer obj = new Customer(resultSet.getInt("id"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"), resultSet.getBoolean("underage"), resultSet.getInt("membershipId"));
                objects.put(obj.getId(), obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }
}
