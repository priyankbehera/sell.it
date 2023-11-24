package Objects;

import java.io.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
/**
 * PJ-04 -- Sell.it
 * <p>
 * Contains methods for messaging customers, adding sellers to the sellers list,
 * retrieving the list of customers, and determining if an email belongs to a customer.
 * Manages seller related functions like interacting with customers.
 *
 * @author Brayden Reimann, 26047-L25
 * @version November 11, 2023
 */

public class Seller extends User {
    String store;

    public String getStores() {
        return store;
    }

    public Seller(String email, boolean hasConversationLog, String ConversationHistoryFile, String store) {
        super(email, hasConversationLog, ConversationHistoryFile);
        this.store = store;
    }

    public Seller(String email) {
        super(email);
    }

    public Seller(String email, String store) {
        super(email);
        this.store = store;
    }

    // Getters
    public String getEmail() {
        return super.getEmail();
    }

    /*
    The following method takes in a customer as a parameter and adds it
    to the file "CustomersList.txt".
     */
    public static boolean addSellersList(Seller seller) {
        if (seller.getEmail().equals("")) {  // Cannot be empty
            return false;
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter("seller_data/SellersList.csv", true))) {
            pw.println(seller);
            return true;
        } catch (IOException e) {
            System.out.println("Error: Unable to add seller.");
        }
        return false;
    }

    public ArrayList<String> getCustomersList() {
        ArrayList<String> customersList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("customer_data/CustomersList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String [] attributes = line.split(",");
                String customer = attributes[0];
                customersList.add(customer);
            }
        } catch (IOException e) {
            System.out.println("Unable to retrieve list of stores.");
        }
        return customersList;
    }

    public boolean isCustomer(String email) {
        try (BufferedReader br = new BufferedReader(new FileReader("customer_data/CustomersList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to verify customer.");
        }
        return false;

    }

    /*
    The following method takes a customer, seller, and a message as parameters.
    It then creates a new message object, using date and time as retrieved by LocalDateTime.
    After creating the method, a String representation of the message is added to the file
    "customer.getemail() + '_Messages'", which contains all the customers' messages.
     */
    public void messageCustomer(String seller, String customer, String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = dtf.format(now);
        String date = currentDateTime.substring(0, 10);
        String time = currentDateTime.substring(11);
        Message message1 = new Message(customer, seller, message, date, time);

        String fileName = "conversation_data/" + seller + "_" + customer + "_Messages.csv";
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            pw.println(message1);
        } catch (IOException e) {
            System.out.println("That does not work!");
        }
    }

    public ArrayList<String> readSellerConversation(String filename) throws IOException {
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

    @Override
    public String toString() {
        return getEmail() + "," + getStores() + "," + hasConversationHistory() + "," + getConversationHistoryFile();
    }

    public ArrayList<String> viewSentStatistics(int sort) throws IOException {
        ArrayList<String> sentMessages = new ArrayList<>();

        if (sort == 1) { // Ascending
            ArrayList<String> conversations = getConversationHistory();
            // Extracts data from each conversation
            for (String conversation : conversations) {
                // Gathers store from each conversation
                String[] stores = conversation.split("_");
                String customer = stores[1];  // Adds store name to start of line

                ArrayList<String> contents = readSellerConversation(conversation);
                int sent = 0;
                for (String content : contents) {
                    sent = 0;
                    if (content.contains("You")) {
                        sent++;
                    }
                }
                sentMessages.add(customer + ": " + sent + " messages sent");
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
                String store = stores[1];  // Adds store name to start of line

                ArrayList<String> contents = readSellerConversation(conversation);
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

    // Finds most common words
    public ArrayList<String> findCommonWords(int order) throws IOException {
        ArrayList<String> commmonWords = new ArrayList<>();
        ArrayList<String> conversations = getConversationHistory();
        ArrayList<String> words = new ArrayList<>();

        // Extracts data from each conversation
        Map<String, Integer> wordCount = new HashMap<>();

        // Reads through all files
        for (String conversation : conversations) {
            ArrayList<String> contents = readSellerConversation(conversation);
            for (String content : contents) {
                String[] message = content.split(" ");
                for (String word : message) {
                    if (wordCount.containsKey(word)) {
                        wordCount.put(word, wordCount.get(word) + 1);
                    } else {
                        wordCount.put(word, 1);
                    }
                }
            }
        }
        // Sorts words by frequency, finds to 5 most common words
        if (order == 1) {  // Ascending
            for (int i = 0; i < 5; i++) {
                int max = 0;
                String maxWord = "";
                for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                    if (entry.getValue() > max) {
                        max = entry.getValue();
                        maxWord = entry.getKey();
                    }
                }
                commmonWords.add(maxWord + ": " + max + " occurrences");
                wordCount.remove(maxWord);
            }
        } else {  // Descending
            for (int i = 0; i < 5; i++) {
                int min = 1000000;
                String minWord = "";
                for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                    if (entry.getValue() < min) {
                        min = entry.getValue();
                        minWord = entry.getKey();
                    }
                }
                commmonWords.add(minWord + ": " + min + " occurrences");
                wordCount.remove(minWord);
            }
        }

        return null;
    }

}