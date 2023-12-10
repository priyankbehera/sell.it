package Objects;

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

    private String password;


    public User(String email, boolean hasConversationLog, String ConversationHistoryFile) {
        this.email = email;
        this.hasConversationHistory = hasConversationLog;
        this.ConversationHistoryFile = ConversationHistoryFile;
        this.messages = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
        this.invisible = false;
    }

    public User(String email, String password) {
        this.email = email;
        this.hasConversationHistory = false;
        this.ConversationHistoryFile = null;
        this.messages = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
        this.invisible = false;
        this.password = password;
    }
    public User(String email) {
        this.email = email;
        this.hasConversationHistory = false;
        this.ConversationHistoryFile = null;
        this.messages = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
        this.invisible = false;
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

    @Override
    public String toString() {
        return "Objects.User{" +
                "email='" + email + '\'' +
                ", hasConversationHistory=" + hasConversationHistory +
                ", ConversationHistoryFile='" + ConversationHistoryFile + '\'' +
                '}';
    }

}