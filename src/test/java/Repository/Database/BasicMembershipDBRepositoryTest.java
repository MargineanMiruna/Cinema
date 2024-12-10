package Repository.Database;

import Model.BasicMembership;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BasicMembershipDBRepositoryTest {
    Connection connection;
    final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/test/java/Files/cinemaDB.db";
    BasicMembershipDBRepository basicMembershipRepo;
    BasicMembership basicMembership1;
    BasicMembership basicMembership2;

    @BeforeEach
    void setUp() {
        try {
            connection = DriverManager.getConnection(DB_URL, "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        basicMembershipRepo = new BasicMembershipDBRepository(connection);
        basicMembership1 = new BasicMembership(1,1, LocalDate.now(), LocalDate.now().plusDays(30));
        basicMembership2 = new BasicMembership(2,2, LocalDate.now(), LocalDate.now().plusDays(15));
    }

    @AfterEach
    void tearDown() throws SQLException {
        String createSQL = "DELETE FROM BasicMembership;";
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
        assertEquals(1, basicMembershipRepo.generateNewId());
        basicMembershipRepo.add(basicMembership1);
        assertEquals(2, basicMembershipRepo.generateNewId());
    }

    @Test
    void testAdd() {
        basicMembershipRepo.add(basicMembership1);
        basicMembershipRepo.add(basicMembership2);
        assertEquals(2, basicMembershipRepo.getAll().size());
        assertEquals(basicMembership1.getId(), basicMembershipRepo.read(1).getId());
        assertEquals(basicMembership1.getCustomerId(), basicMembershipRepo.read(1).getCustomerId());
        assertEquals(basicMembership1.getStartDate(), basicMembershipRepo.read(1).getStartDate());
        assertEquals(basicMembership1.getEndDate(), basicMembershipRepo.read(1).getEndDate());
        assertEquals(basicMembership2.getId(), basicMembershipRepo.read(2).getId());
        assertEquals(basicMembership2.getCustomerId(), basicMembershipRepo.read(2).getCustomerId());
        assertEquals(basicMembership2.getStartDate(), basicMembershipRepo.read(2).getStartDate());
        assertEquals(basicMembership2.getEndDate(), basicMembershipRepo.read(2).getEndDate());
    }

    @Test
    void testRead() {
        basicMembershipRepo.add(basicMembership1);
        basicMembershipRepo.add(basicMembership2);
        assertEquals(basicMembership1.getId(), basicMembershipRepo.read(1).getId());
        assertEquals(basicMembership1.getCustomerId(), basicMembershipRepo.read(1).getCustomerId());
        assertEquals(basicMembership1.getStartDate(), basicMembershipRepo.read(1).getStartDate());
        assertEquals(basicMembership1.getEndDate(), basicMembershipRepo.read(1).getEndDate());
        assertEquals(basicMembership2.getId(), basicMembershipRepo.read(2).getId());
        assertEquals(basicMembership2.getCustomerId(), basicMembershipRepo.read(2).getCustomerId());
        assertEquals(basicMembership2.getStartDate(), basicMembershipRepo.read(2).getStartDate());
        assertEquals(basicMembership2.getEndDate(), basicMembershipRepo.read(2).getEndDate());
    }

    @Test
    void testDelete() {
        basicMembershipRepo.add(basicMembership1);
        basicMembershipRepo.add(basicMembership2);
        basicMembershipRepo.delete(1);
        assertEquals(1, basicMembershipRepo.getAll().size());
        assertEquals(basicMembership2.getId(), basicMembershipRepo.read(2).getId());
    }

    @Test
    void testUpdate() {
        basicMembershipRepo.add(basicMembership1);
        basicMembershipRepo.add(basicMembership2);
        BasicMembership basicMembership3 = new BasicMembership(2,3, LocalDate.now(), LocalDate.now().plusDays(60));
        basicMembershipRepo.update(basicMembership3);
        assertEquals(basicMembership3.getCustomerId(), basicMembershipRepo.read(2).getCustomerId());
        assertEquals(basicMembership3.getStartDate(), basicMembershipRepo.read(2).getStartDate());
        assertEquals(basicMembership3.getEndDate(), basicMembershipRepo.read(2).getEndDate());
        assertNotSame(basicMembership2.getCustomerId(), basicMembershipRepo.read(2).getCustomerId());
        assertEquals(basicMembership2.getStartDate(), basicMembershipRepo.read(2).getStartDate());
        assertNotSame(basicMembership2.getEndDate(), basicMembershipRepo.read(2).getEndDate());
    }

    @Test
    void testGetAll() {
        basicMembershipRepo.add(basicMembership1);
        basicMembershipRepo.add(basicMembership2);
        assertEquals(2, basicMembershipRepo.getAll().size());
        assertEquals(basicMembership1.getId(), basicMembershipRepo.getAll().get(1).getId());
        assertEquals(basicMembership1.getCustomerId(), basicMembershipRepo.getAll().get(1).getCustomerId());
        assertEquals(basicMembership1.getStartDate(), basicMembershipRepo.getAll().get(1).getStartDate());
        assertEquals(basicMembership1.getEndDate(), basicMembershipRepo.getAll().get(1).getEndDate());
        assertEquals(basicMembership2.getId(), basicMembershipRepo.getAll().get(2).getId());
        assertEquals(basicMembership2.getCustomerId(), basicMembershipRepo.getAll().get(2).getCustomerId());
        assertEquals(basicMembership2.getStartDate(), basicMembershipRepo.getAll().get(2).getStartDate());
        assertEquals(basicMembership2.getEndDate(), basicMembershipRepo.getAll().get(2).getEndDate());
    }
}