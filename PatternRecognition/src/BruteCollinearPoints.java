import edu.princeton.cs.algs4.StdOut;

import javax.sound.sampled.Line;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)  {
        List<LineSegment> segmentsList = new ArrayList<LineSegment>();

        if (points == null)
            throw new NullPointerException("Auguments is null");
        for (int p = 0; p < points.length; p++){
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        if (points[q] == null || points[q] == null || points[r] == null || points[s] == null) {
                            throw new NullPointerException("Point in points list is null");
                        }
                        double pq = points[p].slopeTo(points[q]);
                        double pr = points[p].slopeTo(points[r]);
                        double ps = points[p].slopeTo(points[s]);
                        if (pq == Double.NEGATIVE_INFINITY || pr == Double.NEGATIVE_INFINITY || ps == Double.NEGATIVE_INFINITY)
                            throw new IllegalArgumentException("Have repeat pint");
                        if (Math.abs(pq) == Math.abs(pr) && Math.abs(pq) == Math.abs(pr) && Math.abs(pq) == Math.abs(ps)) {
                            Point[] seqmentPoints = {points[p], points[q], points[r], points[s]};
                            Arrays.sort(seqmentPoints);
                            segmentsList.add(new LineSegment(seqmentPoints[0], seqmentPoints[3]));
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
        return segments;
    }


    public static void main(String[] args) {
        Point[] ps = {new Point(19000, 10000), new Point(18000, 10000), new Point(32000, 10000), new Point(12000, 10000)};
        BruteCollinearPoints bcp = new BruteCollinearPoints(ps);
        LineSegment[] ls = bcp.segments();
        int number = bcp.numberOfSegments();
        StdOut.println(number);
    }
}