
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private double[] percThresholdArr;

	public PercolationStats(int n, int trials) {
		
		checkInput(n, trials);
		
		// Create the aray that will hold threshold values
		percThresholdArr = new double[trials];
		
		// Populate the threshold array
		for(int i = 0; i < trials; i++){
			percThresholdArr[i] = getPercThreshold(n);
		}
	}
	
	public double mean() {
		return StdStats.mean(percThresholdArr);
	}
	
	public double stddev() {
		return StdStats.stddev(percThresholdArr);
	}
	
	public double confidenceLo() {
		return mean() - ((1.96 * stddev())/(Math.sqrt(percThresholdArr.length)));
	}
	
	public double confidenceHi() {
		return this.mean() + ((1.96*this.stddev())/(Math.sqrt(percThresholdArr.length)));
	}
	
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);

		PercolationStats percStats = new PercolationStats(n, t);
		
		// Lets imagine this file is changed
		
		System.out.println("mean                    = " + percStats.mean());
        System.out.println("stddev                  = " + percStats.stddev());
        System.out.println("95% confidence interval = " + "[" + percStats.confidenceLo() + ", "
                + percStats.confidenceHi()+"]");
	}

	private void checkInput(int n, int trials){
		if(n < 1 || trials < 1){
			throw new IllegalArgumentException();
		}
	}
	
	private double getPercThreshold(int n){
		Percolation perc = new Percolation(n);
		int row, col;
		while(!perc.percolates()){
			do {
				row = StdRandom.uniform(1, n+1);
				col = StdRandom.uniform(1, n+1);
			} while(perc.isOpen(row, col));
			perc.open(row, col);
		}
		return ((double)perc.numberOfOpenSites()) / (n*n); 
	}
}











