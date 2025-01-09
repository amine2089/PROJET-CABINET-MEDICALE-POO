package Cabinet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;

import donnees_cabinet.Ordonnance1;
import Information_Patient.Ordonnance;

public class SupprimerOrdonnance extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JPanel panel1, panel2, panel3;
    JLabel SSN, id;
    JTextField SSNtxt;
    JTextArea info;
    JButton recherche, supprimer;

    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";  // Database URL

    public SupprimerOrdonnance() {
        this.setTitle("Supprimer Ordonnance");
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel1 = new JPanel(new GridLayout(3, 1));
        panel2 = new JPanel(new GridLayout(1, 3));
        panel3 = new JPanel(new GridLayout(1, 3));

        SSN = new JLabel("SSN: ");
        SSNtxt = new JTextField();
        recherche = new JButton("Rechercher");
        info = new JTextArea("id        patient_ssn    contenu    nom_medicament    nb_prise_jour    nb_boites    diagnostic\n");
        supprimer = new JButton("Supprimer");

        panel1.add(panel2);
        panel1.add(info);
        info.setEnabled(false);
        panel1.add(panel3);
        panel2.add(SSN);
        panel2.add(SSNtxt);
        panel2.add(recherche);
        recherche.addActionListener(this);
        panel3.add(supprimer);
        supprimer.setEnabled(false);
        supprimer.addActionListener(this);

        this.add(panel1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == recherche) {
            // Ensure the SSN is valid and not empty
            if (SSNtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un SSN.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int ssn = Integer.parseInt(SSNtxt.getText());

                // Get ordonnances for the SSN
                LinkedList<Ordonnance> lc = RechercherOrdonnances(ssn);

                info.setEnabled(true);
                supprimer.setEnabled(true);

                // Clear previous results in case of new search
                info.setText("id        patient_ssn    contenu    nom_medicament    nb_prise_jour    nb_boites    diagnostic\n");

                // Iterate over the ordonnances and display them
                while (!lc.isEmpty()) {
                    Ordonnance o = lc.poll();
                    extracted(o);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un SSN valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == supprimer) {
            // Ensure the SSN is valid before attempting to delete
            if (SSNtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un SSN.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int ssn = Integer.parseInt(SSNtxt.getText());

                // Call the method to delete ordonnances by SSN
                SupprimerOrdonnanceBySSN(ssn);

                JOptionPane.showMessageDialog(this, "Ordonnances supprimées avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); // Close the window after deleting
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un SSN valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to search for ordonnances by patient_ssn
    private LinkedList<Ordonnance> RechercherOrdonnances(int ssn) {
        LinkedList<Ordonnance> ordonnanceList = new LinkedList<>();
        String query = "SELECT * FROM Ordonnance WHERE patient_ssn = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ssn);  // Set the patient_ssn parameter in the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int ordonnanceId = rs.getInt("id");
                int patientSsn = rs.getInt("patient_ssn");
                String contenu = rs.getString("contenu");
                String nomMedicament = rs.getString("nom_medicament");
                int nbPriseJour = rs.getInt("nb_prise_jour");
                int nbBoites = rs.getInt("nb_boites");
                String diagnostic = rs.getString("diagnostic");

                Ordonnance ordonnance = new Ordonnance(ordonnanceId, contenu, patientSsn);
                ordonnanceList.add(ordonnance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ordonnanceList;
    }

    // Method to delete ordonnances records by patient_ssn
    private void SupprimerOrdonnanceBySSN(int ssn) {
        String query = "DELETE FROM Ordonnance WHERE patient_ssn = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ssn);  // Set the patient_ssn parameter to delete

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ordonnances deleted successfully!");
            } else {
                System.out.println("No ordonnances found for the provided SSN.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the JTextArea with ordonnance details
    private void extracted(Ordonnance o) {
        info.append(o.getNum_O() + "   "  + o.getContenu() +"   " + o.getSSN()+ "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupprimerOrdonnance().setVisible(true));
    }
}

