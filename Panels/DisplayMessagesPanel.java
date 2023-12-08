package Panels;

import Objects.Customer;
import Objects.Seller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
public class DisplayMessagesPanel extends JPanel {
    private JTextArea conversationArea;
    private JTextField inputField;
    private JButton moreButton;
    private JButton exportButton;
    private JButton importButton;
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

        JPanel displayMessages = new JPanel();
        displayMessages.setSize(300,400);
        setLayout(new BorderLayout());


        moreButton = new JButton("\u22EE");
        moreButton.setPreferredSize(new Dimension(30, 20));

        exportButton = new JButton("Import");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportFileAction();
            }
        });

        // Add import button
        importButton = new JButton("Export");
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFileAction();
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
        conversationArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(conversationArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create the input field for user messages
        inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        //sends request to server to get conversation history

        // Add action listener to the button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageRequest( seller, customer, ifSeller, br, pw);
            }
        });

        //more button action listener
        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMessage(moreButton, seller, customer, br, pw);
            }
        });


        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        inputPanel.add(exportButton, BorderLayout.WEST);
        inputPanel.add(importButton, BorderLayout.WEST);

        inputPanel.add(moreButton, BorderLayout.NORTH);

        add(inputPanel, BorderLayout.SOUTH);
    }

    // pop-up window for editing/deleting messages
    //create more button
    private void editMessage(JButton moreButton, String seller, String customer, BufferedReader br, PrintWriter pw) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem edit = new JMenuItem("Edit Message");
        JMenuItem delete = new JMenuItem("Delete Message");
        menu.add(edit);
        menu.add(delete);
        menu.show(moreButton, moreButton.getWidth()/2, moreButton.getHeight()/2);

        edit.addActionListener(new ActionListener() {
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
                String selectedMessage = messageList.getSelectedValue();
                if (selectedMessage != null) {
                    String selectedMessage1 = selectedMessage.split(": ")[1];
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
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an option",
                            "Error", JOptionPane.ERROR_MESSAGE);
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

        // delete message action listener
        delete.addActionListener(new ActionListener() {
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
                System.out.println("Selected message: " + selectedMessage);
                String requestString = "deleteMessage," + seller + "," + customer + "," + selectedMessage;

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
        });
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
            String response = line;
            boolean responseBoolean = Boolean.parseBoolean(response);
            System.out.println("Message sent: " + response);
            // clear input text
            inputField.setText("");

            if (!responseBoolean) {
                // Joption pane error
                JOptionPane.showMessageDialog(null, "Please enter a message.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        }
    private void exportFileAction() {

        JFileChooser fileChooser = new JFileChooser();
        int userChoice = fileChooser.showSaveDialog(this);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            String requestString = "exportFile," + filePath;
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

    private void importFileAction() {

        JFileChooser fileChooser = new JFileChooser();
        int userChoice = fileChooser.showOpenDialog(this);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            String requestString = "importFile," + filePath;
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
                    JOptionPane.showMessageDialog(null, "Messages imported successfully.");
                    conversationArea.setText("");
                    requestConversationHistory(seller, customer, ifSeller, br, pw);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error importing messages.");
            }
        }
    }
}


