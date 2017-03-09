import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * @author Martins Abolins
 * 
 */
public class Percolation {

	private WeightedQuickUnionUF wqu;
	private boolean[] grid;
	private int size;
	private int numOfOpen = 0;

	/**
	 *  Creates new percolation object.
	 *  @param 
	 */
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		this.size = n;
		wqu = new WeightedQuickUnionUF((n * n) + 2);
		grid = new boolean[(n * n) + 2];
	}

	public void open(int row, int col) {
		checkInput(row, col);

		int index = getIndex(row, col);

		if (!isOpen(row, col)) {
			grid[index] = true;
			numOfOpen++;

			// If top or bottom of grid, connect to the associated abstract site
			if (row == 1)
				wqu.union(index, 0);
			if (row == this.size)
				wqu.union(index, (this.size * this.size) + 1);

			// Connects given site to TOP site, if it's open
			if (row > 1 && isOpen(row - 1, col)) {
				wqu.union(index, getIndex(row - 1, col));
			}

			// Connects given site to RIGHT site, if it's open
			if (col < this.size && isOpen(row, col + 1)) {
				wqu.union(index, getIndex(row, col + 1));
			}

			// Connects given site to BOTTOM site, if it's open
			if (row < this.size && isOpen(row + 1, col)) {
				wqu.union(index, getIndex(row + 1, col));
			}

			// Connects given site to LEFT site, if it's open
			if (col > 1 && isOpen(row, col - 1)) {
				wqu.union(index, getIndex(row, col - 1));
			}
		}
	}

	public boolean isOpen(int row, int col) {
		checkInput(row, col);
		return grid[getIndex(row, col)];
	}

	public boolean isFull(int row, int col) {
		checkInput(row, col);
		return wqu.connected(getIndex(row, col), 0);
	}

	public int numberOfOpenSites() {
		return numOfOpen;
	}

	public boolean percolates() {
		return wqu.connected(0, (size*size)+1);
	}

	private int getIndex(int row, int col) {
		return (row - 1) * this.size + col;
	}

	private void checkInput(int row, int col) {
		if (row < 1 || row > this.size || col < 1 || col > this.size) {
			throw new java.lang.IndexOutOfBoundsException();
		}
	}
}