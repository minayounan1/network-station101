package com.example.adam;

import java.util.HashMap;

public class Router {

    private static Dijkstra dijkstra;
    private HashMap<Node, Node> routeTable;
    private final Node node;

    public Router(Node node, Network net) {
        routeTable = new HashMap<Node, Node>();
        dijkstra = new Dijkstra(net);
        this.node = node;
    }

    public HashMap<Node, Node> getRouteTable() {
        return routeTable;
    }

    public Node getPath(Node nodeTo) {
        return routeTable.get(nodeTo);
    }

    public void updateRouteTable() {
        routeTable = dijkstra.run(node);
    }


}
