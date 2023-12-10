package Panels;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
public class DisplayMessagesPanel extends JPanel {
    private JTextArea conversationArea;
    private JTextField inputField;
    private JButton viewStoresButton;
    private JButton addStoreButton;
    private String seller;
    private String customer;
    private boolean ifSeller;
    private PrintWriter pw;
    private BufferedReader br;

    // constructor for display messages panel
    public DisplayMessagesPanel(String seller, String customer, boolean ifSeller, PrintWriter pw, BufferedReader br) {
        this.seller = seller;
        this.customer = customer;
        this.ifSeller = ifSeller;
        this.pw = pw;
        this.br = br;
        setLayout(new BorderLayout());

        JPanel displayMessages = new JPanel();
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton exportButton = new JButton("Export");
        JButton importButton = new JButton("Import");

        viewStoresButton = new JButton("View Stores");
        addStoreButton = new JButton("Add Store");
        if (ifSeller) {
            buttonPanel.add(addStoreButton);
        } else {
            buttonPanel.add(viewStoresButton);
        }

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(importButton);
        buttonPanel.add(exportButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(displayMessages, BorderLayout.CENTER);

        // view seller_data.stores action listener
        viewStoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String request = "getStores," + seller;
                    pw.println(request);
                    pw.flush();
                ArrayList<String> storeList = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.equals("end")) {
                        break;
                    } else {
                        storeList.add(line);
                    }
                }
                // turn arraylist into list of formatted strings
                String[] storeList1 = new String[storeList.size()];
                for (int i = 0; i < storeList.size(); i++) {
                    String[] store = storeList.get(i).split(",");
                    storeList1[i] =  store[0] + "\n" + ": " + store[1];

                }
                // display list of stores
                JList<String> storeJList = new JList<>(storeList1);
                JScrollPane scrollPane = new JScrollPane(storeJList);
                scrollPane.setPreferredSize(new Dimension(500, 400));
                // make the text bigger
                Font font = new Font("Monospace", Font.BOLD, 15);
                storeJList.setFont(font);

