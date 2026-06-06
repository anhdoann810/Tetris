package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import controller.GameEngine;

public class DifficultyPanel extends JPanel {
    private BufferedImage bgImage;

    public DifficultyPanel(JPanel parentContainer, CardLayout layout, GameEngine gameEngine) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        try {
            bgImage = ImageIO.read(getClass().getResource("/view/bg.jpg"));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            setOpaque(true);
            setBackground(Color.GRAY);
        }

        JLabel title = new JLabel("CHOOSE DIFFICULTY");
        title.setFont(new Font("Futura Black", Font.BOLD, 35));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] difficultyLevels = { "Easy", "Medium", "Hard" };
        JComboBox<String> difficultyComboBox = new JComboBox<>(difficultyLevels);

        difficultyComboBox.setFont(new Font("Futura Black", Font.BOLD, 24));
        difficultyComboBox.setBackground(Color.GRAY);
        difficultyComboBox.setForeground(Color.WHITE);
        difficultyComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyComboBox.setSelectedIndex(1); 

        DefaultListCellRenderer rdr = new DefaultListCellRenderer();
        rdr.setHorizontalAlignment(SwingConstants.CENTER);
        rdr.setBackground(Color.GRAY);
        rdr.setForeground(Color.WHITE);
        difficultyComboBox.setRenderer(rdr);

        Dimension comboSize = new Dimension(150, 40);
        difficultyComboBox.setPreferredSize(comboSize);
        difficultyComboBox.setMaximumSize(comboSize);

        JButton startButton = new JButton("PLAY NOW");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backButton = new JButton("BACK");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(e -> {
            if (gameEngine != null) {
                Object selected = difficultyComboBox.getSelectedItem();

                if ("Easy".equals(selected)) {
                    gameEngine.setDifficultyDelay(800, 1);
                } else if ("Medium".equals(selected)) {
                    gameEngine.setDifficultyDelay(500, 2);
                } else if ("Hard".equals(selected)) {
                    gameEngine.setDifficultyDelay(200, 3);
                }
                gameEngine.resetGame();
            }
            layout.show(parentContainer, Constants.SCREEN_GAME);
        });

        backButton.addActionListener(e -> layout.show(parentContainer, Constants.SCREEN_MENU));

        add(Box.createVerticalGlue());
        add(title);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(difficultyComboBox); 
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(backButton);
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