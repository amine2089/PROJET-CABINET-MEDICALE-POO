package Cabinet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Information_Patient.FichePatient;
import donnees_cabinet.FichePatient1;
import java.sql.Date;

public class AjouterFichePatient extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    // UI Components
    JLabel Prenom, Nom, SSN, date_naissance, address, post, ville, email, num, job, sexe, grp_sanguin;
    JButton save, quit;
    JRadioButton homme, femme, autre;
    ButtonGroup sexeGroup;
    JTextField Prenomtxt, Nomtxt, SSNtxt, Adtxt, Posttxt, Villetxt, Emailtxt, Numtxt, Jobtxt;
    JComboBox<String> date_naissance_jour, date_naissance_mois, date_naissance_ann, bloodGroupComboBox;

    public AjouterFichePatient() {
        // Frame Setup
        this.setTitle("Ajouter Fiche Patient");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Custom Font and Colors
        Font customFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color bgColor = new Color(255, 228, 240); // Light pink
        Color btnColor = new Color(240, 128, 128); // Salmon

        // Initialize Components
        Prenom = new JLabel("Prénom: ");
        Nom = new JLabel("Nom: ");
        SSN = new JLabel("SSN: ");
        date_naissance = new JLabel("Né(e) le: ");
        address = new JLabel("Adresse: ");
        post = new JLabel("Code postal: ");
        ville = new JLabel("Ville: ");
        email = new JLabel("Email: ");
        num = new JLabel("Numéro de téléphone: ");
        job = new JLabel("Profession: ");
        sexe = new JLabel("Sexe: ");
        grp_sanguin = new JLabel("Groupe sanguin: ");

        Prenomtxt = new JTextField();
        Nomtxt = new JTextField();
        SSNtxt = new JTextField();
        Adtxt = new JTextField();
        Posttxt = new JTextField();
        Villetxt = new JTextField();
        Emailtxt = new JTextField();
        Numtxt = new JTextField();
        Jobtxt = new JTextField();

        save = new JButton("Enregistrer");
        quit = new JButton("Annuler");

        // Date of Birth Components
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

        // Blood Group ComboBox
        bloodGroupComboBox = new JComboBox<>(new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});

        // Sexe Radio Buttons
        homme = new JRadioButton("Homme");
        femme = new JRadioButton("Femme");
        autre = new JRadioButton("Autre");
        sexeGroup = new ButtonGroup();
        sexeGroup.add(homme);
        sexeGroup.add(femme);
        sexeGroup.add(autre);

        // Panels
        JPanel panel = new JPanel(new GridLayout(14, 2, 5, 5));
        panel.setBackground(bgColor);
        panel.add(Prenom);
        panel.add(Prenomtxt);
        panel.add(Nom);
        panel.add(Nomtxt);
        panel.add(SSN);
        panel.add(SSNtxt);
        panel.add(date_naissance);
        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.setBackground(bgColor);
        datePanel.add(date_naissance_jour);
        datePanel.add(date_naissance_mois);
        datePanel.add(date_naissance_ann);
        panel.add(datePanel);
        panel.add(grp_sanguin);
        panel.add(bloodGroupComboBox);
        panel.add(address);
        panel.add(Adtxt);
        panel.add(post);
        panel.add(Posttxt);
        panel.add(ville);
        panel.add(Villetxt);
        panel.add(email);
        panel.add(Emailtxt);
        panel.add(num);
        panel.add(Numtxt);
        panel.add(job);
        panel.add(Jobtxt);
        panel.add(sexe);
        JPanel sexePanel = new JPanel(new FlowLayout());
        sexePanel.setBackground(bgColor);
        sexePanel.add(homme);
        sexePanel.add(femme);
        sexePanel.add(autre);
        panel.add(sexePanel);

        JPanel fin = new JPanel();
        fin.setBackground(bgColor);
        fin.add(save);
        fin.add(quit);

        save.setBackground(btnColor);
        save.setForeground(Color.WHITE);
        quit.setBackground(btnColor);
        quit.setForeground(Color.WHITE);

        // Add ActionListeners
        save.addActionListener(this);
        quit.addActionListener(this);

        // Frame Layout
        this.add(panel, BorderLayout.CENTER);
        this.add(fin, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            // Validate and Save Logic
            if (Prenomtxt.getText().isEmpty() || Nomtxt.getText().isEmpty() || SSNtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Gather Data
                String prenom = Prenomtxt.getText();
                String nom = Nomtxt.getText();
                int ssn = Integer.parseInt(SSNtxt.getText());
                String address = Adtxt.getText();
                String postalCode = Posttxt.getText();
                String ville = Villetxt.getText();
                String email = Emailtxt.getText();
                String phone = Numtxt.getText(); // Updated to parse as long
                String job = Jobtxt.getText();
                String sexe = homme.isSelected() ? "Homme" : femme.isSelected() ? "Femme" : "Autre";
                String bloodGroup = (String) bloodGroupComboBox.getSelectedItem();
                String birthDate = String.format("%s-%02d-%02d",
                        date_naissance_ann.getSelectedItem(),
                        date_naissance_mois.getSelectedIndex() + 1,
                        Integer.parseInt((String) date_naissance_jour.getSelectedItem()));

                // Create FichePatient Object
                FichePatient patient = new FichePatient(ssn, prenom, nom, address, postalCode, ville, email, phone, job, sexe, bloodGroup, Date.valueOf(birthDate));

                // Save to Database
                FichePatient1.AjouterFichePatient(ssn, prenom, nom, address, postalCode, ville, email, phone, job, sexe, bloodGroup, Date.valueOf(birthDate));

                JOptionPane.showMessageDialog(this, "Fiche Patient enregistrée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un numéro valide pour le SSN ou le numéro de téléphone.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de l'enregistrement.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == quit) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AjouterFichePatient().setVisible(true));
    }
}
