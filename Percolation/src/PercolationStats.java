import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double[] results;
    private int sites;

    /**
     * perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.sites = n * n;
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            int count = 0;
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int a = StdRandom.uniform(1, n + 1);
                int b = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(a, b)) {
                    percolation.open(a, b);
                    ++count;
                }
            }
            results[i] = (double) count / this.sites;
        }
    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        /* double stdDeviation =0 ;
        double mean = StdStats.mean(results);
        for(int i=0; i< results.length; i++) {
            stdDeviation += ((results[i] - mean) *(results[i] - mean));
        }
        stdDeviation += stdDeviation/(trials-1);
        stdDeviation += Math.sqrt(stdDeviation);
        return stdDeviation;*/
        return StdStats.stddev(results);
    }

    /**
     * low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return (mean() - (1.96 * stddev() / Math.sqrt(trials)));
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return (mean() - (1.96 * stddev() / Math.sqrt(trials)));
    }

    public static void main(String[] args) {
        // test client (described below)
        if (args.length < 1) {
            args = new String[2];
            args[0] = "4";
            args[1] = "100";
        }
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        if (n <= 0 || trails <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        PercolationStats ps = new PercolationStats(n, trails);
        StdOut.printf("mean = %f\n", ps.mean());
        StdOut.printf("stddev = %f\n", ps.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", ps.confidenceLo(), ps.confidenceHi());
    }
}
