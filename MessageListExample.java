import javax.swing.*;
import java.awt.*;

public class MessageListExample extends JFrame {

    public MessageListExample() {
        setTitle("Message List Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        // Sample data for the list (twenty names)
        String[] people = new String[20];
        for (int i = 0; i < 20; i++) {
            people[i] = "Person " + (i + 1);
        }

        // Create a JList with the array of people
        JList<String> messageList = new JList<>(people);

        // Create a JScrollPane and add the JList to it
        JScrollPane scrollPane = new JScrollPane(messageList);

        // Add the JScrollPane to the frame
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MessageListExample example = new MessageListExample();
            example.setVisible(true);
        });
    }
}
