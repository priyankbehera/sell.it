package Panels;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class HomePanel extends JPanel {
    private MenuPanel menuPanel;

    public HomePanel(boolean ifSeller) {
        // setting the layout manager
        setLayout(new BorderLayout());

        // creating menuPanel
        menuPanel = new MenuPanel(ifSeller);
        menuPanel.setPreferredSize(new Dimension(500, 800));

        add(menuPanel, BorderLayout.WEST);
        setVisible(true);
    }

    public HomePanel(String user, boolean ifSeller, PrintWriter pw, BufferedReader br) {
        // setting the layout manager
        setLayout(new BorderLayout());

        // creating menuPanel
        menuPanel = new MenuPanel(ifSeller);
        menuPanel.setPreferredSize(new Dimension(500, 800));

        add(menuPanel, BorderLayout.WEST);
        setVisible(true);

        // a card layout manager that keeps stuff on a card layout
        // makes it easier to switch between different conversations
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        cardPanel.setSize(new Dimension(1500,800));
        cardLayout.layoutContainer(cardPanel);
        add(cardPanel);
        if ( ifSeller ) {
            String[] customers = getList(true);
            for (String s : customers) {
                DisplayMessagesPanel displayMessagesPanel = new DisplayMessagesPanel(user, s, true, pw, br);
                cardPanel.add(displayMessagesPanel, s);
            }
        } else {
            String[] sellers = getList(false);
            for (String s : sellers) {
                DisplayMessagesPanel displayMessagesPanel = new DisplayMessagesPanel(s, user, false, pw, br);
                cardPanel.add(displayMessagesPanel, s);
            }
        }
        menuPanel.getMessageList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Perform action based on the selected item
                    int selectedIndex = menuPanel.getMessageList().getSelectedIndex();
                    if (selectedIndex != -1) {
                        String selectedItem = menuPanel.getMessageList().getModel().getElementAt(selectedIndex).toString();
                        cardLayout.show(cardPanel, selectedItem);
                    }

                }
            }
        });
        // adding a mouse adapter
//        menuPanel.getMessageList().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                String selectedPerson = menuPanel.getMessageList()
//                        .getModel()
//                        .getElementAt(menuPanel.getMessageList().locationToIndex(e.getPoint()))
//                        .toString();
//
//                if (ifSeller) {
//                    DisplayMessagesPanel displayMessagesPanel = new DisplayMessagesPanel(seller, selectedPerson, true);
//                    displayMessagesPanel.setPreferredSize(new Dimension(900, 800));
//                    add(displayMessagesPanel, BorderLayout.EAST);
//                } else {
//                    DisplayMessagesPanel displayMessagesPanel = new DisplayMessagesPanel(selectedPerson, customer, false);
//                    displayMessagesPanel.setPreferredSize(new Dimension(900, 800));
//                    add(displayMessagesPanel, BorderLayout.EAST);
//                }
//                revalidate();
//                repaint();
//            }
//        });
    }

    // getting the user list
    public String[] getList(boolean ifSeller) {
        ArrayList<String> list = new ArrayList<>();
        String[] users;
        String[] empty = {"empty"};
        if (ifSeller) {
            String filename = "customer_data/CustomersList.csv";
            try (BufferedReader bfr = new BufferedReader(new FileReader(filename))) {
                String line;
                while ( (line = bfr.readLine()) != null ) {
                    list.add(line);
                }
                users = new String[list.size()];
                for ( int i = 0; i < users.length; i++ ) {
                    users[i] = list.get(i).split(",")[0];
                }
                return users;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error - IOException");
                return empty;
            }
        } else {
            String filename = "seller_data/SellersList.csv";
            try (BufferedReader bfr = new BufferedReader(new FileReader(filename))) {
                String line;
                while ( (line = bfr.readLine()) != null ) {
                    list.add(line);
                }
                users = new String[list.size()];
                for ( int i = 0; i < users.length; i++ ) {
                    users[i] = list.get(i).split(",")[0];
                }
                return users;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error - IOException");
                return empty;
            }
        }
    }
}


