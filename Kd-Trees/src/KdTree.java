/**
 * Created by Nero on 17/4/14.
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.List;
import java.util.ArrayList;

public class KdTree {
    // construct an empty set of points
    private Node root;

    public KdTree() {
        root = null;
    }

    // is the set empty?
    public           boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public               int size() {
//        if (isEmpty())
//            return 0;
//        return root.size;
        return sizeOfNode(root);
    }

    private int sizeOfNode(Node node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    private Node put(Node root, Point2D point, int level) {
        if (root == null)
            return new Node(point, level);
        Node left = root.left;
        Node right = root.right;
        int cmp = root.comp(point);
        if (cmp < 0) { // point is larger than center
            right = put(right, point, level+1);
        }else if (cmp > 0) {
            left = put(left, point, level+1);
        }else {
            return root;
        }
        root.left = left;
        root.right = right;
        root.size = sizeOfNode(right) + sizeOfNode(left) + 1;
        return root;
    }
    // add the point to the set (if it is not already in the set)
    public              void insert(Point2D p) {
        checkArgValid(p);
        root = put(root, p, 0);
    }


    private boolean contains(Node node, Point2D point) {
        if (node == null)
            return false;
        int cmp = node.comp(point);
        if (cmp == 0)
            return true;
        if (cmp < 0) { //point larger
            return contains(node.right, point);
        }else { // point smaller
            return contains(node.left, point);
        }
    }
    // does the set contain point p?
    public           boolean contains(Point2D p)   {
        return contains(root, p);
    }

    private void drawIter(Node node) {
        if (node == null)
            return;
        node.point.draw();
        drawIter(node.left);
        drawIter(node.right);
    }

    // draw all points to standard draw
    public              void draw() {
        drawIter(root);
    }

    private List<Point2D> rangIter(Node node, RectHV rect) {
        if (node == null) {
            List<Point2D> list = new ArrayList<>();
            return list;
        }
        int cmp = node.comp(rect);
        if (cmp < 0) { // rect is up or right
            return rangIter(node.right, rect);
        }else if (cmp >0) {
            return rangIter(node.left, rect);
        }else {
            List<Point2D> leftList = rangIter(node.left, rect);
            List<Point2D> rightList = rangIter(node.right, rect);
            leftList.addAll(rightList);
            if (rect.distanceTo(node.point) == 0) {
                leftList.add(node.point);
            }
            return leftList;
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return rangIter(root, rect);
    }

    public  Point2D nearestIter(Node node, Point2D p, double minDistance, Point2D minP) {
        if (node == null)
            return minP;
        double disToP = node.point.distanceTo(p);
        if (disToP < minDistance) {
            minDistance = disToP;
            minP = node.point;
        }
        Node first = node.firstToGo(p);
        Node second = node.secondToGo(p);
        if (first != null) {
            assert second != null;
            Point2D nereastP1 = nearestIter(first, p, minDistance, minP);
            double newMinDistance1 = nereastP1.distanceSquaredTo(p);
            double nerestToSplitLine = node.nereastDisToSplitLine(p);
            if (newMinDistance1 < nerestToSplitLine) {
                return nereastP1;
            }else {
                Point2D nereastP2 = nearestIter(second, p, newMinDistance1, nereastP1);
                return nereastP2;
            }
        }else {
            Point2D nereastP1 = nearestIter(node.left, p, minDistance, minP);
            double newMinDistance1 = nereastP1.distanceSquaredTo(p);
            Point2D nereastP2 = nearestIter(second, p, newMinDistance1, nereastP1);
            return nereastP2;
        }
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public           Point2D nearest(Point2D p) {
        checkArgValid(p);
        return nearestIter(root, p, 1000, null);
    }
//
//    public static void main(String[] args)                  // unit testing of the methods (optional)

    private void checkArgValid(Object p) {
        if (p == null)
            throw new NullPointerException();
    }
}

class Node {
    public Node left;
    public Node right;
    public Point2D point;

    private int level;
    int size;

    public Node(Point2D point, int level) {
        left = null;
        right = null;
        this.point = point;
        this.level = level;
        size = 1;
    }

    // Vertical case   -1 node's point is one the left of given point
    //                  1 node's point is one the right of the given point
    //                  0 same x
    // Horizontal case -1 node's point is below of the given point
    //                  1 node's point is above of the given point
    //                  0 same y
    public int comp(Point2D p) {
        double diff;
        if (isVertical())
            diff = point.x() - p.x();
        else
            diff = point.y() - p.y();
        if (diff < 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }

    // in the nearrest fun return the which child first to travel
    // if return is null should go to both
    public Node firstToGo(Point2D targetPoint) {
        int cmp = comp(targetPoint);
        if (cmp < 0) { // target point on the right/up of the node
            return right;
        }else if (cmp > 0) {
            return left;
        }else {
            return null;
        }
    }

    public Node secondToGo(Point2D targetPoint) {
        Node first = firstToGo(targetPoint);
        if (first == null)
            return null;
        if (first == left) {
            return right;
        }else {
            return left;
        }
    }

    // if Vertical return subtraction of x
    //    Horizontal return subtraction of y
    public double nereastDisToSplitLine(Point2D p) {
        if (isVertical()) {
            return Math.abs(point.x() - p.x());
        }else {
            return Math.abs(point.y() - p.y());
        }
    }

    // comp the node's x(vertical) or node's y(Horizontal)
    // Vertical case:  1 means rect is on the left of node
    //                -1 means rect is on the right of node
    //                 0 means node's x is between minX and maxX
    // Horizontal case:  1 means rect is below the node
    //                  -1 means rect is above the node
    //                   0 means node's y is between minY and maxY
    public int comp(RectHV rect) {
        if(isVertical()) {
            if (point.x() < rect.xmin())
                return -1;
            else if (point.x() > rect.xmax())
                return 1;
            else
                return 0;
        }else {
            if (point.y() < rect.ymin())
                return -1;
            else if (point.y() > rect.ymax())
                return 1;
            else
                return 0;
        }
    }



    public boolean isVertical() {
        return level % 2 == 0;
    }

}