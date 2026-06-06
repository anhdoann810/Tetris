package view;

import controller.SoundController;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import model.Board;
import model.Cell;
import model.Tetromino;

public class GamePanel extends JPanel {
    private final int CELL_SIZE = 30;
    private Board gameBoard;
    private Tetromino currentPiece;
    private controller.GameEngine engine;
    private JPanel parentContainer;
    private CardLayout layout;
    private JButton bgmToggleButton;

    public GamePanel(Board gameBoard) {
        this.gameBoard = gameBoard;
        this.setBackground(Color.BLACK);
        this.setLayout(null); // allow absolute positioning for the button

        bgmToggleButton = new JButton("\uD83D\uDD0A Music");
        bgmToggleButton.setBounds(Board.COLS * CELL_SIZE + 20, 500, 120, 35);
        bgmToggleButton.setFocusable(false);
        this.add(bgmToggleButton);

        setupKeyBindings();
    }

    public void setEngineAndRouting(controller.GameEngine engine, JPanel parentContainer, CardLayout layout) {
        this.engine = engine;
        this.parentContainer = parentContainer;
        this.layout = layout;

        bgmToggleButton.addActionListener(e -> {
            SoundController sm = engine.getSoundManager();
            sm.toggleBGM();
            bgmToggleButton.setText(sm.isBgmMuted() ? "\uD83D\uDD07 Music" : "\uD83D\uDD0A Music");
        });
    }

    private void setupKeyBindings() {
        InputMap im = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getActionMap();

        im.put(KeyStroke.getKeyStroke("ESCAPE"), "backToMenu");
        am.put("backToMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine != null && parentContainer != null && layout != null) {
                    engine.togglePause();
                    int choice = JOptionPane.showConfirmDialog(
                            GamePanel.this,
                            "Do you want to return to the Main Menu?",
                            "Paused",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (choice == JOptionPane.YES_OPTION) {
                        engine.stop();
                        layout.show(parentContainer, Constants.SCREEN_MENU);
                    } else {
                        engine.togglePause();
                    }
                }
            }
        });

        // Other keys that should be in the game based on HelpPanel
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine != null)
                    engine.moveLeft();
            }
        });

        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine != null)
                    engine.moveRight();
            }
        });

        im.put(KeyStroke.getKeyStroke("UP"), "up");
        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine != null)
                    engine.rotateCurrentPiece();
            }
        });

        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine != null)
                    engine.softDrop();
            }
        });

        im.put(KeyStroke.getKeyStroke("SPACE"), "space");
        am.put("space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine != null)
                    engine.hardDrop();
            }
        });
    }

    // render
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawLockedBlocks(g);
        drawActivePiece(g);
        drawShadowPiece(g);

        // Add score drawing
        drawScore(g);

        // draw next Piece in queue waiting to be spawn
        drawNextPieces(g);

        if (engine != null && engine.isGameOver()) {
            drawGameOverMessage(g);
        }
    }

    // reusable
    private void drawSingleBlock(Graphics g, int x, int y, Color blockColor) {
        g.setColor(blockColor);
        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
    }

    // draw 3 next pieces waited to be spawn
    private void drawNextPieces(Graphics g) {
        if (engine == null)
            return;

        // Get the current queue state from the engine
        java.util.Queue<Tetromino> queueList = engine.getUpcomingPieces();
        if (queueList == null)
            return;

        // starting coordinates for the sidebar
        int startX = (Board.COLS * CELL_SIZE) + 40;
        int startY = 80;

        // vertical space between each preview piece
        int verticalSpacing = (4 * CELL_SIZE) + 20;
        int currentIndex = 0;

        for (Tetromino piece : queueList) {

            int drawY = startY + (currentIndex * verticalSpacing);

            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            for (Cell cell : piece.getCells()) {
                if (cell.getx() < minX)
                    minX = cell.getx();
                if (cell.gety() < minY)
                    minY = cell.gety();
            }

            for (Cell cell : piece.getCells()) {

                int relativeX = (cell.getx() - minX) * CELL_SIZE;
                int relativeY = (cell.gety() - minY) * CELL_SIZE;

                int finalDrawX = startX + relativeX;
                int finalDrawY = drawY + relativeY;

                drawSingleBlock(g, finalDrawX, finalDrawY, cell.getColor());
            }

            currentIndex++;
        }
    }

    private void drawGameOverMessage(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));
        String msg = "GAME OVER";
        java.awt.FontMetrics fm = g.getFontMetrics();
        int msgWidth = fm.stringWidth(msg);
        int msgHeight = fm.getAscent();

        g.drawString(msg, (getWidth() - msgWidth) / 2, getHeight() / 2);

        g.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 15));
        String subMsg = "Press ESC to Menu";
        int subMsgWidth = g.getFontMetrics().stringWidth(subMsg);
        g.drawString(subMsg, (getWidth() - subMsgWidth) / 2, (getHeight() / 2) + msgHeight + 10);
    }

    // helper methods
    private void drawGrid(Graphics g) {
        g.setColor(Color.DARK_GRAY);

        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                int x = c * CELL_SIZE;
                int y = r * CELL_SIZE;
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void drawLockedBlocks(Graphics g) {
        // iterate through gameBoard
        Color[][] grid = gameBoard.getGrid();

        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                Color blockColor = grid[r][c];

                if (blockColor != null) {
                    int x = c * CELL_SIZE;
                    int y = r * CELL_SIZE;

                    drawSingleBlock(g, x, y, blockColor);
                }
            }
        }
    }

    private void drawActivePiece(Graphics g) {
        // draw the currentPiece at specific X/Y coordinates
        if (currentPiece != null) {
            for (Cell cell : currentPiece.getCells()) {
                int x = cell.getx() * CELL_SIZE;
                int y = cell.gety() * CELL_SIZE;

                drawSingleBlock(g, x, y, cell.getColor());
            }
        }
    }

    private void drawShadowPiece(Graphics g) {
        if (currentPiece != null) {
            int dy = 0;
            while (gameBoard.isValidBlock(currentPiece, 0, dy + 1)) {
                dy++;
            }

            for (Cell cell : currentPiece.getCells()) {
                int x = cell.getx() * CELL_SIZE;
                int y = (cell.gety() + dy) * CELL_SIZE;

                drawSingleBlock(g, x, y, new Color(cell.getColor().getRed(), cell.getColor().getGreen(),
                        cell.getColor().getBlue(), 100));
            }
        }
    }

    // display score
    private void drawScore(Graphics g) {
        if (engine != null) {
            g.setColor(Color.WHITE);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));

            String scoreText = "Score: " + engine.getScore();

            g.drawString(scoreText, 320, 30);
        }
    }

    // interface for controller
    public void setActivePiece(Tetromino piece) {
        // updates reference to the falling piece
        this.currentPiece = piece;

    }

    public void refreshBoard() {
        // called by GameEngine to trigger repaint()
        this.repaint();
    }
}
