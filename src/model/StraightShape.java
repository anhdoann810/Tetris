package model;

import java.awt.Color;

public class StraightShape extends Tetromino {
    private boolean isVertical = true;

    public StraightShape(int startX, int startY) {
        Color color = Color.RED;

        initCells(
            new Cell(startX, startY, color),
            new Cell(startX, startY + 1, color),
            new Cell(startX, startY + 2, color),
            new Cell(startX, startY + 3, color)
        );
    }

    @Override
    public void rotate() {
        int pivotX = getCell(1).getx();
        int pivotY = getCell(1).gety();

        if (isVertical){
            getCell(0).move(pivotX - 1 - getCell(0).getx(), pivotY - getCell(0).gety());
            getCell(2).move(pivotX + 1 - getCell(2).getx(), pivotY - getCell(2).gety());
            getCell(3).move(pivotX + 2 - getCell(3).getx(), pivotY - getCell(3).gety());
        } else {
            getCell(0).move(pivotX - getCell(0).getx(), pivotY - 1 - getCell(0).gety());
            getCell(2).move(pivotX - getCell(2).getx(), pivotY + 1 - getCell(2).gety());
            getCell(3).move(pivotX - getCell(3).getx(), pivotY + 2 - getCell(3).gety());
        }

        isVertical = !isVertical;
    }

    @Override
    public void rotateBack() {
        rotate();
    }
}
