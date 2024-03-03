import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }

        List<LineSegment> segmentList = new ArrayList<>();

        for (int p1 = 0; p1 < points.length - 3; p1++) {
            for (int p2 = p1 + 1; p2 < points.length - 2; p2++) {
                for (int p3 = p2 + 1; p3 < points.length - 1; p3++) {
                    for (int p4 = p3 + 1; p4 < points.length; p4++) {
                        if (points[p1] == null || points[p2] == null || points[p3] == null
                                || points[p4] == null) {
                            throw new IllegalArgumentException("point cannot be null");
                        }
                        if (points[p1].compareTo(points[p2]) == 0
                                || points[p1].compareTo(points[p3]) == 0
                                || points[p1].compareTo(points[p4]) == 0) {
                            throw new IllegalArgumentException("points cannot have duplicates");
                        }

                        double slopeToP2 = points[p1].slopeTo(points[p2]);
                        double slopeToP3 = points[p1].slopeTo(points[p3]);
                        double slopeToP4 = points[p1].slopeTo(points[p4]);
                        if (slopeToP2 == slopeToP3 && slopeToP3 == slopeToP4) {
                            Point[] line = new Point[] {
                                    points[p1], points[p2], points[p3], points[p4]
                            };
                            Point min = min(line);
                            Point max = max(line);
                            segmentList.add(new LineSegment(min, max));
                        }
                    }
                }
            }
        }

        segments = new LineSegment[segmentList.size()];
        segmentList.toArray(segments);
    }

    private Point min(Point[] points) {
        Point min = points[0];

        for (int i = 1; i < points.length; i++) {
            if (min.compareTo(points[i]) > 0) {
                min = points[i];
            }
        }

        return min;
    }

    private Point max(Point[] points) {
        Point max = points[0];

        for (int i = 1; i < points.length; i++) {
            if (max.compareTo(points[i]) < 0) {
                max = points[i];
            }
        }

        return max;
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
    }

}
