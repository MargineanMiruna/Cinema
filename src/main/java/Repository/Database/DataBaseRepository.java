package Repository.Database;

import Model.HasId;
import Repository.IRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataBaseRepository<T extends HasId> implements IRepository<T>, AutoCloseable {
    protected Connection connection;

    public DataBaseRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

}
