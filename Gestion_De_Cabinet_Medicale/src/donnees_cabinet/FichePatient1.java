package donnees_cabinet;
import java.text.SimpleDateFormat;

import Information_Patient.FichePatient;
import javafx.collections.transformation.FilteredList;
import java.util.LinkedList;
import java.sql.*;
import javax.swing.JOptionPane;

public class FichePatient1 {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db"; // Database URL

    // Add a new FichePatient to the database
    public static void AjouterFichePatient(int ssn, String prenom, String nom, String address, String postalCode, 
                                            String ville, String email, String phone, String job, String sexe, 
                                            String grp_sanguin, Date date_naissance) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "INSERT INTO FichePatient (ssn, prenom, nom, address, postal_code, ville, email, phone, job, sexe, grp_sanguin, date_naissance) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, ssn);
            stmt.setString(2, prenom);
            stmt.setString(3, nom);
            stmt.setString(4, address);
            stmt.setString(5, postalCode);
            stmt.setString(6, ville);
            stmt.setString(7, email);
            stmt.setString(8, phone);
            stmt.setString(9, job);
            stmt.setString(10, sexe);
            stmt.setString(11, grp_sanguin);
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dob = formatter.format(date_naissance);
            stmt.setString(12, dob);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Fiche Patient ajoutée avec succès!");
            } else {
                System.out.println("Échec de l'ajout de la fiche patient.");
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'accès à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete a FichePatient entry by SSN
    public static void SupprimerFichePatient(int SSN) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "DELETE FROM FichePatient WHERE SSN = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, SSN);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient supprimé avec succès!");
            } else {
                System.out.println("Aucun patient trouvé avec le SSN donné.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du patient.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private LinkedList<FichePatient> RechercherFichePatient(int ssn) {
        LinkedList<FichePatient> patientList = new LinkedList<>(); // Correct initialization of LinkedList
        String query = "SELECT * FROM FichePatient WHERE ssn = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ssn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) { // Check if result set is empty
                    JOptionPane.showMessageDialog(this, "Aucun patient trouvé avec ce SSN.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    return patientList;
                }

                while (rs.next()) {
                    FichePatient patient = new FichePatient(
                        rs.getInt("ssn"),
                        rs.getString("prenom"),
                        rs.getString("nom"),
                        rs.getString("address"),
                        rs.getString("postal_code"),
                        rs.getString("ville"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("job"),
                        rs.getString("sexe"),
                        rs.getString("grp_sanguin"),
                        rs.getDate("date_naissance")
                    );
                    patientList.add(patient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la recherche dans la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return patientList;
    }
}
