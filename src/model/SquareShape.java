package model;

import java.awt.Color;

public class SquareShape extends Tetromino {
    public SquareShape(int startX, int startY) {
        Color color = Color.GREEN;
        
        initCells(
            new Cell(startX, startY, color),
            new Cell(startX + 1, startY, color),
            new Cell(startX, startY + 1, color),
            new Cell(startX + 1, startY + 1, color)
        );
    }

    @Override
    public void rotate() {}

    @Override
    public void rotateBack() {}
}
