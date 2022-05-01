package com.example.adam;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Dijkstra {

    public static int INFINITY = 99999;
    private final Network network;
    private final NodePriorityQueue queue;
    public Dijkstra(Network network) {
        this.network = network;
        queue = new NodePriorityQueue();
    }

    public HashMap<Node, Node> run(Node startNode) {
        initialize(startNode);
        Node node = null;
        Node adjNode = null;
        int possiblePathCost = -1;

        while (!queue.isEmpty()) {
            node = queue.remove();
            for (Link link : node.getLinks()) {
                if (link != null) {

                    adjNode = link.getNodeTo();

                    possiblePathCost = link.getCost() + node.getBaseNodeDistance();

                    if (possiblePathCost < adjNode.getBaseNodeDistance()) {
                        adjNode.setBaseNodeDistance(possiblePathCost);
                        adjNode.setPreviousNode(node);
                        queue.updateNode(adjNode);
                    }
                }
            }

        }
        return getHashMap(startNode);
    }

    private void initialize(Node startNode) {
        for (Node n : network.getNodes()) {
            n.setBaseNodeDistance(INFINITY);
            n.setPreviousNode(null);
        }
        startNode.setBaseNodeDistance(0);
        queue.addAll(network.getNodes());
    }

    private HashMap<Node, Node> getHashMap(Node startNode) {
        HashMap<Node, Node> map = new HashMap<Node, Node>();
        Node prev;

        for (Node n : network.getNodes()) {
            if (n != startNode) {
                prev = n.getPreviousNode();
                if (prev == startNode)
                    map.put(n, n);
                else {
                    while (prev.getPreviousNode() != startNode) {
                        prev = prev.getPreviousNode();
                    }
                    map.put(n, prev);
                }
            }
        }
        return map;
    }

    public void printDistance() {
        for (Node node : network.getNodes()) {
            System.out.println(node.getName() + " : " + node.getBaseNodeDistance());
        }
    }

    private static class NodePriorityQueue extends PriorityQueue<Node> {
        public NodePriorityQueue() {
            super();
        }


        public void updateNode(Node node) {
            remove(node);
            add(node);
        }
    }


}
