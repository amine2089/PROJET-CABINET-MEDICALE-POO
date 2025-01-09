package Cabinet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

@SuppressWarnings("serial")
public class SupprimerPaiement extends JFrame implements ActionListener {

    JPanel panel1, panel2;
    JLabel ssnLabel;
    JTextField ssnTextField;
    JTextArea infoArea;
    JButton searchButton, deleteButton;

    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";

    public SupprimerPaiement() {
        // Set up the JFrame
        this.setTitle("Supprimer Paiement");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel1 = new JPanel(new BorderLayout());
        panel2 = new JPanel(new GridLayout(1, 3));

        ssnLabel = new JLabel("SSN : ");
        ssnTextField = new JTextField();
        searchButton = new JButton("Rechercher");
        deleteButton = new JButton("Supprimer");
        deleteButton.setEnabled(false);

        infoArea = new JTextArea("SSN    Nom    Prénom    Montant    Date    Type    Statut\n");
        infoArea.setEnabled(false);

        panel2.add(ssnLabel);
        panel2.add(ssnTextField);
        panel2.add(searchButton);

        panel1.add(panel2, BorderLayout.NORTH);
        panel1.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        panel1.add(deleteButton, BorderLayout.SOUTH);

        this.add(panel1);

        // Add action listeners
        searchButton.addActionListener(this);
        deleteButton.addActionListener(this);

        this.setVisible(true);
    }

    // Method to search for payments by SSN
    private void searchPaymentBySSN(int ssn) {
        String query = "SELECT * FROM Paiement WHERE ssn = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ssn);
            ResultSet rs = stmt.executeQuery();

            infoArea.setEnabled(true);
            infoArea.setText("SSN    Nom    Prénom    Montant    Date    Type    Statut\n");

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                int paymentSSN = rs.getInt("ssn");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                double montant = rs.getDouble("montant");
                Date datePaiement = rs.getDate("datePaiement");
                String typePaiement = rs.getString("typePaiement");
                boolean paiementFait = rs.getBoolean("paiementFait");

                infoArea.append(String.format("%d    %s    %s    %.2f    %s    %s    %s\n",
                        paymentSSN, nom, prenom, montant, datePaiement, typePaiement, paiementFait ? "Effectué" : "Non-effectué"));
            }

            if (hasResults) {
                deleteButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Aucun paiement trouvé pour le SSN donné.", "Information", JOptionPane.INFORMATION_MESSAGE);
                deleteButton.setEnabled(false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche du paiement.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to delete payment by SSN
    private void deletePaymentBySSN(int ssn) {
        String query = "DELETE FROM Paiement WHERE ssn = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ssn);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Paiement supprimé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Aucun paiement trouvé pour le SSN donné.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du paiement.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String ssnInput = ssnTextField.getText().trim();
            if (ssnInput.isEmpty() || !ssnInput.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un SSN valide (uniquement des chiffres).", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int ssn = Integer.parseInt(ssnInput);
            searchPaymentBySSN(ssn);
        }

        if (e.getSource() == deleteButton) {
            String ssnInput = ssnTextField.getText().trim();
            if (ssnInput.isEmpty() || !ssnInput.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un SSN valide (uniquement des chiffres).", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int ssn = Integer.parseInt(ssnInput);
            deletePaymentBySSN(ssn);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupprimerPaiement());
    }
}
