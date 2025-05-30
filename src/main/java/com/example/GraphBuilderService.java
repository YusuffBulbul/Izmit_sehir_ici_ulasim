package com.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraphBuilderService {
    private final CityDataRepository cityDataRepository;

    @Autowired
    public GraphBuilderService(CityDataRepository cityDataRepository) {
        this.cityDataRepository = cityDataRepository;
    }

    public ManualGraph buildGraph() throws Exception {
        CityData cityData = cityDataRepository.loadCityData();

        Map<String, Stop> stopMap = new HashMap<>();
        for (Stop stop : cityData.getDuraklar()) {
            stopMap.put(stop.getId(), stop);
        }

        ManualGraph graph = new ManualGraph();
        for (Stop stop : cityData.getDuraklar()) {
            graph.addVertex(stop);
        }

        for (Stop stop : cityData.getDuraklar()) {
            if (!stop.isSonDurak() && stop.getNextStops() != null) {
                for (NextStopInfo ns : stop.getNextStops()) {
                    Stop target = stopMap.get(ns.getStopId());
                    if (target != null) {
                        RouteEdge edge = new RouteEdge(ns.getMesafe(), ns.getSure(), ns.getUcret());
                        graph.addEdge(stop, target, edge);
                    }
                }
            }
            if (stop.getTransfer() != null) {
                Transfer transfer = stop.getTransfer();
                Stop transferTarget = stopMap.get(transfer.getTransferStopId());
                if (transferTarget != null) {
                    RouteEdge transferEdge = new RouteEdge(0.0, transfer.getTransferSure(), transfer.getTransferUcret());
                    graph.addEdge(stop, transferTarget, transferEdge);
                }
            }
        }
        return graph;
    }
}
