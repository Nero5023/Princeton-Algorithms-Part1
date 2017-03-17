/**
 * Created by Nero on 17/3/17.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials)  {
        double[] results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                percolation.open(row, col);
            }
            results[i] = percolation.numberOfOpenSites()/(n*n*1.0);
        }
        this.mean = StdStats.mean(results);
        this.stddev = StdStats.stddev(results);
        double temp = 1.96/Math.sqrt(trials);
        this.confidenceLo = mean - temp;
        this.confidenceHi = mean + temp;
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t  = Integer.parseInt(args[1]);
        if (n <= 0 || t <= 0)
            throw new IllegalArgumentException();
        PercolationStats stats = new PercolationStats(n, t);
        System.out.printf("mean                    = %g\nstddev                  = %g\n95%% confidence interval" +
                " = [%g, %g]\n", stats.mean(), stats.stddev(), stats.confidenceLo(), stats.confidenceHi());
    }
}