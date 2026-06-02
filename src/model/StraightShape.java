package model;

import java.awt.Color;

public class StraightShape extends Tetromino {
    private boolean isVertical = true;

    public StraightShape(int startX, int startY) {
        this.pieceColor = Color.CYAN;

        cells[0] = new Cell(startX, startY, pieceColor);
        cells[1] = new Cell(startX, startY + 1, pieceColor);
        cells[2] = new Cell(startX, startY + 2, pieceColor);
        cells[3] = new Cell(startX, startY + 3, pieceColor);
    }

    @Override
    public void rotate() {
        int pivotX = cells[1].getx();
        int pivotY = cells[1].gety();

        if (isVertical){
            cells[0].setx(pivotX - 1);
            cells[0].sety(pivotY);
            cells[2].setx(pivotX + 1);
            cells[2].sety(pivotY);
            cells[3].setx(pivotX + 2);
            cells[3].sety(pivotY);
        } else {
            cells[0].setx(pivotX);
            cells[0].sety(pivotY - 1);
            cells[2].setx(pivotX); 
            cells[2].sety(pivotY + 1);
            cells[3].setx(pivotX); 
            cells[3].sety(pivotY + 2);
        }

        isVertical = !isVertical;
    }

    @Override
    public void rotateBack() {
        rotate();
    }
}
