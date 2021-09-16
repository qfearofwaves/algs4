import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int sizeN;
    private final WeightedQuickUnionUF uf;
    private int count;

    private final int top;
    private final int bottom;
    // top node is 0
    // bottom node is 
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[n+1][n+1];
        sizeN = n;
        uf = new WeightedQuickUnionUF((n+1)*(n+1));
        count = 0;
        top = 0;
        bottom = (n+1)*(n+1)-1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // false is closed
                // true is open
                grid[i][j] = false;
            }
        }

        // union the top node with the first row
        for (int j = 1; j <= n; j++) {
            uf.union(top, j);
            // System.out.println(j);
        }
        
        // System.out.println();

        // union the bottom node with the last row
        for (int j = 1; j <= n; j++) {
            uf.union(bottom, (n-1) * sizeN + j);
            // System.out.println((n-1)*N+j);
        }

        // System.out.println(bottom);

        // for (int i = 1; i <= n; i++) {
        //     for (int j = 1; j <= n; j++) {
        //         // 0 is closed
        //         // 1 is open
        //         System.out.print(grid[i][j] + " ");
        //     }
        //     System.out.println();
        // }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            count++;
            unionNeighbor(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > sizeN) {
            throw new IllegalArgumentException();
        }
        if (col < 1 || col > sizeN) {
            throw new IllegalArgumentException();
        }

        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > sizeN) {
            throw new IllegalArgumentException();
        }
        if (col < 1 || col > sizeN) {
            throw new IllegalArgumentException();
        }

        int current = (row - 1) * sizeN + col;
        // is it connected to the top?
        // must be open first
        return isOpen(row, col) && (uf.find(top) == uf.find(current));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return (count > 0) && (uf.find(top) == uf.find(bottom));
    }

    private void unionNeighbor(int row, int col) {
        if (row < 1 || row > sizeN) {
            return;
        }
        if (col < 1 || col > sizeN) {
            return;
        }
        int current = (row - 1) * sizeN + col;
        // up
        if (isInbound(row-1, col) && isOpen(row-1, col)) {
            int up = (row - 2) * sizeN + col;
            uf.union(current, up);
        }
        // down
        if (isInbound(row+1, col) && isOpen(row+1, col)) {
            int down = row * sizeN + col;
            uf.union(current, down);
        }
        // left
        if (isInbound(row, col-1) && isOpen(row, col-1)) {
            int left = (row - 1) * sizeN + (col - 1);
            uf.union(current, left);
        }
        // right
        if (isInbound(row, col+1) && isOpen(row, col+1)) {
            int right = (row - 1) * sizeN + (col + 1);
            uf.union(current, right);
        }
    }

    private boolean isInbound(int row, int col) {
        if (row < 1 || row > sizeN) {
            return false;
        }
        if (col < 1 || col > sizeN) {
            return false;
        }
        return true;
    }

    // test client (optional)
    public static void main(String[] args) {
        // int n = 5;
        // Percolation pc = new Percolation(n);
    }
}