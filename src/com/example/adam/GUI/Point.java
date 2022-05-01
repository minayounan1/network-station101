package com.example.adam.GUI;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean contains(Point p, int offset) {
        return (p.getX() > x - offset / 2 && p.getX() < x + offset / 2 &&
                p.getY() > y - offset / 2 && p.getY() < y + offset / 2);
    }
}
