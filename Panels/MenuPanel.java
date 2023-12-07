package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPanel extends JPanel {
    private JList<String> messageList;
    private final JTextField searchField;
    private final JButton moreButton;
    private boolean searchButtonClicked = false;
    private boolean seeAllButtonClicked = false;
    private final java.util.Set<String> blockedUsers = new java.util.HashSet<>();
    private boolean isVisible = true;

    public JList getMessageList() {
        return messageList;
    }

    public MenuPanel(boolean ifSeller) {
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
        for (int i = 0; i < people.length; i++) {
            listModel.addElement(people[i]);
        }
        messageList = new JList<>(listModel);
        messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        messageList.setFixedCellHeight(40);
        messageList.setFixedCellWidth(300);
        messageList.setVisibleRowCount(16);

        JScrollPane scrollPane = new JScrollPane(messageList);
        add(scrollPane, BorderLayout.CENTER);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!searchButtonClicked || seeAllButtonClicked) {
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
                    searchButtonClicked = true;
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
        JMenuItem blockUserItem = new JMenuItem("Block User");
        JMenuItem invisibleItem = new JMenuItem(isVisible ? "Become Invisible" : "Become Visible");

        blockUserItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockSelectedUser();
            }
        });

        invisibleItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeVisibility();
                invisibleItem.setText(isVisible ? "Become Invisible" : "Become Visible");
            }
        });

        popupMenu.add(blockUserItem);
        popupMenu.add(invisibleItem);

        popupMenu.show(component, 0, component.getHeight());
    }

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
                    // Additional logic if needed
                } else {
                    JOptionPane.showMessageDialog(null, "Error blocking user.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a user to block.");
        }
    }

    private boolean blockUser(String user) {
        return blockedUsers.add(user);
    }

    private boolean searchUser(String name, boolean ifSeller) {
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

    private String[] getList(boolean ifSeller) {
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

    private void createJList(String[] people) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String person : people) {
            model.addElement(person);
        }
        messageList.setModel(model);
    }

    private void changeVisibility() {
        isVisible = !isVisible;
        String[] people = getList(true);
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
