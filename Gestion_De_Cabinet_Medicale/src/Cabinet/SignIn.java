package Cabinet;

import javax.swing.*;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class SignIn extends JFrame {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";

    public SignIn() {
        // Frame setup
        setTitle("Sign Up - Cabinet Médical");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Components
        JPanel panel = new JPanel();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel roleLabel = new JLabel("Role:");
        JTextField roleField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton signUpButton = new JButton("Sign Up");

        // Add components to panel
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(roleLabel);
        panel.add(roleField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signUpButton);

        // Add panel to frame
        add(panel);
        setLocationRelativeTo(null); // Center frame

        // Action for Sign Up button
        signUpButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = roleField.getText().trim();
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Both email and password must be provided.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean isRegistered = registerUser(email, password, role);
            if (isRegistered) {
                JOptionPane.showMessageDialog(this, "Sign Up successful!");

                // Open the Dashboard and pass the user info
                SwingUtilities.invokeLater(() -> new Dashboard(email, role).setVisible(true)); // Default to 'Médecin' role
                dispose(); // Close Sign Up window
            } else {
                JOptionPane.showMessageDialog(this, "Error registering user.", "Sign Up Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean registerUser(String email, String password, String role) {
        String query = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, BCrypt.hashpw(password, BCrypt.gensalt())); // Hash the password
            stmt.setString(3, role); // Default to 'Médecin' role

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error accessing database.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}

