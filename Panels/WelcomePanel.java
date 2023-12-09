package Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WelcomePanel extends JPanel {
    JTextField emailText;
    JPasswordField passwordField;
    JButton continueButton;
    JButton createAccButton;
    JLabel successMessage;

    public WelcomePanel(Color backgroundColor) {
        // Set the layout manager for this panel
        setLayout(null);

        // Set the background color for the panel
        setBackground(backgroundColor);

        // Create components
        try {
            BufferedImage iconImage = ImageIO.read(new File("Images/OriginalSizeLogo.png"));
            ImageIcon logo = new ImageIcon(iconImage);
            JLabel imageLabel = new JLabel(logo);
            imageLabel.setBounds(362, 200, 300, 125);
            add(imageLabel);
        } catch (IOException e) {
            JLabel imageLabel = new JLabel("Unable to load image.");
            imageLabel.setBounds(412, 200, 200, 24);
            add(imageLabel);
        }

        JLabel loginLabel = new JLabel("Sign in");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        loginLabel.setBounds(392, 332, 100, 24);
        add(loginLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(392, 372, 80, 24);
        add(emailLabel);

        emailText = new JTextField(20);
        emailText.setBounds(482, 372, 160, 24);
        add(emailText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(392, 402, 80, 24);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(482, 402, 160, 24);
        add(passwordField);

        continueButton = new JButton("Continue");
        continueButton.setBounds(392, 432, 120, 24);
        add(continueButton);

        JLabel messageLabel = new JLabel("Or if you are a new user, please create an account.");
        messageLabel.setBounds(367, 592, 290, 24);
        add(messageLabel);

        createAccButton = new JButton("Create Account");
        createAccButton.setBounds(432, 622, 160, 24);
        add(createAccButton);

        // Success message
        successMessage = new JLabel("");
        successMessage.setBounds(392, 462, 300, 24);
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

    public JButton getCreateAccButton() {
        return this.createAccButton;
    }

    public JLabel getSuccessMessage() {
        return this.successMessage;
    }
}
