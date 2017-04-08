/**
 * Created by Nero on 17/4/7.
 */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private Board initalBoard;

    private SearchNode lastSearchNode;

    private List<Board> solution;

    private boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException();
        this.initalBoard = initial;
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial, initial.manhattan(), 0, null));

        MinPQ<SearchNode> pq1 = new MinPQ<SearchNode>();
        Board twinBoard = initial.twin();
        pq1.insert(new SearchNode(twinBoard, twinBoard.manhattan(), 0, null));

        lastSearchNode = aStarSearch2(pq, pq1);
        solution = new ArrayList<>();
        solutionList(lastSearchNode, solution);
        isSolvable = (solution.get(0) == initial);
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable())
            return lastSearchNode.moves;
        else
            return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable())
            return solution;
        else
            return null;
    }
// solve a slider puzzle (given below)
    public static void main(String[] args) {
        In in = new In(args[0]);
//        In in = new In("/Users/Nero/Documents/onlineCourse/Princeton-Algorithms-Part1/8_Puzzle/src/8puzzle/puzzle3x3-31.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

    private SearchNode aStarSearch(MinPQ<SearchNode> pq, MinPQ<SearchNode> anOtherPq) {

        SearchNode node = pq.delMin();
        if (node.board.isGoal()) {
//            aStarSearch(anOtherPq, pq);
            return node;
        }
        for (Board nebBoard: node.board.neighbors()) {
            if (node.previous == null || !isAlreadyInSolution(node, nebBoard)) {
                pq.insert(new SearchNode(nebBoard, nebBoard.manhattan(), node.moves+1, node));
            }
        }
        return aStarSearch(anOtherPq, pq);
    }

    private boolean isAlreadyInSolution(SearchNode currentSN, Board boardToCheck) {
        while (currentSN.previous != null) {
            if (currentSN.previous.board.equals(boardToCheck))
                return true;
            currentSN = currentSN.previous;
        }
        return false;
    }

    private SearchNode aStarSearch2(MinPQ<SearchNode> pq, MinPQ<SearchNode> anOtherPq) {
        SearchNode sn;
        SearchNode snTwin;
        while (!pq.min().board.isGoal() && !anOtherPq.min().board.isGoal()) {
            sn = pq.delMin();
            for (Board negBoard: sn.board.neighbors()) {
                if (!isAlreadyInSolution(sn, negBoard))
                    pq.insert(new SearchNode(negBoard, negBoard.manhattan(), sn.moves+1, sn));
            }

            snTwin = anOtherPq.delMin();
            for (Board negBoard: snTwin.board.neighbors()) {
                if (!isAlreadyInSolution(snTwin, negBoard))
                    anOtherPq.insert(new SearchNode(negBoard, negBoard.manhattan(), snTwin.moves+1, snTwin));
            }
        }

//        return aStarSearch(anOtherPq, pq);
        sn = pq.delMin();
        snTwin = anOtherPq.delMin();
        if (sn.board.isGoal()) {
            return sn;
        }
        else {
            return snTwin;
        }
    }

    private void solutionList(SearchNode sNode, List<Board> list) {
        if (sNode.previous != null)
            solutionList(sNode.previous, list);
        list.add(sNode.board);
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int manhattan;
        private int moves;
        private SearchNode previous;

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
