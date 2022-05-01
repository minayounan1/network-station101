package com.example.adam.GUI;

import java.awt.*;

public class LinkItem {

    public final static int DISTANCE = 100;

    private NodeItem nodeFrom;
    private NodeItem nodeTo;

    public LinkItem(NodeItem nodeFrom, NodeItem nodeTo) {
        this.nodeFrom = nodeFrom;
        this.nodeTo = nodeTo;
    }

    public LinkItem(NodeItem nodeFrom) {
        this(nodeFrom, null);
    }

    public NodeItem getNodeFrom() {
        return nodeFrom;
    }

    public void setNodeFrom(NodeItem nodeFrom) {
        this.nodeFrom = nodeFrom;
    }

    public NodeItem getNodeTo() {
        return nodeTo;
    }

    public void setNodeTo(NodeItem nodeTo) {
        this.nodeTo = nodeTo;
    }

    public Point getPos() {
        final int x = Math.abs(nodeFrom.getPos().getX() + nodeTo.getPos().getX()) / 2;
        final int y = Math.abs(nodeFrom.getPos().getY() + nodeTo.getPos().getY()) / 2;
        return new Point(x, y);
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(nodeFrom.getPos().getX(), nodeFrom.getPos().getY(),
                nodeTo.getPos().getX(), nodeTo.getPos().getY());
    }

}
