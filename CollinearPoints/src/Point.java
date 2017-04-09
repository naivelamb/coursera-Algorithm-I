import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point>{
	
	private final int x;
	private final int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw() {
		StdDraw.point(x, y);
	}
	
	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public int compareTo(Point that) {
		if (this.x == that.x && this.y == that.y) {
			return 0;
		}
		else if (this.y == that.y) {
			if (this.x < that.x) {
				return -1;
			}
			else return 1;
		}
		else {
			if (this.y < that.y) {
				return -1;
			}
			else return 1;
		}
	}
	
	public double slopeTo(Point that) {
		if (this.x == that.x && this.y == that.y) {
			return Double.NEGATIVE_INFINITY;
		}
		else if (this.x == that.x) {
			return Double.POSITIVE_INFINITY;
		}
		else if (this.y == that.y) {
			return +0;
		}
		else {
			return (that.y - this.y)*1.0/(that.x - this.x);
		}
	}
	
	public Comparator<Point> slopeOrder() {
		return new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				// TODO Auto-generated method stub
				double slop1 = slopeTo(p1);
				double slop2 = slopeTo(p2);
				if (slop1 < slop2) return -1;
				else if (slop1 == slop2) return 0;
				else return 1;
			}	
		};
	}
	
	public static void main(String[] args) {
		
	}

}
