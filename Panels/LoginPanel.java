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
        userLabel.setBounds(392, 342, 80, 24);
        add(userLabel);

        emailText = new JTextField(20);
        emailText.setBounds(482, 342, 160, 24);
        add(emailText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(392, 372, 80, 24);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(482, 372, 160, 24);
        add(passwordField);

        continueButton = new JButton("Continue");
        continueButton.setBounds(392, 402, 120, 24);
        add(continueButton);

        // Success message
        successMessage = new JLabel("");
        successMessage.setBounds(392, 432, 320, 24);
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
