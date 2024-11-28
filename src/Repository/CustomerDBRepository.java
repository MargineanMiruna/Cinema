package Repository;

import Model.Customer;

import java.sql.*;
import java.util.Map;

import org.sqlite.SQLiteDataSource;

public class CustomerDBRepository extends InMemoryRepository<Customer> {
    private Connection connection;

    public CustomerDBRepository() {

    }


}
