package Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * PJ-05 -- Sell.it
 * <p>
 * This class stores the components for the create account panel.
 *
 *
 * @author Brayden Reimann, 26047-L25
 * @version December 10, 2023
 */
public class CreateAccPanel extends JPanel {
    JTextField emailText;
    JPasswordField passwordField;
    JComboBox<String> accountType;
    JButton continueButton;
    JButton returnLoginButton;
    JLabel successMessage;

    public CreateAccPanel(Color backgroundColor) {
        // Set the layout manager for this panel
        setLayout(null);

        // Sets the background color for the panel
        setBackground(backgroundColor);

        // Create components
        try {
            BufferedImage iconImage = ImageIO.read(new File("Images/marketplacecropped.png"));
            ImageIcon logo = new ImageIcon(iconImage);
            JLabel imageLabel = new JLabel(logo);
            imageLabel.setBounds(212, 10, 600, 480);
            add(imageLabel);
        } catch (IOException e) {
            JLabel imageLabel = new JLabel("Unable to load image.");
            imageLabel.setBounds(412, 200, 200, 24);
            add(imageLabel);
        }

        JLabel loginLabel = new JLabel("Create Account");
        loginLabel.setFont(new Font("Josefin Slab", Font.PLAIN, 24));
        loginLabel.setBounds(392, 332, 200, 30);
        loginLabel.setForeground(Color.WHITE);
        add(loginLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(392, 372, 80, 24);
        emailLabel.setForeground(Color.WHITE);
        add(emailLabel);

        emailText = new JTextField(20);
        emailText.setBounds(482, 372, 160, 24);
        add(emailText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(392, 402, 80, 24);
        passwordLabel.setForeground(Color.WHITE);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(482, 402, 160, 24);
        add(passwordField);

        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setBounds(392, 432, 124, 24);
        accountTypeLabel.setForeground(Color.WHITE);
        add(accountTypeLabel);

        // Drop down menu for selecting account type
        accountType = new JComboBox<>(new String[]{"Customer", "Seller"});
        accountType.setSelectedIndex(0); // Sets the default option as Customer
        accountType.setBounds(492, 432, 124, 24);
        add(accountType);

        continueButton = new JButton("Continue");
        continueButton.setBounds(392, 462, 120, 24);
        add(continueButton);

        returnLoginButton = new JButton("Return to Sign in");
        returnLoginButton.setBounds(432, 592, 160, 24);
        add(returnLoginButton);

        // Success message
        successMessage = new JLabel("");
        successMessage.setBounds(392, 492, 320, 24);
        add(successMessage);
    }

    // Method to reset the panel
    public void resetPanel() {
        emailText.setText("");
        passwordField.setText("");
        accountType.setSelectedIndex(0);
        successMessage.setText("");
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

    public JButton getReturnLoginButton() {
        return this.returnLoginButton;
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
            CreateAccPanel createAccPanel = new CreateAccPanel(color);
            mainframe.setContentPane(createAccPanel);
            mainframe.setVisible(true);
        });
    }
}
