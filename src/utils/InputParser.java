package utils;

import structures.Edge;
import structures.Graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParser {
    public static <SimpleHeaderTable> List<Graph> parse(String filePath) throws IOException, InterruptedException {
        String s = Files.readString(Paths.get(filePath));

        List<Graph> graphs = new ArrayList<>();
        String gArray = s.substring(s.indexOf("["), s.lastIndexOf("]") + 1);
        String[] items = gArray.split("\\n\\s*\\},\\s*\\{");
        for (String item : items) {
            if (!item.contains("\"nodes\"")) continue;
            // nodes
            int ni = item.indexOf("\"nodes\"");
            int restart = item.indexOf('[', ni);
            int send = item.indexOf(']', restart);

            int start = item.indexOf('[');
            int end = item.indexOf(']');

            if (start >= 0 && end > start) {
                String nodesRaw = item.substring(start + 1, end).replaceAll("[\" ]", "");
                List<String> nodes = nodesRaw.isEmpty() ? new ArrayList<>() : Arrays.asList(nodesRaw.split(","));
            } else {
                System.out.println("Invalid substring range: start=" + start + ", end=" + end);
                List<String> nodes = new ArrayList<>();
            }

            // edges
            List<Edge> edges = new ArrayList<>();
            int ei = item.indexOf("\"edges\"");
            if (ei >= 0) {
                restart = item.indexOf('[', ei);
                send = item.indexOf(']', restart);
                String edgesRaw = item.substring(restart + 1, send);
                String[] es = edgesRaw.split("},\\s*\\{");
                Arrays.stream(es).map(InputParser::apply).forEachOrdered(e -> {
                    String from = extractString(e, "from");
                    String to = extractString(e, "to");
                    int w = Integer.parseInt(extractString(e, "weight"));
                    boolean add = edges.add(new Edge(from, to, w));
                });
            }
            List<String> nodes = List.of();
            graphs.add(new Graph(nodes, edges));
        }
        return graphs;
    }

    private static String extractString(String s, String key) {
        int ki = s.indexOf('"' + key + '"');
        if (ki < 0) ki = s.indexOf(key);
        int colon = s.indexOf(':', ki);
        int comma = s.indexOf(',', colon);
        if (comma < 0) comma = s.length();
        String val = s.substring(colon + 1, comma).trim();
        val = val.replaceAll("\"", "");
        return val;
    }

    private static String apply(String item) {
        return item.replaceAll("[\\{\\}\\[\\]\\n\\r]", "");
    }
}
