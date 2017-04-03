import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.*;

public class PercolationStats {
	
	private double[] fracList;
	private int N_exp;
	/**
	 * perform trails independent experiments on a n-by-n grid
	 * @param n
	 * @param trials
	 */
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		else {
			this.fracList = new double[trials];
			N_exp = trials;
			for (int i = 0; i < trials; i++) {
				Percolation percolationExp = new Percolation(n);
				while (!percolationExp.percolates()) {
					int row = StdRandom.uniform(n) + 1;
					int col = StdRandom.uniform(n) + 1;
					percolationExp.open(row, col);
				}
				this.fracList[i] = percolationExp.numberOfOpenSites()/(n * n + 0.0); 
			}
			
		}
	}
	
	/**
	 * sample mean of percolation threshold
	 * @return
	 */
	public double mean() {
		return StdStats.mean(this.fracList);
	}
	
	/**
	 * sample standard deviation of percolation threshold
	 * @return
	 */
	public double stddev() {
		return StdStats.stddev(this.fracList);
	}
	
	/**
	 * low endpoint of 95% confidence interval
	 * @return
	 */
	public double confidenceLo() {
		return mean() - stddev() * 1.96 / Math.sqrt(this.N_exp);
	}
	
	/**
	 * high endpoint of 95% confidence interval
	 * @return
	 */
	public double confidenceHi() {
		return mean() + stddev() * 1.96 / Math.sqrt(this.N_exp);
	}
	
	public static void main(String[] args) {
		PercolationStats test = new PercolationStats(200, 100);
		System.out.println(test.fracList[1]);
		System.out.println(test.mean());
		System.out.println(test.stddev());
		System.out.println(test.confidenceLo());
		System.out.println(test.confidenceHi());
	}

}
