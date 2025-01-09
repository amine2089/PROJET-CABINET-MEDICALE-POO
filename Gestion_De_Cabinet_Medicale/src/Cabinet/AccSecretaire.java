package Cabinet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import DatabaseConnection.DBConnection;

@SuppressWarnings("serial")
public class AccSecretaire extends JFrame implements ActionListener {

    private String secretaryEmail;

    // Menu Bar Components
    private JMenuBar mb;
    private JMenu ajouter, sup;
    private JMenuItem ajouterFiche, ajouterConsultation, ajouterOrdonnance, fiche2, cons2, ord2, rendezvousAdd, rendezvousDelete, rendezvousModify, dossier1, dossier2;

	private JMenuItem p1;

	private JMenuItem p2;

    public AccSecretaire(String secretaryEmail) {
        this.secretaryEmail = secretaryEmail;

        // Frame setup
        setTitle("Accueil Secrétaire");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main Panel with Buttons
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton viewPatients = new JButton("Accéder aux Patients");
        JButton viewConsultations = new JButton("Accéder aux Consultations");
        JButton viewOrdonnances = new JButton("Accéder aux Ordonnances");
        JButton viewRendezVous = new JButton("Accéder aux Rendez-vous");
        JButton viewDossier = new JButton("Accéder aux Dossiers Médicaux");
        JButton viewPaiement = new JButton("Accéder aux Paiements");
        viewPaiement.addActionListener(e-> openDatabaseSection("Paiement"));
      

        viewPatients.addActionListener(e -> openDatabaseSection("Patients"));
        viewConsultations.addActionListener(e -> openDatabaseSection("Consultations"));
        viewOrdonnances.addActionListener(e -> openDatabaseSection("Ordonnances"));
        viewRendezVous.addActionListener(e -> openDatabaseSection("Rendez-vous"));
        viewDossier.addActionListener(e -> openDatabaseSection("Dossier"));
        mainPanel.add(viewPatients);
        mainPanel.add(viewConsultations);
        mainPanel.add(viewOrdonnances);
        mainPanel.add(viewRendezVous);
        mainPanel.add(viewDossier);
        mainPanel.add(viewPaiement);
        

        this.add(mainPanel, BorderLayout.CENTER);

        // Menu Bar Setup
        setupMenuBar();

        // Logout Button
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new Main().main(null)); // Reopen Main
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logoutButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupMenuBar() {
        mb = new JMenuBar();

        ajouter = new JMenu("Ajouter");
        sup = new JMenu("Supprimer");
      

        ajouterFiche = new JMenuItem("Fiche Patient");
        ajouterConsultation = new JMenuItem("Consultation");
        ajouterOrdonnance = new JMenuItem("Ordonnance");
        fiche2 = new JMenuItem("Fiche Patient");
        cons2 = new JMenuItem("Consultation");
        ord2 = new JMenuItem("Ordonnance");
        dossier1 = new JMenuItem("Dossier");
        dossier2 = new JMenuItem("Dossier");
        rendezvousAdd = new JMenuItem("Ajouter Rendez-vous");
        rendezvousDelete = new JMenuItem("Supprimer Rendez-vous");
        rendezvousModify = new JMenuItem("Modifier Rendez-vous");
        p1 = new JMenuItem("Paiements");
        p2 = new JMenuItem("Paiements");

        ajouter.add(ajouterFiche);
        ajouter.add(ajouterConsultation);
        ajouter.add(ajouterOrdonnance);
        ajouter.add(dossier1);
        sup.add(fiche2);
        sup.add(cons2);
        sup.add(ord2);
        sup.add(dossier2);
        

        // Add rendez-vous actions
        ajouter.add(rendezvousAdd);
        sup.add(rendezvousDelete);

        ajouterFiche.addActionListener(this);
        ajouterConsultation.addActionListener(this);
        ajouterOrdonnance.addActionListener(this);
        dossier1.addActionListener(this);
        dossier2.addActionListener(this);
        ajouter.add(p1);
        sup.add(p2);
        fiche2.addActionListener(this);
        cons2.addActionListener(this);
        ord2.addActionListener(this);
        rendezvousAdd.addActionListener(this);
        rendezvousDelete.addActionListener(this);
        p1.addActionListener(this);
        p2.addActionListener(this);

        mb.add(ajouter);
        mb.add(sup);
        

        this.setJMenuBar(mb);
    }

    private void openDatabaseSection(String section) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "";

            switch (section) {
                case "Patients":
                    query = "SELECT * FROM FichePatient";
                    break;
                case "Consultations":
                    query = "SELECT * FROM Consultation";
                    break;
                case "Ordonnances":
                    query = "SELECT * FROM Ordonnance";
                    break;
                case "Rendez-vous":
                    query = "SELECT * FROM RendezVous";
                    break;
                case "Dossier":
                    query = "SELECT * FROM Dossier";
                    break;
                case "Paiement":
                	query = "SELECT * FROM Paiement"; break;
                default:
                    JOptionPane.showMessageDialog(this, "Unknown section: " + section, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            // Execute the query
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                StringBuilder result = new StringBuilder("Results for: " + section + "\n\n");

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Add column headers
                for (int i = 1; i <= columnCount; i++) {
                    result.append(metaData.getColumnName(i)).append("\t");
                }
                result.append("\n");

                // Add rows
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        result.append(rs.getObject(i)).append("\t");
                    }
                    result.append("\n");
                }

                // Display results
                JTextArea textArea = new JTextArea(result.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);

                JOptionPane.showMessageDialog(this, scrollPane, "Database Results", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error accessing database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Manage Rendez-vous: Add, Delete, Modify
    private void manageRendezVous() {
        String[] options = {"Ajouter Rendez-vous", "Supprimer Rendez-vous", "Modifier Rendez-vous"};
        int choice = JOptionPane.showOptionDialog(this, "Choose action for Rendez-vous:", "Manage Rendez-vous",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                // Add Rendez-vous
                new AjouterRendezVous().setVisible(true);
                break;
            case 1:
                // Delete Rendez-vous
                new SupprimerRendezVous().setVisible(true);
                break;
            
            default:
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ajouterFiche) {
            new AjouterFichePatient().setVisible(true);
        } else if (e.getSource() == ajouterConsultation) {
            new AjouterConsultation().setVisible(true);
        } else if (e.getSource() == ajouterOrdonnance) {
            new AjouterOrdonnance().setVisible(true);
        } else if (e.getSource() == rendezvousAdd) {
            new AjouterRendezVous().setVisible(true);
        } else if (e.getSource() == rendezvousModify) {
            new ModifierRendezvous().setVisible(true);
        } else if (e.getSource() == rendezvousDelete) {
            new SupprimerRendezVous().setVisible(true);
        }  else if (e.getSource()== cons2) {
        	new SupprimerConsultation().setVisible(true);
        } else if (e.getSource() == ord2) {
        	new SupprimerOrdonnance().setVisible(true);
        } else if (e.getSource() == fiche2) {
        	new SupprimerFichePatient().setVisible(true);
        } else if (e.getSource() == dossier1) {
        	new AjouterDossierMedicale().setVisible(true);
        } else if (e.getSource() == p2) {
        	new SupprimerPaiement().setVisible(true);
        }else if (e.getSource() == p1) {
        	new AjouterPaiement().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccSecretaire("secretary_email@example.com").setVisible(true));
    }
}
