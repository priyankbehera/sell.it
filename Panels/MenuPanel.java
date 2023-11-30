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
    private final JButton viewStatisticsButton;

    public MenuPanel() {

        setLayout(new FlowLayout());

        JLabel searchLabel = new JLabel("Enter Seller's Name:");
        add(searchLabel);

        searchField = new JTextField(20);
        add(searchField);

        searchButton = new JButton("Search Seller");
        add(searchButton);

        viewStatisticsButton = new JButton("View Statistics");
        add(viewStatisticsButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sellerName = searchField.getText();
                // TODO: Implement seller search functionality
                System.out.println("Searching for seller: " + sellerName);
            }
        });

        viewStatisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getViewStatisticsButton() {
        return viewStatisticsButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame("MenuPanel Test");
            testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            MenuPanel menuPanel = new MenuPanel();

            testFrame.getContentPane().add(menuPanel, BorderLayout.CENTER);

            testFrame.setSize(450, 100);
            testFrame.setLocationRelativeTo(null);
            testFrame.setVisible(true);
        });
    }
}