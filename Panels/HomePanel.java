package Panels;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
/**
 * PJ-05 -- Sell.it
 * <p>
 *
 * This class creates the MenuPanel which controls the list
 * of users to be selected. It also contains other features like search and block.
 *
 *
 * @author Priyank Behera, Brayden Reimann 26047-L25
 * @version December 10, 2023
 */
public class HomePanel extends JPanel {
    private MenuPanel menuPanel;
    private PrintWriter pw;
    private BufferedReader br;

    public HomePanel(String user, boolean ifSeller, PrintWriter pw, BufferedReader br) {

        this.pw = pw;
        this.br = br;

        // setting the layout manager
        setLayout(new BorderLayout());

        // creating menuPanel
        menuPanel = new MenuPanel(ifSeller, user, pw, br);
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
    }

    // getting the user list
    private String[] getList(boolean ifSeller) {
        // send request to server
        String request = "getUsers," + ifSeller;

        try {
            pw.println(request);
            pw.flush();

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    break;
                }
            }
            String[] users = line.split(",");

            return users;

        } catch (IOException e) {
            return null;
        }
    }
}



