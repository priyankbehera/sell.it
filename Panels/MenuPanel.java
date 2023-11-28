package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // Add action listeners
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
                // TODO: Implement view statistics functionality
                System.out.println("Viewing Statistics");
            }
        });
    }

    public String getSearchText() {
        return searchField.getText();
    }
}