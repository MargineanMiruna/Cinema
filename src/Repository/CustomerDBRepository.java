package Repository;

import Model.Customer;

import java.sql.*;
import java.util.Map;

import org.sqlite.SQLiteDataSource;

public class CustomerDBRepository extends InMemoryRepository<Customer> {
    private Connection connection;
    private String DB_URL = "jdbc:sqlite:src/customer_db";
    public CustomerDBRepository() {
        openConnection();
        createTable();


    }

    private void createTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS players (" +
                "id int, firstName varchar(100), lastName varchar(100), email varchar(100), underage int," +
                "PRIMARY KEY(id));";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.execute(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void openConnection() {
        try {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl(DB_URL);
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public void closeConnection() {
        if (connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }


}
