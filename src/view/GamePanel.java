package view;
import model.Board;
import model.Tetromino;
import model.Cell;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;  
import java.awt.Graphics;

public class GamePanel extends JPanel {
    private final int CELL_SIZE = 30;
    private Board gameBoard;
    private Tetromino currentPiece;
    
    public GamePanel(Board gameBoard) {
        this.gameBoard = gameBoard;

        int panelWidth = Board.COLS * CELL_SIZE;
        int panelHeight = Board.ROWS * CELL_SIZE;

        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setBackground(Color.BLACK);
    }

    //render
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawLockedBlocks(g);
        drawActivePiece(g);
    }

    //helper methods
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
        //iterate through gameBoard
        Color[][] grid = gameBoard.getGrid();

        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                Color blockColor = grid[r][c];

                if (blockColor != null) {
                    int x = c * CELL_SIZE;
                    int y = r * CELL_SIZE;

                    g.setColor(blockColor);
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);

                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    private void drawActivePiece(Graphics g) {
        //draw the currentPiece at specific X/Y coordinates
        if (currentPiece != null) {
            for (Cell cell : currentPiece.getCells()) {
                int x = cell.getx() * CELL_SIZE;
                int y = cell.gety() * CELL_SIZE;

                g.setColor(cell.getColor());
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);

                g.setColor(Color.DARK_GRAY);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    //interface for controller
    public void setActivePiece(Tetromino piece) {
        //updates reference to the falling piece
        this.currentPiece = piece;

    }

    public void refreshBoard() {
        //called by GameEngine to trigger repaint()
        this.repaint();
    }
}