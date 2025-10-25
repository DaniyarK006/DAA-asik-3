package algoritm;

import java.util.*;

import structures.Edge;
import structures.Graph;

public class PrimMST {
    public record Result(List<Edge> mstEdges, int totalCost, long operations, double timeMs) {
    }

    public static Result run(Graph g) {
        long start = System.nanoTime();
        Map<String, List<Edge>> adj = g.adjacencyList();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        long ops = 0;
        List<Edge> mst = new ArrayList<>();
        int total = 0;

        if (g.nodes().isEmpty()) return new Result(mst, 0, ops, 0);

        String startNode = g.nodes().get(0);
        visited.add(startNode);
        ops++;
        for (Edge e : adj.get(startNode)) { pq.add(e); ops++; }

        while (!pq.isEmpty() && mst.size() < g.vertexCount() - 1) {
            Edge e = pq.poll();
            ops++; // poll
            if (visited.contains(e.to)) { ops++; continue; }
            visited.add(e.to); ops++;
            mst.add(e);
            total += e.weight;
            for (Edge ne : adj.get(e.to)) { if (!visited.contains(ne.to)) { pq.add(ne); ops++; } ops++; }
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;
        return new Result(mst, total, ops, timeMs);
    }
}