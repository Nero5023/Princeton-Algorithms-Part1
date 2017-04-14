/**
 * Created by Nero on 17/4/14.
 */
import java.lang.Math;
import edu.princeton.cs.algs4.StdDraw;

public class Point2D implements Comparable<Point2D> {
    // construct the point (x, y)
    private double x;
    private double y;
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // x-coordinate
    public  double x() {
        return x;
    }
    // y-coordinate
    public  double y() {
        return y;
    }

    // Euclidean distance between two points
    public  double distanceTo(Point2D that) {
        return Math.sqrt(distanceSquaredTo(that));
    }

    // square of Euclidean distance between two points
    public  double distanceSquaredTo(Point2D that) {
        double diffX = x - that.x;
        double diifY = y - that.y;
        return diffX*diffX + diifY*diifY;
    }

    // for use in an ordered symbol table
    public     int compareTo(Point2D that) {
        if (this.x < that.x || (this.x == that.x && this.y < that.y)) {
            return -1;
        }
        if (this.x == that.x && this.y == that.y) {
            return 0;
        }
        // this.y > that.y
        return 1;
    }

    // does this point equal that object?
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (this == this)
            return true;

        if (that.getClass() == Point2D.class) {
            Point2D anotherP = (Point2D) that;
            if (this.compareTo(anotherP) == 0)
                return true;
            else
                return false;
        }
        return false;
    }

    // draw to standard draw
    public    void draw() {
        StdDraw.point(x, y);
    }

    // string representation
    public  String toString() {
        return "(" + x + ", " + y + ")";
    }
}