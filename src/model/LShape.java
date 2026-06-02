package model;

import java.awt.Color;

public class LShape extends Tetromino {
    public LShape(int startX, int startY) {
        this.pieceColor = Color.ORANGE;

        cells[0] = new Cell(startX, startY, pieceColor);
        cells[1] = new Cell(startX, startY + 1, pieceColor); 
        cells[2] = new Cell(startX, startY + 2, pieceColor);
        cells[3] = new Cell(startX + 1, startY + 2, pieceColor);
    }

    @Override
    public void rotate() {
        int pivotX = cells[1].getx();
        int pivotY = cells[1].gety();
        
        for (Cell cell : cells) {
            int relativeX = cell.getx() - pivotX;
            int relativeY = cell.gety() - pivotY;
            
            int newRelativeX = -relativeY;
            int newRelativeY = relativeX;
            
            cell.setx(pivotX + newRelativeX);
            cell.sety(pivotY + newRelativeY);
        }
    }

    @Override
    public void rotateBack() {
        int pivotX = cells[1].getx();
        int pivotY = cells[1].gety();
        
        for (Cell cell : cells) {
            int relativeX = cell.getx() - pivotX;
            int relativeY = cell.gety() - pivotY;
            
            int newRelativeX = relativeY;
            int newRelativeY = -relativeX;
            
            cell.setx(pivotX + newRelativeX);
            cell.sety(pivotY + newRelativeY);
        }
    }
}
