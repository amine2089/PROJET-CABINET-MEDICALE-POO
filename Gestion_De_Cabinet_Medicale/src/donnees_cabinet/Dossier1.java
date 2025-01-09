package donnees_cabinet;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Information_Patient.DossierMedical;

public class Dossier1 {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";

    public static void AjoutDossier(
            int ssn,
            String nom,
            String prenom,
            String notes,
            Date date,
            ArrayList<String> antecedentsMedicaux,
            ArrayList<String> antecedentsChirurgicaux,
            ArrayList<String> maladiesChroniques,
            ArrayList<String> allergies,
            ArrayList<String> vaccinations,
            ArrayList<String> traitementsEnCours,
            ArrayList<String> historiqueConsultation,
            String contactUrgence
    ) {
        String sql = "INSERT INTO Dossier (ssn, nom, prenom, contact_urgence, antecedents_medicaux, antecedents_chirurgicaux, maladies_chroniques, allergies, vaccinations, traitements_en_cours, historique_consultation, date_creation, notes_generales) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set values in the correct order:
            stmt.setInt(1, ssn);
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            stmt.setString(4, contactUrgence);
            stmt.setString(5, String.join(",", antecedentsMedicaux));
            stmt.setString(6, String.join(",", antecedentsChirurgicaux));
            stmt.setString(7, String.join(",", maladiesChroniques));
            stmt.setString(8, String.join(",", allergies));
            stmt.setString(9, String.join(",", vaccinations));
            stmt.setString(10, String.join(",", traitementsEnCours));
            stmt.setString(11, String.join(",", historiqueConsultation));

            stmt.setDate(12, date);
            stmt.setString(13, notes);

            stmt.executeUpdate();
            System.out.println("Dossier ajouté avec succès!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SupprimerDossier(int id) {
        String sql = "DELETE FROM Dossier WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Dossier supprimé avec succès!");
            } else {
                System.out.println("Aucun dossier trouvé pour l'ID : " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DossierMedical RechercherDossier(int ssn) {
        DossierMedical dossier = null;
        String sql = "SELECT * FROM Dossier WHERE ssn = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ssn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crée un nouvel objet DossierMedical à partir des infos de la base
                    dossier = new DossierMedical(
                        rs.getInt("ssn"),                                // ssn
                        rs.getString("prenom"),                          // prenom
                        rs.getString("nom"),                             // nom
                        rs.getInt("id"),                                 // codeDossier
                        rs.getDate("date_creation"),// dateCreation
                        rs.getString("notes_generales"),                 // notesGenerales
                        rs.getString("contact_urgence")                  // contactUrgence
                    );

                    // Initialise les listes à partir des colonnes CSV
                    dossier.setAntecedentsMedicaux(convertToArrayList(rs.getString("antecedents_medicaux")));
                    dossier.setAntecedentsChirurgicaux(convertToArrayList(rs.getString("antecedents_chirurgicaux")));
                    dossier.setMaladiesChroniques(convertToArrayList(rs.getString("maladies_chroniques")));
                    dossier.setAllergies(convertToArrayList(rs.getString("allergies")));
                    dossier.setVaccinations(convertToArrayList(rs.getString("vaccinations")));
                    dossier.setTraitementsEnCours(convertToArrayList(rs.getString("traitements_en_cours")));
                    dossier.setHistoriqueConsultation(convertToArrayList(rs.getString("historique_consultation")));
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dossier;
    }

    /**
     * Convertit une chaîne CSV en ArrayList<String>.
     */
    private static ArrayList<String> convertToArrayList(String csv) {
        ArrayList<String> list = new ArrayList<>();
        if (csv != null && !csv.isEmpty()) {
            String[] items = csv.split(",");
            for (String item : items) {
                list.add(item.trim());
            }
        }
        return list;
    }
}

