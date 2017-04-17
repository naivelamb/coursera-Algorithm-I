import java.util.LinkedList;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	
	private class Node implements Comparable<Node> {
		private Node previous;
		private Board board;
		private int numMoves = 0;
		
		public Node(Board board) {
			this.board = board;
		}
		
		public Node(Board board, Node previous) {
			this.board = board;
			this.previous = previous;
			this.numMoves = previous.numMoves + 1;
		}

		@Override
		public int compareTo(Node node) {
			return (this.board.manhattan() - node.board.manhattan()) + (this.numMoves - node.numMoves);
		}
		
	}
	
	private Node lastNode;
	
	public Solver(Board initial) {
		if (initial == null) throw new java.lang.NullPointerException();
		
		MinPQ<Node> sols = new MinPQ<Node>();
		sols.insert(new Node(initial));
		
		MinPQ<Node> twinSols = new MinPQ<Node>();
		twinSols.insert(new Node(initial.twin()));
		
		while(true) {
			lastNode = addNode(sols);
			if (lastNode != null || addNode(twinSols) != null) return;
		}
	}
	
	private Node addNode(MinPQ<Node> sols) {
		if (sols.isEmpty()) return null;
		Node bestNode = sols.delMin();
		
		if (bestNode.board.isGoal()) return bestNode;
		for (Board neighbor: bestNode.board.neighbors()) {
			if (bestNode.previous == null || !neighbor.equals(bestNode.previous.board)) {
				sols.insert(new Node(neighbor, bestNode));
			}
		}
		return null;
	}
	
	public boolean isSolvable() {
		return (lastNode != null);
	}
	
	public int moves() {
		return isSolvable()? lastNode.numMoves: -1;
	}
	
	public Iterable<Board> solution() {
		if(!isSolvable()) return null;
				
		Stack<Board> sol = new Stack<Board>();
		Node lastNode_sol = lastNode;
		while(lastNode_sol != null) {
			sol.push(lastNode_sol.board);
			lastNode_sol = lastNode_sol.previous;
		}
		
		return sol;
	}
	
	public static void main(String[] args) {
		
	}
}
