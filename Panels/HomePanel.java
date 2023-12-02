package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    public HomePanel(String seller, String customer, boolean ifSeller) {
        // setting the layout manager
        setLayout(new BorderLayout());

        // creating menuPanel
        menuPanel = new MenuPanel(ifSeller);
        menuPanel.setPreferredSize(new Dimension(500, 800));

        add(menuPanel, BorderLayout.WEST);
        setVisible(true);

        // adding a mouse adapter
        menuPanel.getMessageList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String selectedPerson = menuPanel.getMessageList()
                        .getModel()
                        .getElementAt(menuPanel.getMessageList().locationToIndex(e.getPoint()))
                        .toString();

                if (ifSeller) {
                    DisplayMessagesPanel displayMessagesPanel = new DisplayMessagesPanel(seller, selectedPerson, true);
                    displayMessagesPanel.setPreferredSize(new Dimension(900, 800));
                    add(displayMessagesPanel, BorderLayout.EAST);
                } else {
                    DisplayMessagesPanel displayMessagesPanel = new DisplayMessagesPanel(selectedPerson, customer, false);
                    displayMessagesPanel.setPreferredSize(new Dimension(900, 800));
                    add(displayMessagesPanel, BorderLayout.EAST);
                }
                revalidate();
                repaint();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HomePanel homePanel = new HomePanel(true);
                JFrame jFrame = new JFrame("HomePanel");
                jFrame.setSize(2000, 800);
                jFrame.setLayout(new BorderLayout());
                jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jFrame.add(homePanel);

                homePanel = new HomePanel("testseller", "testcustomer", true);
                jFrame.add(homePanel);
                homePanel.setVisible(true);
                jFrame.setVisible(true);
            }
        });
    }
}


