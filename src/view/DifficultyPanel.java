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

        JRadioButton easyButton = new JRadioButton("Easy");
        JRadioButton mediumButton = new JRadioButton("Medium");
        JRadioButton hardButton = new JRadioButton("Hard");

        easyButton.setForeground(Color.WHITE);
        easyButton.setBackground(Color.BLUE);
        mediumButton.setForeground(Color.WHITE);
        mediumButton.setBackground(Color.BLUE);
        hardButton.setForeground(Color.WHITE);
        hardButton.setBackground(Color.BLUE);

        mediumButton.setSelected(true);

        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyButton);
        difficultyGroup.add(mediumButton);
        difficultyGroup.add(hardButton);

        JPanel radioPanel = new JPanel();
        radioPanel.setBackground(Color.BLUE);
        radioPanel.add(easyButton);
        radioPanel.add(mediumButton);
        radioPanel.add(hardButton);
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("PLAY NOW");
        startButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton backButton = new JButton("BACK");
        backButton.setAlignmentX(CENTER_ALIGNMENT);

        startButton.addActionListener(e -> {
            if (gameEngine != null) {
                if (easyButton.isSelected()) {
                    gameEngine.setDifficultyDelay(800, 1);
                } else if (mediumButton.isSelected()) {
                    gameEngine.setDifficultyDelay(500, 2);
                } else if (hardButton.isSelected()) {
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
        add(radioPanel);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(backButton);
        add(Box.createVerticalGlue());
    }
}
