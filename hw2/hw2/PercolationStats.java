package hw2;
import edu.princeton.cs.introcs.StdRandom;
public class PercolationStats {
    public double sum_fraction=0;
    public int experiment_num;
    public double[] t;
    public double mean;
    public double Std;
    public PercolationStats(int N, int T, PercolationFactory pf){
        experiment_num = T;
        t = new double[T];
        for (int i = 0;i<T;i++){
            Percolation perc = pf.make(N);
            while (!perc.percolates()){
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                perc.open(row,col);
            }
            double fraction = (double)perc.numberOfOpenSites()/(N*N);
            t[i] = fraction;
            sum_fraction+=fraction;
        }
    }

    public double mean(){
        mean = sum_fraction/experiment_num;
        return mean;
    }

    public double stddev() {
        double sum_std=0;
        for (int i = 0;i<t.length;i++){
            sum_std += (t[i]-mean)*(t[i]-mean);
        }
        double std = Math.sqrt(sum_std/(experiment_num-1));
        Std = std;
        return Std;
    }

    public double confidenceLow(){
        double confidencelow = mean - ((1.96*Std)/Math.sqrt(experiment_num));
        return confidencelow;
    }

    public double confidenceHigh(){
        double confidencehigh = mean + ((1.96*Std)/Math.sqrt(experiment_num));
        return confidencehigh;
    }


}
