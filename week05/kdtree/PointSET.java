import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

public class PointSET {
    private SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("p");

        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("p");

        return points.contains(p);
    }

    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("rect");

        List<Point2D> result = new ArrayList<>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                result.add(point);
            }
        }
        return result;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("p");

        Point2D result = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (Point2D point : points) {
            double currentDistance = point.distanceSquaredTo(p);
            if (currentDistance < minDistance) {
                result = point;
                minDistance = currentDistance;
            }
        }

        return result;
    }

    public static void main(String[] args) {

    }
}
