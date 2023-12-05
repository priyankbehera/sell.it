import Objects.Customer;
import Objects.User;
import Panels.*;
import Objects.*;
import customer_data.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Main {

    // Adjust portNumber and hostName if needed
    private static final int portNumber = 4242;
   private static final String hostName = "localhost";


    public static void main(String[] args) {
        // Connects to server
        try  {
            // TODO: Remove print statements
            Socket socket = new Socket(hostName, portNumber);
            System.out.println("Connected to server.");

            // input & output for server
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), false);
            BufferedReader br = new BufferedReader((new InputStreamReader(socket.getInputStream())));

            // Creates the main frame

            SwingUtilities.invokeLater(() -> {
                JFrame mainframe = new JFrame("Send.it");
                Container content = mainframe.getContentPane();
                mainframe.setLayout(new BorderLayout());

                // Sets  frame
                mainframe.setSize(1024, 768);
                mainframe.setLocationRelativeTo(null);
                mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Create an instance of panels
                WelcomePanel welcomePanel = new WelcomePanel();
                LoginPanel loginPanel = new LoginPanel();
                CreateAccPanel createAccPanel = new CreateAccPanel();
                MyAccountPanel myAccountPanel = new MyAccountPanel();

                // Creates an instance of the home panel with default parameters
                boolean ifSeller = true;
                String testCustomer = "testcustomer";
                String testSeller = "testseller";

                HomePanel homePanel = new HomePanel(testSeller, ifSeller);

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
                    boolean isLoggedIn = false;
                    String email = loginPanel.getEmailText().getText();
                    String password = String.valueOf(loginPanel.getPasswordField().getPassword());
                    System.out.println("Email: " + email);
                    System.out.println("Password: " + password);

                    // Sends login credentials to server
                    String request = "login," + "0," + email + "," + password;
                    isLoggedIn = loginRequest(request, pw, br);
                    System.out.println("Is logged in: " + isLoggedIn);

                    if (isLoggedIn) {
                        mainframe.setContentPane(homePanel);
                        mainframe.revalidate();
                        mainframe.repaint();
                    } else {
                        loginPanel.getSuccessMessage().setText("Incorrect email or password.");
                    }
                });

                // Listens for "Continue" button on Panels.CreateAccPanel
                createAccPanel.getContinueButton().addActionListener(e -> {
                    //Adds user
                    String userType = createAccPanel.getAccountType();
                    String accountType = "";
                    if (userType.equals("Customer")) {  // Create customer account
                        Customer customer = new Customer(createAccPanel.getUsername(), createAccPanel.getPassword());
                        accountType = "0";
                    } else {
                        // Pop up window for seller to add a store
                        String storeName = JOptionPane.showInputDialog(null, "Enter store name: ", "Create Store", JOptionPane.QUESTION_MESSAGE);
                        Seller seller = new Seller(createAccPanel.getUsername(), storeName, createAccPanel.getPassword());
                        accountType = "1";
                    }

                    // send request to server
                    String requestString = "createAccount," + accountType + "," + createAccPanel.getUsername() + "," + createAccPanel.getPassword();
                    boolean success = createAccountRequest(requestString, pw, br);
                    System.out.println("Account created: " + success);

                    // display results
                    if (success) {
                        // send back to login
                        mainframe.setContentPane(loginPanel);
                        mainframe.revalidate();
                        mainframe.repaint();

                    }
                    else {
                        createAccPanel.getSuccessMessage().setText("Account already exists. Try a different email.");
                        mainframe.setContentPane(createAccPanel);
                        mainframe.revalidate();
                        mainframe.repaint();

                    }

                });

                // Listens for "Send Objects.Message" button on Panels.HomePanel
//          homePanel.getSendMessageButton().addActionListener(new ActionListener() {
//              @Override
//              public void actionPerformed(ActionEvent e) {
//                  mainframe.setContentPane(customerPanel);
//                  mainframe.revalidate();
//                  mainframe.repaint();
//              }
//          });

//          // Listens for "My Account" button on Panels.HomePanel
//          homePanel.getMyAccountButton().addActionListener(e -> {
//              mainframe.setContentPane(myAccountPanel);
//              mainframe.revalidate();
//              mainframe.repaint();
//          });
                // Makes the frame visible
                mainframe.setVisible(true);
            });

        } catch (
                IOException e) { // Throws error if unable to connect to server
            System.out.println("Unable to connect to server.");
            System.out.println("Make sure the server is running before starting the client.");
        }

    }

    public static boolean loginRequest(String request, PrintWriter printWriter, BufferedReader br) {
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
            System.out.println("Receive response: " + line);
            System.out.println(line);

            return Boolean.parseBoolean(line);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
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
