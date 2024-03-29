import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("point cannot be null");
            }
        }

        Point[] pointsAux = points.clone();

        Arrays.sort(pointsAux);
        for (int i = 0; i < pointsAux.length - 1; i++) {
            if (pointsAux[i].compareTo(pointsAux[i + 1]) == 0) {
                throw new IllegalArgumentException("points cannot have duplicates");
            }
        }

        segments = new ArrayList<>();

        for (int p1 = 0; p1 < points.length - 3; p1++) {
            for (int p2 = p1 + 1; p2 < points.length - 2; p2++) {
                for (int p3 = p2 + 1; p3 < points.length - 1; p3++) {
                    for (int p4 = p3 + 1; p4 < points.length; p4++) {
                        double slopeToP2 = points[p1].slopeTo(points[p2]);
                        double slopeToP3 = points[p1].slopeTo(points[p3]);
                        double slopeToP4 = points[p1].slopeTo(points[p4]);
                        if (slopeToP2 == slopeToP3 && slopeToP3 == slopeToP4) {
                            Point[] line = new Point[] {
                                    points[p1], points[p2], points[p3], points[p4]
                            };
                            Point min = min(line);
                            Point max = max(line);
                            segments.add(new LineSegment(min, max));
                        }
                    }
                }
            }
        }
    }

    private Point min(Point[] points) {
        Point min = points[0];

        for (Point p : points) {
            if (p.compareTo(min) < 0) {
                min = p;
            }
        }

        return min;
    }

    private Point max(Point[] points) {
        Point max = points[0];

        for (Point p : points) {
            if (p.compareTo(max) > 0) {
                max = p;
            }
        }

        return max;
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        StdOut.println("Total segments: " + collinear.numberOfSegments());
        StdOut.println("end");
    }
}
