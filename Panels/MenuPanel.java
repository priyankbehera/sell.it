package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPanel extends JPanel {
    private final JTextField searchField;
    private final JButton searchButton;
    private final JButton moreButton;

    public MenuPanel() {

        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel searchLabel = new JLabel("Enter Seller's Name:");
        searchPanel.add(searchLabel);

        searchField = new JTextField(20);
        searchPanel.add(searchField);

        searchButton = new JButton("Search Seller");
        searchPanel.add(searchButton);

        moreButton = new JButton("\u22EE");
        moreButton.setPreferredSize(new Dimension(30, 20));
        buttonPanel.add(moreButton);

        add(searchPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sellerName = searchField.getText();
                // TODO: Implement seller search functionality
                System.out.println("Searching for seller: " + sellerName);
            }
        });

        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a popup menu with additional options
                showStatistics(moreButton);
            }
        });
    }

    private void showStatistics(Component component) {

        JPopupMenu popupMenu = new JPopupMenu();


        JMenuItem messageStatisticsItem = new JMenuItem("Statistics");
        // Add more items as needed...


        messageStatisticsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                displayMessageStatistics();
            }
        });


        popupMenu.add(messageStatisticsItem);
        // Add more items as needed...

        // Show the popup menu
        popupMenu.show(component, 0, component.getHeight());
    }

    private void displayMessageStatistics() {
        String sellerName = searchField.getText();

        if (!sellerName.isEmpty()) {
            Customer customer = new Customer(sellerName);

            try {
                ArrayList<String> sentStatistics = customer.viewSentStatistics(1);
                for (String stat : sentStatistics) {
                    System.out.println(stat);
                }

                ArrayList<String> receivedStatistics = customer.viewReceivedStatistics(1);
                for (String stat : receivedStatistics) {
                    System.out.println(stat);
                }
            } catch (IOException ex) {
                System.out.println("Error while retrieving statistics.");
            }
        } else {
            System.out.println("Please enter a valid seller name.");
        }
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getMoreButton() {
        return moreButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame("MenuPanel Test");
            testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            MenuPanel menuPanel = new MenuPanel();

            testFrame.getContentPane().add(menuPanel);

            testFrame.setSize(450, 100);
            testFrame.setLocationRelativeTo(null);
            testFrame.setVisible(true);
        });
    }
}
