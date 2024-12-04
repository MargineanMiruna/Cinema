package Repository.Database;

import Model.HasId;
import Repository.IRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataBaseRepository<T extends HasId> implements IRepository<T>, AutoCloseable {
    protected  Connection connection;
    private String DB_URL = "jdbc:sqlite:src/cinemaDB.db";

    public DataBaseRepository() {
        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void close() throws Exception {
        if (connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

}
