import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class KdTree {
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

    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("p");
        root = insert(root, p, new RectHV(0.0, 0.0, 1.0, 1.0), true);
    }

    private Node insert(Node x, Point2D p, RectHV rect, boolean isVertical) {
        if (x == null) {
            size++;
            return new Node(p, rect);
        }

        if (p.equals(x.p)) return x;

        int cmp = isVertical ?
                  Point2D.X_ORDER.compare(p, x.p) :
                  Point2D.Y_ORDER.compare(p, x.p);

        RectHV newRect = createNewRect(x, isVertical, cmp < 0);

        if (cmp < 0) {
            x.lb = insert(x.lb, p, newRect, !isVertical);
        }
        else {
            x.rt = insert(x.rt, p, newRect, !isVertical);
        }

        return x;
    }

    private RectHV createNewRect(Node x, boolean isVertical, boolean isLeft) {
        // Cache rectangle and point coordinates
        double xmin = x.rect.xmin();
        double ymin = x.rect.ymin();
        double xmax = x.rect.xmax();
        double ymax = x.rect.ymax();
        double px = x.p.x();
        double py = x.p.y();

        if (isVertical) {
            return isLeft ?
                   new RectHV(xmin, ymin, px, ymax) :
                   new RectHV(px, ymin, xmax, ymax);
        }
        else {
            return isLeft ?
                   new RectHV(xmin, ymin, xmax, py) :
                   new RectHV(xmin, py, xmax, ymax);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("p");
        return get(root, p, true) != null;
    }

    private static Point2D get(Node x, Point2D p, boolean isVertical) {
        if (x == null) return null;

        if (p.equals(x.p)) return x.p;

        int cmp = isVertical ?
                  Point2D.X_ORDER.compare(p, x.p) :
                  Point2D.Y_ORDER.compare(p, x.p);

        return cmp < 0 ? get(x.lb, p, !isVertical) : get(x.rt, p, !isVertical);
    }

    public void draw() {
        drawPrivate(root, true);
    }

    private static void drawPrivate(Node n, boolean isVertical) {
        if (n == null) return;

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(Color.black);
        StdDraw.point(n.p.x(), n.p.y());

        StdDraw.setPenRadius();
        if (isVertical) {
            StdDraw.setPenColor(Color.red);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        }
        else {
            StdDraw.setPenColor(Color.blue);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }

        drawPrivate(n.lb, !isVertical);
        drawPrivate(n.rt, !isVertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect");
        }
        List<Point2D> result = new ArrayList<>();

        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node n = stack.pop();
            if (n == null) continue;

            if (rect.contains(n.p)) result.add(n.p);
            if (n.lb != null && n.lb.rect.intersects(rect)) stack.push(n.lb);
            if (n.rt != null && n.rt.rect.intersects(rect)) stack.push(n.rt);
        }

        return result;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("p");
        return nearestPrivate(p, root, null, Double.POSITIVE_INFINITY, true);
    }

    private static Point2D nearestPrivate(Point2D p, Node n, Point2D nearest, double minDistance,
                                          boolean isVertical) {
        if (n == null) return nearest;

        double currentDistance = n.p.distanceSquaredTo(p);
        if (currentDistance < minDistance) {
            nearest = n.p;
            minDistance = currentDistance;
        }

        int cmp = isVertical ?
                  Point2D.X_ORDER.compare(p, n.p) :
                  Point2D.Y_ORDER.compare(p, n.p);

        Node first = cmp < 0 ? n.lb : n.rt;
        Node second = cmp < 0 ? n.rt : n.lb;

        // Search the subtree that contains the query point first
        if (first != null) {
            nearest = nearestPrivate(p, first, nearest, minDistance, !isVertical);
            minDistance = nearest.distanceSquaredTo(p);
        }

        // Only search the other subtree if it could contain a closer point
        if (second != null && second.rect.distanceSquaredTo(p) < minDistance) {
            nearest = nearestPrivate(p, second, nearest, minDistance, !isVertical);
        }

        return nearest;
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.5, 0.6));
        kdTree.insert(new Point2D(0.4, 0.3));
        kdTree.insert(new Point2D(0.4, 0.3));
        kdTree.insert(new Point2D(0.4, 0.3));
        kdTree.insert(new Point2D(0.4, 0.3));
        kdTree.insert(new Point2D(0.4, 0.3));
        kdTree.insert(new Point2D(0.4, 0.3));
        kdTree.insert(new Point2D(0.4, 0.3));
        StdOut.println(kdTree.contains(new Point2D(0.5, 0.6)));
        StdOut.println(kdTree.contains(new Point2D(0.4, 0.3)));

    }
}
