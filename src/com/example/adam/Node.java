package com.example.adam;

import com.example.adam.exceptions.NodeFullLinksException;

public abstract class Node implements Comparable<Node> {

    protected static int idCount = 0;
    protected final int ID;
    protected String name;
    protected boolean isActive;
    protected Router router;
    protected int baseNodeDistance;
    protected Node previousNode;

    public Node(String name, Network net) {
        this.name = name;
        router = new Router(this, net);
        isActive = true;
        baseNodeDistance = -1;
        previousNode = null;
        ID = idCount++;
    }

    public Node(Network net) {
        this("Node", net);
        name += String.valueOf(ID);
    }

    public abstract void addLink(Link link) throws NodeFullLinksException;

    public abstract void removeLinkTo(Node nodeTo);

    public abstract Link[] getLinks();

    public abstract Link getLinkTo(Node nodeTo);

    public abstract void removeAllLinks();

    public abstract boolean isConnectedTo(Node nodeTo);

    public abstract Node[] getNodesConnected();

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public int getBaseNodeDistance() {
        return baseNodeDistance;
    }

    public void setBaseNodeDistance(int baseNodeDistance) {
        this.baseNodeDistance = baseNodeDistance;
    }

    public Router getRouter() {
        return router;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    @Override
    public int compareTo(Node node) {
        Integer dist = new Integer(baseNodeDistance);
        return dist.compareTo(new Integer(node.getBaseNodeDistance()));
    }
}
