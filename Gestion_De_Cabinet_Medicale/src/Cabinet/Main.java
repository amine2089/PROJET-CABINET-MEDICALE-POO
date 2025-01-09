package Cabinet;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create the main window
        JFrame frame = new JFrame("Bienvenue sur votre Logiciel de Gestion de Cabinet Médical");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Buttons for user choice
        JButton loginButton = new JButton("Login");
        JButton signInButton = new JButton("Sign Up");

        // Action for Login button
        loginButton.addActionListener(e -> {
            frame.setVisible(false); // Hide main window
            SwingUtilities.invokeLater(() -> {
                Login loginWindow = new Login();
                loginWindow.setVisible(true); // Open Login window
            });
        });

        // Action for Sign Up button
        signInButton.addActionListener(e -> {
            frame.setVisible(false); // Hide main window
            SwingUtilities.invokeLater(() -> {
                SignIn signInWindow = new SignIn();
                signInWindow.setVisible(true); // Open Sign Up window
            });
        });

        // Add buttons to panel
        JPanel panel = new JPanel();
        panel.add(loginButton);
        panel.add(signInButton);

        // Add panel to frame and center it
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); // Show the main frame
    }
}

