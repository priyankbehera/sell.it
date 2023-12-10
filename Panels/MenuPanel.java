package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MenuPanel extends JPanel {
    private JList<String> messageList;
    private final JTextField searchField;
    private final JButton moreButton;
    private boolean searchButtonClicked = false;
    private boolean seeAllButtonClicked = false;
    private final Set<String> blockedUsers = new HashSet<>();
    private boolean isVisible = true;
    /*
        private final String blockedUsersFile;
        private final String blockedByUsersFile;
        private ArrayList<String> blockedUsersList;
    */
    private String currentUser;
    // private final String invisibleUsersFile;
    private ArrayList<String> invisibleUsersList;

    public JList getMessageList() {
        return messageList;
    }

    //global printwriter
    private PrintWriter pw;
    private BufferedReader br;

    public MenuPanel(boolean ifSeller, String currentUser, PrintWriter pw, BufferedReader br) {
        setLayout(new BorderLayout());
        this.pw = pw;
        this.br = br;
        this.currentUser = currentUser;

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
        for (int i = 0; people != null && i < people.length; i++) {
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
                    for (int i = 0; temp != null && i < temp.length; i++) {
                        listModel.addElement(temp[i]);
                    }
                    messageList.setModel(listModel);
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
                if (isVisible && searchUser(username, ifSeller, pw, br)) {
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
                showMenuPopup(moreButton, pw, br);
            }
        });
    }

    private void showMenuPopup(Component component, PrintWriter pw, BufferedReader br) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem censorKeywords = new JMenuItem("Censor keywords");
        popupMenu.add(censorKeywords);

        JMenuItem blockUserItem = new JMenuItem("Block User");
        popupMenu.add(blockUserItem);

        JMenuItem invisibleItem = new JMenuItem(isVisible ? "Become Invisible" : "Become Visible");
        popupMenu.add(invisibleItem);

        JMenuItem deleteAccount = new JMenuItem("Delete Account");
        popupMenu.add(deleteAccount);

        JMenuItem editAccount = new JMenuItem("Edit Account");
        popupMenu.add(editAccount);

        censorKeywords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean hasKeyword = false;
                while (!hasKeyword) {
                    String keyword = JOptionPane.showInputDialog(null, "Please enter a keyword:", "Censor a Keyword", JOptionPane.WARNING_MESSAGE);
                    if (keyword.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: Your keyword cannot be empty.");
                    } else {
                        hasKeyword = setCensoredKeyword(keyword, currentUser, br, pw);
                        JOptionPane.showMessageDialog(null, "Keyword " + '"' + keyword + '"' + " successfully censored.", "Censor a Keyword", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        editAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to edit your account?\n" +
                                "This will only have an affect on future messages. Current messages will be lost.\nNOTE: If only password is changed, all data will remain.",
                        "Confirm Delete Account",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    // prompt user for new username
                    String newUsername = JOptionPane.showInputDialog("Enter new username:");
                    if (newUsername == null) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid username.");
                        return;
                    }
                    if (newUsername.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid username.");
                        return;
                    }
                    if (newUsername.contains(",")) {
                        JOptionPane.showMessageDialog(null, "Username cannot contain commas.");
                        return;
                    }
                    String newPassword = JOptionPane.showInputDialog("Enter new password:");
                    if (newPassword == null) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid password.");
                        return;
                    }
                    if (newPassword.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid password.");
                        return;
                    }
                    if (newPassword.contains(",")) {
                        JOptionPane.showMessageDialog(null, "Password cannot contain commas.");
                        return;
                    }
                    String request = "editAccount," + currentUser + "," + newUsername + "," + newPassword;
                    try {
                        pw.println(request);
                        pw.flush();

                        String line;
                        while ((line = br.readLine()) != null) {
                            if (!line.isEmpty()) {
                                break;
                            }
                        }
                        boolean success = Boolean.parseBoolean(line);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Account edited successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error editing account.");
                        }

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Error editing account.");
                    }
                }
            }

        });
        blockUserItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockSelectedUser(pw, br);
            }
        });

        deleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete your account?",
                        "Confirm Delete Account",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    String request = "deleteAccount," + currentUser;
                    try {
                        pw.println(request);
                        pw.flush();

                        String line;
                        while ((line = br.readLine()) != null) {
                            if (!line.isEmpty()) {
                                break;
                            }
                        }
                        boolean success = Boolean.parseBoolean(line);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Account deleted successfully.");
                            System.exit(0);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error deleting account.");
                        }

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Error deleting account.");
                    }
                }
            }
        });

        invisibleItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // @TODO Add functionality
            }
        });

        // Make the popup menu visible
        popupMenu.show(component, 0, component.getHeight());
    }

    private void blockSelectedUser(PrintWriter pw, BufferedReader br) {
        ArrayList<String> blockedUsers = requestBlockedUsers(currentUser, pw, br);
        String selectedUser = messageList.getSelectedValue();
        if (selectedUser != null) {
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to block " + selectedUser + "?",
                    "Confirm Block User",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                if (requestBlock(currentUser, selectedUser, br, pw)) {
                    JOptionPane.showMessageDialog(null, selectedUser + " has been blocked. Please click 'See All' to refresh the list.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error blocking user.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a user to block.");
        }
    }

    private boolean setCensoredKeyword(String keyword, String user, BufferedReader br, PrintWriter pw) {
        String request = "setKeyword," + user + "," + keyword;
        boolean success = false;
        try {
            pw.println(request);
            pw.flush();

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    break;
                }
            }
            success = Boolean.parseBoolean(line);

        } catch (IOException e) {
            return false;
        }
        return success;
    }

    private boolean requestBlock(String blocker, String toBlock, BufferedReader br, PrintWriter pw) {
        String request = "blockUser," + blocker + "," + toBlock;
        boolean success = false;
        try {
            pw.println(request);
            pw.flush();

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    break;
                }
            }
            success = Boolean.parseBoolean(line);

        } catch (IOException e) {
            return false;
        }
        return success;
    }

    private ArrayList<String> requestBlockedUsers(String user, PrintWriter pw, BufferedReader br) {
        String request = "getBlockedUsers," + user;
        ArrayList<String> blockedUsers = new ArrayList<>();
        try {
            pw.println(request);
            pw.flush();

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    break;
                }
            }
            String[] blockedUsersArray = line.split(",");
            for (String blockedUser : blockedUsersArray) {
                blockedUsers.add(blockedUser);
            }

        } catch (IOException e) {
            return null;
        }
        return blockedUsers;
    }

    private boolean searchUser(String name, boolean ifSeller, PrintWriter pw, BufferedReader br) {
        boolean isPresent = false;
        String request = "searchUser," + name + "," + ifSeller;

        pw.println(request);
        pw.flush();

        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    break;
                }
            }
            isPresent = Boolean.parseBoolean(line);
        } catch (IOException e) {
            return false;
        }
        return isPresent;
    }

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


            // removes blocked users
            ArrayList<String> blockedUsers = requestBlockedUsers(currentUser, pw, br);
            for (int i = 0; i < users.length; i++) {
                if (blockedUsers.contains(users[i])) {
                    users[i] = null;
                }
            }
            // remove null users from array
            ArrayList<String> tempUsers = new ArrayList<>();
            for (String user : users) {
                if (user != null) {
                    tempUsers.add(user);
                }
            }
            users = new String[tempUsers.size()];
            for (int i = 0; i < users.length; i++) {
                users[i] = tempUsers.get(i);
            }

            return users;

        } catch (IOException e) {
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

}
