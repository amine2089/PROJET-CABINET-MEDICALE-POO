package Cabinet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import donnees_cabinet.Consultation1;
import Information_Patient.Consultation;
import java.sql.Date;

public class AjouterConsultation extends JFrame {

    // UI Components
    JLabel ssnLabel, numPatientLabel, dateLabel, typeLabel, observationLabel;
    JTextField ssnField, numPatientField;
    JComboBox<String> dayComboBox, monthComboBox, yearComboBox, typeComboBox;
    JTextArea observationArea;
    JButton saveButton, quitButton;
    JPanel panel, footer;
    JPanel datePanel;

    // Data
    String[] consultationTypes = {"Consultation", "Contrôle"};

    public AjouterConsultation() {
        // Frame Setup
        this.setTitle("Ajouter Consultation");
        this.setSize(500, 450);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Custom Font and Colors
        Font customFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color bgColor = new Color(255, 228, 240); // Light pink
        Color btnColor = new Color(240, 128, 128); // Salmon

        // Initialize Components
        ssnLabel = new JLabel("SSN:");
        numPatientLabel = new JLabel("Numéro Patient:");
        dateLabel = new JLabel("Date:");
        typeLabel = new JLabel("Type de Consultation:");
        observationLabel = new JLabel("Observation:");

        ssnField = new JTextField();
        numPatientField = new JTextField();

        // ComboBoxes for date selection
        dayComboBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(String.format("%02d", i));
        }

        monthComboBox = new JComboBox<>();
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        for (String month : months) {
            monthComboBox.addItem(month);
        }

        yearComboBox = new JComboBox<>();
        for (int i = 2056; i >= 1900; i--) {
            yearComboBox.addItem(String.valueOf(i));
        }

        typeComboBox = new JComboBox<>(consultationTypes);

        observationArea = new JTextArea();
        observationArea.setRows(4);
        observationArea.setLineWrap(true);
        observationArea.setWrapStyleWord(true);
        JScrollPane observationScroll = new JScrollPane(observationArea);

        saveButton = new JButton("Enregistrer");
        quitButton = new JButton("Annuler");

        // Apply Styles
        for (JComponent comp : new JComponent[]{ssnLabel, numPatientLabel, dateLabel, typeLabel, observationLabel}) {
            comp.setFont(customFont);
            comp.setForeground(Color.DARK_GRAY);
        }

        for (JTextField field : new JTextField[]{ssnField, numPatientField}) {
            field.setFont(customFont);
            field.setBackground(Color.WHITE);
            field.setForeground(Color.BLACK);
        }

        saveButton.setBackground(btnColor);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(customFont);

        quitButton.setBackground(btnColor);
        quitButton.setForeground(Color.WHITE);
        quitButton.setFont(customFont);

        // Date Panel
        datePanel = new JPanel();
        datePanel.setBackground(bgColor);
        datePanel.add(dayComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);

        // Main Panel Layout
        panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBackground(bgColor);

        panel.add(ssnLabel);
        panel.add(ssnField);
        panel.add(numPatientLabel);
        panel.add(numPatientField);
        panel.add(dateLabel);
        panel.add(datePanel); // Add the datePanel containing ComboBoxes
        panel.add(typeLabel);
        panel.add(typeComboBox);
        panel.add(observationLabel);
        panel.add(observationScroll);

        // Footer panel for buttons
        footer = new JPanel();
        footer.setBackground(bgColor);
        footer.add(saveButton);
        footer.add(quitButton);

        // Add ActionListeners
        saveButton.addActionListener(e -> {
            try {
                // Validate Inputs
                if (ssnField.getText().isEmpty() || numPatientField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Veuillez remplir tous les champs obligatoires.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Gather Data
                int ssn = Integer.parseInt(ssnField.getText());
                int numPatient = Integer.parseInt(numPatientField.getText());
                String day = (String) dayComboBox.getSelectedItem();
                String month = (String) monthComboBox.getSelectedItem();
                String year = (String) yearComboBox.getSelectedItem();
                String selectedDate = year + "-" + month + "-" + day; // Combine into YYYY-MM-DD format
                Date date = Date.valueOf(selectedDate); // Convert to java.sql.Date
                String type = (String) typeComboBox.getSelectedItem();
                String observation = observationArea.getText().trim();

                // Create Consultation Object
                Consultation consultation = new Consultation(ssn, numPatient, date, type, observation);

                // Save to Database
                Consultation1.AjoutConsultation(ssn, numPatient, date, type, observation);

                JOptionPane.showMessageDialog(this,
                        "Consultation enregistrée avec succès!",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez entrer des valeurs numériques valides pour SSN et Numéro Patient.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        "Date invalide. Veuillez vérifier la sélection des éléments de la date.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Une erreur est survenue lors de l'enregistrement.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        quitButton.addActionListener(e -> this.dispose());

        // Frame Layout
        this.add(panel, BorderLayout.CENTER);
        this.add(footer, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AjouterConsultation().setVisible(true));
    }
}
