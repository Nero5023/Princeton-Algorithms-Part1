/**
 * Created by Nero on 17/3/17.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF sitesUF;
    private WeightedQuickUnionUF sidesFullUF;
    private boolean[] sitesIsOpen;
    private int side;
    private int upSiteIndex;
    private int downSiteIndex;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        // sitsUF's n*n site is up virtual site
        //        n*n+1 site is down virtual site
        sitesUF = new WeightedQuickUnionUF(n*n + 2);
        sidesFullUF = new WeightedQuickUnionUF(n*n + 1);
        sitesIsOpen = new boolean[n*n];
        for (int i = 0; i < n*n; i++)
            sitesIsOpen[i] = false;
        side = n;
        upSiteIndex = side*side;
        downSiteIndex = side*side+1;
    }

    // convert the row and col to index
    private int rowColToIndex(int row, int col) {
        row = row - 1;
        col = col - 1;
        int index = row * side + col;
        if (index >= side*side || index < 0 || row < 0 || row >= side || col < 0 || col >= side)
            throw new IndexOutOfBoundsException();
        return index;
    }

    // add element to array
    private int[] appendArray(int[] array, int x) {
        int[] result = new int[array.length + 1];

        for (int i = 0; i < array.length; i++)
            result[i] = array[i];

        result[result.length - 1] = x;

        return result;
    }

    // return the legal index aournd the given index
    private int[] indexsAround(int row, int col) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int [] aroundIndexs = {};
        for (int[] dir : directions) {
            try {
                int index = rowColToIndex(row+dir[0], col+dir[1]);
                aroundIndexs = appendArray(aroundIndexs, index);
            }
            catch (IndexOutOfBoundsException e) {
                continue;
            }
        }

        return aroundIndexs;
    }

    // open site (row, col) if it is not open already
    public    void open(int row, int col) {
        if (isOpen(row, col))
            return;
        int index = rowColToIndex(row, col);
        sitesIsOpen[index] = true;
        if (row == 1) {
            sitesUF.union(index, upSiteIndex);
            sidesFullUF.union(index, upSiteIndex);
        }


        //!this.percolates() this situation is prevent after percolates, every
        //sites open down will be full, but not fix 在下面按了几个后, 另一块区域联通是的,原来的区域 full 了
        if (row == side  && !this.percolates())
            sitesUF.union(index, downSiteIndex);

        for (int aroundIndex : indexsAround(row, col)) {
            if (sitesIsOpen[aroundIndex]) {
                sitesUF.union(aroundIndex, index);
                sidesFullUF.union(aroundIndex, index);
            }
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = rowColToIndex(row, col);
        return sitesIsOpen[index];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = rowColToIndex(row, col);
        return sidesFullUF.connected(index, upSiteIndex);
//        return sitesUF.connected(index, upSiteIndex);
    }

//    private boolean isFull(int index) {
//        if (index >= side*side || index < 0)
//            throw new IllegalArgumentException();
//        return sitesUF.connected(index, upSiteIndex);
//    }

    // number of open sites
    public     int numberOfOpenSites() {
        int opened = 0;
        for (boolean x : sitesIsOpen) {
            if (x)
                opened += 1;
        }
        return opened;
    }

    // does the system percolate?
    public boolean percolates() {
        return sitesUF.connected(upSiteIndex, downSiteIndex);
    }

//    public static void main(String[] args)   // test client (optional)
}