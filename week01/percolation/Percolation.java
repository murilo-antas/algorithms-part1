import java.util;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private int n;
    private int[] id;
    private int[] sz;
    private boolean[] open;
    int openSites = 0;
    private int virtualTop = 0;
    private int virtualBottom;

    public Percolation(int n) {
        this.n = n;
        this.virtualBottom = n * n + 1;
        // Grid + 2 
        // index 0 is the virtual top and index n * n + 2 is the virtual bottom
        var arraySize = n * n + 2;
        id = new int[arraySize];
        sz = new int[arraySize];
        open = new boolean[arraySize];

        for (int i = 0; i < arraySize; i++) {
            id[i] = i;
            sz[i] = 1;
            if (i > 0 && i < n * n + 1 && i <= n) {
                union(0, i);
            }
            if (i > n * n - n && i <= n * n) {
                union(virtualBottom, i);
            }
        }

        // Virtual top and bottom start open
        open[0] = true;
        open[arraySize - 1] = true;
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    private void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) {
            return;
        }
        if (sz[i] < sz[j]) {
            id[i] = j; sz[j] += sz[i];
        } else {
            id[j] = i; sz[i] += sz[j];
        }
    }

    private boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private int rowColumnToIndex(int row, int col) {
        return n * (row - 1) + col;
    }

    private int[] getValidNeighbors(int row, int col) {
        var result = new ArrayList<int>();
        if (row - 1 > 0) {
            result.add(rowColumnToIndex(row - 1, col));
        }

        if (row + 1 <= n) {
            result.add(rowColumnToIndex(row + 1, col));
        }

        if (col - 1 > 0) {
            result.add(rowColumnToIndex(row, col - 1));
        }

        if (col + 1 <= n) {
            result.add(rowColumnToIndex(row, col + 1));
        }

        return result.toArray();
    }

    public void open(int row, int col) {
        open[rowColumnToIndex(row, col)] = true;
        var validNeighbors = getValidNeighbors(row, col);
        for (int i : validNeighbors) {
            if (open[i]) {
                union(rowColumnToIndex(row, col), i);
            }
        }
        openSites += 1;
    }

    public boolean isOpen(int row, int col) {
        return open[rowColumnToIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        return isOpen(row, col) && connected(virtualTop, rowColumnToIndex(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation uf = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col  = StdIn.readInt();
            uf.open(row, col);
        }
        StdOut.println(uf.percolates());
    }
}
