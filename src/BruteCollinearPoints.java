import java.util.Arrays;
import java.util.Objects;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    private int numOfLineSegments = 0;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        int N = points.length;

        if(points == null)
            throw new java.lang.NullPointerException();

        for(Point point: points) {

            if (point == null)
                throw new java.lang.NullPointerException();
        }

        Arrays.sort(points);

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {

                if (j != i && points[j] == points[i])
                    throw new java.lang.IllegalArgumentException();

                for (int k = j + 1; k < N; k++) {
                    for (int l = k + 1; l < N; l++) {

                        Point point1 = points[i];
                        Point point2 = points[j];
                        Point point3 = points[k];
                        Point point4 = points[l];

                        if (point1.slopeTo(point2) == point2.slopeTo(point3) &&
                                point2.slopeTo(point3) == point3.slopeTo(point4) &&
                                point1.compareTo(point2) < 1 &&
                                point2.compareTo(point3) < 1 &&
                                point3.compareTo(point4) < 1) {

                            lineSegments[numOfLineSegments++] = new LineSegment(point1, point4);

                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numOfLineSegments;
    }

    // the line segments
    public LineSegment[] segments() { return lineSegments; }

}