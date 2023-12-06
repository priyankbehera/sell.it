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

    // constructor for display messages panel
    public DisplayMessagesPanel(String seller, String customer, boolean ifSeller, PrintWriter pw, BufferedReader br) {
        JPanel displayMessages = new JPanel();
        displayMessages.setSize(300,400);
        setLayout(new BorderLayout());

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

        // Add action listener to the button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageRequest( seller, customer, ifSeller, br, pw);
            }
        });

        // Create a panel to hold the input field and send button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add the input panel to the bottom of the frame
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
                JOptionPane.showMessageDialog(null, "Error sending message.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        }
    }