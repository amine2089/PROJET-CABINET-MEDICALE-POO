package Cabinet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;

import donnees_cabinet.RendezVous1;  // Assuming RendezVous1 has the necessary methods
import Information_Patient.RendezVous;  // Assuming a RendezVous class exists

public class SupprimerRendezVous extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JPanel panel1, panel2, panel3;
    JLabel SSN, id;
    JTextField SSNtxt, Idtxt;
    JTextArea info;
    JButton recherche, supprimer;

    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";  // Database URL

    public SupprimerRendezVous() {
        this.setTitle("Supprimer Rendez-vous");
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel1 = new JPanel(new GridLayout(3, 1));
        panel2 = new JPanel(new GridLayout(1, 3));
        panel3 = new JPanel(new GridLayout(1, 3));

        SSN = new JLabel("SSN: ");
        SSNtxt = new JTextField();
        recherche = new JButton("Rechercher");
        info = new JTextArea("id        jour    patient_ssn    nom    prenom\n");
        id = new JLabel("Identifiant de la rendez-vous: ");
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

                // Get rendezvous for the SSN
                LinkedList<RendezVous> lc = RendezVous1.RechercherRendezVous(ssn);

                info.setEnabled(true);
                Idtxt.setEnabled(true);
                supprimer.setEnabled(true);

                // Clear previous results in case of new search
                info.setText("id        jour    patient_ssn    nom    prenom\n");

                // Iterate over the rendezvous and display them
                while (!lc.isEmpty()) {
                    RendezVous r = lc.poll();
                    extracted(r);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un SSN valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == supprimer) {
            // Ensure the Id is valid before attempting to delete
            if (Idtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un identifiant de rendez-vous.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int rendezVousId = Integer.parseInt(Idtxt.getText());

                // Call the method to delete the rendez-vous by Id
                RendezVous1.SupprimerRendezVous(rendezVousId);

                JOptionPane.showMessageDialog(this, "Rendez-vous supprimé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); // Close the window after deleting
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un identifiant valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to update the JTextArea with rendezvous details
    private void extracted(RendezVous r) {
        info.append(r.getJour() + "    " + r.getSSN() + "    " + r.getNom() + "    " + r.getPrenom() + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupprimerRendezVous().setVisible(true));
    }
}
