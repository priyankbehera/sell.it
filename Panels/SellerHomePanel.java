package Panels;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SellerHomePanel extends JPanel {
    private PrintWriter pw;
    private BufferedReader br;
    private JList<String> messageList;
    private JTextField searchField;
    private JButton moreButton;
    private boolean searchButtonClicked = false;
    private boolean seeAllButtonClicked = false;
    private boolean isVisible = true;
    private String currentUser;
    public SellerHomePanel(boolean ifSeller, String currentUser, PrintWriter pw, BufferedReader br) {
        Color color = new Color(27,50,84);
        JPanel panel = new JPanel();
        add(panel);
    }
    private String[] getUsers(boolean ifSeller) {
        String request = "getUsers," + ifSeller;

        try {
            pw.println(request);
            pw.flush();

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    break;
                }
            }
            String[] users = line.split(",");
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
