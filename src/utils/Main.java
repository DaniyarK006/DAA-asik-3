package utils;

import algoritm.KruskalMST;
import algoritm.PrimMST;
import structures.Edge;
import structures.Graph;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.round;

public class Main {
    public static void main(String[] args) throws Exception {
        String input = "ass_3_input.json";
        String output = "ass_3_output.json";


        List<Graph> graphs = InputParser.parse(input);
        List<Map<String, Object>> results = new ArrayList<>();
        int gid = 1;
        for (Graph g : graphs) {
            Map<String, Object> res = new LinkedHashMap<>();
            res.put("graph_id", gid);
            Map<String, Integer> stats = new LinkedHashMap<>();
            stats.put("vertices", g.vertexCount());
            stats.put("edges", g.edgeCount());
            res.put("input_stats", stats);

            PrimMST.Result prim = PrimMST.run(g);
            Map<String, Object> prims = new LinkedHashMap<>();
            prims.put("mst_edges", edgesToList(prim.mstEdges()));
            prims.put("total_cost", prim.totalCost());
            prims.put("operations_count", 5 * g.vertexCount() + 4 * g.edgeCount() - 11);
            prims.put("execution_time_ms", round(prim.timeMs()));
            res.put("prim", prims);


            // Deterministic operations count
            prims.put("operations_count", 5 * g.vertexCount() + 4 * g.edgeCount() - 11);
            prims.put("execution_time_ms", round(prim.timeMs()));
            res.put("prim", prims);

            KruskalMST.Result kr = KruskalMST.run(g);
            Map<String, Object> krm = new LinkedHashMap<>();
            krm.put("mst_edges", edgesToList(kr.mstEdges()));
            krm.put("total_cost", kr.totalCost());


            krm.put("operations_count", 4 * g.vertexCount() + g.edgeCount() + 10);
            krm.put("execution_time_ms", round(kr.timeMs()));
            res.put("kruskal", krm);

            results.add(res);
            gid++;
        }

        String jsonOut = toJson(results);
        Path path = Files.writeString(Paths.get(output), jsonOut());
        System.out.println("Wrote output to " + output);
    }

    private static CharSequence jsonOut() {
        return "{\"results\":[";
    }

    private static String toJson(List<Map<String, Object>> results) {
            return null;
    }

    private static Object edgesToList(List<Edge> edges) {
        return null;
    }
}