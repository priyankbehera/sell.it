import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class Main {
    public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
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

              // Set the WelcomePanel as the content pane of the main frame
              mainframe.setContentPane(welcomePanel);

              // Listens for press of login button
              welcomePanel.getLoginButton().addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                      mainframe.setContentPane(loginPanel); // Resets the content pane
                      mainframe.revalidate(); // Reorders components
                      mainframe.repaint(); // Repaints components
                  }
              });

              // Listens create account button on WelcomePanel
              welcomePanel.getCreateAccButton().addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                      mainframe.setContentPane(createAccPanel);
                      mainframe.revalidate();
                      mainframe.repaint();
                  }
              });

              // Listens for continue button on LoginPanel
              loginPanel.getContinueButton().addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                      mainframe.setContentPane(homePanel);
                      mainframe.revalidate();
                      mainframe.repaint();
                  }
              });

              // Listens for continue button on CreateAccPanel
              createAccPanel.getContinueButton().addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                      mainframe.setContentPane(homePanel);
                      mainframe.revalidate();
                      mainframe.repaint();
                  }
              });

              // Makes the frame visible
              mainframe.setVisible(true);
          }
      });
    }

}
