package Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class LoginPanel extends JPanel {
    JTextField emailText;
    JPasswordField passwordField;
    JButton continueButton;
    JLabel successMessage;

    public LoginPanel(Color backgroundColor) {
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
        successMessage.setBounds(392, 432, 300, 24);
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
