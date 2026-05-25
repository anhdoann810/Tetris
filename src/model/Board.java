package model;

import java.awt.Color;

public class Board {
    public static final int ROWS = 20;
    public static final int COLS = 10;
    
    private final Color[][] grid;

    public Board() {
        grid = new Color[ROWS][COLS];
    }

    public void clear() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c] = null;
            }
        }
    }

    public Color[][] getGrid() {
        return grid;
    }

    public boolean isValidBlock(Tetromino piece, int dx, int dy){
        for (Cell cell: piece.getCells()) {
            int newX = cell.getx() + dx;
            int newY = cell.gety() + dy;

            if (newX < 0 || newX >= COLS || newY >= ROWS) {
                return false;
            }

            if (newY >= 0 && grid[newY][newX] != null) {
                return false;
            }
        }
        return true;
    }

    public void lockPiece(Tetromino piece) {
        for (Cell cell: piece.getCells()) {
            int x = cell.getx();
            int y = cell.gety();

            if (y >= 0 && y < ROWS && x >= 0 && x < COLS) {
                grid[y][x] = cell.getColor();
            }
        }
    }

    public int clearLines() {
        int linesCleared = 0;

        for (int r = ROWS - 1; r >= 0; r--) {
            boolean isFull = true;

            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == null) {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                linesCleared++;

                for (int shiftR = r; shiftR > 0; shiftR--) {
                    for (int c = 0; c < COLS; c++) {
                        grid[shiftR][c] = grid[shiftR - 1][c];
                    }
                }
                
                for (int c = 0 ; c < COLS; c++) {
                        grid[0][c] = null;
                }

                r++;
            }
        }

        return linesCleared;
    }
}
