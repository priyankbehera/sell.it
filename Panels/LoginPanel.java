package Panels;

import Objects.Customer;
import Objects.Seller;
import Objects.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginPanel extends JPanel {
    private final JButton continueButton;
    private boolean isLoggedIn;

    public LoginPanel() {
        // Set the layout manager for this panel
        setLayout(null);

        // Create components
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 20, 80 , 25);
        add(emailLabel);

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

        JLabel successMessage = new JLabel("");
        successMessage.setBounds(10, 110, 300, 25);
        add(successMessage);

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = usernameText.getText();
                String password = Arrays.toString(passwordField.getPassword());
                User user = new User(email);

                if (User.isValidLogin(email, password, "customerNames.txt")) {
                    Customer existingCustomer = new Customer(email);
                    successMessage.setText("Login successful!");
                    isLoggedIn = true;
                } else if (User.isValidLogin(email, password, "sellerNames.txt")) {
                    Seller existingSeller = new Seller(email);
                    successMessage.setText("Login successful!");
                    isLoggedIn = true;
                } else {
                    successMessage.setText("Login attempt failed.");
                    isLoggedIn = false;
                }
            }
        });
    }

    // Allows other classes to access elements
    public JButton getContinueButton() {
        return this.continueButton;
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }
}
