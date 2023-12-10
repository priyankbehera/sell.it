package Objects;

import java.io.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
/**
 * PJ-05 -- Sell.it
 * <p>
 *     contains credentials of sellers and methods to get and set.
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

    public Seller(String email, String store, String password) {
        super(email, password);
        this.store = store;
    }

    // Getters
    public String getEmail() {
        return super.getEmail();
    }


    @Override
    public String toString() {
        return getEmail() + "," + getStores() + "," + hasConversationHistory() + "," + getConversationHistoryFile();
    }

}