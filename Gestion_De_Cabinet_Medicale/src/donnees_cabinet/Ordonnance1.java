package donnees_cabinet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Information_Patient.Ordonnance;

public class Ordonnance1 {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";

    public static void AjoutOrdonnance(int ssn, String ordonnanceContent, String nomMedicament, int nbPriseJour, int nbBoites, String diagnostic) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);
            String sql = "INSERT INTO Ordonnance (patient_ssn, contenue, nom_medicament, nb_prise_jour, nb_boites, diagnostic) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ssn);
            stmt.setString(2, ordonnanceContent);
            stmt.setString(3, nomMedicament);
            stmt.setInt(4, nbPriseJour);
            stmt.setInt(5, nbBoites);
            stmt.setString(6, diagnostic);

            stmt.executeUpdate();
            System.out.println("Ordonnance ajoutée avec succès!");

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SupprimerOrdonnance(int ssn, String ordonnanceContent) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);

            String sql = "DELETE FROM Ordonnance WHERE patient_ssn = ? AND contenue = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ssn);
            stmt.setString(2, ordonnanceContent);

            stmt.executeUpdate();
            System.out.println("Ordonnance supprimée avec succès!");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Ordonnance RechercherOrdonnance(int ssn, String ordonnanceContent) {
        Ordonnance ordonnance = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);

            String sql = "SELECT * FROM Ordonnance WHERE patient_ssn = ? AND contenue = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ssn);
            stmt.setString(2, ordonnanceContent);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ordonnance = new Ordonnance(rs.getInt("id"),
                		rs.getString("contenue"), rs.getInt("patient_ssn")
                    
                );
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordonnance;
    }
}
