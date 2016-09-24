import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private int numOfLineSegments = 0;
    private ArrayList<LineSegment> hugLineSegment = new ArrayList<>();
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        int N = points.length;

        if(points == null)
            throw new NullPointerException();

        for(Point point: points) {

            if (point == null)
                throw new NullPointerException();
        }
        Arrays.sort(points);

        for (Point naturalPoint : points) {

            // clone natural order, then sort to slope order based on slope to first point
            Point[] slopeOrderPoints = points.clone();
            Arrays.sort(slopeOrderPoints, naturalPoint.slopeOrder());

            // create new segment
            LinkedList<Point> segment = new LinkedList<>();
            // add point i as origin
            segment.add(naturalPoint);

            // index 0 in slope order sorted is always the origin point (slope negative infinity)
            // this loop compares i to j and i to j+1. j only needs to loop through N-2
            for (int j = 1; j < N-1; j++) {

                Point slopePoint = slopeOrderPoints[j];
                Point nextSlopePoint = slopeOrderPoints[j+1];

                double slope = naturalPoint.slopeTo(slopePoint);
                double nextSlope = naturalPoint.slopeTo(nextSlopePoint);

                if (slope == nextSlope) {
                    if (segment.peekLast() != slopePoint) {
                        segment.add(slopePoint);
                    }
                    segment.add(nextSlopePoint);
                }

                // clear segment if no match (end of segment or loop)
                if (slope != nextSlope || j == N-2) {
                    // first output segment if it is large enough
                    if (segment.size() > 3) {
                        outputSegment(segment);
                    }
                    segment.clear();
                    segment.add(naturalPoint);
                }
            }
        }
        lineSegments = new LineSegment[hugLineSegment.size()];
        for(int i = 0; i < hugLineSegment.size(); i++) {
            lineSegments[i] = hugLineSegment.get(i);
        }
    }

    private  void outputSegment(LinkedList<Point> segment) {
        // to remove sub-segments, we rely on the following logic:
        // the outer loop's array is sorted via natural order
        // the inner loop is sorted in slope order according
        // to the current number in the outer loop
        // a discovered segment should always start at
        // its naturally lowest point
        // in the case of a sub-segment, the outer loop will
        // start the segment at somewhere other than its lowest
        // in this case, we can discover this by comparing
        // whether the first point of the segment is in fact the lowest

        Point first = segment.removeFirst();
        Point second = segment.removeFirst();
        Point last = segment.removeLast();
        if (first.compareTo(second) < 0) {
            hugLineSegment.add(new LineSegment(first, last));

        }
    }
    // the number of line segments
    public int numberOfSegments() {
        return numOfLineSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }

}