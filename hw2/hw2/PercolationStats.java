package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
public class PercolationStats {
    public int experiment_num;
    public double[] t;
    public double mean;
    public double Std;
    double confidencelow=0;
    double confidencehigh=0;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N<=0||T<=0){
            throw new IllegalArgumentException();
        }
        experiment_num = T;
        t = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation perc = pf.make(N);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                perc.open(row, col);
            }
            double fraction = (double) perc.numberOfOpenSites() / (N * N);
            t[i] = fraction;
        }
        mean = StdStats.mean(t);
        Std = StdStats.stddev(t);
        confidencelow = mean - ((1.96 * Std) / Math.sqrt(experiment_num));
        confidencehigh = mean + ((1.96 * Std) / Math.sqrt(experiment_num));
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return Std;
    }

    public double confidenceLow() {
        return confidencelow;
    }

    public double confidenceHigh() {
        return confidencehigh;
    }

    public static void main(String arg[]) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats a = new PercolationStats(20, 10, pf);
        System.out.println( a.confidenceHigh());;
        System.out.println(a.mean());
        System.out.println(a.confidenceLow());;
        System.out.println( a.confidenceHigh());
        System.out.println(a.mean());
        System.out.println(a.stddev());
        System.out.println( a.confidenceHigh());;
        System.out.println(a.confidenceLow());;
        System.out.println(a.stddev());



    }
}