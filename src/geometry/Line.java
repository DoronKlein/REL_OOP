package geometry;

public class Line {
    private Point start;
    private Point end;

    // constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    // Return the length of the line
    public double length() {
        return this.end.distance(this.start);
    }

    // Returns the middle point of the line
    public Point middle() {
        double xMiddle = (this.end.getX() + this.start.getX()) / 2;
        double yMiddle = (this.end.getY() + this.start.getY()) / 2;

        return new Point(xMiddle, yMiddle);
    }

    // Returns the start point of the line
    public Point start() {
        return this.start;
    }

    // Returns the end point of the line
    public Point end() {
        return this.end;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        return this.intersectionWith(other) != null;
    }

    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {
        double thisXdistance = this.end.getX() - this.start.getX();
        double thisYdistance = this.end.getY() - this.start.getY();
        double otherXdistance = other.end.getX() - other.start.getX();
        double otherYdistance = other.end.getY() - other.start.getY();

        double denominator = thisYdistance * otherXdistance - thisXdistance * otherYdistance;

        if (denominator == 0) {
            Point intersection;

            intersection = checkEdgeIntersection(this.start, this.end, other.start, other.end);
            if (intersection != null) {
                return intersection;
            }
            intersection = checkEdgeIntersection(this.start, this.end, other.end, other.start);
            if (intersection != null) {
                return intersection;
            }
            intersection = checkEdgeIntersection(this.end, this.start, other.end, other.start);
            if (intersection != null) {
                return intersection;
            }
            intersection = checkEdgeIntersection(this.end, this.start, other.start, other.end);
            if (intersection != null) {
                return intersection;
            }
            return null;
        }

        double otherThisStartYdiff = other.start.getY() - this.start.getY();
        double otherThisStartXdiff = other.start.getX() - this.start.getX();

        double alpha = (otherXdistance * otherThisStartYdiff - otherYdistance * otherThisStartXdiff) / denominator;
        double beta = (thisXdistance * otherThisStartYdiff - thisYdistance * otherThisStartXdiff) / denominator;

        if (alpha < 0 || alpha > 1 || beta < 0 || beta > 1) {
            return null;
        }

        double intersectionX = this.start.getX() + alpha * thisXdistance;
        double intersectionY = this.start.getY() + alpha * thisYdistance;

        return new Point(intersectionX, intersectionY);
    }

    private Point checkEdgeIntersection(Point start1, Point end1, Point start2, Point end2) {
        Line line1 = new Line(start1, end1);
        Line line2 = new Line(start2, end2);
        if (start1.equals(start2)) {
            if (line1.isPointOnLine(end2) || line2.isPointOnLine(end1)) {
                return null;
            }
            return start1;
        }
        return null;
    }

    private boolean isPointOnLine(Point point) {
        final double EPSILON = 0.00001;

        double distance = this.start.distance(point) + this.end.distance(point);
        double length = this.start.distance(this.end);

        return distance <= length + EPSILON && distance >= length - EPSILON;
    }

    // equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        boolean equalsStraight = this.start.equals(other.start) && this.end.equals(other.end);
        boolean equalsFlipped = this.start.equals(other.end) && this.end.equals(other.start);

        return equalsStraight || equalsFlipped;
    }

}
