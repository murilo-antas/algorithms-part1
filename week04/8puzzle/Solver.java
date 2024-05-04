import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver {
    private final Board initial;
    private final SearchNode goal;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial");
        }

        this.initial = initial;
        Board twin = this.initial.twin();

        MinPQ<SearchNode> pq = new MinPQ<>(new ManhattamOrder());
        MinPQ<SearchNode> pqt = new MinPQ<>(new ManhattamOrder());

        pq.insert(new SearchNode(this.initial, 0, null));
        pqt.insert(new SearchNode(twin, 0, null));

        while (!(pq.isEmpty() || pqt.isEmpty())) {
            SearchNode sn = pq.delMin();
            SearchNode snt = pqt.delMin();

            if (sn.board.isGoal()) {
                this.goal = sn;
                return;
            }

            if (snt.board.isGoal()) {
                break;
            }

            for (Board b : sn.board.neighbors()) {
                if (sn.previousSearchNode != null && b.equals(sn.previousSearchNode.board)) {
                    continue;
                }
                pq.insert(new SearchNode(b, sn.numberOfMoves + 1, sn));
            }

            for (Board b : snt.board.neighbors()) {
                if (snt.previousSearchNode != null && b.equals(snt.previousSearchNode.board)) {
                    continue;
                }
                pqt.insert(new SearchNode(b, snt.numberOfMoves + 1, snt));
            }
        }
        goal = null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return goal != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (goal == null) {
            return -1;
        }

        return goal.numberOfMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (goal == null) {
            return null;
        }

        Stack<Board> s = new Stack<>();
        s.push(goal.board);

        SearchNode sn = goal;
        while (sn.previousSearchNode != null) {
            sn = sn.previousSearchNode;
            s.push(sn.board);
        }
        return s;
    }

    // test client
    public static void main(String[] args) {

    }

    private class SearchNode {
        private final Board board;
        private final int numberOfMoves;
        private final SearchNode previousSearchNode;
        private final int manhattamDistance;
        private final int manhattamPriority;

        public SearchNode(Board board, int numberOfMoves, SearchNode previousSearchNode) {
            this.board = board;
            this.numberOfMoves = numberOfMoves;
            this.previousSearchNode = previousSearchNode;
            this.manhattamDistance = board.manhattan();
            this.manhattamPriority = this.manhattamDistance + numberOfMoves;
        }
    }

    private class ManhattamOrder implements Comparator<SearchNode> {
        public int compare(SearchNode o1, SearchNode o2) {
            if (o1.manhattamPriority != o2.manhattamPriority) {
                return Integer.compare(o1.manhattamPriority, o2.manhattamPriority);
            }
            else if (o1.manhattamDistance != o2.manhattamDistance) {
                return Integer.compare(o1.manhattamDistance, o2.manhattamDistance);
            }
            else {
                return Integer.compare(o1.numberOfMoves, o2.numberOfMoves);
            }
        }
    }
}
