package Cabinet;

import javax.swing.*;

import Information_Patient.FichePatient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;

public class SupprimerFichePatient extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JPanel panel1, panel2, panel3;
    JLabel SSN, id;
    JTextField SSNtxt, Idtxt;
    JTextArea info;
    JButton recherche, supprimer;

    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";  // Database URL

    public SupprimerFichePatient() {
        this.setTitle("Supprimer Fiche Patient");
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel1 = new JPanel(new GridLayout(3, 1));
        panel2 = new JPanel(new GridLayout(1, 3));
        panel3 = new JPanel(new GridLayout(1, 3));

        SSN = new JLabel("SSN: ");
        SSNtxt = new JTextField();
        recherche = new JButton("Rechercher");
        info = new JTextArea("ssn    prenom    nom    address    postal_code    ville    email    phone    job    sexe    grp_sanguin    date_naissance    doctor_email\n");
        id = new JLabel("Identifiant de la fiche: ");
        Idtxt = new JTextField();
        supprimer = new JButton("Supprimer");

        panel1.add(panel2);
        panel1.add(info);
        info.setEnabled(false);
        panel1.add(panel3);
        panel2.add(SSN);
        panel2.add(SSNtxt);
        panel2.add(recherche);
        recherche.addActionListener(this);
        panel3.add(id);
        panel3.add(Idtxt);
        Idtxt.setEnabled(false);
        panel3.add(supprimer);
        supprimer.setEnabled(false);
        supprimer.addActionListener(this);

        this.add(panel1);
    }



    // Method to search for a patient by SSN
    private LinkedList<FichePatient> RechercherFichePatient(int ssn) {
        LinkedList<FichePatient> patientList = new LinkedList<>();
        String query = "SELECT * FROM FichePatient WHERE ssn = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ssn);  // Set the SSN parameter in the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int patientSsn = rs.getInt("ssn");
                String prenom = rs.getString("prenom");
                String nom = rs.getString("nom");
                String address = rs.getString("address");
                String postalCode = rs.getString("postal_code");
                String ville = rs.getString("ville");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String job = rs.getString("job");
                String sexe = rs.getString("sexe");
                String grpSanguin = rs.getString("grp_sanguin");
                Date dateNaissance = rs.getDate("date_naissance");
                String doctorEmail = rs.getString("doctor_email");

                FichePatient patient = new FichePatient(patientSsn, prenom, nom, address, postalCode, ville, email, phone, job, sexe, grpSanguin, dateNaissance);
                patientList.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patientList;
    }

    // Method to delete the patient record by Id
    private void SupprimerFichePatientById(int ficheId) {
        String query = "DELETE FROM FichePatient WHERE ssn = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ficheId);  // Set the SSN (ID) to delete

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Fiche Patient deleted successfully!");
            } else {
                System.out.println("No Fiche Patient found with the provided SSN.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == recherche) {
            String ssnInput = SSNtxt.getText().trim();
            if (ssnInput.isEmpty() || !ssnInput.matches("\\d+")) { // Validate input
                JOptionPane.showMessageDialog(this, "Veuillez entrer un SSN valide (uniquement des chiffres).", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int ssn = Integer.parseInt(ssnInput);
            LinkedList<FichePatient> patients = RechercherFichePatient(ssn);
            if (patients.isEmpty()) return; // No need to proceed if no patients found

            info.setEnabled(true);
            Idtxt.setEnabled(true);
            supprimer.setEnabled(true);
            info.setText("ssn    prenom    nom    address    postal_code    ville    email    phone    job    sexe    grp_sanguin    date_naissance\n");

            for (FichePatient patient : patients) {
                info.append(String.format("%d    %s    %s    %s    %s    %s    %s    %s    %s    %s    %s    %s\n",
                    patient.getSSN(), patient.getPrenom(), patient.getNom(), patient.getAddress(), patient.getPost(),
                    patient.getVille(), patient.getEmail(), patient.getNum(), patient.getJob(),
                    patient.getSexe(), patient.getGrp_Sanguin(), patient.getDate_naissance()));
            }
        }

        if (e.getSource() == supprimer) {
            String ficheIdInput = Idtxt.getText().trim();
            if (ficheIdInput.isEmpty() || !ficheIdInput.matches("\\d+")) { // Validate input
                JOptionPane.showMessageDialog(this, "Veuillez entrer un identifiant valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int ficheId = Integer.parseInt(ficheIdInput);
            SupprimerFichePatientById(ficheId);
            JOptionPane.showMessageDialog(this, "Fiche Patient supprimée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
    }


    // Method to update the JTextArea with patient details
    private void extracted(FichePatient p) {
        info.append(p.getSSN() + "        " + p.getPrenom() + "    " + p.getNom() + "    " + p.getAddress() + "    " + p.getPost() + "    " + p.getVille() + "    " + p.getEmail() + "    " + p.getNum() + "    " + p.getJob() + "    " + p.getSexe() + "    " + p.getGrp_Sanguin() + "    " + p.getDate_naissance() + "    " + p.getEmail() + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupprimerFichePatient().setVisible(true));
    }
}
