package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuPanel extends JPanel {
    private final JList<String> messageList;
    private final JTextField searchField;
    private final JButton moreButton;
    private final boolean ifSeller;
    private final Set<String> blockedUsers;
    private boolean isVisible = true;
    private boolean searchButtonClicked = false;
    private boolean seeAllButtonClicked = false;

    public JList<String> getMessageList() {
        return messageList;
    }

    public MenuPanel(boolean ifSeller) {

        this.ifSeller = ifSeller;
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Enter User's Name:");
        searchField = new JTextField(10);
        JButton searchButton = new JButton("Search User");
        JButton seeAllButton = new JButton("See All");
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(seeAllButton);

        moreButton = new JButton("\u22EE");
        moreButton.setPreferredSize(new Dimension(30, 20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(moreButton);

        add(searchPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        String[] people = getList(ifSeller);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < people.length; i++ ) {
            listModel.addElement(people[i]);
        }
        messageList = new JList<>(listModel);
        messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        messageList.setFixedCellHeight(40);
        messageList.setFixedCellWidth(300);
        messageList.setVisibleRowCount(16);

        JScrollPane scrollPane = new JScrollPane(messageList);
        add(scrollPane, BorderLayout.CENTER);

        blockedUsers = new HashSet<>();

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( !searchButtonClicked  || seeAllButtonClicked ) {
                    String[] temp = getList(ifSeller);
                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    for (int i = 0; i < temp.length; i++) {
                        listModel.addElement(temp[i]);
                    }
                    messageList.setModel(listModel); // Update the existing JList model
                    searchButtonClicked = true;
                    seeAllButtonClicked = false;
                }
            }
        });
        timer.setRepeats(true);
        timer.start();

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

        seeAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seeAllButtonClicked = true;
                String[] people = getList(ifSeller);
                createJList(people);
            }
        });

        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMenuPopup(moreButton);
            }
        });
    }

    private void showMenuPopup(Component component) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem messageStatisticsItem = new JMenuItem("Statistics");
        JMenuItem blockUserItem = new JMenuItem("Block User");
        JMenuItem sortSentMessagesItem = new JMenuItem("Sort Sent Messages");
        JMenuItem sortReceivedMessagesItem = new JMenuItem("Sort Received Messages");
        JMenuItem invisibleItem = new JMenuItem(isVisible ? "Become Invisible" : "Become Visible");

//        messageStatisticsItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                displayMessageStatistics();
//            }
//        });

        blockUserItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockSelectedUser();
            }
        });

//        sortSentMessagesItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sortSentMessages();
//            }
//        });
//
//        sortReceivedMessagesItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sortReceivedMessages();
//            }
//        });

        invisibleItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeVisibility();
                invisibleItem.setText(isVisible ? "Become Invisible" : "Become Visible");
            }
        });

        popupMenu.add(messageStatisticsItem);
        popupMenu.add(blockUserItem);
        popupMenu.add(sortSentMessagesItem);
        popupMenu.add(sortReceivedMessagesItem);
        popupMenu.add(invisibleItem);

        popupMenu.show(component, 0, component.getHeight());
    }


//    public void displayMessageStatistics() {
//        String selectedUser = messageList.getSelectedValue();
//        if (selectedUser != null) {
//            Customer customer = new Customer(selectedUser);
//
//            try {
//                ArrayList<String> sentStatistics = customer.viewSentStatistics(1);
//                ArrayList<String> receivedStatistics = customer.viewReceivedStatistics(1);
//                ArrayList<String> commonWordsStatistics = customer.findCommonWords(1);
//
//                JPanel statisticsPanel = new JPanel(new GridLayout(6, 1));
//
//                JLabel sentLabel = new JLabel("Sent messages:");
//                JList<String> sentList = new JList<>(sentStatistics.toArray(new String[0]));
//                JScrollPane sentScrollPane = new JScrollPane(sentList);
//
//                JLabel receivedLabel = new JLabel("Received messages:");
//                JList<String> receivedList = new JList<>(receivedStatistics.toArray(new String[0]));
//                JScrollPane receivedScrollPane = new JScrollPane(receivedList);
//
//                JLabel commonWordsLabel = new JLabel("Common words:");
//                JList<String> commonWordsList = new JList<>(commonWordsStatistics.toArray(new String[0]));
//                JScrollPane commonWordsScrollPane = new JScrollPane(commonWordsList);
//
//                statisticsPanel.add(sentLabel);
//                statisticsPanel.add(sentScrollPane);
//                statisticsPanel.add(receivedLabel);
//                statisticsPanel.add(receivedScrollPane);
//                statisticsPanel.add(commonWordsLabel);
//                statisticsPanel.add(commonWordsScrollPane);
//
//                JOptionPane.showMessageDialog(null, statisticsPanel, "User Statistics", JOptionPane.PLAIN_MESSAGE);
//            } catch (IOException ex) {
//                System.out.println("Error while retrieving statistics.");
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Please select a user to view statistics.");
//        }
//    }


    private void blockSelectedUser() {
        String selectedUser = messageList.getSelectedValue();
        if (selectedUser != null) {
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to block " + selectedUser + "?",
                    "Confirm Block User",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                if (blockUser(selectedUser)) {
                    JOptionPane.showMessageDialog(null, selectedUser + " has been blocked.");

                    // Remove blocked user from conversation history
                    try {
                        removeFromConversationHistory(selectedUser);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error removing blocked user from conversation history: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error blocking user.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a user to block.");
        }
    }

    private boolean blockUser(String user) {
        // Add the user to the set of blocked users
        return blockedUsers.add(user);
    }

    private void removeFromConversationHistory(String blockedUserEmail) throws IOException {
        String directoryName = "conversation_data/";
        String blockedUserSearch = blockedUserEmail;
        File directory = new File(directoryName);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().contains(blockedUserSearch)) {
                    // Delete the file if it contains the blocked user's email
                    if (file.delete()) {
                        System.out.println("Blocked user removed from conversation history: " + blockedUserEmail);
                    } else {
                        System.out.println("Error removing blocked user from conversation history: Unable to delete file");
                    }
                }
            }
        }
    }

    private boolean searchUser(String name, boolean ifSeller) {
        if (isVisible) {
            // Search only if user is visible
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
        } else {
            // User is invisible, so always return false
            return false;
        }
    }

    private String[] getList(boolean ifSeller) {
        ArrayList<String> menuList = new ArrayList<>();
        String folderName = ifSeller ? "customer_data" : "seller_data";
        String filename = folderName + "/CustomersList.csv";
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

    private void createJList(String[] people) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String person : people) {
            model.addElement(person);
        }
        messageList.setModel(model);
    }
//    private void sortSentMessages() {
//        try {
//            List<String> sentStatistics = new Customer(ifSeller).viewSentStatistics(2);
//            createJList(sentStatistics.toArray(new String[0]));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void sortReceivedMessages() {
//        try {
//            List<String> receivedStatistics = new Customer(ifSeller).viewReceivedStatistics(2);
//            createJList(receivedStatistics.toArray(new String[0]));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void changeVisibility() {
        isVisible = !isVisible;
        String[] people = getList(ifSeller);
        createJList(people);
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
