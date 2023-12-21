package Panels;

import javax.swing.*;
import java.awt.*;


public class TestPanel extends JPanel {
    public TestPanel() {
        setLayout(null);
        JPanel menuPanel = new JPanel();

        Color color1 = new Color(54,91,131);
        Color color2 = new Color(146, 164,184);
        Color color3 = new Color(231, 217, 194);
        Color blue = new Color(27, 50,84);

        JPanel labelPanel = new JPanel();
        labelPanel.setSize(683,154);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setBackground(color3);
        scrollPanel.setSize(new Dimension(819, 584));

        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.X_AXIS));
        // Add some components to the panel
        for (int i = 1; i <= 10; i++) {
            JButton button = new JButton("Button " + i);
            scrollPanel.add(button);
        }
        ImageIcon imageIcon = new ImageIcon("Images/labelIntelliJ.png");
        ImageIcon resizedImageIcon = resizeImage(imageIcon, 683, 153);
        JLabel imageLabel = new JLabel(resizedImageIcon);
        labelPanel.add(imageLabel);
        labelPanel.setBounds(207, 2, 683, 154);
        add(labelPanel);

        // code for the scroll panel
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        JLabel yourStores = new JLabel("Your Stores");
        yourStores.setForeground(Color.WHITE);
        yourStores.setPreferredSize(new Dimension(0, 50)); // Adjust the height as needed

        Font font = new Font("Josefin Slab", Font.PLAIN, 24);
        yourStores.setFont(font);
        yourStores.setOpaque(true);
        yourStores.setBackground(color2);

        scrollPane.setColumnHeaderView(yourStores);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(205, 154, 819,584);
        add(scrollPane);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainframe = new JFrame();
            mainframe.setSize(1024, 768);
            mainframe.setLocationRelativeTo(null);
            mainframe.setResizable(false);
            mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            TestPanel testPanel = new TestPanel();
            mainframe.setContentPane(testPanel);
            mainframe.setVisible(true);
        });
    }
    private ImageIcon resizeImage(ImageIcon originalIcon, int width, int height) {
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(683, 153, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
