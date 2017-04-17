import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private int[][] blocks;
	
	public Board(int[][] blocks) {
		this.blocks = copy(blocks);
	}
	
	private int[][] copy(int[][] blocks) {
		int[][] copy = new int[blocks.length][blocks.length];
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				copy[i][j] = blocks[i][j];
			}
		}
		return copy;
	}
	
	public int dimension() {
		return this.blocks.length;
	}
	
	public int hamming() {
		int ham = 0;
		for (int i = 0; i < this.dimension(); i++) {
			for (int j = 0; j < this.dimension(); j++) {
				if(blocks[i][j] != 0) {
					if(blocks[i][j] != i*this.dimension() + j + 1) ham++;
				}
			}
		}
		return ham;
	}
	
	public int manhattan() {
		int man = 0;
		for (int i = 0; i < this.dimension(); i++) {
			for (int j = 0; j < this.dimension(); j++) {
				int row = 0;
				int col = 0;
				if (blocks[i][j] != 0) {
					row = (blocks[i][j] - 1)/this.dimension();
					col = (blocks[i][j] - 1)%this.dimension();
					man += Math.abs(row - i) + Math.abs(col - j);
				}
			}
		}
		return man;
	}
	
	public boolean isGoal() {
		return this.hamming() == 0;
	}
	
	public Board twin() {
		for (int i = 0; i < this.dimension(); i ++) {
			for (int j = 0; j < this.dimension() - 1 ; j ++) {
				if (!isSpace(i, j) && !isSpace(i, j + 1)) return swap(i, j, i, j + 1);
			}
		}
		throw new RuntimeException();
	}
	
	private boolean isSpace(int i, int j) {
		int[] space = spaceIdx();
		if (i < this.dimension() && j < this.dimension()) {
			return (space[0] == i && space[1] == j);
		}
		return false;
	}
	
	public boolean equals(Object y) {
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;
		Board that = (Board) y;
		if (that.dimension() != this.dimension()) return false;
		for (int i = 0; i < this.dimension(); i++) {
			for (int j = 0; j< this.dimension(); j++) {
				if (this.blocks[i][j] != that.blocks[i][j]) return false;
			}
		}
		return true;
	}
	
	public Iterable<Board> neighbors() {
		LinkedList<Board> neighbors = new LinkedList<Board>();
		int [] space = spaceIdx();
		int spaceRow = space[0];
		int spaceCol = space[1];
		if (spaceRow > 0) neighbors.add(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol));
		if (spaceRow < this.dimension() - 1) neighbors.add(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol));
		if (spaceCol > 0) neighbors.add(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1));
		if (spaceCol < this.dimension() - 1) neighbors.add(swap(spaceRow, spaceCol, spaceRow, spaceCol + 1));
		return neighbors;
	}
	
	private int[] spaceIdx() {
		int[] spaceIdx = new int[2];
		for (int i = 0; i < this.dimension(); i++) {
			for(int j = 0; j < this.dimension(); j++)
				if (this.blocks[i][j] == 0) {
					spaceIdx[0] = i;
					spaceIdx[1] = j;
					return spaceIdx;
				}
		}
		throw new RuntimeException();
	}
	
	private Board swap(int row, int col, int newrow, int newcol) {
		int[][] newblocks = copy(blocks);
		int temp = newblocks[row][col];
		newblocks[row][col] = newblocks[newrow][newcol];
		newblocks[newrow][newcol] = temp;
		Board newBoard = new Board(newblocks);
		return newBoard;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(this.dimension() + "\n");
		for(int i = 0; i < this.dimension(); i++) {
			for(int j = 0; j <this.dimension() ; j++) {
				s.append(String.format("%2d ", blocks[i][j]));
			}
			s.append("\n");
		};
		return s.toString();
	}
	
	public static void main(String[] args) {
		In in = new In("8puzzle-test/" + "puzzle04.txt");
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);
	    StdOut.println(initial.toString());
	    StdOut.println(initial.hamming());
	    StdOut.println(initial.manhattan());
	    for (Board test: initial.neighbors()) {
	    	StdOut.println(test.toString());
	    }
	    
	    /*
	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	            */
	    }

}
