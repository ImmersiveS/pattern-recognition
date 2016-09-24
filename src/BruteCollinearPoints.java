import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    private int numOfLineSegments = 0;
    private ArrayList<LineSegment> hugSegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        int N = points.length;

        if(points == null)
            throw new NullPointerException();

        for(Point point: points) {

            if (point == null)
                throw new NullPointerException();
        }

        Arrays.sort(points);


        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {

                if (j != i && points[j] == points[i])
                    throw new IllegalArgumentException();

                for (int k = j + 1; k < N; k++) {
                    for (int l = k + 1; l < N; l++) {

                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];

                        if (p.slopeTo(q) == q.slopeTo(r)
                                &&
                                q.slopeTo(r) == r.slopeTo(s)
                                &&
                                !hugSegments.contains(new LineSegment(p, s))
                                &&
                                !hugSegments.contains(new LineSegment(p, q))
                                &&
                                !hugSegments.contains(new LineSegment(p, r))
                                &&
                                !hugSegments.contains(new LineSegment(q, s))
                                &&
                                !hugSegments.contains(new LineSegment(q, r))
                                &&
                                !hugSegments.contains(new LineSegment(r, s))) {
                            hugSegments.add(new LineSegment(p,s));
                            numOfLineSegments++;
                        }
                    }
                }
            }
        }

        lineSegments = new LineSegment[hugSegments.size()];
        for(int i = 0; i < hugSegments.size(); i++) {
            lineSegments[i] = hugSegments.get(i);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numOfLineSegments;
    }

    // the line segments
    public LineSegment[] segments() { return lineSegments; }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}