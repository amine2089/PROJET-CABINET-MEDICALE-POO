package donnees_cabinet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.LinkedList;

import Information_Patient.Paiement;

public class Paiement1 {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";

    public static void AjoutPaiement(
            int ssn,
            String nom,
            String prenom,
            double montant,
            Date datePaiement,
            String typePaiement,
            boolean paiementFait) throws SQLException {
        String sql = "INSERT INTO Paiement (ssn, nom, prenom, montant, datePaiement, typePaiement, paiementFait) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
        	     PreparedStatement stmt = conn.prepareStatement(sql)) {
        	    conn.setAutoCommit(false);
        	    stmt.setInt(1, ssn);
        	    stmt.setString(2, nom);
        	    stmt.setString(3, prenom);
        	    stmt.setDouble(4, montant);
        	    stmt.setDate(5, new java.sql.Date(datePaiement.getTime()));
        	    stmt.setString(6, typePaiement);
        	    stmt.setBoolean(7, paiementFait);
        	    stmt.executeUpdate();
        	    conn.commit();
        	    
        	} catch (SQLException e) {
        	    System.err.println("Erreur lors de l'ajout du paiement : " + e.getMessage());
        	} 

    }

    public static void SupprimerPaiement(int id) {
        String sql = "DELETE FROM Paiement WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Facture supprimée avec succès!");
            } else {
                System.out.println("Aucune facture trouvée pour l'ID : " + id);
            }
            conn.commit();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du paiement : " + e.getMessage());
        }
    }

    public static LinkedList<Paiement> RechercherPaiement(int ssn) {
        LinkedList<Paiement> fl = new LinkedList<>(); // Initialized the LinkedList
        String sql = "SELECT * FROM Paiement WHERE ssn = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement s = conn.prepareStatement(sql)) {

            conn.createStatement().execute("PRAGMA busy_timeout = 5000");
            s.setInt(1, ssn);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Paiement f = new Paiement(
                        rs.getInt("ssn"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDouble("montant"),
                        rs.getDate("datePaiement"),
                        rs.getString("typePaiement"),
                        rs.getBoolean("paiementFait")
                );
                fl.add(f);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du paiement : " + e.getMessage());
        }
        return fl;
    }
}
