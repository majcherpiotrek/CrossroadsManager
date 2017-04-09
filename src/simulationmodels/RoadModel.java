package simulationmodels;

import javafx.geometry.Point2D;
import interfaces.StraightLinesShapeInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a road made with simple, straight lines.
 * It is possible to specify the length of the road, horizontal or vertical orientation,
 * number of lanes, the width of a single lane and the position of left upper corner of the road.
 * Created by Piotrek on 01.04.2017.
 */
public class RoadModel implements StraightLinesShapeInterface {

    public enum Orientation {
        HORIZONTAL, VERTICAL

    }

    private ArrayList<StraightLine> lines;

    private Double roadLength;
    private Double laneWidth;
    private Integer totalLanesNum;
    private Orientation orientation;
    private Point2D leftUpperCorner;
    private Double separatingLineLength = 10.0;

    /**
     * The constructor of the road model
     *
     * @param roadLength      the length of the road
     * @param laneWidth       the width of each lane
     * @param totalLanesNum   number of the lanes on the road (total, not only one way)
     * @param orientation     horizontal or vertical orientation of the road (inner enum class)
     * @param leftUpperCorner the left upper corner of the upper (horizontal orientation) or left (vertical orientation) edge of the road
     */
    public RoadModel(Double roadLength, Double laneWidth, Integer totalLanesNum, Orientation orientation, Point2D leftUpperCorner) {
        this.lines = new ArrayList<>();
        this.roadLength = roadLength;
        this.laneWidth = laneWidth;
        this.totalLanesNum = totalLanesNum;
        this.orientation = orientation;
        this.leftUpperCorner = leftUpperCorner;
        if (this.separatingLineLength > this.roadLength)
            this.separatingLineLength = roadLength;

        this.computeLines();
    }

    private void computeLines() {
        switch (orientation) {
            case VERTICAL: {
                /*
                 * Add left edge of the road to the lines array
                 */
                lines.add(new StraightLine(
                        leftUpperCorner,
                        new Point2D(leftUpperCorner.getX(), leftUpperCorner.getY() + this.roadLength))
                );

                /*
                 * Add right edge of the road to the lines array
                 */
                lines.add(new StraightLine(
                        new Point2D(leftUpperCorner.getX() + this.totalLanesNum * this.laneWidth, leftUpperCorner.getY()),
                        new Point2D(leftUpperCorner.getX() + this.totalLanesNum * this.laneWidth, leftUpperCorner.getY() + this.roadLength)
                ));

                /*
                 * Draw lines separating the lanes
                 */
                Double lengthDrawn = 0.0;
                Double spaceBetweenLines = this.separatingLineLength / 3;
                for (int i = 1; i <= this.totalLanesNum - 1; i++) {
                    Point2D startingPoint = new Point2D(this.leftUpperCorner.getX() + i * this.laneWidth, this.leftUpperCorner.getY() + lengthDrawn);

                    //Drawing the separating line along all the road length
                    while (lengthDrawn < this.roadLength) {

                        //Make sure the separating line does not get longer then the borders
                        Boolean lastLine = false;
                        if (lengthDrawn + this.separatingLineLength > this.roadLength)
                            lastLine = true;


                        lines.add(new StraightLine(
                                startingPoint,
                                new Point2D(startingPoint.getX(), startingPoint.getY() + ((lastLine) ? this.roadLength - lengthDrawn : this.separatingLineLength))
                        ));

                        //Updating the starting point after adding the line
                        startingPoint = new Point2D(startingPoint.getX(),
                                startingPoint.getY() + this.separatingLineLength + spaceBetweenLines
                        );

                        //Update the drawn length with the line and space after the line
                        lengthDrawn += (this.separatingLineLength + spaceBetweenLines);
                    }
                }

                break;
            }
            case HORIZONTAL: {
                /*
                 * Add upper edge of the road to the lines array
                 */
                lines.add(new StraightLine(
                        leftUpperCorner,
                        new Point2D(leftUpperCorner.getX() + this.roadLength, leftUpperCorner.getY()))
                );

                /*
                 * Add lower edge of the road to the lines array
                 */
                lines.add(new StraightLine(
                        new Point2D(leftUpperCorner.getX(), leftUpperCorner.getY() + (this.totalLanesNum * this.laneWidth)),
                        new Point2D(leftUpperCorner.getX() + this.roadLength, leftUpperCorner.getY() + (this.totalLanesNum * this.laneWidth))
                ));

                /*
                 * Draw lines separating the lanes
                 */
                Double lengthDrawn = 0.0;
                Double spaceBetweenLines = this.separatingLineLength / 3;
                for (int i = 1; i <= this.totalLanesNum - 1; i++) {
                    Point2D startingPoint = new Point2D(this.leftUpperCorner.getX() + lengthDrawn, this.leftUpperCorner.getY() + i * this.laneWidth);

                    //Drawing the separating line along all the road length
                    while (lengthDrawn < this.roadLength) {

                        //Make sure the separating line does not get longer then the borders
                        Boolean lastLine = false;
                        if (lengthDrawn + this.separatingLineLength > this.roadLength)
                            lastLine = true;


                        lines.add(new StraightLine(
                                startingPoint,
                                new Point2D(startingPoint.getX() + ((lastLine) ? this.roadLength - lengthDrawn : this.separatingLineLength), startingPoint.getY())
                        ));

                        //Updating the starting point after adding the line
                        startingPoint = new Point2D(startingPoint.getX() + this.separatingLineLength + spaceBetweenLines,
                                startingPoint.getY()
                        );

                        //Update the drawn length with the line and space after the line
                        lengthDrawn += (this.separatingLineLength + spaceBetweenLines);
                    }
                }
                break;
            }
        }
    }

    public Double getRoadLength() {
        return roadLength;
    }

    public void setRoadLength(Double roadLength) {
        this.roadLength = roadLength;
        this.computeLines();
    }

    public Double getLaneWidth() {
        return laneWidth;
    }

    public void setLaneWidth(Double laneWidth) {
        this.laneWidth = laneWidth;
        this.computeLines();
    }

    public Integer getTotalLanesNum() {
        return totalLanesNum;
    }

    public void setTotalLanesNum(Integer totalLanesNum) {
        this.totalLanesNum = totalLanesNum;
        this.computeLines();
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        this.computeLines();
    }

    public Point2D getLeftUpperCorner() {
        return leftUpperCorner;
    }

    @Override
    public List<StraightLine> getLines() {
        return this.lines;
    }

    /**
     * Check if given poind is on road
     * @param point2D point to check
     * @return true if point is on road, false if not
     */
    public boolean pointOnRoad(Point2D point2D) {

        if (this.orientation == Orientation.HORIZONTAL) {
            if (this.leftUpperCorner.getX() <= point2D.getX() && point2D.getX() <= this.leftUpperCorner.getX() + this.roadLength) {
                if (this.leftUpperCorner.getY() <= point2D.getY() && point2D.getY() <= this.leftUpperCorner.getY() + this.laneWidth*this.totalLanesNum )
                    return true;
            }
        }
        else if (this.orientation == Orientation.VERTICAL) {
            if (this.leftUpperCorner.getY() <= point2D.getY() && point2D.getY() <= this.leftUpperCorner.getY() + this.roadLength) {
                if (this.leftUpperCorner.getX() <= point2D.getX() && point2D.getX() <= this.leftUpperCorner.getX() + this.laneWidth * this.totalLanesNum)
                    return true;
            }
        }
        return false;
    }
}
