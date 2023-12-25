package Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class CircularButton extends JButton {
    private BufferedImage image;

    public CircularButton(String imagePath, int size) {
        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(size, size)); // Set the preferred size of the button
        setContentAreaFilled(false); // Make the button transparent
        setBorderPainted(false); // Remove the button border
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Create a circular shape
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());

        // Get the graphics context of the button
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setClip(circle);

        // Draw the circular image on the button
        if (image != null) {
            g2.drawImage(image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
        }

        // Dispose of the graphics context
        g2.dispose();
    }
}
