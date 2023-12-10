
/**
 * PJ-05 -- Sell.it
 * <p>
 * This class represents a message with attributes such as sender,
 * receiver, message content, date sent, and time sent.
 * It also provides getters and setters for these attributes.
 *
 * @author Matthew Allen, 26047-L25
 * @version November 11, 2023
 */

public class Message {
    private String sender;
    private String receiver;
    private String message;
    private String dateSent; //Date that message was sent. Format MMDDYYYY (eg. January 1, 2023 = 01012023).
    private String timeSent; //Time that message was sent. Format: HHMM (eg. 1:30pm = 1330).

    // Constructor
    public Message(String sender, String receiver, String message, String dateSent, String timeSent) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.dateSent = dateSent;
        this.timeSent = timeSent;
    }

    @Override
    public String toString() {
        return sender + "," + receiver + "," + message + "," + dateSent + "," + timeSent;
    }
}
