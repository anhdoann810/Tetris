package model;

import java.awt.Color;

public class TShape extends Tetromino {
    public TShape(int startX, int startY) {
        this.pieceColor = Color.MAGENTA;

        cells[0] = new Cell(startX, startY, pieceColor);
        cells[1] = new Cell(startX + 1, startY, pieceColor);
        cells[2] = new Cell(startX + 2, startY, pieceColor);
        cells[3] = new Cell(startX + 1, startY + 1, pieceColor);
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
