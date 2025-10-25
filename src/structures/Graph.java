package structures;


import java.util.*;

public record Graph(List<String> nodes, List<Edge> edges) {
    public Graph(List<String> nodes, List<Edge> edges) {
        this.nodes = new ArrayList<>(nodes);
        this.edges = new ArrayList<>(edges);
    }

    public int vertexCount() {
        return nodes.size();
    }

    public int edgeCount() {
        return edges.size();
    }

    public Map<String, List<Edge>> adjacencyList() {
        Map<String, List<Edge>> adj = new HashMap<>();


        for (String n : nodes) {
            adj.put(n, new ArrayList<>());
        }

        for (Edge e : edges) {

            adj.putIfAbsent(e.from, new ArrayList<>());
            adj.putIfAbsent(e.to, new ArrayList<>());

            adj.get(e.from).add(e);
            adj.get(e.to).add(new Edge(e.to, e.from, e.weight));
        }

        return adj;
    }
}
