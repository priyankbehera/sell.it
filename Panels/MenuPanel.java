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
    private final JTextField searchField;

    public MenuPanel( boolean ifSeller ) {
        setLayout(new FlowLayout());

        JLabel searchLabel = new JLabel("Enter Seller's Name:");
        add(searchLabel);

        searchField = new JTextField(20);
        add(searchField);

        JButton searchButton = new JButton("Search Seller");
        add(searchButton);

        JButton resetButton = new JButton("Reset");
        add(resetButton);

        String[] people = null;
        try {
            people = getList( ifSeller );
        } catch ( NullPointerException e ) {
            JOptionPane.showMessageDialog(null, "Error - Null");
        }
        // creating a JList
        JList<String> messageList = new JList<>(people);
        messageList.setFixedCellHeight(40);
        messageList.setFixedCellWidth(300);
        messageList.setVisibleRowCount(16);

        // Create a JScrollPane and add the JList to it
        JScrollPane scrollPane = new JScrollPane(messageList);

        // Add the JScrollPane to the frame
        add(scrollPane);

        //createJList( people );

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = searchField.getText();
                if ( searchUser( username, ifSeller ) ) {
                    scrollPane.setVisible(false);
                    String[] temp = {username};
                    createJList(temp);
                    revalidate();
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "No such user found!");
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setVisible(false);
                String[] people = getList(ifSeller);
                createJList(people);
                revalidate();
                repaint();
            }
        });
    }

    // takes the name to be searched and if it's a seller
    public boolean searchUser( String name, boolean ifSeller ) {
        ArrayList<String> list = new ArrayList<>();
        boolean isPresent = false;
        String folderName;
        if ( ifSeller ) {
            folderName = "customer_data";
            String filename = folderName + "/CustomersList.csv";
            try ( BufferedReader bfr = new BufferedReader(new FileReader( filename) ) ) {
                String line;
                while ( ( line = bfr.readLine()) != null ) {
                    list.add(line);
                }
                String[] usernames = new String[list.size()];
                // read the file to get the list of customers
                for ( int i = 0; i < list.size(); i++ ) {
                    String username = list.get(i).split(",")[0];
                    usernames[i] = username;
                }
                // testing if the username is present;
                for (String username : usernames) {
                    if (username.equals(name)) {
                        isPresent = true;
                        break;
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error - IOException");
                return isPresent;
            }
        } else {
            folderName = "seller_data";
            String filename = folderName + "/SellersList.csv";
            try ( BufferedReader bfr = new BufferedReader(new FileReader( filename) ) ) {
                String line;
                while ( ( line = bfr.readLine()) != null ) {
                    list.add(line);
                }
                String[] usernames = new String[list.size()];
                // read the file to get the list of sellers
                for ( int i = 0; i < list.size(); i++ ) {
                    String username = list.get(i).split(",")[0];
                    usernames[i] = username;
                }
                // testing if the username is present;
                for (String username : usernames) {
                    if (username.equals(name)) {
                        isPresent = true;
                        break;
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error - IOException");
                return isPresent;
            }
        }
        return isPresent;
    }

    // returns the list of usernames
    // if it can't get the list, then it returns null
    public String[] getList( boolean ifSeller ) {
        ArrayList<String> menuList = new ArrayList<>();
        if ( ifSeller ) {
            String folderName = "customer_data";
            String filename = folderName + "/" + "CustomersList.csv";
            try (BufferedReader bfr = new BufferedReader(
                    new FileReader( filename) ) ) {
                String line;
                while ( (line = bfr.readLine()) != null ) {
                    menuList.add(line);
                }
                String[] menuArray = new String[menuList.size()];
                for ( int i = 0; i < menuArray.length; i++ ) {
                    String customerName = menuList.get(i).split(",")[0];
                    menuArray[i] = customerName;
                }
                return menuArray;
            } catch (IOException e ) {
                JOptionPane.showMessageDialog(null, "error");
                return null;
            }
        } else {
            String folderName = "seller_data";
            String filename = folderName + "/" + "SellersList.csv";
            try (BufferedReader bfr = new BufferedReader(
                    new FileReader( filename) ) ) {
                String line;
                while ( (line = bfr.readLine()) != null ) {
                    menuList.add(line);
                }
                String[] menuArray = new String[menuList.size()];
                for ( int i = 0; i < menuArray.length; i++ ) {
                    String customerName = menuList.get(i).split(",")[0];
                    menuArray[i] = customerName;
                }
                return menuArray;
            } catch (IOException e ) {
                JOptionPane.showMessageDialog(null, "error");
                return null;
            }
        }
    }

    // create JList
    public void createJList( String[] people ) {
        //Create a JList with the array of people
        JList<String> messageList = new JList<>(people);
        messageList.setFixedCellHeight(40);
        messageList.setFixedCellWidth(300);
        messageList.setVisibleRowCount(16);

        // Create a JScrollPane and add the JList to it
        JScrollPane scrollPane = new JScrollPane(messageList);
        // Add the JScrollPane to the frame
        add(scrollPane);
   }

    // this tests the menuPanel
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame("MenuPanel Test");
            testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            MenuPanel menuPanel = new MenuPanel(true);

            testFrame.getContentPane().add(menuPanel, BorderLayout.CENTER);

            testFrame.setSize(341, 768);
            testFrame.setLocationRelativeTo(null);
            testFrame.setVisible(true);
        });
    }
}