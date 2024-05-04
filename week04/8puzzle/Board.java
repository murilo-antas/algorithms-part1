import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Objects;

public class Board {
    private final int[][] tiles;
    private final int dimension;
    private Stack<Board> neighbors;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = copyTiles(tiles);
        this.dimension = tiles.length;
        this.neighbors = null;
    }

    private int[][] copyTiles(int[][] tiles) {
        int[][] result = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            result[i] = new int[tiles.length];
            for (int j = 0; j < tiles.length; j++) {
                result[i][j] = tiles[i][j];
            }
        }

        return result;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append(String.format("%2d ", tiles[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int wrongtiles = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != i * dimension + j + 1) {
                    wrongtiles++;
                }
            }
        }

        return wrongtiles;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int value = tiles[i][j];
                if (value == 0) continue;
                int correctI = (value - 1) / dimension;
                int correctJ = value - 1 - correctI * dimension;
                int verticalDistance = Math.abs(correctI - i);
                int horizontalDistance = Math.abs(correctJ - j);
                manhattanDistance += verticalDistance + horizontalDistance;
            }
        }

        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0 && hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (getClass() != y.getClass()) return false;

        Board other = (Board) y;
        if (dimension != other.dimension) {
            return false;
        }
        return Objects.deepEquals(tiles, other.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (neighbors != null) {
            return neighbors;
        }

        neighbors = new Stack<Board>();

        outerLoop:
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == 0) {
                    if (i - 1 >= 0) {
                        Board b = new Board(tiles);
                        b.swap(i, j, i - 1, j);
                        neighbors.push(b);
                    }
                    if (i + 1 < dimension) {
                        Board b = new Board(tiles);
                        b.swap(i, j, i + 1, j);
                        neighbors.push(b);
                    }
                    if (j - 1 >= 0) {
                        Board b = new Board(tiles);
                        b.swap(i, j, i, j - 1);
                        neighbors.push(b);
                    }
                    if (j + 1 < dimension) {
                        Board b = new Board(tiles);
                        b.swap(i, j, i, j + 1);
                        neighbors.push(b);
                    }
                    break outerLoop;
                }
            }
        }

        return neighbors;
    }

    private void swap(int i1, int j1, int i2, int j2) {
        int temp = tiles[i1][j1];
        tiles[i1][j1] = tiles[i2][j2];
        tiles[i2][j2] = temp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(this.tiles);
        int tempI = 0;
        int tempJ = 0;
        int tempTile = 0;
        outerloop:
        for (int i = 0; i < twin.dimension; i++) {
            for (int j = 0; j < twin.dimension; j++) {
                if (twin.tiles[i][j] == 0) {
                    continue;
                }
                if (tempTile == 0) {
                    tempI = i;
                    tempJ = j;
                    tempTile = twin.tiles[i][j];
                }
                else {
                    twin.tiles[tempI][tempJ] = twin.tiles[i][j];
                    twin.tiles[i][j] = tempTile;
                    break outerloop;
                }
            }
        }
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial);

        StdOut.println("Hamming: " + initial.hamming());
        StdOut.println("Manhattan: " + initial.manhattan());
        StdOut.println("Is Goal? " + initial.isGoal());

        Board twin = initial.twin();
        StdOut.println("\nTwin board:");
        StdOut.println(twin);

        StdOut.println("\nNeighbors:");
        for (Board b : initial.neighbors()) {
            StdOut.println(b);
        }
    }
}
