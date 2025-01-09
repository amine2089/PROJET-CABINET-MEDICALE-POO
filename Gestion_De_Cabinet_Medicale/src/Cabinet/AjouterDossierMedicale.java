package Cabinet;

import javax.swing.*;
import donnees_cabinet.Dossier1;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AjouterDossierMedicale extends JFrame implements ActionListener {

    // Labels for the fields
    JLabel ssnLabel, nomLabel, prenomLabel, contactUrgenceLabel,
            antecedentsMedicauxLabel, antecedentsChirurgicauxLabel, maladiesChroniquesLabel,
            allergiesLabel, vaccinationsLabel, traitementsEnCoursLabel, historiqueConsultationLabel;

    // Text fields for input
    JTextField dateTextField1, notesTextField, ssnTextField, nomTextField, prenomTextField, contactUrgenceTextField;

    // TextArea for lists (medical history, allergies, treatments, etc.)
    JTextArea antecedentsMedicauxArea, antecedentsChirurgicauxArea, maladiesChroniquesArea,
            allergiesArea, vaccinationsArea, traitementsEnCoursArea, historiqueConsultationArea;
    JComboBox<String> date_naissance_jour, date_naissance_mois, date_naissance_ann;
    JLabel dateLabel, notesLabel;
    // Buttons for actions
    JButton saveButton, cancelButton;

    // Date of Creation Components
    JButton selectDateButton;
    JTextField dateTextField;

    public AjouterDossierMedicale() {
        // Set up the JFrame
        this.setTitle("Ajouter Dossier Médical");
        this.setSize(600, 800);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        ssnLabel = new JLabel("Numéro SSN");
        nomLabel = new JLabel("Nom");
        prenomLabel = new JLabel("Prénom");
        contactUrgenceLabel = new JLabel("Contact Urgence");
        dateLabel = new JLabel("Date de Création:");
        notesLabel = new JLabel("Notes Générales");
        antecedentsMedicauxLabel = new JLabel("Antécédents Médicaux");
        antecedentsChirurgicauxLabel = new JLabel("Antécédents Chirurgicaux");
        maladiesChroniquesLabel = new JLabel("Maladies Chroniques");
        allergiesLabel = new JLabel("Allergies");
        vaccinationsLabel = new JLabel("Vaccinations");
        traitementsEnCoursLabel = new JLabel("Traitements en Cours");
        historiqueConsultationLabel = new JLabel("Historique de Consultation");

        // Text fields for data input
        ssnTextField = new JTextField();
        nomTextField = new JTextField();
        prenomTextField = new JTextField();
        contactUrgenceTextField = new JTextField();

        // Date Components using JComboBox for day, month, and year
        date_naissance_jour = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            date_naissance_jour.addItem(String.format("%02d", i));
        }
        date_naissance_mois = new JComboBox<>(new String[]{
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        });
        date_naissance_ann = new JComboBox<>();
        int currentYear = java.time.Year.now().getValue();
        for (int i = currentYear; i >= 1900; i--) {
            date_naissance_ann.addItem(String.valueOf(i));
        }
        notesTextField = new JTextField();
        // Text areas for lists
        antecedentsMedicauxArea = new JTextArea(5, 30);
        antecedentsChirurgicauxArea = new JTextArea(5, 30);
        maladiesChroniquesArea = new JTextArea(5, 30);
        allergiesArea = new JTextArea(5, 30);
        vaccinationsArea = new JTextArea(5, 30);
        traitementsEnCoursArea = new JTextArea(5, 30);
        historiqueConsultationArea = new JTextArea(5, 30);

        // Buttons for saving or canceling
        saveButton = new JButton("Enregistrer");
        cancelButton = new JButton("Annuler");

        // Date selection components
        selectDateButton = new JButton("Sélectionner la Date");
        dateTextField1 = new JTextField("Date non sélectionnée", 15);
        dateTextField1.setEditable(false);

        // Set the layout and add components to the frame
        this.setLayout(new GridLayout(16, 2, 10, 10));

        // Add components to the frame
        this.add(ssnLabel);
        this.add(ssnTextField);
        this.add(nomLabel);
        this.add(nomTextField);
        this.add(prenomLabel);
        this.add(prenomTextField);
        this.add(contactUrgenceLabel);
        this.add(contactUrgenceTextField);
        this.add(dateLabel);
        JPanel datePanel = new JPanel(new FlowLayout());
        
        datePanel.add(date_naissance_jour);
        datePanel.add(date_naissance_mois);
        datePanel.add(date_naissance_ann);
        this.add(datePanel);
        this.add(selectDateButton);
        this.add(dateTextField1);
        this.add(notesLabel);
        this.add(notesTextField);
        this.add(antecedentsMedicauxLabel);
        this.add(new JScrollPane(antecedentsMedicauxArea));
        this.add(antecedentsChirurgicauxLabel);
        this.add(new JScrollPane(antecedentsChirurgicauxArea));
        this.add(maladiesChroniquesLabel);
        this.add(new JScrollPane(maladiesChroniquesArea));
        this.add(allergiesLabel);
        this.add(new JScrollPane(allergiesArea));
        this.add(vaccinationsLabel);
        this.add(new JScrollPane(vaccinationsArea));
        this.add(traitementsEnCoursLabel);
        this.add(new JScrollPane(traitementsEnCoursArea));
        this.add(historiqueConsultationLabel);
        this.add(new JScrollPane(historiqueConsultationArea));

        this.add(saveButton);
        this.add(cancelButton);

        // Action listeners for buttons
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);
        selectDateButton.addActionListener(this);

        // Set the frame visibility
        this.setVisible(true);
    }

    public java.sql.Date convertStringToDate(String dateString) {
        try {
            // Define the format of the input string (e.g., "yyyy-MM-dd")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Parse the string into a java.util.Date object
            java.util.Date utilDate = sdf.parse(dateString);

            // Convert to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            return sqlDate;
        } catch (Exception e) {
            // Handle any errors (e.g., invalid date format)
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.");
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            try {
                // Validate required fields
                if (ssnTextField.getText().isEmpty() || nomTextField.getText().isEmpty() || prenomTextField.getText().isEmpty()) {
                    throw new IllegalArgumentException("Veuillez remplir les champs obligatoires (SSN, Nom, Prénom).");
                }

                // Get the patient information from the fields
                int ssn = Integer.parseInt(ssnTextField.getText());
                String nom = nomTextField.getText();
                String prenom = prenomTextField.getText();
                String contactUrgence = contactUrgenceTextField.getText();
                String notes = notesTextField.getText();
                Date date = convertStringToDate(dateTextField1.getText());

                // Convert the text areas into ArrayLists
                ArrayList<String> antecedentsMedicaux = convertToArrayList(antecedentsMedicauxArea.getText());
                ArrayList<String> antecedentsChirurgicaux = convertToArrayList(antecedentsChirurgicauxArea.getText());
                ArrayList<String> maladiesChroniques = convertToArrayList(maladiesChroniquesArea.getText());
                ArrayList<String> allergies = convertToArrayList(allergiesArea.getText());
                ArrayList<String> vaccinations = convertToArrayList(vaccinationsArea.getText());
                ArrayList<String> traitementsEnCours = convertToArrayList(traitementsEnCoursArea.getText());
                ArrayList<String> historiqueConsultation = convertToArrayList(historiqueConsultationArea.getText());

                // Call the method to add the dossier to the database
                Dossier1.AjoutDossier(ssn, nom, prenom, notes, date, antecedentsMedicaux, antecedentsChirurgicaux, maladiesChroniques, allergies,
                        vaccinations, traitementsEnCours, historiqueConsultation, contactUrgence);

                JOptionPane.showMessageDialog(this, "Dossier médical ajouté avec succès!");
                this.dispose(); // Close the window after saving
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "SSN doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du dossier médical.", "Erreur", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }

        if (e.getSource() == cancelButton) {
            this.dispose(); // Close the window when cancel is clicked
        }

        if (e.getSource() == selectDateButton) {
            // Format the selected date as "YYYY-MM-DD"
            String selectedDate = String.format("%s-%02d-%02d",
                    date_naissance_ann.getSelectedItem(),
                    date_naissance_mois.getSelectedIndex() + 1,
                    Integer.parseInt((String) date_naissance_jour.getSelectedItem()));
            dateTextField1.setText(selectedDate); // Display the selected date in the text field
        }
    }

    // Convert a text area (separated by new lines) into an ArrayList<String>
    private ArrayList<String> convertToArrayList(String text) {
        ArrayList<String> list = new ArrayList<>();
        if (text != null && !text.isEmpty()) {
            String[] items = text.split("\n");
            for (String item : items) {
                list.add(item.trim());
            }
        }
        return list;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AjouterDossierMedicale::new);
    }
}

