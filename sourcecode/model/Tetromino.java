package model;

import java.awt.Color;
import java.util.Arrays;

public abstract class Tetromino {
    private Cell[] cells = new Cell[4];

    public abstract void rotate();
    public abstract void rotateBack();

    protected void initCells(Cell c0, Cell c1, Cell c2, Cell c3) {
        cells[0] = c0;
        cells[1] = c1;
        cells[2] = c2;
        cells[3] = c3;
    }

    protected Cell getCell(int index) {
        return cells[index];
    }

    public Cell[] getCells() {
        return Arrays.copyOf(cells, cells.length);
    }

    public Color getColor() {
        return cells[0].getColor();
    }

    public void move(int dx, int dy) {
        for (Cell cell: cells){
            cell.move(dx, dy);
        }
    }
}
