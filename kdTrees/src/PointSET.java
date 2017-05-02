import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	private SET<Point2D> set;
	
	public PointSET() {
		set = new SET<Point2D>();
	}
	
	public boolean isEmpty() {
		return set.isEmpty();
	}
	
	public int size() {
		return set.size();
	}
	
	public void insert(Point2D p) {
		set.add(p);
	}
	
	public boolean contains(Point2D p) {
		return set.contains(p);
	}
	
	public void draw() {
		for (Point2D p: set) {
			p.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> q = new Queue<Point2D>();
		for (Point2D p: set) {
			if (rect.contains(p)) {
				q.enqueue(p);
			}
		}
		return q;
	}
	
	public Point2D nearest(Point2D p) {
		Point2D NN = null;
		double NNDist = Double.MAX_VALUE;
		
		for (Point2D p0: set) {
			double dist = p.distanceTo(p0);
			if (dist < NNDist) {
				NN = p0;
				NNDist = dist;
			}
		}
		return NN;
	}
	
	public static void main(String[] args) {
		
	}
}
