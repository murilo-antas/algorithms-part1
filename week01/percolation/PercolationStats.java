import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double[] thresholds;
    private int trials;

    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n");
        }

        if (trials <= 0) {
            throw new IllegalArgumentException("trials");
        }

        this.trials = trials;
        thresholds = new double[trials];

        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                percolation.open(row, col);
            }
            thresholds[t] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        var ps = new PercolationStats(n, t);
        StdOut.println("mean\t\t\t = " + ps.mean());
        StdOut.println("stddev\t\t\t = " + ps.stddev());
        StdOut.println("95% confidence interval\t = [" + ps.confidenceLo() +
                ", " + ps.confidenceHi() + "]");
    }
}
