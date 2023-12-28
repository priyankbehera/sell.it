package Panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MiniProductTestPanel extends JPanel {
    public MiniProductTestPanel(String productName, int productPrice, String imagePath) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(282,340));
        Color beige = new Color(231, 217, 194);
        Color darkBlue = new Color(27, 50, 84);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(beige);
        centerPanel.setPreferredSize(new Dimension(282,270));
        CircularButton circularButton = new CircularButton(imagePath, 220);
        centerPanel.add(circularButton);
        add(centerPanel, BorderLayout.CENTER);

        Border border = BorderFactory.createLineBorder(beige, 15);
        setBorder(border);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(beige);
        bottomPanel.setForeground(darkBlue);
        bottomPanel.setPreferredSize(new Dimension(282, 70));
        Font font = new Font("Smiley Sans", Font.PLAIN, 24);
        JLabel productNameLabel = new JLabel(productName);
        productNameLabel.setForeground(darkBlue);
        productNameLabel.setFont(font);
        JLabel productDescriptionLabel = new JLabel("Product Price: $" + productPrice);
        Font descriptionFont = new Font("Smiley Sans", Font.PLAIN, 18);
        productDescriptionLabel.setFont(descriptionFont);
        productDescriptionLabel.setForeground(darkBlue);
        productNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        productDescriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        productNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        productDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productDescriptionLabel.setVerticalAlignment(SwingConstants.CENTER);
        bottomPanel.add(productNameLabel, BorderLayout.CENTER);
        bottomPanel.add(productDescriptionLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainframe = new JFrame();
            mainframe.setSize(282, 320);
            mainframe.setLocationRelativeTo(null);
            mainframe.setResizable(false);
            mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            String imagePath = "Images/circular_image.png";
            MiniProductTestPanel miniProductTestPanel = new MiniProductTestPanel("Product Name", 10, imagePath);
            mainframe.setContentPane(miniProductTestPanel);
            mainframe.setVisible(true);
        });
    }
}
