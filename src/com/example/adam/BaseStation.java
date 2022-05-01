package com.example.adam;

import com.example.adam.GUI.AlarmDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseStation extends Node {

    private final ArrayList<Link> links;
    private final HashMap<Station, String> packets;
    private int temperature;
    private final AlarmDisplayer displayer;

    public BaseStation(String name, Network net, AlarmDisplayer displayer) {
        super(name, net);
        links = new ArrayList<Link>();
        packets = new HashMap<Station, String>();
        this.displayer = displayer;
    }

    @Override
    public void addLink(Link link) {
        links.add(link);
    }

    @Override
    public void removeLinkTo(Node nodeTo) {
        for (Link l : links)
            if (l.getNodeTo() == nodeTo) {
                links.remove(l);
                break;
            }
    }

    @Override
    public void removeAllLinks() {
        links.clear();
    }


    @Override
    public boolean isConnectedTo(Node nodeTo) {
        for (Link l : links)
            if (l.getNodeTo() == nodeTo)
                return true;
        return false;
    }

    @Override
    public Node[] getNodesConnected() {
        return new Node[0];
    }

    @Override
    public Link[] getLinks() {
        return links.toArray(new Link[1]);
    }

    @Override
    public Link getLinkTo(Node nodeTo) {
        for (Link l : links)
            if (l.getNodeTo() == nodeTo)
                return l;
        return null;
    }


    public void sendRequests() {
        Packet packet = null;
        String data = null;
        Station station = null;


        for (Node nodeTo : router.getRouteTable().keySet()) {
            System.out.println("\nBase sends request to " + nodeTo.getName());


            station = (Station) router.getPath(nodeTo);
            packet = station.handlePacket(new Packet(this, nodeTo));

            try {
                System.out.println("Base receives packet from " +
                        packet.getNodeFrom().getName() + ", Data: " + packet.getData());

                Station sta = (Station) nodeTo;
                sta.getSensor().setRandomValue();


                if (!packets.containsKey(sta))
                    packets.put(sta, "");

                int temp = Integer.parseInt(sta.getSensor().readValue());
                //Checks if base stations Temperature is above or equal to the send packet/stations temperature
                if (temp > this.temperature) {
                    System.out.println("\n----------- WARNING --------------");
                    System.out.println("\nPacket received from " +
                            packet.getNodeFrom().getName() + " shows Temperature is " +
                            temp + "C, limit is " +
                            temperature + "C");
                    displayer.setAlarmPicture(false);
                }

                data = packets.get(sta);
                packets.put(sta, data.concat(packet.getData()));
            } catch (NullPointerException ex) {
                System.out.println("No reply!");
            }
        }
    }


    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
