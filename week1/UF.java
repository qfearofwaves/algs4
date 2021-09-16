/**
    The code UF class represents a union-find data type 
    (also known as the disjoint-sets data type).
    It supports the classic union and find operations, 
    along with a count operation that returns 
    the total number of sets.

    The union-find data type models a collection of 
    sets containing n elements, with each element in 
    exactly one set. The elements are named 0 through n-1.
    Initially, there are n sets, with each element in its 
    own set. The canonical element of a set (also known as the 
    root, identifier, leader, or set representative) is one
    distinguished element in the set. Here is a summary of the 
    operations:

        find(p) returns the canonical element of the set containing
        p. The find operation returns the same value for two elements
        if and only if they are in the same set.

        union(p, q) merges the set containing element p with the set
        containing element q. That is, if p and q are in different
        sets, replace these two sets with a new set that is the union
        of the two.

        count() return the number of sets.

    The canonical element of a set can change only when the set itself
    changes during a call to union; it cannot change during a call to 
    either find or count.

    This implementation uses weighted quick union by rank with path 
    compression by halving.
    The constructor takes Theta(n) time, where n is the number of elements.
    The union and find operations take Theta(logn) time in the worst case.
    The count operations takes Theta(1) time.
    Moreover, starting from an empty data structure with n sites, any 
    intermixed sequence of m union and find operations takes O(m alpha(n)) 
    time, where alpha(n) is the inversion of Ackermann's function (
        https://en.wikipedia.org/wiki/Ackermann_function#Inverse
    )


 */

public class UF {
    private int[] parent;       // parent[i] = parent of i
    private byte[] rank;        // rank[i] = rank of subtree
    private int count;

    public UF(int n) {
        if (n < 0) throw new IllegalArgumentException();
        count = n;
        parent = new int[n];
        rank = new byte[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];      // path compression by halving
            p = parent[p];
        }
        return p;
    }

    public int count() {
        return count;
    }

    @Deprecated
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        if (rank[rootP] < rank[rootQ]) {
            parent[rootP] = rootQ;
        } else if (rank[rootP] > rank[rootQ]) {
            parent[rootQ] = rootP;
        } else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }

    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p > n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        UF uf = new UF(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.find(p) == uf.find(q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}