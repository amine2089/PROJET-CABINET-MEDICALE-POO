package Cabinet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import donnees_cabinet.RendezVous1;
import Information_Patient.RendezVous;
import java.sql.Date;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class AjouterRendezVous extends JFrame implements ActionListener {
    JLabel ssnLabel, nomLabel, prenomLabel, jourLabel;
    JButton save, quit;
    JTextField ssn, nom, prenom;
    JComboBox<String> dayComboBox, monthComboBox, yearComboBox;
    JPanel pannel, fin;

    public AjouterRendezVous() {
        this.setTitle("Ajouter Un Rendez-Vous");
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Custom Font
        Font customFont = new Font("Arial", Font.PLAIN, 14);
        Color bgColor = new Color(255, 228, 240);
        Color btnColor = new Color(240, 128, 128);

        ssnLabel = new JLabel("SSN: ");
        nomLabel = new JLabel("Nom: ");
        prenomLabel = new JLabel("Prénom: ");
        jourLabel = new JLabel("Rendez-vous le: ");
        ssn = new JTextField();
        nom = new JTextField();
        prenom = new JTextField();

        save = new JButton("Enregistrer");
        quit = new JButton("Annuler");

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

        JPanel datePanel = new JPanel();
        datePanel.setBackground(bgColor);
        datePanel.add(dayComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);

        pannel = new JPanel(new GridLayout(5, 2, 5, 5));
        pannel.setBackground(bgColor);
        pannel.add(nomLabel);
        pannel.add(nom);
        pannel.add(prenomLabel);
        pannel.add(prenom);
        pannel.add(ssnLabel);
        pannel.add(ssn);
        pannel.add(jourLabel);
        pannel.add(datePanel);

        fin = new JPanel();
        fin.setBackground(bgColor);
        fin.add(save);
        fin.add(quit);

        save.addActionListener(this);
        quit.addActionListener(this);

        this.add(pannel, BorderLayout.CENTER);
        this.add(fin, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) throws NumberFormatException {
        if (e.getSource() == save) {
            if (nom.getText().isEmpty() || prenom.getText().isEmpty() || ssn.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
            	try {
            	    // Build the date
            	    String selectedDate = String.format("%s-%s-%s",
            	            yearComboBox.getSelectedItem(),
            	            monthComboBox.getSelectedItem(),
            	            dayComboBox.getSelectedItem());
            	    Date jour = Date.valueOf(selectedDate);

            	    // Parse SSN
            	    int patientSSN = Integer.parseInt(ssn.getText());

            	    // Create the RendezVous object
            	    RendezVous rendezVous = new RendezVous(jour, patientSSN, prenom.getText(), nom.getText());

            	    // Add RendezVous to the database
            	    RendezVous1.AjouterRendezVous(rendezVous);

            	    // Success message
            	    JOptionPane.showMessageDialog(this, "Rendez-vous enregistré avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            	    this.dispose();
            	} catch (IllegalArgumentException ex) {
            	    JOptionPane.showMessageDialog(this, "Date invalide ou format incorrect. Format attendu: YYYY-MM-DD.", "Erreur", JOptionPane.ERROR_MESSAGE);
            	} catch (Exception ex) {
            	    // This is a generic catch for any unexpected exceptions
            	    JOptionPane.showMessageDialog(this, "Une erreur inattendue s'est produite: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            	    ex.printStackTrace();
            	}

            }
        } else if (e.getSource() == quit) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AjouterRendezVous().setVisible(true));
    }
}
