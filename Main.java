import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
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
          MyAccountPanel myAccountPanel = new MyAccountPanel();

          // Set the WelcomePanel as the content pane of the main frame
          mainframe.setContentPane(welcomePanel);

          // Listens "Login" button on WelcomePanel
          welcomePanel.getLoginButton().addActionListener(e -> {
              mainframe.setContentPane(loginPanel); // Resets the content pane
              mainframe.revalidate(); // Reorders components
              mainframe.repaint(); // Repaints components
          });

          // Listens for "Create Account" button on WelcomePanel
          welcomePanel.getCreateAccButton().addActionListener(e -> {
              mainframe.setContentPane(createAccPanel);
              mainframe.revalidate();
              mainframe.repaint();
          });

          // Listens for "Continue" button on LoginPanel
          loginPanel.getContinueButton().addActionListener(e -> {
              mainframe.setContentPane(homePanel);
              mainframe.revalidate();
              mainframe.repaint();
          });

          // Listens for "Continue" button on CreateAccPanel
          createAccPanel.getContinueButton().addActionListener(e -> {
              mainframe.setContentPane(homePanel);
              mainframe.revalidate();
              mainframe.repaint();
          });

          // Listens for "My Account" button on HomePanel
          homePanel.getMyAccountButton().addActionListener(e -> {
              mainframe.setContentPane(myAccountPanel);
              mainframe.revalidate();
              mainframe.repaint();
          });

          // Makes the frame visible
          mainframe.setVisible(true);
      });
    }

}
