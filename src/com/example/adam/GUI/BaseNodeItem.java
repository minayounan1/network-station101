package com.example.adam.GUI;

import java.awt.*;

public class BaseNodeItem extends NodeItem {

    public BaseNodeItem(Point pos, int id) {
        super(pos, id);
    }

    @Override
    public void paint(Graphics g) {
        final int x = pos.getX() - width / 2;
        final int y = pos.getY() - height / 2;
        g.setColor(Color.RED);
        g.fillOval(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);
    }
}
