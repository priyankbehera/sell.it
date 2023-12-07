package Panels;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private final JButton continueButton;
    JTextField emailText;
    JPasswordField passwordField;
    JLabel successMessage;

    public LoginPanel() {
        // Set the layout manager for this panel
        setLayout(new BorderLayout());

        // Create panel for login components
        JPanel loginComponents = new JPanel();
        loginComponents.setLayout(new GridLayout(3, 2, 0, 10));
        loginComponents.setPreferredSize(new Dimension(400, 300));

        // Create components
        JLabel emailLabel = new JLabel("Email:");
        loginComponents.add(emailLabel);

        emailText = new JTextField(20);
        loginComponents.add(emailText);

        JLabel passwordLabel = new JLabel("Password:");
        loginComponents.add(passwordLabel);

        passwordField = new JPasswordField(20);
        loginComponents.add(passwordField);

        continueButton = new JButton("Continue");
        loginComponents.add(continueButton);

        successMessage = new JLabel("");
        loginComponents.add(successMessage);

        // Add loginComponents to the panel
        add(loginComponents, BorderLayout.CENTER);
    }

    // Allows other classes to access elements
    public JButton getContinueButton() {
        return this.continueButton;
    }

    public JTextField getEmailText() {
        return this.emailText;
    }

    public JPasswordField getPasswordField() {
        return this.passwordField;
    }

    public JLabel getSuccessMessage() {
        return this.successMessage;
    }
}
