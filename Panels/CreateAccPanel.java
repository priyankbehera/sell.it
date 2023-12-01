package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccPanel extends JPanel {
    private final JButton continueButton;
    private JTextField usernameText;
    private JPasswordField passwordField;

    private JComboBox<String> accountType;
    private String accountTypeString;
    public CreateAccPanel() {
        // Set the layout manager for this panel
        setLayout(null);

        // Drop down menu for account type
        JComboBox<String> accountType = new JComboBox<>(new String[] {"Customer", "Seller"});
        accountType.setBounds(110, 80, 125, 25);
        JButton getAccountType = new JButton("Select");
        getAccountType.setBounds(250, 80, 80, 25);
        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setBounds(10, 80, 125, 25);
        add(accountTypeLabel);
        add(accountType);
        add(getAccountType);
        // Create components
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80 , 25);
        add(userLabel);

        usernameText = new JTextField(20);
        usernameText.setBounds(100, 20, 165, 25);
        add(usernameText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 165, 25);
        add(passwordField);

        this.continueButton = new JButton("Continue");
        this.continueButton.setBounds(10, 160, 125, 25);
        add(this.continueButton);
        // Action listener
        getAccountType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountTypeString = (String) accountType.getSelectedItem();

            }
        });
    }

    // Allows other classes to access buttons
    public JButton getContinueButton() {
        return this.continueButton;
    }

    public JButton getAccountTypeButton() {
        return this.continueButton;
    }

    public String getAccountType() {
        return this.accountTypeString;
    }

    public String getUsername() {
        return this.usernameText.getText();
    }

    public String getPassword() {
        return String.valueOf(this.passwordField.getPassword());
    }

}
