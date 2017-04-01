/******************************************************************************
 *  Compilation:  javac-algs4  BruteCollinearPoints.java
 *  Execution:    java-algs4  BruteCollinearPoints
 *  Dependencies: none
 *
 *  Pattern Recognition: if 4 points are on a line.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)  {

        checkDuplicatedEntries(points);

        List<LineSegment> segmentsList = new ArrayList<LineSegment>();

        if (points == null)
            throw new NullPointerException("Auguments is null");

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        for (int p = 0; p < pointsCopy.length - 3; p++) {
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        if (points[q] == null || points[q] == null || points[r] == null || points[s] == null) {
                            throw new NullPointerException("Point in points list is null");
                        }
                        if (pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[r]) &&
                                pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[s])) {
                            segmentsList.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
        }


        segments = new LineSegment[segmentsList.size()];
        segmentsList.toArray(segments);
    }

    // the number of line segments
    public           int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }

    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
    }


    public static void main(String[] args) {
        Point[] ps = {new Point(19000, 10000), new Point(18000, 10000), new Point(32000, 10000), new Point(12000, 10000)};
        BruteCollinearPoints bcp = new BruteCollinearPoints(ps);
        LineSegment[] ls = bcp.segments();
        int number = bcp.numberOfSegments();
        StdOut.println(number);
    }
}