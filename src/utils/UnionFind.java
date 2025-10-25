
package utils;

import java.util.HashMap;
import java.util.Map;

public class UnionFind {
    private final Map<String, String> parent;
    private final Map<String, Integer> rank;
    private long ops;

    public UnionFind(Iterable<String> elements) {
        this.parent = new HashMap<>();
        this.rank = new HashMap<>();
        this.ops = 0;

        for (String elem : elements) {
            parent.put(elem, elem);
            rank.put(elem, 0);
        }
    }

    public String find(String x) {
        ops++;
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    public boolean union(String x, String y) {
        ops++;
        String rootX = find(x);
        String rootY = find(y);

        if (rootX.equals(rootY)) {
            return false;
        }

        int rankX = rank.get(rootX);
        int rankY = rank.get(rootY);

        if (rankX < rankY) {
            parent.put(rootX, rootY);
        } else if (rankX > rankY) {
            parent.put(rootY, rootX);
        } else {
            parent.put(rootY, rootX);
            rank.put(rootX, rankX + 1);
        }

        return true;
    }

    public long getOperations() {
        return ops;
    }
}