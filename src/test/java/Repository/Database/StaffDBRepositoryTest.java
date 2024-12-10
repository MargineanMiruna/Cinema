package Repository.Database;

import Model.Staff;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class StaffDBRepositoryTest {
    Connection connection;
    final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/test/java/Files/cinemaDB.db";
    StaffDBRepository staffRepo;
    Staff staff1;
    Staff staff2;

    @BeforeEach
    void setUp() {
        try {
            connection = DriverManager.getConnection(DB_URL, "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        staffRepo = new StaffDBRepository(connection);
        staff1 = new Staff(1, "John", "Doe", "john.doe@gmail.com");
        staff2 = new Staff(2, "Jane", "Doe", "jane.doe@gmail.com");
    }

    @AfterEach
    void tearDown() throws SQLException {
        String createSQL = "DELETE FROM Staff;";
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
        assertEquals(1, staffRepo.generateNewId());
        staffRepo.add(staff1);
        assertEquals(2, staffRepo.generateNewId());
    }

    @Test
    void testAdd() {
        staffRepo.add(staff1);
        staffRepo.add(staff2);
        assertEquals(staffRepo.getAll().size(), 2);
        assertEquals(staff1.getId(), staffRepo.read(1).getId());
        assertEquals(staff1.getFirstName(), staffRepo.read(1).getFirstName());
        assertEquals(staff1.getLastName(), staffRepo.read(1).getLastName());
        assertEquals(staff1.getEmail(), staffRepo.read(1).getEmail());
        assertEquals(staff2.getId(), staffRepo.read(2).getId());
        assertEquals(staff2.getFirstName(), staffRepo.read(2).getFirstName());
        assertEquals(staff2.getLastName(), staffRepo.read(2).getLastName());
        assertEquals(staff2.getEmail(), staffRepo.read(2).getEmail());
    }

    @Test
    void testRead() {
        staffRepo.add(staff1);
        staffRepo.add(staff2);
        assertEquals(staff1.getId(), staffRepo.read(1).getId());
        assertEquals(staff1.getFirstName(), staffRepo.read(1).getFirstName());
        assertEquals(staff1.getLastName(), staffRepo.read(1).getLastName());
        assertEquals(staff1.getEmail(), staffRepo.read(1).getEmail());
        assertEquals(staff2.getId(), staffRepo.read(2).getId());
        assertEquals(staff2.getFirstName(), staffRepo.read(2).getFirstName());
        assertEquals(staff2.getLastName(), staffRepo.read(2).getLastName());
        assertEquals(staff2.getEmail(), staffRepo.read(2).getEmail());
    }

    @Test
    void testDelete() {
        staffRepo.add(staff1);
        staffRepo.add(staff2);
        staffRepo.delete(1);
        assertEquals(1, staffRepo.getAll().size());
        assertNotSame(staffRepo.read(1).getId(), staff1.getId());
        assertEquals(staffRepo.read(2).getId(), staff2.getId());
    }

    @Test
    void testUpdate() {
        staffRepo.add(staff1);
        staffRepo.add(staff2);
        Staff staff3 = new Staff(2, "John", "Smith", "john.smith@gmail.com");
        staffRepo.update(staff3);
        assertEquals(staff3.getFirstName(), staffRepo.read(2).getFirstName());
        assertEquals(staff3.getLastName(), staffRepo.read(2).getLastName());
        assertEquals(staff3.getEmail(), staffRepo.read(2).getEmail());
        assertNotSame(staff2.getFirstName(), staffRepo.read(2).getFirstName());
        assertNotSame(staff2.getLastName(), staffRepo.read(2).getLastName());
        assertNotSame(staff2.getEmail(), staffRepo.read(2).getEmail());
    }

    @Test
    void testGetAll() {
        staffRepo.add(staff1);
        staffRepo.add(staff2);
        assertEquals(2, staffRepo.getAll().size());
        assertEquals(staff1.getId(), staffRepo.getAll().get(1).getId());
        assertEquals(staff1.getFirstName(), staffRepo.getAll().get(1).getFirstName());
        assertEquals(staff1.getLastName(), staffRepo.getAll().get(1).getLastName());
        assertEquals(staff1.getEmail(), staffRepo.getAll().get(1).getEmail());
        assertEquals(staff2.getId(), staffRepo.getAll().get(2).getId());
        assertEquals(staff2.getFirstName(), staffRepo.getAll().get(2).getFirstName());
        assertEquals(staff2.getLastName(), staffRepo.getAll().get(2).getLastName());
        assertEquals(staff2.getEmail(), staffRepo.getAll().get(2).getEmail());
    }
}