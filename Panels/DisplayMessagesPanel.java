package Panels;

import Objects.Customer;
import Objects.Seller;

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
                sendMessage( seller, customer, ifSeller );
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
    private void addConversationHistory(String seller, String customer, boolean ifSeller) {
        ArrayList<String> list = new ArrayList<>();
        String folderName = "conversation_data";
        String filename = folderName + "/" + seller + "_" + customer + "_Messages.csv";

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

            for (int i = 0; i < senderList.length; i++) {
                String str;
                if (ifSeller) {
                    if (senderList[i].equals(seller)) {
                        str = "You: " + messages[i];

                    } else {
                        str = customer + ": " + messages[i];
                    }
                } else {
                    if (senderList[i].equals(seller)) {
                        str = seller + ": " + messages[i];
                    } else {
                        str = "You: " + messages[i];
                    }
                }

                // Adds time stamp above the message
                String timeStampStr = timeStamp[i] + " " + dateStamp[i] + "\n";
                str = timeStampStr + str + "\n\n";

                conversationArea.append(str);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error in Program, please refresh",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void sendMessage( String seller, String customer, boolean ifSeller ) {
        String message = inputField.getText();
        if (!message.trim().isEmpty()) {
            conversationArea.setText("");
            // if the user is a seller, then the seller will send the message
            // implementing the seller message
            if ( !ifSeller ) {
                Seller existingSeller = new Seller(seller);
                existingSeller.messageCustomer(seller, customer, message);
            } else {
                Customer existingCustomer = new Customer(customer);
                existingCustomer.messageSeller(seller, customer, message);
            }
            addConversationHistory( seller, customer, ifSeller );
            inputField.setText(""); // Clear the input field
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Display Messages Panel");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new DisplayMessagesPanel("testSeller", "testCustomer", false));
                frame.setSize(450, 500);
                frame.setVisible(true);
            }
        });
    }
}