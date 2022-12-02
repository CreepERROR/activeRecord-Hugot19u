import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPersonne {
    @BeforeEach
    public void demarrage() throws SQLException {
        Personne.createTable();
        Personne p1 = new Personne("Smith", "John");
        Personne p2 = new Personne("Smith", "Carol");
        Personne p3 = new Personne("Cena", "John");
        Personne p4 = new Personne("God", "Billy");
        p1.save();
        p2.save();
        p3.save();
        p4.save();
    }

    @AfterEach
    public void arreter() throws SQLException {
        Personne.createTable();
    }

    @Test
    public void testConnectionIdentique() throws SQLException {
        Connection beta = DBConnection.getConnection();
        Connection alpha = DBConnection.getConnection();
        boolean checker = false;
        if (alpha == beta) {
            checker = true;
        }
        assertEquals(true, checker, "Connection identique");
    }
}
