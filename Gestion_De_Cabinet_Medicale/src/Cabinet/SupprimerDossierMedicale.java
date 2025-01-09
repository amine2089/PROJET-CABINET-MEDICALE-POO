package Cabinet;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import Information_Patient.DossierMedical;
import donnees_cabinet.Dossier1;

/**
 * SupprimerDossierMedicale
 * Allows user to search a dossier by SSN, display it, and delete it by ID.
 */
public class SupprimerDossierMedicale extends JFrame implements ActionListener {

    private JPanel panel1, panel2, panel3;
    private JLabel ssnLabel, idLabel;
    private JTextField ssnTextField, idTextField;
    private JTextArea infoArea;
    private JButton rechercherButton, supprimerButton;

    public SupprimerDossierMedicale() {
        this.setTitle("Supprimer Dossier Médical");
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel1 = new JPanel(new GridLayout(3, 1));
        panel2 = new JPanel(new GridLayout(1, 3));
        panel3 = new JPanel(new GridLayout(1, 3));

        // Labels
        ssnLabel = new JLabel("SSN: ");
        idLabel = new JLabel("ID: ");

        // Text fields
        ssnTextField = new JTextField();
        idTextField = new JTextField();
        idTextField.setEnabled(false); // user can’t edit until we find a dossier

        // Text area
        infoArea = new JTextArea(
              "id       ssn       nom       prenom      contact_urgence     \n"
            + "antecedents_medicaux      antecedents_chirurgicaux    maladies_chroniques\n"
            + "allergies    vaccinations    traitements_en_cours    historique_consultation\n"
            + "date_creation    notes_generales\n\n"
        );
        infoArea.setEditable(false); // Make it read-only

        // Buttons
        rechercherButton = new JButton("Rechercher");
        supprimerButton = new JButton("Supprimer");
        supprimerButton.setEnabled(false); // Only enable after we’ve found a dossier

        // Add listeners
        rechercherButton.addActionListener(this);
        supprimerButton.addActionListener(this);

        // Assemble panels
        // Top row: SSN label, SSN textfield, Rechercher button
        panel2.add(ssnLabel);
        panel2.add(ssnTextField);
        panel2.add(rechercherButton);

        // Middle: infoArea
        panel1.add(panel2);
        panel1.add(new JScrollPane(infoArea));

        // Bottom: ID label, ID textfield, Supprimer button
        panel3.add(idLabel);
        panel3.add(idTextField);
        panel3.add(supprimerButton);

        // Add them
        panel1.add(panel3);

        this.add(panel1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rechercherButton) {
            // Check SSN input
            if (ssnTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un SSN.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int ssn = Integer.parseInt(ssnTextField.getText());
                // Search the single DossierMedical by SSN
                DossierMedical dossier = Dossier1.RechercherDossier(ssn);

                // Clear area each time we search
                infoArea.setText(
                      "id       ssn       nom       prenom      contact_urgence     \n"
                    + "antecedents_medicaux      antecedents_chirurgicaux    maladies_chroniques\n"
                    + "allergies    vaccinations    traitements_en_cours    historique_consultation\n"
                    + "date_creation    notes_generales\n\n"
                );

                if (dossier == null) {
                    JOptionPane.showMessageDialog(this, "Aucun dossier trouvé pour ce SSN.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    idTextField.setEnabled(false);
                    supprimerButton.setEnabled(false);
                } else {
                    // Display in infoArea
                    displayDossier(dossier);
                    // Enable deleting
                    idTextField.setEnabled(true);
                    supprimerButton.setEnabled(true);
                    // Show the codeDossier in idTextField
                    idTextField.setText(String.valueOf(dossier.getCodeDossier()));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un SSN valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getSource() == supprimerButton) {
            // Deleting by ID
            if (idTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un ID de dossier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int dossierId = Integer.parseInt(idTextField.getText());
                // Delete by ID
                Dossier1.SupprimerDossier(dossierId);

                JOptionPane.showMessageDialog(this, "Dossier supprimé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); // Close the window
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Appends details of a single DossierMedical to the infoArea
     */
    private void displayDossier(DossierMedical c) {
        // Build a single-line or multi-line representation
        infoArea.append(
                c.getCodeDossier() + "     " + c.getSsn() + "     " + c.getNom() + "     "
                + c.getPrenom() + "     " + c.getContactUrgence() + "\n"
                + c.getAntecedentsMedicaux() + "  " + c.getAntecedentsChirurgicaux() + "  "
                + c.getMaladiesChroniques() + "  " + c.getAllergies() + "  "
                + c.getVaccinations() + "  " + c.getTraitementsEnCours() + "  "
                + c.getHistoriqueConsultation() + "  " + c.getDateCreation() + "  "
                + c.getNotesGenerales() + "\n\n"
        );
    }

    // Main to launch this frame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupprimerDossierMedicale().setVisible(true));
    }
}
