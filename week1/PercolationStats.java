import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

 public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;

    private final int trials;
    private final double[] stats;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        stats = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation pc = new Percolation(n);
            while (!pc.percolates()) {
                // choose a point uniformly random
                int rdm = StdRandom.uniform(n*n); // random num 0 - n*n-1
                int row = rdm / n;
                int col = rdm % n;
                // starting with 1
                row++;
                col++;
                pc.open(row, col);
            }
            // System.out.println(pc.numberOfOpenSites() + "---");
            stats[i] = (double) pc.numberOfOpenSites() / (n * n);
            // System.out.println(stats[i]);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(stats);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(stats);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

   // test client (see below)
   public static void main(String[] args) {
       int n = Integer.parseInt(args[0]);
       int t = Integer.parseInt(args[1]);

    //    System.out.println(N);
    //    System.out.println(T);
       
       PercolationStats pcSts = new PercolationStats(n, t);
       System.out.println("mean                    = " + pcSts.mean());
       System.out.println("stddev                  = " + pcSts.stddev());
       System.out.println("95% confidence interval = [" + pcSts.confidenceLo()
            + ", " + pcSts.confidenceHi() + "]"
       );
   }
}