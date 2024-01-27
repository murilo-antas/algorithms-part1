import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class UFQuickUnion {
    private int[] id;

    public UFQuickUnion(int n) {
        id = new int[n];

        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            i = id[i];
        }
        return i;
    }

    void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }

    boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        UFQuickUnion uf = new UFQuickUnion(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                StdOut.println(p + " " + q);
            }
        }
    }
}
