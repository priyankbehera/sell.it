package Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * PJ-05 -- Sell.it
 * <p>
 * this class creates and stores the welcome panel which
 * prompts a user to sign in or create an account.
 *
 *
 * @author Brayden Reimann, 26047-L25
 * @version December 10, 2023
 */
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
            BufferedImage iconImage = ImageIO.read(new File("Images/marketplace logo.png"));
            ImageIcon logo = new ImageIcon(iconImage);
            JLabel imageLabel = new JLabel(logo);
            imageLabel.setBounds(212, -20, 600, 480);
            add(imageLabel);
        } catch (IOException e) {
            JLabel imageLabel = new JLabel("Unable to load image.");
            imageLabel.setBounds(412, 200, 200, 24);
            add(imageLabel);
        }

        JLabel loginLabel = new JLabel("Sign in");
        loginLabel.setFont(new Font("Josefin Slab", Font.PLAIN, 24));
        loginLabel.setBounds(392, 372, 100, 30);
        loginLabel.setForeground(Color.WHITE);
        add(loginLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(392, 412, 80, 24);
        emailLabel.setForeground(Color.WHITE);
        add(emailLabel);

        emailText = new JTextField(20);
        emailText.setBounds(482, 412, 160, 24);
        add(emailText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(392, 442, 80, 24);
        passwordLabel.setForeground(Color.WHITE);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(482, 442, 160, 24);
        add(passwordField);

        continueButton = new JButton("Continue");
        continueButton.setBounds(392, 492, 120, 24);
        add(continueButton);

        JLabel messageLabel = new JLabel("                Or if you are a new user...");
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBounds(367, 592, 290, 24);
        add(messageLabel);

        createAccButton = new JButton("Create An Account");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainframe = new JFrame();
            mainframe.setSize(1024, 768);
            mainframe.setLocationRelativeTo(null);
            mainframe.setResizable(false);
            mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            Color color = new Color(27, 50, 84);
            WelcomePanel welcomePanel = new WelcomePanel(color);
            mainframe.setContentPane(welcomePanel);
            mainframe.setVisible(true);
        });
    }
}
