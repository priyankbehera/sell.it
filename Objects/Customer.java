package Objects;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * PJ-05 -- Sell.it
 * <p>
 *     contains credentials of customers and methods to get and set.
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