                JOptionPane.showMessageDialog(null, scrollPane, seller + "'s stores", JOptionPane.PLAIN_MESSAGE);

                } catch (Exception d) {
                    JOptionPane.showMessageDialog(null, "Seller has no stores, try selecting a different seller.");
                }

            }
        });

        // add store action listener
        addStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // popup for store name and description
                try {
                    String store = JOptionPane.showInputDialog(null, "Enter store name: ", "Continue", JOptionPane.QUESTION_MESSAGE);
                    String description = JOptionPane.showInputDialog(null, "Enter store description: ", "Create Store", JOptionPane.QUESTION_MESSAGE);
                    if (store.equals("") || description.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid name");
                    } else {
                        String request = "addStore," + seller + "," + store + "," + description;
                        pw.println(request);
                        pw.flush();

                        String line;
                        while ((line = br.readLine()) != null) {
                            if (!line.isEmpty()) {
                                break;
                            }
                        }
                        boolean response = Boolean.parseBoolean(line);
                        if (response) {
                            JOptionPane.showMessageDialog(null, "Store added successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error adding store.");
                        }

                    }
                } catch(Exception d) {
                    //ignore
                }
            }
        });
        // edit button action listener logic
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String allMessages = conversationArea.getText();
                JTextField editField = new JTextField(20);
                String[] messages = allMessages.split("\n");
                String[] messages1 = new String[messages.length/2];
                for ( int i = 0; i < messages1.length; i++ ) {
                    messages1[i] = messages[2*i + 1];
                }
                JList<String> messageList = new JList<>(messages1);
                JScrollPane scrollPane = new JScrollPane(messageList);
                scrollPane.setPreferredSize(new Dimension(300,400));
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(scrollPane, BorderLayout.CENTER);
                panel.add(editField, BorderLayout.SOUTH);

                JOptionPane.showMessageDialog(null, panel, "Edit Message", JOptionPane.PLAIN_MESSAGE);
                // gets selected message


                // if they click x or cancel, selectedMessage will be null

                String selectedMessage = messageList.getSelectedValue();
                if (selectedMessage == null) {
                    return;
                }

                    String selectedMessage1 = selectedMessage.split(": ")[1];
                    if ( !selectedMessage.split(": ")[0].equals("You") ) {
                        JOptionPane.showMessageDialog(null, "You can only edit your own message",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        System.out.println("Selected message: " + selectedMessage);
                        String messageToEdit = "";
                        if (!editField.getText().equals("")) {
                            messageToEdit = editField.getText();
                        }
                        String requestString = "editMessage," + seller + "," + customer + "," + selectedMessage1 + "," + messageToEdit;
                        // send request to server
                        pw.println(requestString);
                        pw.flush();
                        System.out.println("Request sent: " + requestString);
                    }
                // gets server response
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.isEmpty()) {
                            break;
                        }
                    }
                    String response = line;
                    boolean responseBoolean = Boolean.parseBoolean(response);

                    if (!responseBoolean) {
                        // JOption pane error
                        JOptionPane.showMessageDialog(null, "Error editing message.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Message edited.");
                    }
                } catch (IOException x) {
                    JOptionPane.showMessageDialog(null, "Error editing message.");
                }
            }
        });
        // delete button action listener logic
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete message button pressed.");
                // gets all messages from the conversationArea
                String allMessages = conversationArea.getText();
                String[] messages = allMessages.split("\n");
                String[] messages1 = new String[messages.length/2];
                for ( int i = 0; i < messages1.length; i++ ) {
                    messages1[i] = messages[2*i + 1];
                }
                JList<String> messageList = new JList<>(messages1);
                JScrollPane scrollPane = new JScrollPane(messageList);
                scrollPane.setPreferredSize(new Dimension(300, 400));
                JOptionPane.showMessageDialog(null, scrollPane, "Delete Message", JOptionPane.PLAIN_MESSAGE);
                // gets selected message
                String selectedMessage = messageList.getSelectedValue();
                if ( selectedMessage == null ) {
                    JOptionPane.showMessageDialog(null, "Please select a message", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    String selectedMessage1 = selectedMessage.split(": ")[0];
                    if ( !selectedMessage1.equals("You")) {
                        JOptionPane.showMessageDialog(null, "You can only delete your " +
                                "own message!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String selectedMessage2 = selectedMessage.split(": ")[1];
                        System.out.println("Selected message: " + selectedMessage2);
                        String requestString = "deleteMessage," + seller + "," + customer + "," + selectedMessage2;
                        // send request to server
                        pw.println(requestString);
                        pw.flush();
                        System.out.println("Request sent: " + requestString);
                        // gets server response
                        try {
                            String line;
                            while ((line = br.readLine()) != null) {
                                if (!line.isEmpty()) {
                                    break;
                                }
                            }
                            String response = line;
                            boolean responseBoolean = Boolean.parseBoolean(response);

                            if (!responseBoolean) {
                                // Joption pane error
                                JOptionPane.showMessageDialog(null, "Error deleting message.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Message deleted.");
                            }
                        } catch (IOException x) {
                            JOptionPane.showMessageDialog(null, "Error deleting message.");
                        }
                    }
                }
            }
        });

        // export button action listener
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportFileAction(seller, customer, br, pw);
            }
        });
        // import button action listener
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFileAction(seller, customer, ifSeller, br, pw);
            }
        });

        // refresh button action listener
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conversationArea.setText("");
                requestConversationHistory(seller, customer, ifSeller, br, pw);
            }
        });

        timer.setRepeats(true);
        timer.start();

        // create conversation history area
        conversationArea = new JTextArea();
        conversationArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(conversationArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create the input field for user messages
        inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        //sends request to server to get conversation history

        // Add KeyListener to the text field
        inputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // No code needed
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessageRequest(seller, customer, ifSeller, br, pw);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // No code needed
            }
        });

        // Add action listener to the button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageRequest( seller, customer, ifSeller, br, pw);
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);
    }

    // this method tries to add the conversation history to the panel
    // the customer and seller are parameters that can be inputted
    // boolean ifSeller identifies if the seller or the customer
    // if ifSeller == true, the user is seller
    // if ifSeller == false, then the user is customer

    private void requestConversationHistory(String seller, String customer, boolean ifSeller, BufferedReader br, PrintWriter pw) {
        // send request to server
        String requestString = "getConversationHistory," + seller + "," + customer + "," + ifSeller;
        pw.println(requestString);
        pw.flush();

        // get response from server
        ArrayList<String> messageList = new ArrayList<>();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.equals("!@#%#$!@#%^@#$")) {
                        break;
                    }
                    messageList.add(line);
                }

            }
            // switch ¶ back to commas
            for (int i = 0; i < messageList.size(); i++) {
                String s = messageList.get(i);
                for (int j = 0; j < s.length(); j++) {
                    if (s.charAt(j) == (char) 182) {
                        s = s.substring(0, j) + "," + s.substring(j + 1);
                    }
                }
                messageList.set(i, s);
            }
            for (String s : messageList) {
                conversationArea.append(s + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    private void sendMessageRequest(String seller, String customer, boolean ifSeller, BufferedReader br, PrintWriter pw) {


        // send request string to server
        String message = inputField.getText();
        if (message.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter a message.");
            return;
        }
        for (int i = 0; i < message.length(); i++) {
            // if comma, replace with ascii code 182 (¶)
            if (message.charAt(i) == ',') {
                message = message.substring(0, i) + (char) 182 + message.substring(i + 1);
            }
        }
        String request = "sendMessage," + seller + "," + customer + "," + ifSeller + "," + message;
        pw.println(request);
        pw.flush();
        System.out.println("Request sent: " + request);

        // get response from server
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    break;
                }
            }
            boolean responseBoolean = Boolean.parseBoolean(line);
            // clear input text
            inputField.setText("");

            if (!responseBoolean) {
                // Joption pane error
                JOptionPane.showMessageDialog(null, "You've been blocked by this user. Cannot send message.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "You've been blocked by this user. Cannot send message.");
        }
    }
    private void exportFileAction(String seller, String customer, BufferedReader br, PrintWriter pw) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
        fileChooser.setFileFilter(txtFilter);

        int userChoice = fileChooser.showSaveDialog(this);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            String requestString = "exportFile," + selectedFile + "," + seller + ","
                    + customer;
            pw.println(requestString);
            pw.flush();

            try {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.isEmpty()) {
                        break;
                    }
                }
                boolean responseBoolean = Boolean.parseBoolean(line);

                if (!responseBoolean) {
                    JOptionPane.showMessageDialog(null, "Error exporting messages.");
                } else {
                    JOptionPane.showMessageDialog(null, "Messages exported successfully.");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error exporting messages.");
            }
        }
    }

    private void importFileAction(String seller, String customer, boolean ifSeller, BufferedReader br, PrintWriter pw) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        fileChooser.setFileFilter(txtFilter);


        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getAbsolutePath().endsWith(".txt")) {
                String requestString = "importFile," + seller + "," + customer + "," + ifSeller + "," + selectedFile;
                pw.println(requestString);
                pw.flush();

                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.isEmpty()) {
                            break;
                        }
                    }
                    boolean responseBoolean = Boolean.parseBoolean(line);

                    if (!responseBoolean) {
                        JOptionPane.showMessageDialog(null, "Error importing messages.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Messages imported successfully");
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error importing messages.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a .txt file only", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


