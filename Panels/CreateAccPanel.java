package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccPanel extends JPanel {
    JTextField emailText;
    JPasswordField passwordField;
    JComboBox<String> accountType;
    JButton continueButton;
    JLabel successMessage;

    public CreateAccPanel(Color backgroundColor) {
        // Set the layout manager for this panel
        setLayout(null);

        // Sets the background color for the panel
        setBackground(backgroundColor);

        // Create components
        JLabel userLabel = new JLabel("Email:");
        userLabel.setBounds(392, 336, 80, 24);
        add(userLabel);

        emailText = new JTextField(20);
        emailText.setBounds(482, 336, 160, 24);
        add(emailText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(392, 366, 80, 24);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(482, 366, 160, 24);
        add(passwordField);

        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setBounds(392, 396, 124, 24);
        add(accountTypeLabel);

        // Drop down menu for selecting account type
        accountType = new JComboBox<>(new String[]{"Customer", "Seller"});
        accountType.setSelectedIndex(0); // Sets the default option as Customer
        accountType.setBounds(492, 396, 124, 24);
        add(accountType);

        continueButton = new JButton("Continue");
        continueButton.setBounds(392, 426, 120, 24);
        add(continueButton);

        // Success message
        successMessage = new JLabel("");
        successMessage.setBounds(392, 456, 300, 24);
        add(successMessage);
    }

    // Allows other classes to access buttons
    public String getEmail() {
        return this.emailText.getText();
    }

    public String getPassword() {
        return String.valueOf(this.passwordField.getPassword());
    }

    public String getAccountType() {
        return (String) this.accountType.getSelectedItem();
    }

    public JButton getContinueButton() {
        return this.continueButton;
    }

    public JLabel getSuccessMessage() {
        return this.successMessage;
    }
}
