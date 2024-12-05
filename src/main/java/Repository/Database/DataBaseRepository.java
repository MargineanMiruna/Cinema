package Repository.Database;

import Model.HasId;
import Repository.IRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataBaseRepository<T extends HasId> implements IRepository<T>, AutoCloseable {
    protected Connection connection;
    private String DB_URL = "jdbc:sqlite:D:/Facultate/Semestrul3/MAP/Cinema-Management-Project/cinemaDB.db";

    public DataBaseRepository(Connection connection) {
//        try {
//            connection = DriverManager.getConnection(DB_URL, "user", "password");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        this.connection = connection;
    }
    @Override
    public void close() throws Exception {
        connection.close();
    }

}
