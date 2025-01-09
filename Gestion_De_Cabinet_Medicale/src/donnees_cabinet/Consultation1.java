package donnees_cabinet;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import Information_Patient.Consultation;
import java.util.Date;
public class Consultation1 {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db"; // Update with your actual DB path

    // Method to add a consultation to the database
    public static void AjoutConsultation(int ssn, int numPatient, Date date, String type, String observation) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false); // Ensure auto-commit is off

            String query = "INSERT INTO Consultation (ssn, numPatient, date, type, observation) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, ssn);
                stmt.setInt(2, numPatient);
                SimpleDateFormat formatter = new SimpleDateFormat();
                String jour = formatter.format(date);
                stmt.setString(3, jour);
                stmt.setString(4, type);
                stmt.setString(5, observation);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Consultation enregistrée avec succès!");
                } else {
                    System.err.println("Aucune ligne affectée par l'insertion.");
                }

                conn.commit(); // Commit the transaction
                
            } catch (Exception e) {
                conn.rollback(); // Rollback in case of error
                System.err.println("Erreur lors de l'ajout de la consultation : " + e.getMessage());
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
}

    public static void SupprimerConsultation(int ssn) {
        try {
            String url = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);

            // Corrected SQL query
            PreparedStatement s = conn.prepareStatement("DELETE FROM Consultation WHERE ssn = ?;");
            s.setInt(1, ssn);

            s.executeUpdate();
            System.out.println("Suppression consultation réussi!");
            conn.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @SuppressWarnings("finally")
	public static LinkedList<Consultation> RechercherConsultation(int ssn) {
        LinkedList<Consultation> fl = new LinkedList<>(); // Initialized the LinkedList
        try {
            String url = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);

            // Corrected SQL query
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Consultation WHERE ssn = ?;");
            s.setInt(1, ssn);

            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                // Ensure the columns match the table schema
                Consultation f = new Consultation(
                    rs.getInt("ssn"),
                    rs.getInt("numPatient"),
                    rs.getDate("date"),
                    rs.getString("type"),
                    rs.getString("observation")
                );
                fl.add(f);
            }
            conn.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            return fl;
        }
    }
}
