
public class Board {
	int[][] blocks;
	
	public Board(int[][] blocks) {
		this.blocks = blocks;
	}
	
	public int dimension() {
		return 0;
	}
	
	public int hamming() {
		return 0;
	}
	
	public int manhattan() {
		return 0;
	}
	
	public boolean isGoal() {
		return false;
	}
	
	public Board twin() {
		return new Board(blocks);
	}
	
	public boolean equals(Object y) {
		return false;
	}
	
	public Iterable<Board> neighbors() {
		return null;
	}
	
	public String toString() {
		return null;
	}
	
	public static void main(String[] args) {
		
	}

}
