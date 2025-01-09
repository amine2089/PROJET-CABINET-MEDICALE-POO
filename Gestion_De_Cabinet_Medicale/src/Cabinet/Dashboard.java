package Cabinet;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    public Dashboard(String email, String role) {
        // Frame setup
        setTitle("Cabinet Médical - Tableau de Bord");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Bienvenue !");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> {
            dispose(); // Close the current Dashboard
            SwingUtilities.invokeLater(() -> new Main().main(null)); // Redirect to the main login screen
        });

        // Add components to panel
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing
        panel.add(logoutButton);

        // Add panel to frame
        add(panel);
        setLocationRelativeTo(null); // Center the frame

        // Redirect to the appropriate dashboard based on role
        SwingUtilities.invokeLater(() -> {
            if (role.equalsIgnoreCase("Secrétaire")) {
                new AccSecretaire(email).setVisible(true);
            } else if (role.equalsIgnoreCase("Médecin")) {
                new AccMedecin(email).setVisible(true); // Pass the email to AccMedecin
            } else {
                JOptionPane.showMessageDialog(this, "Rôle inconnu: " + role, "Erreur", JOptionPane.ERROR_MESSAGE);
                SwingUtilities.invokeLater(() -> new Main().main(null)); // Redirect to the main login screen
            }
            dispose(); // Close the current Dashboard
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard("lynaannou@gmail.com", "Médecin").setVisible(true));
    }
}

