/**
 * Created by Nero on 17/3/17.
 */
import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF sitesUF;
    private boolean[] sitesIsOpen;
    private int side;
    private int upSiteIndex;
    private int downSiteIndex;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) throws IllegalArgumentException {
        if (n <= 0)
            throw new IllegalArgumentException();
        // sitsUF's n*n site is up virtual site
        //        n*n+1 site is down virtual site
        sitesUF = new WeightedQuickUnionUF(n*n + 2);
        sitesIsOpen = new boolean[n];
        for (int i = 0; i < n; i++)
            sitesIsOpen[i] = false;
        side = n;
        upSiteIndex = side*side;
        downSiteIndex = side*side+1;
    }

    // convert the row and col to index
    private int rowColToIndex(int row, int col) throws IndexOutOfBoundsException {
        int index = row * side + col;
        if (index >= side*side || index < 0 || row < 0 || row >= side || col < 0 || col >= side)
            throw new IndexOutOfBoundsException();
        return index;
    }

    // add element to array
    private int[] appendArray(int[] array, int x){
        int[] result = new int[array.length + 1];

        for(int i = 0; i < array.length; i++)
            result[i] = array[i];

        result[result.length - 1] = x;

        return result;
    }

    // return the legal index aournd the given index
    private int[] indexsAround(int row, int col) {
        int[][] directions = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        int [] aroundIndexs = {};
        for (int[] dir : directions) {
            try {
                int index = rowColToIndex(row+dir[0], col+dir[1]);
                aroundIndexs = appendArray(aroundIndexs, index);
            }catch (IndexOutOfBoundsException) {
                continue;
            }
        }
        return aroundIndexs;
    }

    // open site (row, col) if it is not open already
    public    void open(int row, int col) {
        if (isOpen(row, col))
            return;
        int index = rowColToIndex(row,col);
        sitesIsOpen[index] = true;
        if (row == 0)
            sitesUF.union(index, upSiteIndex);
        if (row == downSiteIndex-1)
            sitesUF.union(index, downSiteIndex);
        for (int aroundIndex : indexsAround(row, col)) {
            if (sitesIsOpen[aroundIndex]){
                sitesUF.union(aroundIndex, index);
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col)  {
        int index = rowColToIndex(row, col);
        return sitesIsOpen[index];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return !isOpen(row, col);
    }

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