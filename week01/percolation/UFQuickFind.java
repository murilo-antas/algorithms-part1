import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class UFQuickFind {
    private int[] id;

    public UFQuickFind(int n) {
        id = new int[n];

        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    void union(int p, int q) {
        var idP = id[p];
        var idQ = id[q];

        for (int i = 0; i < id.length; i++) {
            if (id[i] == idP) {
                id[i] = idQ;
            }
        }
    }

    boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        UFQuickFind uf = new UFQuickFind(n);
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
