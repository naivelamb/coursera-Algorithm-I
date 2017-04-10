import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FastCollinearPoints {
	private List<LineSegment> segments = new ArrayList<>();
	private HashMap<Double, List<Point>> foundSegments = new HashMap<>();
	
	public FastCollinearPoints(Point[] points) {
		checkRepeatedPoints(points);
		
		for (Point startPoint: points) {
			Arrays.sort(points, startPoint.slopeOrder());
			double previousSlope = startPoint.slopeTo(startPoint);
			List<Point> slopePoints = new ArrayList<>();
			for (int i = 1; i < points.length; i ++) {
				double slope = startPoint.slopeTo(points[i]);
				if (slope == previousSlope) {
					slopePoints.add(points[i]);
				}
				else {
					if (slopePoints.size() >= 3) {
						slopePoints.add(startPoint);
						Collections.sort(slopePoints);
						addSegmentsIfNew(slopePoints.get(0), slopePoints.get(slopePoints.size() - 1));
					}
					slopePoints.clear();
					slopePoints.add(points[i]);
				}
				previousSlope = slope;
			}
			
			if (slopePoints.size() >= 3) {
				slopePoints.add(startPoint);
				Collections.sort(slopePoints);
				addSegmentsIfNew(slopePoints.get(0), slopePoints.get(slopePoints.size() - 1));
			}
		}
	}
	
	public int numberOfSegments() {
		return segments.size();
	}
	
	public LineSegment[] segments() {
		return segments.toArray(new LineSegment[numberOfSegments()]);
	}
	
	private void addSegmentsIfNew(Point p, Point q) {
		double slope = p.slopeTo(q);
		if (!foundSegments.containsKey(slope)) {
			List<Point> endPoints = new ArrayList<>();
			endPoints.add(q);
			foundSegments.put(slope, endPoints);
			segments.add(new LineSegment(p, q));
		}
		else {
			List<Point> endPoints = foundSegments.get(slope);
			for (Point endPoint: endPoints) {
				if (endPoint.compareTo(q) == 0) {
					return;
				}
			}
			endPoints.add(q);
			segments.add(new LineSegment(p, q));
		}
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
