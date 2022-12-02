package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Personne {

    int id;
    String nom;
    String prenom;

    public Personne(String n, String p) {
        this.nom = n;
        this.prenom = p;
        this.id = -1;
    }

    static ArrayList<Personne> findAll() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        ArrayList<Personne> groupe = new ArrayList<>();
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            Personne perso = new Personne(nom, prenom);
            perso.id = rs.getInt("id");
            groupe.add(perso);
        }
        return groupe;
    }

    static Personne findById(int id) throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Personne WHERE id=" + id + ";";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        rs.next();
        if (rs == null) {
            return null;
        } else {
            Personne p = new Personne(rs.getString("prenom"), rs.getString("nom"));
            p.id = rs.getInt("id");
            return (p);
        }
    }

    static ArrayList<Personne> findByName(String nom) throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Personne WHERE nom=\"" + nom + "\";";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        rs.next();
        ArrayList<Personne> groupe = new ArrayList<>();
        while (rs.next()) {
            String prenom = rs.getString("prenom");
            Personne perso = new Personne(nom, prenom);
            perso.id = rs.getInt("id");
            groupe.add(perso);
        }
        return groupe;
    }

    public static void createTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String drop = "DROP TABLE Personne";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    public void saveNew() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
        PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.executeUpdate();
        int id = prep.RETURN_GENERATED_KEYS;
        this.id = id;
    }

    public void update() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.setInt(3, this.id);
        prep.execute();
    }

    public void delete() throws SQLException {
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement("DELETE FROM Personne WHERE id=?");
        prep.setInt(1, this.id);
        prep.execute();
        this.id = -1;
    }

    public void save() throws SQLException {
        if (this.id == -1) {
            this.saveNew();
        } else {
            this.update();
        }
    }
}
