/******************************************************************************
 *  Compilation:  javac-algs4  FastCollinearPoints.java
 *  Execution:    java-algs4  FastCollinearPoints
 *  Dependencies: none
 *
 *  Pattern Recognition: if 4 or more points are on a line.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import javax.swing.text.Segment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FastCollinearPoints {
    // finds all line segments containing 4 or more points

    private LineSegment[] segments;
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("Arguments Points is null");
        for (Point p: points) {
            if (p == null) {
                throw new NullPointerException("Point is null");
            }
        }
        checkDuplicatedEntries(points);

        List<LineSegment> segmentsList = new ArrayList<LineSegment>();
        List<Point[]> fromEndPoints = new ArrayList<Point[]>();
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];

            Arrays.sort(sortedPoints, point.slopeOrder());

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
                    addSegmentIfNew(segmentsList, fromEndPoints, samePointsArray[0], samePointsArray[samePointsSize-1]);
                }

                // restruct the point list
                samePoints = new ArrayList<Point>();
                samePoints.add(p);
            }
        }
        segments = new LineSegment[segmentsList.size()];
        segmentsList.toArray(segments);
    }

    private void addSegmentIfNew(List<LineSegment> segmentsList,List<Point[]> endPoints, Point from, Point to) {
        for (Point[] ps: endPoints) {
            if (ps[0].compareTo(from) == 0 && ps[1].compareTo(to) == 0) {
                return;
            }
        }
        Point[] fromto = {from, to};
        endPoints.add(fromto);
        segmentsList.add(new LineSegment(from, to));
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
}
