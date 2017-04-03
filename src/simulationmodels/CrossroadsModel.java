package simulationmodels;

import interfaces.StraightLinesShapeInterface;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * A class modelling a simple, two roads', right angle crossroads.
 * The roads are entering the crossroads from East, West, North and South.
 * Created by Piotrek on 03.04.2017.
 */
public class CrossroadsModel implements StraightLinesShapeInterface{

    private RoadModel roadNORTH;
    private RoadModel roadEAST;
    private RoadModel roadSOUTH;
    private RoadModel roadWEST;

    private Point2D leftUpperCorner;

    /**
     * The constructor of the crossroads
     * @param upperLeftCorner left upper corner of the crossroads - the point where the road from the North and from the West meet
     * @param lanesNum number of lanes on the roads
     * @param laneWidth the width of each lane
     * @param len_roadN the length of the road coming from the North
     * @param len_roadE the length of the road coming from the East
     * @param len_roadS the length of the road coming from the South
     * @param len_roadW the length of the road coming from the West
     */
    public CrossroadsModel(Point2D upperLeftCorner,
                           Integer lanesNum,
                           double laneWidth,
                           double len_roadN,
                           double len_roadE,
                           double len_roadS,
                           double len_roadW) {
        this.leftUpperCorner = upperLeftCorner;
        /**
         * Create the road from the NORTH
         */
        roadNORTH = new RoadModel(len_roadN,
                laneWidth,
                lanesNum,
                RoadModel.Orientation.VERTICAL,
                new Point2D(this.leftUpperCorner.getX(), this.leftUpperCorner.getY() - len_roadN)
        );

        /**
         * Create the road from the EAST
         */
        roadEAST = new RoadModel(len_roadE,
                laneWidth,
                lanesNum,
                RoadModel.Orientation.HORIZONTAL,
                new Point2D(this.leftUpperCorner.getX() + lanesNum*laneWidth, this.leftUpperCorner.getY())
        );

        /**
         * Create the road from the SOUTH
         */
        roadSOUTH = new RoadModel(len_roadS,
                laneWidth,
                lanesNum,
                RoadModel.Orientation.VERTICAL,
                new Point2D(this.leftUpperCorner.getX(), this.leftUpperCorner.getY() + lanesNum*laneWidth)
        );

        /**
         * Create the road from the WEST
         */
        roadWEST = new RoadModel(len_roadW,
                laneWidth,
                lanesNum,
                RoadModel.Orientation.HORIZONTAL,
                new Point2D(this.leftUpperCorner.getX() - len_roadW, this.leftUpperCorner.getY())
        );
    }

    public RoadModel getRoadNORTH() {
        return roadNORTH;
    }

    public RoadModel getRoadEAST() {
        return roadEAST;
    }

    public RoadModel getRoadSOUTH() {
        return roadSOUTH;
    }

    public RoadModel getRoadWEST() {
        return roadWEST;
    }

    public Point2D getLeftUpperCorner() {
        return leftUpperCorner;
    }

    public void move(Point2D transitionVector) {

        this.leftUpperCorner = new Point2D(
                this.leftUpperCorner.getX() + transitionVector.getX(),
                this.leftUpperCorner.getY() + transitionVector.getY()
        );

        roadNORTH.move(transitionVector);
        roadEAST.move(transitionVector);
        roadSOUTH.move(transitionVector);
        roadWEST.move(transitionVector);
    }

    @Override
    public List<StraightLine> getLines() {
        List<StraightLine> list = new ArrayList<>();
        list.addAll(roadNORTH.getLines());
        list.addAll(roadEAST.getLines());
        list.addAll(roadSOUTH.getLines());
        list.addAll(roadWEST.getLines());

        return list;
    }
}
