package donnees_cabinet;
import java.text.SimpleDateFormat;

import Information_Patient.FichePatient;
import Information_Patient.RendezVous;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RendezVous1 {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";  // Correct database path

    // Add a new RendezVous to the database
    public static void AjouterRendezVous(RendezVous rendezVous) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
           
            conn.setAutoCommit(false); 

            String sql = "INSERT INTO RendezVous (jour, patient_ssn, nom, prenom) VALUES (?, ?, ?, ?)";  // Insert all necessary fields
            PreparedStatement stmt = conn.prepareStatement(sql);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatter.format(rendezVous.getJour());
            stmt.setString(1, date);  // Set the appointment date
            stmt.setInt(2, rendezVous.getSSN());    // Set the patient's SSN
            stmt.setString(3, rendezVous.getNom()); // Set the patient's last name
            stmt.setString(4, rendezVous.getPrenom()); // Set the patient's first name

            // Execute the insert statement
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Rendez-vous ajouté avec succès!");
                conn.commit(); // Commit the transaction
            } else {
                System.out.println("Erreur lors de l'ajout du rendez-vous.");
                conn.rollback(); // Rollback if no rows were affected
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding Rendez-vous: " + e.getMessage());
        
    }
}

    // Delete a RendezVous by ID (optional feature)
    public static void SupprimerRendezVous(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "DELETE FROM RendezVous WHERE patient_ssn = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Rendez-vous supprimé avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all RendezVous for a specific patient
    public static List<RendezVous> RechercherRendezVousParPatient(FichePatient patient) {
        List<RendezVous> rendezVousList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM RendezVous WHERE patient_ssn = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patient.getSSN());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Date jour = rs.getDate("jour");
                rendezVousList.add(new RendezVous(jour, patient.getSSN(), patient.getPrenom(), patient.getNom()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rendezVousList;
    }

    // Retrieve all RendezVous (using a join with FichePatient)
    public static List<RendezVous> RechercherTousRendezVous() {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String sql = "SELECT R.jour, R.patient_ssn, F.nom, F.prenom " +
                     "FROM RendezVous R " +
                     "JOIN FichePatient F ON R.patient_ssn = F.ssn";  // Corrected SQL query
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Date jour = rs.getDate("jour");  // Extract the appointment date
                int patientSSN = rs.getInt("patient_ssn");  // Extract the patient's SSN
                String nom = rs.getString("nom");  // Extract the patient's last name
                String prenom = rs.getString("prenom");  // Extract the patient's first name
                
                // Create a new RendezVous object with the data from the result set
                RendezVous rendezVous = new RendezVous(jour, patientSSN, prenom, nom);
                
                // Add the newly created RendezVous object to the list
                rendezVousList.add(rendezVous);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rendezVousList;
    }

	public static LinkedList<RendezVous> RechercherRendezVous(int ssn) {
		 LinkedList<RendezVous> rendezVousList = new LinkedList<>();

	        String query = "SELECT * FROM RendezVous WHERE patient_ssn = ?"; // Query to get all RendezVous for a specific SSN

	        try (Connection conn = DriverManager.getConnection(DB_URL);
	             PreparedStatement stmt = conn.prepareStatement(query)) {

	            stmt.setInt(1, ssn); // Set the SSN parameter in the query

	            try (ResultSet rs = stmt.executeQuery()) {

	                // Iterate through the result set and add each RendezVous to the list
	                while (rs.next()) {
	                    int id = rs.getInt("id");
	                    Date jour = rs.getDate("jour");
	                    String nom = rs.getString("nom");
	                    String prenom = rs.getString("prenom");

	                    // Create a new RendezVous object and add it to the list
	                    RendezVous rendezVous = new RendezVous(jour, ssn, nom, prenom);
	                    rendezVousList.add(rendezVous);
	                }

	            } catch (SQLException e) {
	                System.out.println("Error processing result set: " + e.getMessage());
	            }

	        } catch (SQLException e) {
	            System.out.println("Error connecting to the database: " + e.getMessage());
	        }

	        return rendezVousList;
	}
}
