package Cabinet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AjouterOrdonnance extends JFrame implements ActionListener {

    JLabel ordonnance, nomMed, nbPrix, nbBoites;
    JTextField ordo, nbprixtf, nbBoitestf;
    JComboBox<String> NomMedCb;
    JPanel panel, fin;
    JButton save, quit;

    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";  // Database URL

    public AjouterOrdonnance() {
        // Frame Setup
        this.setTitle("Ajouter Ordonnance");
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Custom Font and Colors
        Font customFont = new Font("Arial", Font.PLAIN, 14);
        Color bgColor = new Color(255, 228, 240); // Light pink
        Color btnColor = new Color(240, 128, 128); // Salmon

        // Initialize Labels and Components
        ordonnance = new JLabel("Ordonnance");
        ordo = new JTextField(20);
        nomMed = new JLabel("Nom Médicament");
        NomMedCb = new JComboBox<>(new String[] {
            "Aspirin", "Ibuprofen", "Paracetamol", "Amoxicillin", "Metformin",
            "Lisinopril", "Simvastatin", "Omeprazole", "Metoprolol", "Hydrochlorothiazide"
        });
        nbPrix = new JLabel("Nombre de prise par jour");
        nbprixtf = new JTextField();
        nbBoites = new JLabel("Nombre de boites");
        nbBoitestf = new JTextField();

        save = new JButton("Enregistrer");
        quit = new JButton("Annuler");

        // Setting Fonts and Colors for Components
        for (JComponent comp : new JComponent[]{ordonnance, nomMed, nbPrix, nbBoites}) {
            comp.setFont(customFont);
            comp.setForeground(Color.DARK_GRAY);
        }
        for (JTextField field : new JTextField[]{ordo, nbprixtf, nbBoitestf}) {
            field.setFont(customFont);
            field.setBackground(Color.WHITE);
            field.setForeground(Color.BLACK);
        }

        save.setBackground(btnColor);
        save.setForeground(Color.WHITE);
        save.setFont(customFont);

        quit.setBackground(btnColor);
        quit.setForeground(Color.WHITE);
        quit.setFont(customFont);

        // Panels Setup
        panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBackground(bgColor);
        panel.add(ordonnance);
        panel.add(ordo);
        panel.add(nomMed);
        panel.add(NomMedCb);
        panel.add(nbPrix);
        panel.add(nbprixtf);
        panel.add(nbBoites);
        panel.add(nbBoitestf);

        fin = new JPanel();
        fin.setBackground(bgColor);
        fin.add(save);
        fin.add(quit);

        // Action Listeners
        save.addActionListener(this);
        quit.addActionListener(this);

        // Adding Panels to Frame
        this.add(panel, BorderLayout.CENTER);
        this.add(fin, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            // Validate inputs
            if (ordo.getText().isEmpty() || nbprixtf.getText().isEmpty() || nbBoitestf.getText().isEmpty() || NomMedCb.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs avant d'enregistrer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Retrieve inputs
                String ordonnanceContent = ordo.getText();
                String nomMedicament = (String) NomMedCb.getSelectedItem();
                int nbPriseJour = Integer.parseInt(nbprixtf.getText());
                int nbBoites = Integer.parseInt(nbBoitestf.getText());

                // Ask for the Patient SSN and Diagnostic
                int patientSsn = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Patient SSN:", "Patient SSN", JOptionPane.QUESTION_MESSAGE));
                String diagnostic = JOptionPane.showInputDialog(this, "Enter Diagnostic:", "Diagnostic", JOptionPane.QUESTION_MESSAGE);

                // Save to database
                saveOrdonnance(patientSsn, ordonnanceContent, nomMedicament, nbPriseJour, nbBoites, diagnostic);

                JOptionPane.showMessageDialog(this, "Ordonnance enregistrée avec succès!");
                this.dispose();  // Close the window after saving
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide pour le nombre de prises et de boîtes.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de l'enregistrement.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == quit) {
            this.dispose(); // Close the window when cancel is clicked
        }
    }

    // Method to save Ordonnance to the database
    private void saveOrdonnance(int patientSsn, String ordonnanceContent, String nomMedicament, int nbPriseJour, int nbBoites, String diagnostic) {
        String query = "INSERT INTO Ordonnance (patient_ssn, contenue, nom_medicament, nb_prise_jour, nb_boites, diagnostic) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientSsn);
            stmt.setString(2, ordonnanceContent);
            stmt.setString(3, nomMedicament);
            stmt.setInt(4, nbPriseJour);
            stmt.setInt(5, nbBoites);
            stmt.setString(6, diagnostic);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ordonnance successfully added to the database.");
            } else {
                System.out.println("Failed to add ordonnance.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error accessing database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AjouterOrdonnance().setVisible(true));
    }
}

