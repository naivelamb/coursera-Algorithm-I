import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private static final boolean VERTICAL = true;
	private static final boolean HORIZONTAL = false;
	
	private Node root;
	private int size;
	
	private static class Node {
		private Point2D p;
		private RectHV rect;
		private Node lb;
		private Node rt;
		
		public Node(Point2D p, RectHV rect) {
			this.p = p;
			this.rect = rect;
		}
	}
	
	public KdTree() {
		size = 0;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public void insert(Point2D p) {
		root = insert(root, p, VERTICAL, 0, 0, 1, 1);
	}
	
	private Node insert(Node x, Point2D p, boolean orientation,
			double xmin, double ymin, double xmax, double ymax) {
		if (x == null) {
			this.size++;
			return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
		}
		if (x.p.equals(p)) {
			return x;
		}
		
		if (orientation == VERTICAL) {
			double cmp = p.x() - x.p.x();
			if (cmp < 0) { //p on the left
				x.lb = insert(x.lb, p, !orientation,
						x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
			}
			else { //p on the right
				x.rt = insert(x.rt, p, !orientation,
						x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
			}
		}
		else {
			double cmp = p.y() - x.p.y();
			if (cmp < 0) { //p on the top
				x.lb = insert(x.lb, p, !orientation,
						x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
			}
			else { //p on the bottom
				x.rt = insert(x.rt, p, !orientation,
						x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
			}
		}
		return x;
	}
	
	public boolean contains(Point2D p) {
		return contains(root, p, VERTICAL);
	}
	
	private boolean contains(Node x, Point2D p, boolean orientation) {
		if (x == null) return false;
		if (x.p.equals(p)) return true;
		
		double cmp;
		if (orientation == VERTICAL) {
			cmp = p.x() - x.p.x();
		}
		else {
			cmp = p.y() - x.p.y();
		}
		
		if (cmp < 0) {
			return contains(x.lb, p, !orientation);
		}
		else {
			return contains(x.rt, p, !orientation);
		}
	}
	
	public void draw() {
		draw(root, VERTICAL);
	}
	
	private void draw(Node x, boolean orientation) {
		if (orientation = VERTICAL) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
		}
		else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
		}
		
		if (x.lb != null) {
			draw(x.lb, !orientation);
		}
		
		if (x.rt != null) {
			draw(x.rt, !orientation);
		}
		
		StdDraw.setPenColor(StdDraw.BLACK);
		x.p.draw();
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> q = new Queue<Point2D>();
		range(root, rect, q);
		return q;
	}
	
	private void range(Node x, RectHV rect, Queue<Point2D> q) {
		if (x == null) {
			return;
		}
		if (!x.rect.intersects(rect)) {
			return;
		}
		if (rect.contains(x.p)) {
			q.enqueue(x.p);
		}
		
		range(x.lb, rect, q);
		range(x.rt, rect, q);
	}
	
	public Point2D nearest(Point2D p) {
		return nearest(root, p, Double.POSITIVE_INFINITY);
	}
	
	private Point2D nearest(Node x, Point2D p, double dist) {
		if (x == null) {
			return null;
		}
		if (x.rect.distanceTo(p) >= dist) {
			return null;
		}
		
		Point2D NN = null;
		double NNDist = dist;
		double d;
		
		d = x.p.distanceTo(p);
		if (d < NNDist) {
			NN = x.p;
			NNDist = d;
		}
		
		Node n1 = x.lb;
		Node n2 = x.rt;
		
		if (n1 != null && n2 != null) {
			if (n1.rect.distanceTo(p) > n2.rect.distanceTo(p)) {
				n1 = x.rt;
				n2 = x.lb;
			}
		}
		
		Point2D NN1 = nearest(n1, p, NNDist);
		
		if (NN1 != null) {
			d = p.distanceTo(NN1);
			if (d < NNDist) {
				NN = NN1;
				NNDist = d;
			}
		}
		
		Point2D NN2 = nearest(n2, p, NNDist);
		if (NN2 != null) {
			d = p.distanceTo(NN2);
			if (d < NNDist) {
				NN = NN2;
				NNDist = d;
			}
		}
		return NN;
	}
	
	public static void main(String[] args) {
		
	}

}
