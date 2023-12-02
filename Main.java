import Objects.Customer;
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


public class Main {

    // Adjust portNumber and hostName if needed
    public static final int portNumber = 4242;
    public static final String hostName = "localhost";
    public static void main(String[] args) {
        // Connects to server
        try {
            Socket socket = new Socket(hostName, portNumber);
            // TODO: Remove print statements
            System.out.println("Connected to server.");


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

          // ifSeller tells us if it is a seller or customer using the app
          // temporarily adding it as true, but will be changed accordingly
          // again, temporarily adding the customer and seller, but will be changed accordingly
          // will create a constructor for home panel such that it shows just menuPanel
          boolean ifSeller = true;
          String testCustomer = "testcustomer";
          String testSeller = "testseller";
          HomePanel homePanel = new HomePanel(testSeller, testCustomer, ifSeller);
          CustomerPanel customerPanel = new CustomerPanel();
          SellerPanel sellerPanel = new SellerPanel();
          MyAccountPanel myAccountPanel = new MyAccountPanel();

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

          // Listens for "Continue" button on Panels.LoginPanel
          loginPanel.getContinueButton().addActionListener(e -> {
              if (loginPanel.isLoggedIn()) {
                  mainframe.getContentPane().removeAll();
                  mainframe.setContentPane(homePanel);
                  mainframe.revalidate();
                  mainframe.repaint();
                  //print login success
                    System.out.println("Login Successful");
              }
          });

          // Listens for "Continue" button on Panels.CreateAccPanel
          createAccPanel.getContinueButton().addActionListener(e -> {
              //Adds user
              String userType = createAccPanel.getAccountType();
              if (userType.equals("Customer")) {  // Create customer account
                  Customer customer = new Customer(createAccPanel.getUsername(), createAccPanel.getPassword());
              } else {
                  // Pop up window for seller to add a store
                  String storeName = JOptionPane.showInputDialog(null, "Enter store name: ", "Create Store", JOptionPane.QUESTION_MESSAGE);
                  Seller seller = new Seller(createAccPanel.getUsername(), storeName, createAccPanel.getPassword());
              }

              // TODO: Create server requests to add user to database

              mainframe.setLayout(new BorderLayout());
              mainframe.setContentPane(homePanel);
              mainframe.revalidate();
              mainframe.repaint();
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
//
          // Makes the frame visible
          mainframe.setVisible(true);
      });

        } catch (IOException e) { // Throws error if unable to connect to server
            System.out.println("Unable to connect to server.");
            System.out.println("Make sure the server is running before starting the client.");
        }
    }

    // Adds the user's login credentials to the specified file
    public static boolean addLoginDetails(String email, String password, String filename) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filename, true))) {
            printWriter.println(email + "-" + password);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
