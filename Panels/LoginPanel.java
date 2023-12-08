package Panels;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    JTextField emailText;
    JPasswordField passwordField;
    JButton continueButton;
    JLabel successMessage;

    public LoginPanel() {
        // Set the layout manager for this panel
        setLayout(null);

        // Create components
        JLabel userLabel = new JLabel("Email:");
        userLabel.setBounds(10, 20, 80, 25);
        add(userLabel);

        emailText = new JTextField(20);
        emailText.setBounds(100, 20, 165, 25);
        add(emailText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 165, 25);
        add(passwordField);

        continueButton = new JButton("Continue");
        continueButton.setBounds(10, 80, 125, 25);
        add(continueButton);

        // Success message
        successMessage = new JLabel("");
        successMessage.setBounds(10, 110, 300, 25);
        add(successMessage);
    }

    // Allows other classes to access elements
    public String getEmail() {
        return this.emailText.getText();
    }

    public String getPassword() {
        return String.valueOf(this.passwordField.getPassword());
    }

    public JButton getContinueButton() {
        return this.continueButton;
    }

    public JLabel getSuccessMessage() {
        return this.successMessage;
    }
}
