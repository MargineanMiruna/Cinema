package Repository.Database;

import Model.BasicMembership;
import Model.PremiumMembership;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PremiumMembershipDBRepositoryTest {
    Connection connection;
    final String DB_URL = "jdbc:sqlite:D:/Facultate/Semestrul3/MAP/Cinema-Management-Project/src/test/java/Files/cinemaDBTest.db";
    //final String DB_URL = "jdbc:sqlite:C:/Users/aleol/Facultate/Sem3/FP/Cinema-Management-Project/src/test/java/Files/cinemaDBTest.db";
    PremiumMembershipDBRepository premiumMembershipRepo;
    PremiumMembership premiumMembership1;
    PremiumMembership premiumMembership2;

    @BeforeEach
    void setUp() {
        try {
            connection = DriverManager.getConnection(DB_URL, "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        premiumMembershipRepo = new PremiumMembershipDBRepository(connection);
        premiumMembership1 = new PremiumMembership(1,1, LocalDate.now(), LocalDate.now().plusDays(30));
        premiumMembership2 = new PremiumMembership(2,2, LocalDate.now(), LocalDate.now().plusDays(15));
    }

    @AfterEach
    void tearDown() throws SQLException {
        String createSQL = "DELETE FROM PremiumMembership;";
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
        assertEquals(1, premiumMembershipRepo.generateNewId());
        premiumMembershipRepo.add(premiumMembership1);
        assertEquals(2, premiumMembershipRepo.generateNewId());
    }

    @Test
    void testAdd() {
        premiumMembershipRepo.add(premiumMembership1);
        premiumMembershipRepo.add(premiumMembership2);
        assertEquals(2, premiumMembershipRepo.getAll().size());
        assertEquals(premiumMembership1.getId(), premiumMembershipRepo.read(1).getId());
        assertEquals(premiumMembership1.getCustomerId(), premiumMembershipRepo.read(1).getCustomerId());
        assertEquals(premiumMembership1.getStartDate(), premiumMembershipRepo.read(1).getStartDate());
        assertEquals(premiumMembership1.getEndDate(), premiumMembershipRepo.read(1).getEndDate());
        assertEquals(premiumMembership2.getId(), premiumMembershipRepo.read(2).getId());
        assertEquals(premiumMembership2.getCustomerId(), premiumMembershipRepo.read(2).getCustomerId());
        assertEquals(premiumMembership2.getStartDate(), premiumMembershipRepo.read(2).getStartDate());
        assertEquals(premiumMembership2.getEndDate(), premiumMembershipRepo.read(2).getEndDate());
    }

    @Test
    void testRead() {
        premiumMembershipRepo.add(premiumMembership1);
        premiumMembershipRepo.add(premiumMembership2);
        assertEquals(premiumMembership1.getId(), premiumMembershipRepo.read(1).getId());
        assertEquals(premiumMembership1.getCustomerId(), premiumMembershipRepo.read(1).getCustomerId());
        assertEquals(premiumMembership1.getStartDate(), premiumMembershipRepo.read(1).getStartDate());
        assertEquals(premiumMembership1.getEndDate(), premiumMembershipRepo.read(1).getEndDate());
        assertEquals(premiumMembership2.getId(), premiumMembershipRepo.read(2).getId());
        assertEquals(premiumMembership2.getCustomerId(), premiumMembershipRepo.read(2).getCustomerId());
        assertEquals(premiumMembership2.getStartDate(), premiumMembershipRepo.read(2).getStartDate());
        assertEquals(premiumMembership2.getEndDate(), premiumMembershipRepo.read(2).getEndDate());
    }

    @Test
    void testDelete() {
        premiumMembershipRepo.add(premiumMembership1);
        premiumMembershipRepo.add(premiumMembership2);
        premiumMembershipRepo.delete(1);
        assertEquals(1, premiumMembershipRepo.getAll().size());
        assertEquals(premiumMembership2.getId(), premiumMembershipRepo.read(2).getId());
    }

    @Test
    void testUpdate() {
        premiumMembershipRepo.add(premiumMembership1);
        premiumMembershipRepo.add(premiumMembership2);
        PremiumMembership premiumMembership3 = new PremiumMembership(2,3, LocalDate.now(), LocalDate.now().plusDays(60));
        premiumMembershipRepo.update(premiumMembership3);
        assertEquals(premiumMembership3.getCustomerId(), premiumMembershipRepo.read(2).getCustomerId());
        assertEquals(premiumMembership3.getStartDate(), premiumMembershipRepo.read(2).getStartDate());
        assertEquals(premiumMembership3.getEndDate(), premiumMembershipRepo.read(2).getEndDate());
        assertNotSame(premiumMembership2.getCustomerId(), premiumMembershipRepo.read(2).getCustomerId());
        assertEquals(premiumMembership2.getStartDate(), premiumMembershipRepo.read(2).getStartDate());
        assertNotSame(premiumMembership2.getEndDate(), premiumMembershipRepo.read(2).getEndDate());
    }

    @Test
    void testGetAll() {
        premiumMembershipRepo.add(premiumMembership1);
        premiumMembershipRepo.add(premiumMembership2);
        assertEquals(2, premiumMembershipRepo.getAll().size());
        assertEquals(premiumMembership1.getId(), premiumMembershipRepo.getAll().get(1).getId());
        assertEquals(premiumMembership1.getCustomerId(), premiumMembershipRepo.getAll().get(1).getCustomerId());
        assertEquals(premiumMembership1.getStartDate(), premiumMembershipRepo.getAll().get(1).getStartDate());
        assertEquals(premiumMembership1.getEndDate(), premiumMembershipRepo.getAll().get(1).getEndDate());
        assertEquals(premiumMembership2.getId(), premiumMembershipRepo.getAll().get(2).getId());
        assertEquals(premiumMembership2.getCustomerId(), premiumMembershipRepo.getAll().get(2).getCustomerId());
        assertEquals(premiumMembership2.getStartDate(), premiumMembershipRepo.getAll().get(2).getStartDate());
        assertEquals(premiumMembership2.getEndDate(), premiumMembershipRepo.getAll().get(2).getEndDate());

    }
}