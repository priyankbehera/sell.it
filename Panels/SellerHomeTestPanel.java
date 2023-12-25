package Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SellerHomeTestPanel extends JPanel {
    public SellerHomeTestPanel() {
        Color color1 = new Color(54, 91, 131);
        Color color2 = new Color(146, 164, 184);
        Color color3 = new Color(231, 217, 194);
        Color lightBeige = new Color(253, 239, 224);
        Color lightBeige2 = new Color(252, 236, 222);


        Color blue = new Color(27, 50, 84);

        setLayout(null);


        // menuPanel code
        JPanel menuPanel = new JPanel();
        Dimension dimension = new Dimension(80,80);
        int size = 80;
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 10);
        menuPanel.setLayout(flowLayout);
        menuPanel.setBackground(color1);
        int width = 210;

        menuPanel.setPreferredSize(new Dimension(width, 768));
        menuPanel.setBounds(0, 0, width, 768);

        // Adding a larger rigid area before the Inbox button
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel yourAccountPanel = new JPanel();
        yourAccountPanel.setLayout(new BorderLayout());
        yourAccountPanel.setBackground(color1);
        JPanel yourAccountPanelCircular = new JPanel();
        yourAccountPanelCircular.setBackground(color1);
        yourAccountPanelCircular.setPreferredSize(new Dimension(150,80));
        CircularButton circularYourAccount = new CircularButton("Images/yourAccount.png", size);
        yourAccountPanelCircular.add(circularYourAccount);
        yourAccountPanel.add(yourAccountPanelCircular, BorderLayout.CENTER);

        JButton yourAccountButton = new JButton("Your Account");
        yourAccountButton.setOpaque(false);
        yourAccountButton.setBorderPainted(false);
        yourAccountButton.setContentAreaFilled(false);
        yourAccountButton.setFocusPainted(false);
        yourAccountButton.setBackground(color1);
        yourAccountButton.setForeground(Color.white);
        yourAccountPanel.add(yourAccountButton, BorderLayout.SOUTH);
        menuPanel.add(yourAccountPanel);

        // Adding Inbox Panel
        JPanel inboxPanel = new JPanel();
        inboxPanel.setLayout(new BorderLayout());
        inboxPanel.setBackground(color1);
        CircularButton circularInboxButton = new CircularButton("Images/inbox.png", size);
        inboxPanel.add(circularInboxButton, BorderLayout.CENTER);

        JButton inboxButton = new JButton("Inbox");
        inboxButton.setOpaque(false);
        inboxButton.setContentAreaFilled(false);
        inboxButton.setBorderPainted(false);
        inboxButton.setFocusPainted(false);
        inboxButton.setForeground(Color.white);
        inboxPanel.setBackground(color1);
        inboxPanel.add(inboxButton, BorderLayout.SOUTH);
        menuPanel.add(inboxPanel);

        // Adding smaller rigid area after Inbox button
//        menuPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Adjust the gap as needed

        // notifications panel
        JPanel notificationsPanel = new JPanel();
        notificationsPanel.setLayout(new BorderLayout());
        notificationsPanel.setBackground(color1);
        CircularButton circularNotificationsButton = new CircularButton("Images/notifications.png", size);
        JPanel tempNotifications = new JPanel();
        tempNotifications.setBackground(color1);
        tempNotifications.setPreferredSize(new Dimension(150,80));
        tempNotifications.add(circularNotificationsButton);
        notificationsPanel.add(tempNotifications, BorderLayout.CENTER);

        JButton notificationsButton = new JButton("Notifications");
        notificationsButton.setOpaque(false);
        notificationsButton.setBorderPainted(false);
        notificationsButton.setContentAreaFilled(false);
        notificationsButton.setFocusPainted(false);
        notificationsButton.setBackground(color1);
        notificationsButton.setForeground(Color.white);
        notificationsPanel.add(notificationsButton, BorderLayout.SOUTH);
        menuPanel.add(notificationsPanel);

//        menuPanel.add(Box.createRigidArea(new Dimension(0,5)));

        // edit store panel
        JPanel editStorePanel = new JPanel();
        editStorePanel.setLayout(new BorderLayout());
        editStorePanel.setBackground(color1);
        CircularButton circularEditStoreButton = new CircularButton("Images/editStores.png", size);
        JPanel tempEditStore = new JPanel();
        tempEditStore.setPreferredSize(dimension);
        tempEditStore.add(circularEditStoreButton);
        tempEditStore.setBackground(color1);
        editStorePanel.add(tempEditStore, BorderLayout.CENTER);

        JButton editStoreButton = new JButton("Edit Store");
        editStoreButton.setOpaque(false);
        editStoreButton.setBorderPainted(false);
        editStoreButton.setContentAreaFilled(false);
        editStoreButton.setFocusPainted(false);
        editStoreButton.setBackground(color1);
        editStoreButton.setForeground(Color.white);
        editStorePanel.add(editStoreButton, BorderLayout.SOUTH);
        menuPanel.add(editStorePanel);

//        menuPanel.add(Box.createRigidArea(new Dimension(0,5)));

        // add store panel
        JPanel addStorePanel = new JPanel();
        addStorePanel.setLayout(new BorderLayout());
        addStorePanel.setBackground(color1);
        CircularButton circularAddStoreButton = new CircularButton("Images/add.png", size);
        JPanel tempAddStore = new JPanel();
        tempAddStore.setPreferredSize(new Dimension(130,80));
        tempAddStore.add(circularAddStoreButton);
        tempAddStore.setBackground(color1);
        addStorePanel.add(tempAddStore, BorderLayout.CENTER);

        JButton addStoreButton = new JButton("Add Store");
        addStoreButton.setOpaque(false);
        addStoreButton.setBorderPainted(false);
        addStoreButton.setContentAreaFilled(false);
        addStoreButton.setFocusPainted(false);
        addStoreButton.setForeground(Color.white);
        addStoreButton.setBackground(color1);
        addStorePanel.add(addStoreButton, BorderLayout.SOUTH);
        menuPanel.add(addStorePanel);

        // social panel
        JPanel socialPanel = new JPanel();
        socialPanel.setLayout(new BorderLayout());
        socialPanel.setBackground(color1);
        CircularButton circularSocialButton = new CircularButton("Images/Social.png", size);
        JPanel tempSocial = new JPanel();
        tempSocial.setPreferredSize(dimension);
        tempSocial.add(circularSocialButton);
        tempSocial.setBackground(color1);
        socialPanel.add(tempSocial, BorderLayout.CENTER);

        JButton socialButton = new JButton("Social");
        socialButton.setOpaque(false);
        socialButton.setBorderPainted(false);
        socialButton.setContentAreaFilled(false);
        socialButton.setFocusPainted(false);
        socialButton.setBackground(color1);
        socialButton.setForeground(Color.white);
        socialPanel.add(socialButton, BorderLayout.SOUTH);
        // temp
        circularSocialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You clicked social buttion!",
                        "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuPanel.add(socialPanel);

        // adding the menuPanel
        add(menuPanel);

        JPanel labelPanel = new JPanel();
        labelPanel.setSize(683, 154);

        JPanel scrollPanel = new JPanel();

        scrollPanel.setBackground(Color.white);
        scrollPanel.setSize(new Dimension(819, 584));

        // adding the scroll panel code
        GridLayout gridLayout = new GridLayout(0, 2);
        gridLayout.setHgap(20);
        gridLayout.setVgap(20);
        scrollPanel.setLayout(gridLayout);

        Border border = BorderFactory.createLineBorder(Color.white, 20);
        scrollPanel.setBorder(border);
        scrollPanel.setBackground(Color.white);
        // Add some components to the panel
        for (int i = 1; i <= 10; i++) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(200, 350)); // Set preferred size
            panel.setBackground(lightBeige);
            Font font = new Font("Smiley Sans", Font.PLAIN, 24);
            JLabel label = new JLabel("Panel" + i);
            label.setFont(font);
            label.setForeground(blue);
            panel.add(label);
            scrollPanel.add(panel);
        }

        ImageIcon imageIcon = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("Images/labelpanel.png"));
            imageIcon = new ImageIcon(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel imageLabel = new JLabel(imageIcon);
        labelPanel.add(imageLabel);
        labelPanel.setBounds(210, -5, 821, 159);
        add(labelPanel);

        // code for the scroll panel
        JScrollPane scrollPane = new JScrollPane(scrollPanel);

        // Set the border of the viewport to null
        scrollPane.setViewportBorder(null);

        // Set the border of the scroll pane to null
        scrollPane.setBorder(null);

        JLabel yourStores = new JLabel("Your Stores");
        yourStores.setForeground(Color.WHITE);
        yourStores.setPreferredSize(new Dimension(0, 50)); // Adjust the height as needed
        yourStores.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("Smiley Sans", Font.ITALIC, 25);
        yourStores.setFont(font);
        yourStores.setOpaque(true);
        yourStores.setBackground(color2);

        scrollPane.setColumnHeaderView(yourStores);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(210, 154, 819, 584);
        add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainframe = new JFrame();
            mainframe.setSize(1024, 768);
            mainframe.setLocationRelativeTo(null);
            mainframe.setResizable(false);
            mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            SellerHomeTestPanel testPanel = new SellerHomeTestPanel();
            mainframe.setContentPane(testPanel);
            mainframe.setVisible(true);
        });
    }
}
