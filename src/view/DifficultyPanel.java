package view;

import javax.swing.*;
import java.awt.*;
import controller.GameEngine;

public class DifficultyPanel extends JPanel {
    public DifficultyPanel(JPanel parentContainer, CardLayout layout, GameEngine gameEngine) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLUE);

        JLabel title = new JLabel("CHOOSE DIFFICULTY");
        title.setFont(new Font("Futura Black", Font.BOLD, 35));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        DefaultListCellRenderer rdr = new DefaultListCellRenderer();
        rdr.setHorizontalAlignment(SwingConstants.CENTER);
        rdr.setBackground(Color.BLUE);
        rdr.setForeground(Color.WHITE);

        String[] difficultyLevels = { "Easy", "Medium", "Hard" };
        JComboBox<String> difficultyComboBox = new JComboBox<>(difficultyLevels);
        difficultyComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyComboBox.setFont(new Font("Futura Black", Font.BOLD, 24));
        difficultyComboBox.setBackground(Color.BLUE);
        difficultyComboBox.setForeground(Color.WHITE);
        difficultyComboBox.setSelectedIndex(1);

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
            layout.show(parentContainer, "MainGame");
        });

        backButton.addActionListener(e -> layout.show(parentContainer, "Menu"));

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
}