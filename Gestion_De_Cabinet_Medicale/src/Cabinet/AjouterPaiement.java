package Cabinet;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.*;
import Information_Patient.Paiement;
import donnees_cabinet.Paiement1;
import java.text.ParseException;


@SuppressWarnings("serial")
public class AjouterPaiement extends JFrame implements ActionListener {

    JLabel ssnLabel, nomLabel, prenomLabel, montantLabel, dateLabel, typeLabel, faitLabel;
    JTextField ssnTextField, nomTextField, prenomTextField, montantTextField;
    JComboBox<String> date_jour, date_mois, date_ann, type, fait;
    JButton saveButton, cancelButton;

    public AjouterPaiement() {
        // Set up the JFrame
        this.setTitle("Ajouter Paiement");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize labels and fields
        ssnLabel = new JLabel("Numéro SSN");
        nomLabel = new JLabel("Nom");
        prenomLabel = new JLabel("Prénom");
        montantLabel = new JLabel("Montant");
        dateLabel = new JLabel("Date de Paiement");
        typeLabel = new JLabel("Type de Paiement");
        faitLabel = new JLabel("Statut du Paiement");

        ssnTextField = new JTextField();
        nomTextField = new JTextField();
        prenomTextField = new JTextField();
        montantTextField = new JTextField();

        date_jour = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            date_jour.addItem(String.format("%02d", i));
        }

        date_mois = new JComboBox<>(new String[]{
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        });

        date_ann = new JComboBox<>();
        int currentYear = java.time.Year.now().getValue();
        for (int i = currentYear; i >= 1900; i--) {
            date_ann.addItem(String.valueOf(i));
        }

        type = new JComboBox<>(new String[]{"Chéque", "Espèce", "Carte"});
        fait = new JComboBox<>(new String[]{"Effectué", "Non-effectué"});

        saveButton = new JButton("Enregistrer");
        cancelButton = new JButton("Annuler");

        // Set layout and add components
        this.setLayout(new GridLayout(12, 5, 12, 12));

        this.add(ssnLabel);
        this.add(ssnTextField);
        this.add(nomLabel);
        this.add(nomTextField);
        this.add(prenomLabel);
        this.add(prenomTextField);
        this.add(montantLabel);
        this.add(montantTextField);
        this.add(dateLabel);

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.add(date_jour);
        datePanel.add(date_mois);
        datePanel.add(date_ann);
        this.add(datePanel);

        this.add(typeLabel);
        this.add(type);
        this.add(faitLabel);
        this.add(fait);
        this.add(saveButton);
        this.add(cancelButton);

        // Action listeners for buttons
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // Set the frame visibility
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            try {
                // Validate required fields
                if (ssnTextField.getText().isEmpty() || nomTextField.getText().isEmpty() || prenomTextField.getText().isEmpty() || montantTextField.getText().isEmpty()) {
                    throw new IllegalArgumentException("Veuillez remplir les champs obligatoires (SSN, Nom, Prénom, Montant).");
                }

                // Get the payment information from the fields
                int ssn = Integer.parseInt(ssnTextField.getText());
                String nom = nomTextField.getText();
                String prenom = prenomTextField.getText();
                double montant = Double.parseDouble(montantTextField.getText());
                String typePaiement = (String) type.getSelectedItem();
                boolean paiementFait = fait.getSelectedItem().equals("Effectué");

                // Get the selected date
                String selectedDate = String.format("%s-%s-%s",
                        date_ann.getSelectedItem(),
                        date_mois.getSelectedItem(),
                        date_jour.getSelectedItem());
                Date datePaiement = convertStringToDate(selectedDate);

                // Call the method to add the payment to the database
                Paiement nouveauPaiement = new Paiement(ssn, nom, prenom, montant, datePaiement, typePaiement, paiementFait);
                Paiement1.AjoutPaiement(ssn, nom, prenom, montant, datePaiement, typePaiement, paiementFait);

                JOptionPane.showMessageDialog(this, "Paiement ajouté avec succès!");
                this.dispose(); // Close the window after saving
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "SSN et Montant doivent être des nombres valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du paiement.", "Erreur", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }

        if (e.getSource() == cancelButton) {
            this.dispose(); // Close the window when cancel is clicked
        }
    }

    public java.sql.Date convertStringToDate(String dateString) {
        try {
            // Define the format of the input string (e.g., "yyyy-MM-dd")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Parse the string into a java.util.Date object
            java.util.Date utilDate = sdf.parse(dateString);

            // Convert to java.sql.Date
            return new java.sql.Date(utilDate.getTime());

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez yyyy-MM-dd.");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AjouterPaiement());
    }
}
