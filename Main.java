import Objects.Customer;
import Panels.*;
import Objects.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.net.*;

public class Main {

    // Adjust portNumber and hostName if needed
    private static final int portNumber = 4242;
    private static final String hostName = "localhost";

    public static void main(String[] args) {
        // Connects to server
        try {
            Socket socket = new Socket(hostName, portNumber);
            System.out.println("Connected to server.");

            // input & output for server
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), false);
            BufferedReader br = new BufferedReader((new InputStreamReader(socket.getInputStream())));

            // Creates the main frame
            SwingUtilities.invokeLater(() -> {
                JFrame mainframe = new JFrame("Send.it");
                mainframe.setLayout(new BorderLayout());

                // Sets  frame
                mainframe.setSize(1024, 768);
                mainframe.setLocationRelativeTo(null);
                mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Create an instance of panels
                WelcomePanel welcomePanel = new WelcomePanel();
                LoginPanel loginPanel = new LoginPanel();
                CreateAccPanel createAccPanel = new CreateAccPanel();

                // Set the Panels.WelcomePanel as the content pane of the main frame
                mainframe.setContentPane(welcomePanel);

                // Listens for "Login" button on Panels.WelcomePanel
                welcomePanel.getLoginButton().addActionListener(e -> {
                    mainframe.setContentPane(loginPanel); // Resets the content pane
                    mainframe.revalidate(); // Reorders components
                    mainframe.repaint(); // Repaints components
                });

                // Listens for "Create Account" button on Panels.WelcomePanel
                welcomePanel.getCreateAccButton().addActionListener(e -> {
                    // Creates account
                    mainframe.setContentPane(createAccPanel);
                    mainframe.revalidate();
                    mainframe.repaint();
                });

                // Listens for successful login
               loginPanel.getContinueButton().addActionListener(e -> {
                    System.out.println("Here");
                    boolean[] isLoggedIn;
                    String email = loginPanel.getEmail();
                    String password = String.valueOf(loginPanel.getPassword());
                    System.out.println("Email: " + email);
                    System.out.println("Password: " + password);

                    // Sends login credentials to server
                    String request = "login," + email + "," + password;
                    isLoggedIn = loginRequest(request, pw, br);
                    System.out.println("Is logged in: " + isLoggedIn[0]);
                    if (isLoggedIn[0]) {
                        boolean ifSeller = isLoggedIn[1];
                        HomePanel homePanel = new HomePanel(email, ifSeller, pw, br);
                        mainframe.setContentPane(homePanel);
                        mainframe.revalidate();
                        mainframe.repaint();
                    } else {
                        loginPanel.getSuccessMessage().setText("Incorrect email or password.");
                    }
                });

                // Listens for "Continue" button on Panels.CreateAccPanel
                createAccPanel.getContinueButton().addActionListener(e -> {
                    String userType = (String) createAccPanel.getAccountType().getSelectedItem();
                    int accountType = -1;
                    // Creates user accounts
                    if (userType.equals("Customer")) {  // Create customer account
                        Customer customer = new Customer(createAccPanel.getEmail(), createAccPanel.getPassword());
                        accountType = 0;
                    } else {
                        // Pop up window for seller to add a store
                        String storeName = JOptionPane.showInputDialog(null, "Enter store name: ", "Create Store", JOptionPane.QUESTION_MESSAGE);
                        Seller seller = new Seller(createAccPanel.getEmail(), storeName, createAccPanel.getPassword());
                        accountType = 1;
                    }
                    // send request to server
                    String requestString = "createAccount," + accountType + "," + createAccPanel.getEmail() + "," + createAccPanel.getPassword();
                    boolean success = createAccountRequest(requestString, pw, br);
                    System.out.println("Account created: " + success);

                    // display results
                    if (success) {
                        // send back to login
                        JOptionPane.showMessageDialog(null, "Account created, please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        mainframe.setContentPane(loginPanel);
                    } else {
                        createAccPanel.getSuccessMessage().setText("Account already exists. Try a different email.");
                        mainframe.setContentPane(createAccPanel);
                    }
                    mainframe.revalidate();
                    mainframe.repaint();
                });
                mainframe.setVisible(true);
            });
        } catch (IOException e) { // Throws error if unable to connect to server
            System.out.println("Unable to connect to server.");
            System.out.println("Make sure the server is running before starting the client.");
        }
    }

    public static boolean[] loginRequest(String request, PrintWriter printWriter, BufferedReader br) {
        PrintWriter pw = printWriter;
        // send request to server
        pw.println(request);
        pw.flush();
        System.out.println("Request sent: " + request);

        // get response from server
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    break;
                }
            }
            String[] lineSplit = line.split(",");
            boolean[] booleanSplit = new boolean[lineSplit.length];
            for (int i = 0; i < lineSplit.length; i++) {
                booleanSplit[i] = Boolean.parseBoolean(lineSplit[i]);
            }
            System.out.println("Receive response: " + line);
            System.out.println(line);

            return booleanSplit;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        boolean[] empty = {false};
        return empty;
    }

    public static boolean createAccountRequest(String request, PrintWriter printWriter, BufferedReader br) {
        PrintWriter pw = printWriter;

        // send request to server
        pw.println(request);
        pw.flush();
        System.out.println("Request sent: " + request);

        // get response from server
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    break;
                }
            }
            System.out.println("Received response: " + line);
            return Boolean.parseBoolean(line);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
