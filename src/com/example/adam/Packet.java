package com.example.adam;

public class Packet {

    private final Node nodeFrom;
    private final Node nodeTo;
    private String data;

    public Packet(Node nodeFrom, Node nodeTo, String data) {
        this.nodeFrom = nodeFrom;
        this.nodeTo = nodeTo;
        this.data = data;
    }

    public Packet(Node nodeFrom, Node nodeTo) {
        this(nodeFrom, nodeTo, "");
    }

    public String getData() {
        return data;
    }

    public Node getNodeFrom() {
        return nodeFrom;
    }

    public Node getNodeTo() {
        return nodeTo;
    }

    public void writeData(String newData) {
        data += newData;
    }


}
