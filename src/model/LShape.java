package model;

import java.awt.Color;

public class LShape extends Tetromino {
    public LShape(int startX, int startY) {
        Color color = Color.BLUE;

        initCells(
            new Cell(startX, startY, color),
            new Cell(startX, startY + 1, color),
            new Cell(startX, startY + 2, color),
            new Cell(startX + 1, startY + 2, color)
        );
    }

    @Override
    public void rotate() {
        int pivotX = getCell(1).getx();
        int pivotY = getCell(1).gety();
        
        for (int i = 0; i < 4; i++) {
            Cell cell = getCell(i);
            int relativeX = cell.getx() - pivotX;
            int relativeY = cell.gety() - pivotY;
            
            cell.move(-relativeY - relativeX, relativeX - relativeY);
        }
    }

    @Override
    public void rotateBack() {
        int pivotX = getCell(1).getx();
        int pivotY = getCell(1).gety();
        
        for (int i = 0; i < 4; i++) {
            Cell cell = getCell(i);
            int relativeX = cell.getx() - pivotX;
            int relativeY = cell.gety() - pivotY;
            
            cell.move(relativeY - relativeX, -relativeX - relativeY);
        }
    }
}
