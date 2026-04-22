package model;

import java.awt.Color;

public class SquareShape extends Tetromino {
    public SquareShape(int startX, int startY) {
        this.pieceColor = Color.GREEN;
        
        cells[0] = new Cell(startX, startY, pieceColor);
        cells[1] = new Cell(startX + 1, startY, pieceColor);
        cells[2] = new Cell(startX, startY + 1, pieceColor);
        cells[3] = new Cell(startX + 1, startY + 1, pieceColor);
    }

    @Override
    public void rotate() {};
}
