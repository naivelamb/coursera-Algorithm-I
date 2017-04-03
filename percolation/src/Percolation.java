import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.*;

public class Percolation {
	
	private WeightedQuickUnionUF grid, grid_for_full;
	private int size, topIdx, botIdx, n_open; // top and bottom point
	private boolean [] status_open, status_open_full;// True -> open
	
	/**
	 *  reate n by n grid, with all sites blocked
	 * @param n
	 */
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Illeagal input.");
		}
		else {
			this.grid = new WeightedQuickUnionUF(n*n + 2);
			this.grid_for_full = new WeightedQuickUnionUF(n*n + 2);
			this.size = n; 
			this.n_open = 0;
			this.topIdx = n*n; 
			this.botIdx = n*n + 1; 
			this.status_open = new boolean [n*n + 2];
			//Arrays.fill(this.status_open, Boolean.FALSE);
			this.status_open[topIdx] = true;
			this.status_open[botIdx] = true;
			this.status_open_full = new boolean [n*n + 2];
			//Arrays.fill(this.status_open_full, Boolean.FALSE);
			this.status_open_full[topIdx] = true;
			/* 
			this.status_open_full = new Boolean [n*n +2];
			this.status_open = new ArrayList<Boolean>(Arrays.asList(new Boolean[n*n + 2]));
			Collections.fill(this.status_open, Boolean.FALSE);
			this.status_open.set(this.topIdx, Boolean.TRUE);
			this.status_open.set(this.botIdx, Boolean.TRUE);
			this.status_open_full = new ArrayList<Boolean>(Arrays.asList(new Boolean[n*n + 2]));
			Collections.fill(this.status_open_full, Boolean.FALSE);
			this.status_open_full.set(this.topIdx, Boolean.TRUE);
			*/
		}
	}
	
	/**
	 * open site (row, col) if it is not open already
	 * @param row
	 * @param col
	 */
	public void open(int row, int col)  {
		if (!isOpen(row, col)) {
			int idx = xyTo1D(row, col); 
			status_open[idx] = true;
			status_open_full[idx] = true;
			this.n_open++;
			ArrayList<Integer> nList = neighbors(row, col);
			for (int i = 0; i < nList.size(); i++) {
				int nIdx = nList.get(i); 
				if (status_open[nIdx]) {
					grid.union(idx, nIdx);
				}
				if (status_open_full[nIdx]) {
					grid_for_full.union(idx, nIdx);
				}
			}
		}
		else {
			// System.out.println("The site is already open.");
		}
	 }  
	 
	/**
	 * is site (row, col) open?
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isOpen(int row, int col) {
		return this.status_open[xyTo1D(row, col)];
	}
	 
	 /**
	  * is site (row, col) full?
	  * @param row
	  * @param col
	  * @return
	  */
	public boolean isFull(int row, int col) {
		return grid_for_full.connected(xyTo1D(row, col), topIdx);
	}
	 
	/**
	 * number of open sites
	 * @return
	 */
	public int numberOfOpenSites() {
		return this.n_open;
	}
	
	/**
	 * does the system percolate?
	 * @return
	 */
	public boolean percolates() {
		return grid.connected(topIdx, botIdx);
	}
	
	/**
	 * convert 2D coordinates to 1D
	 * @param row
	 * @param col
	 * @return
	 */
	private int xyTo1D(int row, int col) {
		if (validateIdx(row, col)) {
			return (row - 1) * size + col - 1;
		}
		else
			throw new IndexOutOfBoundsException("Index out of range.");
	}
	
	private boolean validateIdx(int row, int col) {
		if (row <= size && row >= 1 && col <= size && col >= 1)
			return true;
		else
			return false;	
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return 1D index of the neighbors
	 */
	private ArrayList<Integer> neighbors(int row, int col) {
		ArrayList<Integer> neighborList = new ArrayList<Integer>();
		if (validateIdx(row, col - 1)) {
			neighborList.add(xyTo1D(row, col - 1));
		}
		if (validateIdx(row, col + 1)) {
			neighborList.add(xyTo1D(row, col + 1));
		}
		if (validateIdx(row + 1, col)) {
			neighborList.add(xyTo1D(row + 1, col));
		}
		if (validateIdx(row - 1, col)) {
			neighborList.add(xyTo1D(row - 1, col));
		}
		if (row == 1)
			neighborList.add(topIdx);
		if (row == size)
			neighborList.add(botIdx);
		return neighborList;
	}
	/**
	 * test client (optional)
	 * @param args
	 */
	 public static void main(String[] args)  {
		 Percolation test = new Percolation(5);
		 System.out.println(test.xyTo1D(5, 5));
		 System.out.println(test.isFull(1, 1));
		 System.out.println(test.status_open_full[2]);
		 System.out.println(test.isOpen(1, 1));
		 System.out.print("number of open sites: ");
		 System.out.println(test.numberOfOpenSites());
		 test.open(1, 1);
		 test.open(1, 1);
		 System.out.println("After openning 1, 1");
		 if (test.isOpen(1, 1)) System.out.println("(1, 1) is open");
		 if (test.isFull(1, 1)) System.out.println("(1, 1) is full");
		 if (test.grid.connected(test.xyTo1D(1, 1), 25)) 
			 System.out.println("(1, 1) and top is connected.");
		 else
			 System.out.println("(1, 1) and top is not connected.");
		 System.out.print("number of open sites: ");
		 System.out.println(test.numberOfOpenSites());
		 if (test.percolates()) System.out.print("The grid percolates!");
		 else System.out.println("Doesn't percolate!");
		 test.open(2, 1);
		 test.open(2, 2);
		 test.open(3, 2);
		 test.open(4, 2);
		 test.open(4, 1);
		 test.open(5, 1);
		 System.out.print("number of open sites: ");
		 System.out.println(test.numberOfOpenSites());
		 if (test.percolates()) System.out.println("The grid percolates!");
		 else System.out.println("Doesn't percolate!");
		 test.open(5, 5);
		 if (test.isFull(5, 5)) System.out.println("(5, 5) is full");
	   } 
	}