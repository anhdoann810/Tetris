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
    protected void setx(int x) {
        this.x = x;
    }
    public int gety() {
        return y;
    }
    protected void sety(int y) {
        this.y = y;
    }
    public Color getColor() {
        return color;
    }
}
