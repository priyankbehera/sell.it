package Panels;

import customer_data.Message;
import customer_data.User;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * PJ-04 -- Sell.it
 * <p>
 * Contains methods for messaging sellers, adding customers to the customer list,
 * retrieving the list of stores, and determining if an email belongs to a seller.
 * Manages customer related functions like interacting with sellers.
 *
 * @author Brayden Reimann, 26047-L25
 * @version November 11, 2023
 */

public class Customer extends User {

    public Customer(String email, boolean hasConversationLog, String ConversationHistoryFile) {
        super(email, hasConversationLog, ConversationHistoryFile);
    }

    public Customer(String email) {
        super(email);
    }

    public void messageSeller(String seller, String customer, String message) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = dtf.format(now);
        String date = currentDateTime.substring(0,10);
        String time = currentDateTime.substring(11);
        Message message1 = new Message(seller, customer, message, date, time);

        try (PrintWriter pw = new PrintWriter(new FileWriter("conversation_data/" +  seller + "_" + customer
                + "_Messages.csv", true))) {
            pw.println(message1.toString());
        } catch (IOException e) {
            System.out.println("That does not work!");
        }
    }

    /*
    The following method takes in a customer as a parameter and adds it
    to the file "CustomersList.txt".
     */
    public static boolean addCustomers(Customer customer) {
        boolean result = false;
        try (PrintWriter pw = new PrintWriter(new FileWriter("customer_data/" +
                "CustomersList.csv", true))) {

            pw.println(customer.getEmail() + "," + customer.hasConversationHistory() + "," +
                    customer.getConversationHistoryFile());
            result = true;
        } catch (IOException e) {
            System.out.println("Error: Unable to add customer.");
        }
        return result;
    }

    public ArrayList<String> getStoresList() {
        ArrayList<String> storesList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("seller_data/SellersList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String [] attributes = line.split(",");
                storesList.add(attributes[1]);
            }
        } catch (IOException e) {
            System.out.println("Unable to retrieve list of stores.");
        }
        return storesList;
    }

    public String getStoreSeller(String store) {
        String seller = "";
        try (BufferedReader br = new BufferedReader(new FileReader("seller_data/SellersList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineArray = line.split(",");

                if (lineArray[1].contains(store)) {
                    return lineArray[0];
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to find associated seller.");
        }
        return seller;
    }

    public boolean isSeller(String email) {

        try (BufferedReader br = new BufferedReader(new FileReader("seller_data" + File.separator +
                "SellersList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to verify seller.");
        }
        return false;
    }

    public ArrayList<String> readCustomerConversation(String filename) throws IOException {
        ArrayList<String> messages = new ArrayList<>();
        try {
            // Opens file for input
            FileInputStream fis = new FileInputStream("conversation_data/" + filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line;
            // Reads next line, beginning of conversation
            // Reads rest of file and stores data in ArrayList of messages
            while ((line = br.readLine()) != null) {
                String[] messageData = line.split(",");
                // Formats message and adds it to ArrayList

                if (messageData[1].equals(this.getEmail())) {
                    messageData[1] = "You";
                }

                String message = messageData[1] + " - " +  messageData[2] + " | sent at " + messageData[4] + " on " +
                        messageData[3];
                messages.add(message);
            }
            br.close();

        } catch (IOException e) {  // Prints error message and throws exception
            System.out.println("Error! Unable to read conversation file: " + filename);
            throw new IOException();
        }
        return messages;
    }

    // Displays sent statistics based on the sort parameter
    // 1 - ascending
    // 2 - descending
    public ArrayList<String> viewSentStatistics(int sort) throws IOException {
        ArrayList<String> sentMessages = new ArrayList<>();

        if (sort == 1) { // Ascending
            ArrayList<String> conversations = getConversationHistory();
            // Extracts data from each conversation
            for (String conversation : conversations) {
                // Gathers store from each conversation
                String[] stores = conversation.split("_");
                String store = stores[0];  // Adds store name to start of line

                ArrayList<String> contents = readCustomerConversation(conversation);
                int sent = 0;
                for (String content : contents) {
                    sent = 0;
                    if (content.contains("You")) {
                        sent++;
                    }
                }
                sentMessages.add(store + ": " + sent + " messages sent");
            }

            // Sort ascending
            for (int i = 0; i < sentMessages.size(); i++) {
                for (int j = i + 1; j < sentMessages.size(); j++) {
                    if (sentMessages.get(i).compareTo(sentMessages.get(j)) > 0) {
                        String temp = sentMessages.get(i);
                        sentMessages.set(i, sentMessages.get(j));
                        sentMessages.set(j, temp);
                    }
                }
            }


        } else {  // Descending order
            ArrayList<String> conversations = getConversationHistory();
            // Extracts data from each conversation
            for (String conversation : conversations) {
                // Gathers store from each conversation
                String[] stores = conversation.split("_");
                String store = stores[0];  // Adds store name to start of line

                ArrayList<String> contents = readCustomerConversation(conversation);
                int sent = 0;
                for (String content : contents) {
                    sent = 0;
                    if (content.contains("You")) {
                        sent++;
                    }
                }
                sentMessages.add(store + ": " + sent + " messages sent");
            }

            // Sort descending
            for (int i = 0; i < sentMessages.size(); i++) {
                for (int j = i + 1; j < sentMessages.size(); j++) {
                    if (sentMessages.get(i).compareTo(sentMessages.get(j)) < 0) {
                        String temp = sentMessages.get(i);
                        sentMessages.set(i, sentMessages.get(j));
                        sentMessages.set(j, temp);
                    }
                }
            }
        }
        return sentMessages;
    }

    public ArrayList<String> viewReceivedStatistics(int sort) throws IOException {
        ArrayList<String> receivedMessages = new ArrayList<>();

        if (sort == 1) { // Ascending
            ArrayList<String> conversations = getConversationHistory();
            // Extracts data from each conversation
            for (String conversation : conversations) {
                // Gathers store from each conversation
                String[] stores = conversation.split("_");
                String store = stores[0];  // Adds store name to start of line

                ArrayList<String> contents = readCustomerConversation(conversation);
                int received = 0;
                for (String content : contents) {
                    received = 0;
                    if (!content.contains("You")) {
                        received++;
                    }
                }
                receivedMessages.add(store + ": " + received + " messages received");
            }

            // Sort ascending
            for (int i = 0; i < receivedMessages.size(); i++) {
                for (int j = i + 1; j < receivedMessages.size(); j++) {
                    if (receivedMessages.get(i).compareTo(receivedMessages.get(j)) > 0) {
                        String temp = receivedMessages.get(i);
                        receivedMessages.set(i, receivedMessages.get(j));
                        receivedMessages.set(j, temp);
                    }
                }
            }


        } else {  // Descending order

            ArrayList<String> conversations = getConversationHistory();
            // Extracts data from each conversation
            for (String conversation : conversations) {
                // Gathers store from each conversation
                String[] stores = conversation.split("_");
                String store = stores[0];  // Adds store name to start of line

                ArrayList<String> contents = readCustomerConversation(conversation);
                int received = 0;
                for (String content : contents) {
                    received = 0;
                    if (!content.contains("You")) {
                        received++;
                    }
                }
                receivedMessages.add(store + ": " + received + " messages received");
            }

            // Sort ascending
            for (int i = 0; i < receivedMessages.size(); i++) {
                for (int j = i + 1; j < receivedMessages.size(); j++) {
                    if (receivedMessages.get(i).compareTo(receivedMessages.get(j)) < 0) {
                        String temp = receivedMessages.get(i);
                        receivedMessages.set(i, receivedMessages.get(j));
                        receivedMessages.set(j, temp);
                    }
                }
            }
        }

        return receivedMessages;
    }


}
