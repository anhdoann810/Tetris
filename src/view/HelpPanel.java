package view;

import java.awt.*;
import javax.swing.*;

public class HelpPanel extends JPanel {

    public HelpPanel(JPanel parentContainer, CardLayout layout) {
        setLayout(new BorderLayout(20, 20)); 
        setBackground(Color.BLUE); 

        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("HOW TO PLAY", SwingConstants.CENTER);
        title.setFont(new Font("Futura Black", Font.BOLD, 35));
        title.setForeground(Color.WHITE);

        JLabel instructions = new JLabel(
                "<html><div style='text-align: center; font-family: Arial; font-size: 16px;'>"
                        + "<br><br>"
                        + "<b>LEFT / RIGHT ARROWS:</b> Move piece horizontally<br><br>"
                        + "<b>UP ARROW:</b> Rotate piece clockwise<br><br>"
                        + "<b>DOWN ARROW:</b> Soft Drop (move down faster)<br><br>"
                        + "<b>SPACEBAR:</b> Hard Drop (instant lock)<br><br>"
                        + "<br>Clear horizontal lines to score points!<br>"
                        + "The game ends if the pieces reach the top."
                        + "</div></html>",
                SwingConstants.CENTER);
        instructions.setForeground(Color.WHITE);

        JButton backButton = new JButton("BACK TO MENU");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        
        backButton.addActionListener(e -> layout.show(parentContainer, "Menu"));

        add(title, BorderLayout.NORTH);
        add(instructions, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
}