package Panels;

import javax.swing.*;

public class LoginPanel extends JPanel {
    private final JButton continueButton;
    public LoginPanel() {
        // Set the layout manager for this panel
        setLayout(null);

        // Create components
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80 , 25);
        add(userLabel);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(100, 20, 165, 25);
        add(usernameText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 165, 25);
        add(passwordField);

        this.continueButton = new JButton("Continue");
        this.continueButton.setBounds(10, 80, 125, 25);
        add(this.continueButton);
    }

    // Allows other classes to access buttons
    public JButton getContinueButton() {
        return this.continueButton;
    }
}
