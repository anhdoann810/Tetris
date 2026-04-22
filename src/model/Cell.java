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
    public void setx(int x) {
        this.x = x;
    }
    public int gety() {
        return y;
    }
    public void sety(int y) {
        this.y = y;
    }
    public Color getColor() {
        return color;
    }
}
