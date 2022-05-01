package com.example.adam;

import com.example.adam.exceptions.NodeFullLinksException;
import com.example.adam.sensor.Thermometer;

public final class Station extends Node {

    private final Thermometer sensor;
    private Link[] links;

    public Station(String name, Network network) {
        super(name, network);
        links = new Link[4];
        sensor = new Thermometer();
    }

    public Station(Network network) {
        super(network);
        links = new Link[4];
        sensor = new Thermometer();
    }

    @Override
    public void addLink(Link link) throws NodeFullLinksException {
        int i;
        for (i = 0; i < links.length; i++) {
            if (links[i] == null) {
                links[i] = link;
                return;
            }
        }
        if (i == links.length)
            throw new NodeFullLinksException();
    }

    @Override
    public void removeLinkTo(Node nodeTo) {
        for (int i = 0; i < links.length; i++) {
            if (links[i] != null && links[i].getNodeTo() == nodeTo) {
                links[i] = null;
                return;
            }
        }
    }

    @Override
    public void removeAllLinks() {
        links = new Link[4];
    }

    @Override
    public boolean isConnectedTo(Node nodeTo) {
        for (Link l : links) {
            if (l != null && l.getNodeTo() == nodeTo) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Link[] getLinks() {
        return links;
    }


    public Thermometer getSensor() {
        return sensor;
    }

    public Packet handlePacket(Packet packet) {
        if (!isActive)
            return null;
        else if (packet.getNodeTo() == this) {
            System.out.println(name + " sends response packet to the Base station");
            return createPacket();
        } else {
            return sendPacket(packet);
        }
    }

    public Packet sendPacket(Packet packet) {
        Station est = (Station) router.getPath(packet.getNodeTo());
        System.out.println(name + " forwards packet to " + est.getName());

        return est.handlePacket(packet);
    }

    private Packet createPacket() {
        Packet packet = new Packet(this, null);
        packet.writeData("Temperature: " + sensor.readValue() + "C");


        return packet;
    }

    @Override
    public Node[] getNodesConnected() {
        Node[] nodes = new Node[4];
        for (int i = 0; i < links.length; i++) {
            if (links[i] != null) {
                nodes[i] = links[i].getNodeTo();
            }
        }
        return nodes;
    }

    @Override
    public Link getLinkTo(Node nodeTo) {
        for (int i = 0; i < links.length; i++) {
            if (links[i] != null && links[i].getNodeTo() == nodeTo)
                return links[i];
        }
        return null;
    }
}
