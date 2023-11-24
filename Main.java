import Panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
          JFrame mainframe = new JFrame("Send.it");
          Container content = mainframe.getContentPane();
          content.setLayout(new BorderLayout());

          // Sets  frame
          mainframe.setSize(600, 400);
          mainframe.setLocationRelativeTo(null);
          mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

          // Create an instance of panels
          WelcomePanel welcomePanel = new WelcomePanel();
          LoginPanel loginPanel = new LoginPanel();
          CreateAccPanel createAccPanel = new CreateAccPanel();
          HomePanel homePanel = new HomePanel();
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
              mainframe.setContentPane(createAccPanel);
              mainframe.revalidate();
              mainframe.repaint();
          });

          // Listens for "Continue" button on Panels.LoginPanel
          loginPanel.getContinueButton().addActionListener(e -> {
              mainframe.setContentPane(homePanel);
              mainframe.revalidate();
              mainframe.repaint();
          });

          // Listens for "Continue" button on Panels.CreateAccPanel
          createAccPanel.getContinueButton().addActionListener(e -> {
              mainframe.setContentPane(homePanel);
              mainframe.revalidate();
              mainframe.repaint();
          });

          // Listens for "Send Objects.Message" button on Panels.HomePanel
          homePanel.getSendMessageButton().addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  mainframe.setContentPane(customerPanel);
                  mainframe.revalidate();
                  mainframe.repaint();
              }
          });

          // Listens for "My Account" button on Panels.HomePanel
          homePanel.getMyAccountButton().addActionListener(e -> {
              mainframe.setContentPane(myAccountPanel);
              mainframe.revalidate();
              mainframe.repaint();
          });

          // Makes the frame visible
          mainframe.setVisible(true);
      });
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
