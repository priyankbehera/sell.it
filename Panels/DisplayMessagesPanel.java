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

    // button for refresh
    private JButton refreshButton;
    
    public JButton getRefreshButton() {
        return this.refreshButton;
    }

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
                sendMessage( seller, customer, ifSeller, br, pw);
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
    private void sendMessage( String seller, String customer, boolean ifSeller, BufferedReader br, PrintWriter pw ) {
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
            requestConversationHistory(seller, customer, ifSeller, br, pw);
            inputField.setText(""); // Clear the input field
        }
    }

    public void simulateButtonClick(final JButton button, int delayMillis) {
        // Use a Timer to trigger the button click after the specified delay
        Timer timer = new Timer(delayMillis, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Trigger the button's ActionListener
                for (ActionListener listener : button.getActionListeners()) {
                    listener.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });

        // Start the timer
        timer.setRepeats(false); // Set to false to trigger only once
        timer.start();
    }
}