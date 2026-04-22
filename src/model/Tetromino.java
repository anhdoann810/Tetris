package model;

import java.awt.Color;

public abstract class Tetromino {
    protected Cell[] cells = new Cell[4];
    protected Color pieceColor;

    public abstract void rotate();

    public Cell[] getCells() {
        return cells;
    }

    public void move(int dx, int dy) {
        for (Cell cell: cells){
            cell.setx(cell.getx() + dx);
            cell.sety(cell.gety() + dy);
        }
    }
}

