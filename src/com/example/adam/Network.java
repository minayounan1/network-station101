package com.example.adam;

import com.example.adam.exceptions.*;

import java.util.ArrayList;

public class Network {

    private final ArrayList<Node> nodes;
    private boolean modified;

    public Network() {
        nodes = new ArrayList<Node>();
        modified = false;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public void addNode(Node node) throws DuplicateNodeException {
        if (nodes.contains(node))
            throw new DuplicateNodeException();
        else {
            nodes.add(node);
            modified = true;
        }
    }

    public BaseStation getBaseStation() {
        for (Node n : nodes)
            if (n.getClass().getName().equals("com.example.adam.BaseStation"))
                return (BaseStation) n;

        return null;
    }

    public void removeNode(com.example.adam.Node node) throws NodeNotFoundException {
        if (nodes.contains(node)) {
            Station station = (Station) node;
            for (Link link : station.getLinks()) {
                if (link != null)
                    removeLink(station, link.getNodeTo());
            }
            nodes.remove(station);
            modified = true;
        } else
            throw new NodeNotFoundException();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getNodeFromID(int id) throws NodeNotFoundException {
        for (Node node : nodes) {
            if (node.getID() == id)
                return node;
        }
        throw new NodeNotFoundException();

    }

    public void addLink(Node nodeFrom, Node nodeTo, int dist) throws DuplicatedLinkException,
            NodeFullLinksException {
        if (nodeFrom.isConnectedTo(nodeTo))
            throw new DuplicatedLinkException();

        try {
            nodeFrom.addLink(new Link(nodeTo, dist));
            nodeTo.addLink(new Link(nodeFrom, dist));
            modified = true;
        } catch (NodeFullLinksException ex) {
            nodeFrom.removeLinkTo(nodeTo);
            nodeTo.removeLinkTo(nodeFrom);
            throw new NodeFullLinksException();
        }
    }

    public void removeLink(Node nodeFrom, Node nodeTo) {
        if (nodeFrom.isConnectedTo(nodeTo)) {
            nodeFrom.removeLinkTo(nodeTo);
            nodeTo.removeLinkTo(nodeFrom);
            modified = true;
        }
    }

    public void modifyLink(Node nodeFrom, Node nodeTo, int dist) throws LinkNotFoundException {
        if (nodeFrom.isConnectedTo(nodeTo)) {
            Link link = nodeFrom.getLinkTo(nodeTo);
            link.setDistance(dist);
            link = nodeTo.getLinkTo(nodeFrom);
            link.setDistance(dist);
            modified = true;
        } else throw new LinkNotFoundException();
    }


}
