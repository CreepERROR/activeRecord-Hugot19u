import activeRecord.DBConnection;
import activeRecord.Film;
import activeRecord.Personne;
import activeRecord.RealisateurAbsentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFilm {

    @BeforeEach
    public void demarrage() throws SQLException, RealisateurAbsentException {
        Personne.createTable();
        Personne p1 = new Personne("Smith", "John");
        Personne p2 = new Personne("Smith", "Carol");
        Personne p3 = new Personne("Cena", "John");
        Personne p4 = new Personne("God", "Billy");
        p1.save();
        p2.save();
        p3.save();
        p4.save();
        Film.createTable();
        Film f1 = new Film("Je code tard", p1);
        Film f2 = new Film("Mais en vrai", p2);
        Film f3 = new Film("j'aime bien", p3);
        Film f4 = new Film("du coup sa va", p4);
        f1.save();
        f2.save();
        f3.save();
        f4.save();
    }

    @AfterEach
    public void arreter() throws SQLException {
        Film.deleteTable();
        Personne.deleteTable();
    }
}