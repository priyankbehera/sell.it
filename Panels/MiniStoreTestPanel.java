package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MiniStoreTestPanel extends JPanel {
    public MiniStoreTestPanel(String storeName, String imagePath) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(380,430));
        Color beige = new Color(231, 217, 194);
        Color darkBlue = new Color(27, 50, 84);


        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(beige);
        centerPanel.setPreferredSize(new Dimension(380,360));
        CircularButton circularButton = new CircularButton(imagePath, 320);
        centerPanel.add(circularButton);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));
        bottomPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        bottomPanel.setBackground(beige);
        bottomPanel.setForeground(darkBlue);
        bottomPanel.setPreferredSize(new Dimension(380, 70));
        Font font = new Font("Smiley Sans", Font.PLAIN, 24);
        JLabel storeNameLabel = new JLabel(storeName);
        storeNameLabel.setFont(font);
        bottomPanel.add(storeNameLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainframe = new JFrame();
            mainframe.setSize(380, 430);
            mainframe.setLocationRelativeTo(null);
            mainframe.setResizable(false);
            mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            String imagePath = "Images/circular_image.png";
            MiniStoreTestPanel miniStoreTestPanel = new MiniStoreTestPanel( "Store Name", imagePath);
            mainframe.setContentPane(miniStoreTestPanel);
            mainframe.setVisible(true);
        });
    }
}
