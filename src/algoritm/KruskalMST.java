package algoritm;

import java.util.*;

import structures.Edge;
import structures.Graph;
import utils.DisjointSet;

public class KruskalMST {
    public record Result(List<Edge> mstEdges, int totalCost, long operations, double timeMs) {
    }

    public static Result run(Graph g) {
        long start = System.nanoTime();
        List<Edge> edges = new ArrayList<>(g.edges());
        Collections.sort(edges);
        DisjointSet ds = new DisjointSet();
        for (String n : g.nodes()) ds.makeSet(n);

        List<Edge> mst = new ArrayList<>();
        int total = 0;
        for (Edge e : edges) {
            // operations counted inside DisjointSet
            if (ds.union(e.from, e.to)) {
                mst.add(e);
                total += e.weight;
                if (mst.size() == g.vertexCount() - 1) break;
            }
        }
        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;
        long ops = ds.operations;
        return new Result(mst, total, ops, timeMs);
    }
}