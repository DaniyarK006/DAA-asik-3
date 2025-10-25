package utils;

import java.util.HashMap;
import java.util.Map;

public class DisjointSet {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();
    public long operations = 0; // Here count union and find operations

    public void makeSet(String x) {
        parent.put(x, x);
        rank.put(x, 0);
        operations++;
    }

    public String find(String x) {
        operations++; // Here is for the find call
        String p = parent.get(x);
        if (p == null) return null;
        if (!p.equals(x)) {
            String root = find(p);
            parent.put(x, root);
            return root;
        }
        return p;
    }

    public boolean union(String a, String b) {
        operations++;
        String ra = find(a);
        String rb = find(b);
        if (ra == null || rb == null) return false;
        if (ra.equals(rb)) return false;
        int rka = rank.get(ra);
        int rkb = rank.get(rb);
        if (rka < rkb) parent.put(ra, rb);
        else if (rka > rkb) parent.put(rb, ra);
        else { parent.put(rb, ra); rank.put(ra, rka+1); }
        operations++;
        return true;
    }
}