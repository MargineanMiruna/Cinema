package Repository.Database;

import Model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDBRepositoryTest {
    Connection connection;
    final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/test/java/Files/cinemaDB.db";
    CustomerDBRepository customerRepo;
    Customer customer1;
    Customer customer2;

    @BeforeEach
    void setUp() {
        try {
            connection = DriverManager.getConnection(DB_URL, "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerRepo = new CustomerDBRepository(connection);
        customer1 = new Customer(1, "John", "Doe", "john.doe@gamil.com", false, 1);
        customer2 = new Customer(2, "Jane", "Doe", "jane.doe@gamil.com", true, 2);
    }

    @AfterEach
    void tearDown() throws SQLException {
        String createSQL = "DELETE FROM Customer;";
        try {
            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        connection.close();
    }

    @Test
    void testGenerateNewId() {
        assertEquals(1, customerRepo.generateNewId());
        customerRepo.add(customer1);
        assertEquals(2, customerRepo.generateNewId());
    }

    @Test
    void testAdd() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        assertEquals(customerRepo.getAll().size(), 2);
        assertEquals(customer1.getId(), customerRepo.read(1).getId());
        assertEquals(customer1.getFirstName(), customerRepo.read(1).getFirstName());
        assertEquals(customer1.getLastName(), customerRepo.read(1).getLastName());
        assertEquals(customer1.getEmail(), customerRepo.read(1).getEmail());
        assertEquals(customer1.getUnderaged(), customerRepo.read(1).getUnderaged());
        assertEquals(customer1.getMembershipId(), customerRepo.read(1).getMembershipId());
        assertEquals(customer2.getId(), customerRepo.read(2).getId());
        assertEquals(customer2.getFirstName(), customerRepo.read(2).getFirstName());
        assertEquals(customer2.getLastName(), customerRepo.read(2).getLastName());
        assertEquals(customer2.getEmail(), customerRepo.read(2).getEmail());
        assertEquals(customer2.getUnderaged(), customerRepo.read(2).getUnderaged());
    }

    @Test
    void testRead() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        assertEquals(customer1.getId(), customerRepo.read(1).getId());
        assertEquals(customer1.getFirstName(), customerRepo.read(1).getFirstName());
        assertEquals(customer1.getLastName(), customerRepo.read(1).getLastName());
        assertEquals(customer1.getEmail(), customerRepo.read(1).getEmail());
        assertEquals(customer1.getUnderaged(), customerRepo.read(1).getUnderaged());
        assertEquals(customer1.getMembershipId(), customerRepo.read(1).getMembershipId());
        assertEquals(customer2.getId(), customerRepo.read(2).getId());
        assertEquals(customer2.getFirstName(), customerRepo.read(2).getFirstName());
        assertEquals(customer2.getLastName(), customerRepo.read(2).getLastName());
        assertEquals(customer2.getEmail(), customerRepo.read(2).getEmail());
        assertEquals(customer2.getUnderaged(), customerRepo.read(2).getUnderaged());
        assertEquals(customer2.getMembershipId(), customerRepo.read(2).getMembershipId());
    }

    @Test
    void testDelete() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        customerRepo.delete(1);
        assertEquals(1, customerRepo.getAll().size());
        assertNotSame(customerRepo.read(1).getId(), customer1.getId());
        assertEquals(customerRepo.read(2).getId(), customer2.getId());
    }

    @Test
    void testUpdate() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        Customer customer3 = new Customer(2, "John", "Smith", "john.smith@gamil.com", false, 2);
        customerRepo.update(customer3);
        assertEquals(customer3.getFirstName(), customerRepo.read(2).getFirstName());
        assertEquals(customer3.getLastName(), customerRepo.read(2).getLastName());
        assertEquals(customer3.getEmail(), customerRepo.read(2).getEmail());
        assertEquals(customer3.getUnderaged(), customerRepo.read(2).getUnderaged());
        assertEquals(customer3.getMembershipId(), customerRepo.read(2).getMembershipId());
        assertNotSame(customer2.getFirstName(), customerRepo.read(2).getFirstName());
        assertNotSame(customer2.getLastName(), customerRepo.read(2).getLastName());
        assertNotSame(customer2.getEmail(), customerRepo.read(2).getEmail());
        assertNotSame(customer2.getUnderaged(), customerRepo.read(2).getUnderaged());
        assertEquals(customer2.getMembershipId(), customerRepo.read(2).getMembershipId());
    }

    @Test
    void testGetAll() {
        customerRepo.add(customer1);
        customerRepo.add(customer2);
        assertEquals(customerRepo.getAll().size(), 2);
        assertEquals(customer1.getId(), customerRepo.getAll().get(1).getId());
        assertEquals(customer1.getFirstName(), customerRepo.getAll().get(1).getFirstName());
        assertEquals(customer1.getLastName(), customerRepo.getAll().get(1).getLastName());
        assertEquals(customer1.getEmail(), customerRepo.getAll().get(1).getEmail());
        assertEquals(customer1.getUnderaged(), customerRepo.getAll().get(1).getUnderaged());
        assertEquals(customer1.getMembershipId(), customerRepo.getAll().get(1).getMembershipId());
        assertEquals(customer2.getId(), customerRepo.getAll().get(2).getId());
        assertEquals(customer2.getFirstName(), customerRepo.getAll().get(2).getFirstName());
        assertEquals(customer2.getLastName(), customerRepo.getAll().get(2).getLastName());
        assertEquals(customer2.getEmail(), customerRepo.getAll().get(2).getEmail());
        assertEquals(customer2.getUnderaged(), customerRepo.getAll().get(2).getUnderaged());
        assertEquals(customer2.getMembershipId(), customerRepo.getAll().get(2).getMembershipId());
    }
}