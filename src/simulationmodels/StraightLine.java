package simulationmodels;

import javafx.geometry.Point2D;

/**
 * Class to represent a geometric straight line from point A to point B.
 * Created by Piotrek on 01.04.2017.
 */
public class StraightLine {
    private Point2D pointA;
    private Point2D pointB;

    public StraightLine(Point2D pointA, Point2D pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public Point2D getPointA() {
        return pointA;
    }

    public void setPointA(Point2D pointA) {
        this.pointA = pointA;
    }

    public Point2D getPointB() {
        return pointB;
    }

    public void setPointB(Point2D pointB) {
        this.pointB = pointB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StraightLine that = (StraightLine) o;

        return (pointA != null ? pointA.equals(that.pointA) : that.pointA == null) && (pointB != null ? pointB.equals(that.pointB) : that.pointB == null);
    }

    @Override
    public String toString() {
        return "StraightLine{" +
                "pointA=" + pointA +
                ", pointB=" + pointB +
                '}';
    }
}
