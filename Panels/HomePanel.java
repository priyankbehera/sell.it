package Panels;

import javax.swing.*;

public class HomePanel extends JPanel {
    private final JButton myAccountButton;
    private final JButton sendMessageButton;

    public HomePanel() {
        // Set the layout manager for this panel
        setLayout(null);

        // Create components
        this.myAccountButton = new JButton("My Account");
        this.myAccountButton.setBounds(450, 20, 125, 25);
        add(this.myAccountButton);

        this.sendMessageButton = new JButton("Send Objects.Message");
        this.sendMessageButton.setBounds(300, 200, 125, 25);
        add(this.sendMessageButton);
    }

    // Allows other classes to access buttons
    public JButton getMyAccountButton() {
        return this.myAccountButton;
    }

    public JButton getSendMessageButton() {
        return this.sendMessageButton;
    }
}
