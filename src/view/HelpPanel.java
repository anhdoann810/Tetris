package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class HelpPanel extends JPanel {
    private BufferedImage bgImage;

    public HelpPanel(JPanel parentContainer, CardLayout layout) {
        setLayout(new BorderLayout(20, 20));
        setOpaque(false);

        try {
            bgImage = ImageIO.read(getClass().getResource("/view/bg.jpg"));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            setOpaque(true);
            setBackground(Color.BLUE);
        }

        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel instructions = new JLabel(
        "<html><div style='text-align: center; font-family: Arial; font-size: 14px; color: white;'>"
                + "<b>Welcome to our OOP Tetris project!</b><br><br>"
                + "<b>This game was built as an academic project to explore and apply OOP principles in a fully functional, real-time software application.</b><br><br>"
                + "<b>HOW TO PLAY</b><br><br>"
                + "<b>LEFT / RIGHT ARROWS:</b> Move piece horizontally<br><br>"
                + "<b>UP ARROW:</b> Rotate piece clockwise<br><br>"
                + "<b>DOWN ARROW:</b> Soft Drop (move down faster)<br><br>"
                + "<b>SPACEBAR:</b> Hard Drop (instant lock)<br><br>"
                + "<br>Clear horizontal lines to score points!<br>"
                + "The game ends if the pieces reach the top."
                + "</div></html>",
        SwingConstants.CENTER);

        JButton backButton = new JButton("BACK TO MENU");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> layout.show(parentContainer, "Menu"));

        add(instructions, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}