package activeRecord;

import java.sql.*;

public class Film {

    String titre;
    int id;
    int id_real;

    public Film(String t, Personne p) {
        this.titre = t;
        this.id = -1;
        this.id_real = p.getId();
    }

    private Film(String t, int id, int idreal) {
        this.titre = t;
        this.id = id;
        this.id_real = idreal;
    }

    static Film findById(int id) throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Film WHERE id=" + id + ";";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        rs.next();
        if (rs == null) {
            return null;
        } else {
            Film p = new Film(rs.getString("titre"), rs.getInt("id"), rs.getInt("id_real"));
            return (p);
        }
    }

    public Personne getRealisateur() throws SQLException {
        Personne p = Personne.findById(this.id_real);
        return p;
    }

    public static void createTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String createString = "CREATE TABLE Film ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "TITRE varchar(40) NOT NULL, " + "ID_REAL INTEGER NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String drop = "DROP TABLE Film";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }

    public void saveNew() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "INSERT INTO Film (titre, id_real) VALUES (?,?);";
        PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.executeUpdate();
        int id = prep.RETURN_GENERATED_KEYS;
        this.id = id;
    }

    public void update() throws SQLException, RealisateurAbsentException {
        if(this.id_real==-1){
            throw new RealisateurAbsentException("Realisateur incorrect");
        }
        Connection connect = DBConnection.getConnection();
        String SQLprep = "update Personne set titre=?, id_real=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.setInt(3, this.id);
        prep.execute();
    }

    public void delete() throws SQLException {
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement("DELETE FROM Film WHERE id=?");
        prep.setInt(1, this.id);
        prep.execute();
        this.id = -1;
    }

    public void save() throws SQLException, RealisateurAbsentException {
        if (this.id == -1) {
            this.saveNew();
        } else {
            this.update();
        }
    }

    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }

    public void setTitre(String t){
        this.titre=t;
    }
}
