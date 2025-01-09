package Cabinet;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.sql.*;

public class Login extends JFrame {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/HP/Desktop/database/cabinet.db";

    public Login() {
        // Frame setup
        setTitle("Login - Cabinet Médical");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Components
        JPanel panel = new JPanel();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        // Add components to panel
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        // Add panel to frame
        add(panel);
        setLocationRelativeTo(null); // Center frame

        // Action for Login button
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Both email and password must be provided.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String role = authenticateUser(email, password);
            if (role != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");

                // Open the Dashboard and pass the user info
                SwingUtilities.invokeLater(() -> {
                    new Dashboard(email, role).setVisible(true); // Open main interface
                });

                dispose(); // Close Login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private String authenticateUser(String email, String password) {
        String query = "SELECT password, role FROM users WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, storedPassword)) {
                    return rs.getString("role"); // Return role if authentication is successful
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error accessing database.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return null; // Return null if authentication fails
    }
}

