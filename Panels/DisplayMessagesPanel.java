package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayMessagesPanel extends JPanel {
    private JTextArea conversationArea;
    private JTextField inputField;

    // constructor for display messages panel
    public DisplayMessagesPanel( String seller, String customer, boolean ifSeller ) {
        JPanel displayMessages = new JPanel();
        displayMessages.setSize(300,400);
        setLayout(new BorderLayout());

        // create conversation history area
        conversationArea = new JTextArea();
        conversationArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(conversationArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create the input field for user messages
        inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        addConversationHistory( seller, customer, ifSeller );
        // Add action listener to the button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Create a panel to hold the input field and send button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add the input panel to the bottom of the frame
        add(inputPanel, BorderLayout.SOUTH);

        // Set the frame to be visible
        setVisible(true);
    }

    // this method tries to add the conversation history to the panel
    // the customer and seller are parameters that can be inputted
    // boolean ifSeller identifies if the seller or the customer
    // if ifSeller == true, the user is seller
    // if ifSeller == false, then the user is customer
    private void addConversationHistory(String seller, String customer, boolean ifSeller) {
        ArrayList<String> list = new ArrayList<>();
        String filename = seller + "_" + customer + "_Messages.csv";
        try (BufferedReader bfr = new BufferedReader(new FileReader( filename ))) {
            String line;
            while ( ( line = bfr.readLine()) != null) {
                list.add(line);
            }
            String[] messages = new String[ list.size() ];
            String[] senderList = new String[ list.size() ];
            String[] dateStamp = new String[ list.size() ];
            String[] timeStamp = new String[ list.size() ];
            for ( int i = 0; i < messages.length; i++ ) {
                String[] messageArray = list.get(i).split(",");
                messages[i] = messageArray[2];
                senderList[i] = messageArray[0];
                dateStamp[i] = messageArray[3];
                timeStamp[i] = messageArray[4];
            }
            for ( int i = 0; i < senderList.length; i++ ) {
                String str = "You: " + messages[i] + "\t\t\t" +
                        timeStamp[i] + " " + dateStamp[i];
                if ( ifSeller ) {
                    if ( senderList[i].equals(seller) ) {
                        conversationArea.append(str);
                    } else {
                        conversationArea.append( customer + messages[i] + "\t\t\t" +
                                timeStamp[i] + " " + dateStamp[i]);
                    }
                } else {
                    if ( senderList[i].equals(seller) ) {
                        conversationArea.append( seller + messages[i] + "\t\t\t" +
                                timeStamp[i] + " " + dateStamp[i]);
                    } else {
                        conversationArea.append(str);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error in Program, please refresh",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void sendMessage() {
        String message = inputField.getText();
        if (!message.trim().isEmpty()) {
            conversationArea.append("You: " + message + "\n");
            inputField.setText(""); // Clear the input field
        }
    }
}
