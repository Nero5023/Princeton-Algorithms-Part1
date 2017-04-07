/**
 * Created by Nero on 17/4/7.
 */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Board initalBoard;

    private SearchNode lastSearchNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException();
        this.initalBoard = initial;
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial, initial.manhattan(), 0, null));

        lastSearchNode = aStarSearch(pq);
        StdOut.println("");
//        MinPQ<SearchNode> pq1 = new MinPQ<SearchNode>();
//        pq1.insert(new );
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return lastSearchNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return null;
    }
// solve a slider puzzle (given below)
    public static void main(String[] args) {
//        In in = new In(args[0]);
        In in = new In("/Users/Nero/Documents/onlineCourse/Princeton-Algorithms-Part1/8_Puzzle/src/8puzzle/puzzle3x3-unsolvable.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }

        solver.printSloutition();
    }

    private SearchNode aStarSearch(MinPQ<SearchNode> pq) {
        SearchNode node = pq.delMin();
        if (node.board.isGoal())
            return node;
        for (Board nebBoard: node.board.neighbors()) {
            if (node.previous == null || !node.previous.board.equals(nebBoard)) {
                pq.insert(new SearchNode(nebBoard, nebBoard.manhattan(), node.moves+1, node));
            }
        }
        return aStarSearch(pq);
    }

    private void printSloutitionIter(SearchNode sNode) {
        if (sNode.previous != null)
            printSloutitionIter(sNode.previous);
        StdOut.println(sNode.board.toString());
    }

    private void printSloutition() {
        printSloutitionIter(lastSearchNode);
    }

    private class SearchNode implements Comparable<SearchNode> {
        public Board board;
        private int manhattan;
        public int moves;
        public SearchNode previous;

        public SearchNode(Board board, int manhattan, int moves, SearchNode previous) {
            this.board = board;
            this.manhattan = manhattan;
            this.moves = moves;
            this.previous = previous;
        }

        public int compareTo(SearchNode another) {
            int aPriority = this.manhattan + this.moves;
            int bPriority = another.manhattan + another.moves;
            if (aPriority < bPriority)
                return -1;
            else if (aPriority == bPriority)
                return 0;
            else // aPriority > bPriority
                return 1;
        }
    }
}
