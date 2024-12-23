import Panels.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
            // input & output for server
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), false);
            BufferedReader br = new BufferedReader((new InputStreamReader(socket.getInputStream())));

            // Creates the main frame
            SwingUtilities.invokeLater(() -> {
                JFrame mainframe = new JFrame("Sell.it");
                mainframe.setLayout(new BorderLayout());


                // Sets frame
                mainframe.setSize(1024, 768);
                mainframe.setLocationRelativeTo(null);
                mainframe.setResizable(false);
                mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // color blue
                Color color = new Color(27,50,84);

                // Create an instance of panels
                WelcomePanel welcomePanel = new WelcomePanel(color);
                CreateAccPanel createAccPanel = new CreateAccPanel(color);

                // Set the Panels.WelcomePanel as the content pane of the main frame
                mainframe.setContentPane(welcomePanel);

                // Listens for "Continue" button on Panels.WelcomePanel
                welcomePanel.getContinueButton().addActionListener(e -> {
                    boolean[] isLoggedIn;
                    String email = welcomePanel.getEmail();
                    String password = String.valueOf(welcomePanel.getPassword());

                    if (!email.isEmpty() && !password.isEmpty()) {
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
                            welcomePanel.getSuccessMessage().setText("Invalid credentials. Please try again.");
                            welcomePanel.getSuccessMessage().setForeground(Color.WHITE);
                        }
                    } else {
                        welcomePanel.getSuccessMessage().setText("Please enter an email and password.");
                        welcomePanel.getSuccessMessage().setForeground(Color.WHITE);
                    }
                });

                // Listens for "Create Account" button on Panels.WelcomePanel
                welcomePanel.getCreateAccButton().addActionListener(e -> {
                    mainframe.setContentPane(createAccPanel);
                    mainframe.revalidate();
                });

                // Listens for "Continue" button on Panels.CreateAccPanel
                createAccPanel.getContinueButton().addActionListener(e -> {
                    String email = createAccPanel.getEmail();
                    String password = createAccPanel.getPassword();

                    if (!email.isEmpty() && !password.isEmpty()) {
                        String userType = createAccPanel.getAccountType();
                        int accountType = -1;
                        // Creates user accounts
                        if (userType.equals("Customer")) {  // Create customer account
                            accountType = 0;
                        } else if (userType.equals("Seller")) {
                            accountType = 1;
                        }

                        // send request to server
                        String requestString = "createAccount," + accountType + "," + createAccPanel.getEmail()
                                + "," + createAccPanel.getPassword();
                        boolean success = createAccountRequest(requestString, pw, br);
                        System.out.println("Account created: " + success);
                        if ( success && accountType == 1 ) {
                            //seller  Pop up window for seller to add a store
                            try {
                                String store = JOptionPane.showInputDialog(null,
                                        "Enter store name: ", "Continue", JOptionPane.QUESTION_MESSAGE);

                                String description = JOptionPane.showInputDialog(null,
                                        "Enter store description: ", "Create Store", JOptionPane.QUESTION_MESSAGE);
                                if (store.equals("") || description.equals("")) {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid name");
                                } else {
                                    String request = "addStore," + email + "," + store + "," + description;
                                    pw.println(request);
                                    pw.flush();

                                    String line;
                                    while ((line = br.readLine()) != null) {
                                        if (!line.isEmpty()) {
                                            break;
                                        }
                                    }
                                    boolean response = Boolean.parseBoolean(line);
                                    if (response) {
                                        JOptionPane.showMessageDialog(null, "Store added successfully.");
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Error adding store.");
                                    }

                                }
                            } catch(Exception d) {
                                //ignore
                            }
                        }
                        // display results
                        if (success) {
                            // send back to log in
                            JOptionPane.showMessageDialog(null, "Account created, please log in.",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                            createAccPanel.resetPanel(); // Resets the Create Account panel
                            mainframe.setContentPane(welcomePanel);
                        } else {
                            createAccPanel.getSuccessMessage().setText("Account already exists. Please log in.");
                            JOptionPane.showMessageDialog(null,
                                    "Account already exists, Please log in or try a different email.",
                                    "Error", JOptionPane.INFORMATION_MESSAGE);
                        }
                        mainframe.revalidate();
                        mainframe.repaint();
                    } else {
                        createAccPanel.getSuccessMessage().setText("Please enter an email and password to continue.");
                    }
                });

                // Listens for "Return to Login" button on Panels.CreateAccPanel
                createAccPanel.getReturnLoginButton().addActionListener(e -> {
                    mainframe.setContentPane(welcomePanel);
                    mainframe.revalidate();
                    mainframe.repaint();
                });

                // Makes the frame visible
                mainframe.setVisible(true);
            });
        } catch (IOException e) { // Throws error if unable to connect to server
            JOptionPane.showMessageDialog(null, "Unable to connect to server. Make sure server"
                            + " is running before launching the client.",
                    "Error", JOptionPane.ERROR_MESSAGE);
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
