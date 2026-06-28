package view;

import controller.GameEngine;
import controller.SoundController;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MenuPanel extends JPanel {
    private BufferedImage bgImage;

    public MenuPanel(JPanel parentContainer, CardLayout layout, GameEngine gameEngine) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        try {
            bgImage = ImageIO.read(getClass().getResource("/view/bg.jpg"));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            setOpaque(true);
            setBackground(Color.BLUE);
        }

        JLabel title = new JLabel("TETRIS");
        title.setFont(new Font("Futura Black", Font.BOLD, 50));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("PLAY");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton helpButton = new JButton("HELP");
        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("EXIT GAME");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton bgmToggleButton = new JButton("\uD83D\uDD0A Music");
        bgmToggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bgmToggleButton.addActionListener(e -> {
            SoundController sm = gameEngine.getSoundManager();
            sm.toggleBGM();
            bgmToggleButton.setText(sm.isBgmMuted() ? "\uD83D\uDD07 Music" : "\uD83D\uDD0A Music");
        });

        startButton.addActionListener(e -> {
            layout.show(parentContainer, Constants.SCREEN_DIFFICULTY);
        });
        helpButton.addActionListener(e -> layout.show(parentContainer, Constants.SCREEN_HELP));
        exitButton.addActionListener(e -> System.exit(0));

        add(Box.createVerticalGlue());

        add(title);
        add(Box.createRigidArea(new Dimension(0, 40)));

        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 15)));

        add(helpButton);
        add(Box.createRigidArea(new Dimension(0, 15)));

        add(exitButton);
        add(Box.createRigidArea(new Dimension(0, 15)));

        add(bgmToggleButton);

        add(Box.createVerticalGlue());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}