package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel {
    private final JButton loginButton;
    private final JButton createAccButton;
    public WelcomePanel() {
        // Set the layout manager for this panel
        setLayout(new GridLayout(3, 2, 10, 10));
        // (rows: , cols: , hgap: , vgap: )

        // Create components
        this.loginButton = new JButton("Login");
        this.createAccButton = new JButton("Create an account");

        // Adds panel to container
        add(this.loginButton);
        add(this.createAccButton);
    }

    // Allows other classes to access buttons
    public JButton getLoginButton() {
        return this.loginButton;
    }

    public JButton getCreateAccButton() {
        return this.createAccButton;
    }
}
