package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccPanel extends JPanel {
    private final JButton continueButton;
    JTextField emailText;
    JPasswordField passwordField;
    JLabel successMessage;
    private JComboBox<String> accountType;
    private String accountTypeString;
    public CreateAccPanel() {
        // Set the layout manager for this panel
        setLayout(null);

        // Create components
        JLabel userLabel = new JLabel("Email:");
        userLabel.setBounds(10, 20, 80 , 25);
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

        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setBounds(10, 80, 125, 25);
        add(accountTypeLabel);

        // Drop down menu for selecting account type
        JComboBox<String> accountType = new JComboBox<>(new String[] {"Customer", "Seller"});
        accountType.setSelectedIndex(0); // Sets the default option as Customer
        accountType.setBounds(110, 80, 125, 25);
        add(accountType);

        this.continueButton = new JButton("Continue");
        this.continueButton.setBounds(10, 160, 125, 25);
        add(this.continueButton);

        // success message
        successMessage = new JLabel("");
        successMessage.setBounds(10, 130, 300, 25);
        add(successMessage);
    }

    // Allows other classes to access buttons
    public JButton getContinueButton() {
        return this.continueButton;
    }
    public JComboBox<String> getAccountType() {
        return this.accountType;
    }
    public String getEmail() {
        return this.emailText.getText();
    }
    public String getPassword() {
        return String.valueOf(this.passwordField.getPassword());
    }
    public JLabel getSuccessMessage() {
        return this.successMessage;
    }

}
