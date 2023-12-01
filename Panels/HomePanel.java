package Panels;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
//    private final JButton myAccountButton;
//    private final JButton sendMessageButton;
//    private final MenuPanel menuPanel; //added this for the menu panel

    public HomePanel() {

        // Set the layout manager for this panel
        setLayout(new BorderLayout());

        // Add other panels
        MenuPanel menuPanel = new MenuPanel();
        // Formatting size
        menuPanel.setPreferredSize(new Dimension(400, 768));

        add(menuPanel, BorderLayout.WEST);

        DisplayMessagesPanel displayMessagesPanel = new DisplayMessagesPanel("testSeller", "testCustomer", false);
        // Formatting size
        displayMessagesPanel.setPreferredSize(new Dimension(624, 768));
        add(displayMessagesPanel, BorderLayout.EAST);

        // Makes the panel visible
        setVisible(true);
//
//
//        // Set the layout manager for this panel
//        setLayout(null);
//        // Create components
//        this.myAccountButton = new JButton("My Account");
//        this.myAccountButton.setBounds(450, 20, 125, 25);
//        add(this.myAccountButton);
//
//        this.sendMessageButton = new JButton("Send Objects.Message");
//        this.sendMessageButton.setBounds(300, 200, 125, 25);
//        add(this.sendMessageButton);
    }

//    // Allows other classes to access buttons
//    public JButton getMyAccountButton() {
//        return this.myAccountButton;
//    }

    //    public JButton getSendMessageButton() {
//        return this.sendMessageButton;
//    }
//
//    public MenuPanel getMenuPanel() {
//        return menuPanel;
//    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jFrame = new JFrame("HomePanel");
                jFrame.setSize(1024, 768);
                jFrame.setLayout(new BorderLayout());
                jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                // Create the MenuPanel
                MenuPanel menuPanel = new MenuPanel();
                menuPanel.setPreferredSize(new Dimension(204, 768));

                // Create the DisplayMessagesPanel
                DisplayMessagesPanel displayMessagesPanel = new DisplayMessagesPanel("testSeller", "testCustomer", false);
                displayMessagesPanel.setPreferredSize(new Dimension(820, 768));
                jFrame.add(menuPanel, BorderLayout.WEST);
                jFrame.add(displayMessagesPanel, BorderLayout.EAST);
                jFrame.setVisible(true);
            }
        });
    }

}


