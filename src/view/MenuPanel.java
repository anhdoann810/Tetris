package view;
import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    
    public MenuPanel(JPanel parentContainer, CardLayout layout) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLUE);

        JLabel title = new JLabel("TETRIS");
        title.setFont(new Font("Futura Black", Font.BOLD, 50));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("START GAME");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton helpButton = new JButton("HELP");
        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("EXIT GAME");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //ở class Main() phải có code đặt tên class GamePanel là "MainGame" và class HelpPanel là "Help" 
        //để switch UI từ MenuPanel
        startButton.addActionListener(e -> layout.show(parentContainer, "MainGame"));
        helpButton.addActionListener(e -> layout.show(parentContainer, "Help"));
        exitButton.addActionListener(e -> System.exit(0));

        add(Box.createVerticalGlue());

        add(title);
        add(Box.createRigidArea(new Dimension(0, 40)));//(width, height)

        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 15)));

        add(helpButton);
        add(Box.createRigidArea(new Dimension(0, 15)));

        add(exitButton);

        add(Box.createVerticalGlue());
    }
}