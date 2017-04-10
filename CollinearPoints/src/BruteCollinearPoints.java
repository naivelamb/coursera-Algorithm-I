import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private LineSegment[] segments;
	
	public BruteCollinearPoints(Point[] points) {
		checkRepeatedPoints(points);
		
		ArrayList<LineSegment> foundSegments = new ArrayList<>();
		
		for (int p = 0; p < points.length; p++) {
			for (int q = p + 1; q < points.length; q++) {
				for (int r = q + 1; r < points.length; r++) {
					for(int s = r + 1; s < points.length; s++) {
						if(points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) &&
								points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
							Point[] temp = new Point[] {points[p], points[q], points[r], points[s]};
							Arrays.sort(temp);
							foundSegments.add(new LineSegment(temp[0], temp[3]));
						}
					}
				}
			}
		}
		
		segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
	}
	
	public int numberOfSegments() {
		return segments.length;
	}
	
	public LineSegment[] segments() {
		return Arrays.copyOf(segments, numberOfSegments());
	}
	
	private void checkRepeatedPoints(Point[] points) {
		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				if (points[i].compareTo(points[j]) == 0)
					throw new java.lang.IllegalArgumentException();
			}
		}
	}

}
