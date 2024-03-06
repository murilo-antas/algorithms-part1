import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("point cannot be null");
            }
        }

        segments = new ArrayList<>();

        Point[] pointsAux = points.clone();

        for (Point p : points) {
            Arrays.sort(pointsAux, p.slopeOrder());
            // StdOut.println("Point: " + p);

            double slope = p.slopeTo(pointsAux[0]);
            List<Point> currentSegment = new ArrayList<>();

            for (int i = 1; i < pointsAux.length; i++) {
                double currentSlope = p.slopeTo(pointsAux[i]);
                if (currentSlope == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("point cannot be duplicate");
                }
                // StdOut.println("Slope: " + slope + " Current slope: " + currentSlope + " Count: " + currentSegment.size());
                if (currentSlope == slope) {
                    currentSegment.add(pointsAux[i]);
                }
                if (currentSlope != slope || i == pointsAux.length - 1) {
                    if (currentSegment.size() >= 3) {
                        Point min = min(currentSegment);
                        if (p.compareTo(min) < 0) {
                            LineSegment lineSegment = new LineSegment(p, max(currentSegment));
                            // StdOut.println(lineSegment);
                            segments.add(lineSegment);
                        }
                    }
                    currentSegment.clear();
                    currentSegment.add(pointsAux[i]);
                    slope = currentSlope;
                }
            }
        }
    }

    private Point min(List<Point> points) {
        Point min = points.get(0);

        for (Point p : points) {
            if (p.compareTo(min) < 0) {
                min = p;
            }
        }

        return min;
    }

    private Point max(List<Point> points) {
        Point max = points.get(0);

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

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        StdOut.println("Total segments: " + collinear.numberOfSegments());
        StdOut.println("end");
    }
}
