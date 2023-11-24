import java.io.*;
import java.util.ArrayList;

/**
 * PJ-04 -- Sell.it
 * <p>
 * Makes a generic user with the attributes email, conversation history, and a list of messages.
 * Handles user login, manages the user's conversation history, and log's the users conversations.
 *
 * @author Brayden Reimann and Shreya Gupta, 26047-L25
 * @version November 11, 2023
 */

public class User {
    private final String email;
    private boolean hasConversationHistory;
    private String ConversationHistoryFile;
    private ArrayList<Message> messages;
    private ArrayList<String> blockedUsers;
    private boolean invisible;


    public User(String email, boolean hasConversationLog, String ConversationHistoryFile) {
        this.email = email;
        this.hasConversationHistory = hasConversationLog;
        this.ConversationHistoryFile = ConversationHistoryFile;
        this.messages = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
        this.invisible = false;
    }

    public User(String email) {
        this.email = email;
        this.hasConversationHistory = false;
        this.ConversationHistoryFile = null;
        this.messages = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
        this.invisible = false;
    }


    /*
    To log a user's conversation, pass in the file and email as parameters
    Then, the LogConversation method will print the name of the logFile to the
    user's src.Conversation History in append mode. If the conversation is successfully logged,
    hasConversationHistory will be set to true.
     */
    public void LogConversation(String logFile) throws IOException {
        ConversationHistoryFile = this.email + "_ConversationHistory.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(ConversationHistoryFile, true))) {
            pw.println(logFile);
        } catch (IOException e) {
            throw new IOException();
        }
        hasConversationHistory = true;
    }

    /*
    The following method takes in a user object as a parameter, and then searches
    for the users' email in the list of customers and list of sellers.
    The method returns "src.Customer" if the user is a customer and "src.Seller" if the
    user is a seller. Otherwise, it returns null.
     */
    public String isCustomerOrSeller(User user) {
        try (BufferedReader br = new BufferedReader(new FileReader("customer_data/CustomersList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // @TODO Determine if this is the most efficient method
                if (line.contains(user.getEmail())) {
                    return "Customer";
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to determine account type.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader("seller_data/SellersList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(user.getEmail())) {
                    return "Seller";
                }
            }

        } catch (IOException e) {
            System.out.println("Error: Unable to determine account type.");
        }
        return null;
    }

    // method that ensures that email is valid
    // if there is a FileNotFoundException, then checkValidity will return false

    public String getEmail() {
        return this.email;
    }

    public boolean hasConversationHistory() {
        return hasConversationHistory;
    }

    public String getConversationHistoryFile() {
        return ConversationHistoryFile;
    }

    private void readConversation(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming messages are stored in CSV format: message_contents,sender,recipient,date,time
                String[] messageData = line.split(",");
                Message message = new Message(messageData[1], messageData[2], messageData[0], messageData[3], messageData[4]);
                messages.add(message);
            }
        } catch (IOException e) {
            System.out.println("Error! Unable to read conversation file: " + filename);
            throw new IOException();
        }
    }

    public void viewReceivedMessages() {
        // Check if the user has a valid conversation history file
        if (!hasConversationHistory()) {
            System.out.println("Error: No conversation history available for " + getEmail());
            return;
        }

        // Construct the file path for the conversation history
        String conversationHistoryFilePath = "conversation_data" + File.separator + getConversationHistoryFile();

        try {
            // Read the conversation history from the file
            readConversation(conversationHistoryFilePath);

            // Display received messages
            for (Message message : getMessages()) {
                // Check if the user is the recipient of the message
                if (message.getReceiver().equals(getEmail())) {
                    String senderName = message.getSender().equals(getEmail()) ? "You" : message.getSender();
                    System.out.println(senderName + ": " + message.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error! Unable to view received messages: " + e.getMessage());
        }
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", hasConversationHistory=" + hasConversationHistory +
                ", ConversationHistoryFile='" + ConversationHistoryFile + '\'' +
                '}';
    }
    // Returns arrayList of string containing conversation history
    public ArrayList<String> getConversationHistory() throws IOException {
        ArrayList<String> conversationHistory = new ArrayList<>();

        // Reads through all conversations and selects the ones that contain the user's email
        String directoryName = "conversation_data/";
        String search = this.email;
        File directory = new File(directoryName);
        File[] files = directory.listFiles();

        if (files == null) {
            throw new IOException();
        }

        // Adds files that have the user's email to the conversationHistory arrayList
        for (File file: files) {
            if (file.getName().contains(search)) {

                conversationHistory.add(file.getName());
            }
        }

        return conversationHistory;
    }


    //below are the methods that implement the blocking user selection

    public boolean isBlocked(String userEmail) {
        return blockedUsers.contains(userEmail);
    }

    public void becomeInvisible() {
        invisible = true;
    }

    public void becomeVisible() {
        invisible = false;
    }

    public boolean isInvisible() {
        return invisible;
    }

    private void removeFromConversationHistory(String blockedUserEmail) throws IOException {
        String directoryName = "conversation_data/";
        String blockedUserSearch = blockedUserEmail;
        File directory = new File(directoryName);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().contains(blockedUserSearch)) {
                    // Delete the file if it contains the blocked user's email
                    if (file.delete()) {
                        System.out.println("Blocked user removed from conversation history: " + blockedUserEmail);
                    } else {
                        System.out.println("Error removing blocked user from conversation history: Unable to delete file");
                    }
                }
            }
        }
    }
    public void blockUser(String userEmail) {
        if (blockedUsers != null && !blockedUsers.contains(userEmail)) {
            blockedUsers.add(userEmail);

            // Remove blocked user from conversation history
            try {
                removeFromConversationHistory(userEmail);
            } catch (IOException e) {
                System.out.println("Error removing blocked user from conversation history: " + e.getMessage());
            }
        }
    }

    public void makeInvisibleToUser(User otherUser) {
        if (!isBlocked(otherUser.getEmail())) {
            otherUser.becomeInvisible();
            System.out.println("You are now invisible to " + otherUser.getEmail());
        } else {
            System.out.println("Error: Unable to make user invisible. User is blocked.");
        }
    }


    // Main method for testing
    public static void main(String[] args) throws IOException {
        User user1 = new User("beatrix");
        user1.LogConversation("testFile.txt");
        System.out.println(user1.hasConversationHistory);
        System.out.println(user1.getConversationHistoryFile());
        System.out.println(user1.getEmail());
    }

}