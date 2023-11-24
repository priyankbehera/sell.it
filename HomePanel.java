import javax.swing.*;

public class HomePanel extends JPanel {
    private final JButton myAccountButton;

    public HomePanel() {
        // Set the layout manager for this panel
        setLayout(null);

        // Create components
        this.myAccountButton = new JButton("My Account");
        this.myAccountButton.setBounds(500, 20, 125, 25);
        add(this.myAccountButton);
    }

    // Allows other classes to access buttons
    public JButton getMyAccountButton() {
        return this.myAccountButton;
    }
}
