import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[] openSites;
    private int qtyOpenSites = 0;
    private int virtualTop = 0;
    private int virtualBottom;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n");
        }

        this.n = n;

        // index 0 is the virtual top and index n * n + 1 is the virtual bottom
        virtualBottom = n * n + 1;

        // Grid + 2 (virtualTop and virtualBottom)
        int arraySize = n * n + 2;
        uf = new WeightedQuickUnionUF(arraySize);
        uf2 = new WeightedQuickUnionUF(n * n + 1);
        openSites = new boolean[arraySize];

        for (int i = 0; i < arraySize; i++) {
            // Unite first row with virtualTop
            if (i > 0 && i <= n) {
                uf.union(virtualTop, i);
                uf2.union(virtualTop, i);
            }
        }

        // Virtual top and bottom start open
        openSites[virtualTop] = true;
        openSites[virtualBottom] = true;
    }

    private int rowColumnToIndex(int row, int col) {
        return n * (row - 1) + col;
    }

    private void uniteNeighbors(int row, int col) {
        int current = rowColumnToIndex(row, col);

        // Up neighbor
        if (row - 1 > 0 && isOpen(row - 1, col)) {
            uf.union(current, rowColumnToIndex(row - 1, col));
            uf2.union(current, rowColumnToIndex(row - 1, col));
        }

        // Down neighbor
        if (row + 1 <= n && isOpen(row + 1, col)) {
            uf.union(current, rowColumnToIndex(row + 1, col));
            uf2.union(current, rowColumnToIndex(row + 1, col));
        }

        // Left neighbor
        if (col - 1 > 0 && isOpen(row, col - 1)) {
            uf.union(current, rowColumnToIndex(row, col - 1));
            uf2.union(current, rowColumnToIndex(row, col - 1));
        }

        // Right neighbor
        if (col + 1 <= n && isOpen(row, col + 1)) {
            uf.union(current, rowColumnToIndex(row, col + 1));
            uf2.union(current, rowColumnToIndex(row, col + 1));
        }

        // Virtual bottom
        if (row == n) {
            uf.union(current, virtualBottom);
        }
    }

    private void checkCoordinates(int row, int col) {
        if (row <= 0 || row > n) {
            throw new IllegalArgumentException("row");
        }
        if (col <= 0 || col > n) {
            throw new IllegalArgumentException("col");
        }
    }

    public void open(int row, int col) {
        checkCoordinates(row, col);

        if (isOpen(row, col)) {
            return;
        }

        openSites[rowColumnToIndex(row, col)] = true;
        uniteNeighbors(row, col);
        qtyOpenSites += 1;
    }

    public boolean isOpen(int row, int col) {
        checkCoordinates(row, col);
        return openSites[rowColumnToIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        checkCoordinates(row, col);
        return isOpen(row, col) && uf2.find(virtualTop) == uf2.find(rowColumnToIndex(row, col));
    }

    public int numberOfOpenSites() {
        return qtyOpenSites;
    }

    public boolean percolates() {
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col  = StdIn.readInt();
            percolation.open(row, col);
        }

        StdOut.println("Percolates: " + percolation.percolates());
        StdOut.println("Number of open sites: " + percolation.numberOfOpenSites());
    }
}
