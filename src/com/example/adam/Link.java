package com.example.adam;

public class Link {

    private int distance;
    private Node nodeTo;

    public Link(Node nodeTo, int distance) {
        this.distance = distance;
        this.nodeTo = nodeTo;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Node getNodeTo() {
        return nodeTo;
    }

    public void setNodeTo(Node nodeTo) {
        this.nodeTo = nodeTo;
    }

    public int getCost() {
        return (distance / 100);
    }
}
