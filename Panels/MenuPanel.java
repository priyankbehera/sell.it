package Panels;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPanel extends JPanel {
    private final JList<String> messageList;
    private final JTextField searchField;
    private final JButton moreButton;

    public JList getMessageList() {
        return messageList;
    }

    public MenuPanel(boolean ifSeller) {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Enter User's Name:");
        searchField = new JTextField(10);
        JButton searchButton = new JButton("Search User");
        JButton resetButton = new JButton("Reset");
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        moreButton = new JButton("\u22EE");
        moreButton.setPreferredSize(new Dimension(30, 20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(moreButton);

        add(searchPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        String[] people = getList(ifSeller);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for ( int i = 0; i < people.length; i++ ) {
            listModel.addElement(people[i]);
        }
        messageList = new JList<>(listModel);
        messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        messageList.setFixedCellHeight(40);
        messageList.setFixedCellWidth(300);
        messageList.setVisibleRowCount(16);

        JScrollPane scrollPane = new JScrollPane(messageList);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = searchField.getText();
                if (searchUser(username, ifSeller)) {
                    String[] temp = {username};
                    createJList(temp);
                } else {
                    JOptionPane.showMessageDialog(null, "No such user found!");
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] people = getList(ifSeller);
                createJList(people);
            }
        });

        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStatistics(moreButton);
            }
        });
    }

    public boolean searchUser(String name, boolean ifSeller) {
        ArrayList<String> list = new ArrayList<>();
        boolean isPresent = false;
        String folderName = ifSeller ? "customer_data" : "seller_data";
        String filename = folderName + "/CustomersList.csv";
        try (BufferedReader bfr = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                list.add(line);
            }
            String[] usernames = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                String username = list.get(i).split(",")[0];
                usernames[i] = username;
            }
            for (String username : usernames) {
                if (username.equals(name)) {
                    isPresent = true;
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error - IOException");
        }
        return isPresent;
    }

    public String[] getList(boolean ifSeller) {
        ArrayList<String> menuList = new ArrayList<>();
        String folderName = ifSeller ? "customer_data" : "seller_data";
        String filename;
        if (ifSeller) {
            filename = folderName + "/CustomersList.csv";
        } else {
            filename = folderName + "/SellersList.csv";
        }
        try (BufferedReader bfr = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                menuList.add(line);
            }
            String[] menuArray = new String[menuList.size()];
            for (int i = 0; i < menuArray.length; i++) {
                String customerName = menuList.get(i).split(",")[0];
                menuArray[i] = customerName;
            }
            return menuArray;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error - IOException");
            return null;
        }
    }

    public void createJList(String[] people) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String person : people) {
            model.addElement(person);
        }
        messageList.setModel(model);
    }

    private void showStatistics(Component component) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem messageStatisticsItem = new JMenuItem("Statistics");

        messageStatisticsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMessageStatistics();
            }
        });

        popupMenu.add(messageStatisticsItem);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame("MenuPanel Test");
            testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            MenuPanel menuPanel = new MenuPanel(true);

            testFrame.getContentPane().add(menuPanel, BorderLayout.CENTER);

            testFrame.setSize(500, 768);
            testFrame.setLocationRelativeTo(null);
            testFrame.setVisible(true);
        });
    }
}