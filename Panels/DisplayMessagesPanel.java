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

    // constructor for display messages panel
    public DisplayMessagesPanel(String seller, String customer, boolean ifSeller, PrintWriter pw, BufferedReader br) {
        JPanel displayMessages = new JPanel();
        displayMessages.setSize(300,400);
        setLayout(new BorderLayout());

        // creates the refresh button
        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(10, 10, 80, 25);
        add(refreshButton, BorderLayout.NORTH);

        // refresh button action listener
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conversationArea.setText("");
                requestConversationHistory(seller, customer, ifSeller, br, pw);

                //TODO: Reset menupanel

            }
        });

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
    }
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

 /*   public static void main(String[] args) {
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
    }*/
}