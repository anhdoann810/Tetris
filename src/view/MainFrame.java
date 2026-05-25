package view;

import javax.swing.*;
import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import controller.GameEngine;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private HelpPanel helpPanel;
    private GameEngine gameEngine;

    public MainFrame() {
        this.setTitle("Tetris");
        this.setSize(400, 700);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmAndQuit();
            }
        });

        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(this.cardLayout);

        this.gameEngine = new GameEngine();
        this.menuPanel = new MenuPanel(this.contentPanel, this.cardLayout, this.gameEngine);
        this.helpPanel = new HelpPanel(this.contentPanel, this.cardLayout);
        this.gamePanel = new GamePanel(this.gameEngine.getBoard());
        
        this.gamePanel.setEngineAndRouting(this.gameEngine, this.contentPanel, this.cardLayout);

        this.gameEngine.setViewUpdater(() -> {
            this.gamePanel.setActivePiece(this.gameEngine.getCurrentPiece());
            this.gamePanel.refreshBoard();
        });

        this.contentPanel.add(this.menuPanel, "Menu");
        this.contentPanel.add(this.gamePanel, "MainGame");
        this.contentPanel.add(this.helpPanel, "Help");

        this.add(this.contentPanel);

        this.cardLayout.show(this.contentPanel, "Menu");
        this.setVisible(true);
    }

    private void confirmAndQuit() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Quit Tetris",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}