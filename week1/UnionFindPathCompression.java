public class UnionFindPathCompression {
    private int[] parent;
    private int[] size;
    private int count;

    public UnionFindPathCompression(int n) {
        parent = new int[n];
        size = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int count() {
        return count;
    }

    int find(int p) {
        int root = p;
        while (root != parent[root]) {
            root = parent[root];
        }

        while (p != root) {
            int newp = parent[p];
            parent[p] = root;
            p = newp;
        }
        return p;
    }

    public void union(int p, int q) {
        int rootp = find(p);
        int rootq = find(q);

        if (size[rootp] > size[rootq]) {
            parent[rootq] = rootp;
            size[rootp] += size[rootq];
        } else {
            parent[rootp] = rootq;
            size[rootq] += size[rootp];
        }
    }
}
