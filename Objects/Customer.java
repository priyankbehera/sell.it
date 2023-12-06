package Objects;

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

    public Customer(String email, String password) {
        super(email, password);
    }

}