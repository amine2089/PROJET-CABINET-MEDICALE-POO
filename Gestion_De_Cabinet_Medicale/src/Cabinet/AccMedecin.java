package Cabinet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.*;

import DatabaseConnection.DBConnection;

@SuppressWarnings("serial")
public class AccMedecin extends JFrame implements ActionListener {

    private String doctorEmail;

    // Menu Bar Components
    private JMenuBar mb;
    private JMenu ajouter, sup;
    private JMenuItem fiche1, cons1, ord1, fiche2, cons2, ord2, dossier1, dossier2, p1, p2;

    public AccMedecin(String doctorEmail) {
        this.doctorEmail = doctorEmail;

        // Frame setup
        this.setTitle("Accueil Médecin");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main Panel with Buttons
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton viewPatients = new JButton("Accéder aux Patients");
        JButton viewConsultations = new JButton("Accéder aux Consultations");
        JButton viewOrdonnances = new JButton("Accéder aux Ordonnances");
        JButton viewRendezVous = new JButton("Accéder aux Rendez-vous");
        
        JButton viewDossier = new JButton("Accéder aux Dossiers Médicaux");
        viewPatients.addActionListener(e -> openDatabaseSection("Patients"));
        viewConsultations.addActionListener(e -> openDatabaseSection("Consultations"));
        viewOrdonnances.addActionListener(e -> openDatabaseSection("Ordonnances"));
        viewRendezVous.addActionListener(e-> openDatabaseSection("Rendez-vous"));
        viewDossier.addActionListener(e-> openDatabaseSection("Dossier Médicale"));
        
        JButton viewPaiement = new JButton("Accéder aux Paiements");
        viewPaiement.addActionListener(e-> openDatabaseSection("Paiement"));

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

        fiche1 = new JMenuItem("Fiche Patient");
        cons1 = new JMenuItem("Consultation");
        ord1 = new JMenuItem("Ordonnance");
        dossier1 = new JMenuItem("Dossier Médicale");
        fiche2 = new JMenuItem("Fiche Patient");
        cons2 = new JMenuItem("Consultation");
        ord2 = new JMenuItem("Ordonnance");
        dossier2 = new JMenuItem("Dossier Médicale");
        p1 = new JMenuItem("Paiements");
        p2 = new JMenuItem("Paiements");

        ajouter.add(fiche1);
        ajouter.add(cons1);
        ajouter.add(ord1);
        ajouter.add(dossier1);
        sup.add(fiche2);
        sup.add(cons2);
        sup.add(ord2);
        sup.add(dossier2);
        ajouter.add(p1);
        sup.add(p2);
        fiche1.addActionListener(this);
        cons1.addActionListener(this);
        ord1.addActionListener(this);
        fiche2.addActionListener(this);
        cons2.addActionListener(this);
        ord2.addActionListener(this);
        dossier1.addActionListener(this);
        dossier2.addActionListener(this);
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
                	query = "SELECT * FROM RendezVous"; break;
                case "Dossier Médicale":
                	query = "SELECT * FROM Dossier"; break;
                	
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


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fiche1) {
            new AjouterFichePatient().setVisible(true);
        } else if (e.getSource() == cons1) {
            new AjouterConsultation().setVisible(true);
        } else if (e.getSource() == ord1) {
            new AjouterOrdonnance().setVisible(true);
        } else if (e.getSource()== cons2) {
        	new SupprimerConsultation().setVisible(true);
        } else if (e.getSource() == ord2) {
        	new SupprimerOrdonnance().setVisible(true);
        } else if (e.getSource() == fiche2) {
        	new SupprimerFichePatient().setVisible(true);
        } else if (e.getSource() == dossier1) {
        	new AjouterDossierMedicale().setVisible(true);
        } else if (e.getSource() == dossier2) {
        	new SupprimerDossierMedicale().setVisible(true);
        }else if (e.getSource() == p2) {
        	new SupprimerPaiement().setVisible(true);
        }else if (e.getSource() == p1) {
        	new AjouterPaiement().setVisible(true);
        }
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccMedecin("doctor_email@example.com").setVisible(true));
    }
}
