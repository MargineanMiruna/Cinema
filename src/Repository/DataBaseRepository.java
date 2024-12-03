package Repository;

import Model.HasId;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataBaseRepository<T extends HasId> implements IRepository<T>, AutoCloseable {
    protected  Connection connection;

    public DataBaseRepository(String dbUrl, String dbUser, String dbPassword) {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void close() throws Exception {
        connection.close();
    }

}
