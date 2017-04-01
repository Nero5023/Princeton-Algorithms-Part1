import edu.princeton.cs.algs4.StdOut;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nero on 17/3/27.
 */
public class FastCollinearPoints {
    // finds all line segments containing 4 or more points

    private LineSegment[] segments;
    public FastCollinearPoints(Point[] points){
        if (points == null)
            throw new NullPointerException("Arguments Points is null");
        for (Point p: points) {
            if (p == null){
                throw new NullPointerException("Point is null");
            }
        }
        checkDuplicatedEntries(points);

        List<LineSegment> segmentsList = new ArrayList<LineSegment>();
        for(int i = 0; i < points.length; i++) {
            Point point = points[i];
//            Point[] sortedPoints = FastCollinearPoints.moveOriginToPoint(points, point);
            Point[] sortedPoints = Arrays.copyOf(points, points.length);
            StdOut.println(point);
            Arrays.sort(sortedPoints, sortedPoints[i].slopeOrder());

            Point lastPoint = null;
            List<Point> samePoints = new ArrayList<Point>();
            for (int j = 0; j < sortedPoints.length; j++) {
                Point p = sortedPoints[j];
                if (lastPoint == null) {
                    samePoints.add(p);
                    lastPoint = p;
                    continue;
                }

                if (lastPoint.slopeTo(point) == p.slopeTo(point)) {
                    samePoints.add(p);
                    if (j != sortedPoints.length - 1) {
                        lastPoint = p;
                        continue;
                    }
                }
                // p is not equal lastPoint or the last point
                lastPoint = p;
                samePoints.add(point);
                int samePointsSize = samePoints.size();
                if (samePointsSize >= 4) {

                    Point[] samePointsArray = new Point[samePointsSize];
                    samePoints.toArray(samePointsArray);
                    Arrays.sort(samePointsArray);
//                    segmentsList.add(new LineSegment(samePointsArray[0], samePointsArray[samePointsSize-1]));
                    addSegmentIfNew(segmentsList, new LineSegment(samePointsArray[0], samePointsArray[samePointsSize-1]));
                }

                // restruct the point list
                samePoints = new ArrayList<Point>();
                samePoints.add(p);
            }
        }
        segments = new LineSegment[segmentsList.size()];
        segmentsList.toArray(segments);
    }

    private void addSegmentIfNew(List<LineSegment> segmentsList, LineSegment segment) {
        boolean isAdded = false;
        for (LineSegment s: segmentsList) {
            if (s.toString().compareTo(segment.toString()) == 0) {
                isAdded = true;
                return;
            }
        }
        if (isAdded == false)
            segmentsList.add(segment);
    }

    private static Point[] moveOriginToPoint(Point[] points, Point target) {
        Point[] res = new Point[points.length-1];
        int j = 0;
        for (int i = 0; i < points.length; i++) {
            if (target.compareTo(points[i]) != 0) {
                res[j] = points[i].deletePoint(target);
                j++;
            }
        }
        if (j < points.length - 1)
            throw new IllegalArgumentException("Have repeat point");
        return res;

    }


    // the number of line segments
    public           int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
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
}
