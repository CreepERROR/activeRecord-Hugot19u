import activeRecord.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDBConnection {

    @Test
    public void testConnectionIdentique() throws SQLException {
        Connection beta= DBConnection.getConnection();
        Connection alpha= DBConnection.getConnection();
        boolean checker=false;
        if(alpha==beta){
            checker=true;
        }
        assertEquals(true, checker,"Connection identique");
    }

    @Test
    public void testConnectionDifferent() throws SQLException {
        Connection beta= DBConnection.getConnection();
        DBConnection.setNomDB("test");
        Connection alpha= DBConnection.getConnection();
        boolean checker=false;
        if(alpha==beta){
            checker=true;
        }
        assertEquals(false, checker,"Connection non identique");
    }
}
