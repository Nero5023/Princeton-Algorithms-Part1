/**
 * Created by Nero on 17/4/7.
 */

import java.util.List;
import java.util.ArrayList;

public class Board {
    private int[][] blocks;
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = cloneArray(blocks);
    }

    // board dimension n
    public int dimension() {
        return this.blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int block = blocks[i][j];
                if (block == 0)
                    continue;
                int index = i * dimension() + j + 1;
                if (index != this.blocks[i][j])
                    sum += 1;
            }
        }
        return sum;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int block = blocks[i][j];
                if (block == 0)
                    continue;
                int target = block;
                int targetI = (target - 1) / dimension();
                int targetJ = (target - 1) % dimension();
                sum += (Math.abs(targetI - i) + Math.abs(targetJ - j));
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int block = blocks[i][j];
                if (block == 0) {
                    if (i == dimension() - 1 && j == dimension() - 1)
                        continue;
                    else
                        return false;
                }
                int target = i * dimension() + j + 1;
                if (target != block)
                    return false;
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
//        int i = StdRandom.uniform(dimension());
//        int j = StdRandom.uniform(dimension());
//        if (j+1 >= dimension())
//            return twin();
//        if (blocks[i][j] == 0 || blocks[i][j+1] == 0)
//            return twin();
//        int[][] newBlocks = cloneArray(this.blocks);
//        exchange(newBlocks, i, j, i, j+1);
//        return new Board(newBlocks);

        int[][] newBlocks = cloneArray(this.blocks);

        int i = 0;
        int j = 0;
        while (newBlocks[i][j] == 0 || newBlocks[i][j + 1] == 0) {
            j++;
            if (j >= newBlocks.length - 1) {
                i++;
                j = 0;
            }
        }

        exchange(newBlocks, i, j, i, j + 1);
        return new Board(newBlocks);

    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (this == y)
            return true;

        if (y.getClass() == Board.class) {
            Board aBoard = (Board) y;
            if (aBoard.dimension() != this.dimension())
                return false;
            for (int i = 0; i < dimension(); i++) {
                for (int j = 0; j < dimension(); j++) {
                    if (this.blocks[i][j] != aBoard.blocks[i][j])
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        BoardIndex emptyIndex = emptyBlockIndex();
        List<BoardIndex> nebIndexs = emptyIndex.indexsAround();

        List<Board> nebBoards = new ArrayList<Board>();

        for (BoardIndex boardIndex: nebIndexs) {
            int[][] newBlocks = cloneArray(this.blocks);
            exchange(newBlocks, emptyIndex.row, emptyIndex.col, boardIndex.row, boardIndex.col);
            nebBoards.add(new Board(newBlocks));
        }
        return nebBoards;

    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuffer strBuffer = new StringBuffer("");
        strBuffer.append(dimension());
        strBuffer.append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                strBuffer.append(String.format("%2d", blocks[i][j]));
                if (j != dimension() - 1) {
                    strBuffer.append(" ");
                }
            }
            strBuffer.append("\n");
        }
        return strBuffer.toString();
    }


//    public static void main(String[] args) // unit tests (not graded)

    private class BoardIndex {
        private int dim;
        private int row;
        private int col;
        public BoardIndex(int row, int col, int dim) {
            if (row >= 0 && row < dim && col >= 0 && col < dim) {
                this.dim = dim;
                this.row = row;
                this.col = col;
            }
            else {
                throw new IndexOutOfBoundsException();
            }
        }

        public List<BoardIndex> indexsAround() {
            int[][] offsets = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            List<BoardIndex> res = new ArrayList<BoardIndex>();
            for (int[] offset: offsets) {
                try {
                    BoardIndex pos = new BoardIndex(row + offset[0], col + offset[1], dim);
                    res.add(pos);
                }
                catch (IndexOutOfBoundsException e) {
                    continue;
                }
            }
            return res;
        }
    }

    private BoardIndex emptyBlockIndex() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    return new BoardIndex(i, j, dimension());
                }
            }
        }
        throw new Error("blocks do not have empty block");
    }


    private static int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    // exchange two element in the n*n block
    private static void exchange(int[][] arr, int i0, int j0, int i1, int j1) {
        validIndex(arr, i0, j0);
        validIndex(arr, i1, j1);
        int temp = arr[i0][j0];
        arr[i0][j0] = arr[i1][j1];
        arr[i1][j1] = temp;
    }

    private static void validIndex(int[][] arr, int i, int j) {
        if (i >= 0 && i < arr.length && j >= 0 && j < arr[i].length)
            return;
        throw new IndexOutOfBoundsException();
    }

}
