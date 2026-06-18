package model;

import java.awt.Color;

public class Cell {
    private int x;
    private int y;
    private Color color;

    public Cell(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getx() { 
        return x; 
    }
    public int gety() {
        return y;
    }

    protected void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public Color getColor() {
        return color;
    }
}
