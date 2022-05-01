package com.example.adam.GUI;

import java.awt.*;

public class NodeItem {

    protected static final int width = 20;
    protected static final int height = 20;
    protected final int ID;
    protected Point pos;

    public NodeItem(Point pos, int id) {
        this.pos = pos;
        ID = id;
    }

    public int getID() {
        return ID;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void paint(Graphics g) {
        final int x = pos.getX() - width / 2;
        final int y = pos.getY() - height / 2;
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);
    }


}
