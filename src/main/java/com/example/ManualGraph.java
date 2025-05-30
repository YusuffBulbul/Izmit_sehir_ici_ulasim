package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ManualGraph {
    private Map<Stop, List<EdgeInfo>> adjacencyList;

    public ManualGraph() {
        adjacencyList = new HashMap<>();
    }

    public void addVertex(Stop stop) {
        if (!adjacencyList.containsKey(stop)) {
            adjacencyList.put(stop, new ArrayList<>());
        }
    }

    public void addEdge(Stop s1, Stop s2, RouteEdge routeEdge) {
        addVertex(s1);
        addVertex(s2);
        adjacencyList.get(s1).add(new EdgeInfo(s1, s2, routeEdge));
        adjacencyList.get(s2).add(new EdgeInfo(s2, s1, routeEdge));
    }

    public Set<Stop> getVertices() {
        return adjacencyList.keySet();
    }

    public List<EdgeInfo> getEdges(Stop stop) {
        return adjacencyList.get(stop);
    }
}
