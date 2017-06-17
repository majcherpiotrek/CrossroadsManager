package simulationmodels;

import interfaces.StraightLinesShapeInterface;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * A class modelling a simple, two roads', right angle crossroads.
 * The roads are entering the crossroads from East, West, North and South.
 * Possibility to add traffic lights to each road entering the crossroads.
 * Created by Piotrek on 03.04.2017.
 */
public class CrossroadsView implements StraightLinesShapeInterface{

    private RoadView roadNORTH;
    private RoadView roadEAST;
    private RoadView roadSOUTH;
    private RoadView roadWEST;

    //The point where the road from the North and from the West meet
    //(left upper corner of the square of the crossroads)
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
    public CrossroadsView(Point2D upperLeftCorner,
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
        roadNORTH = new RoadView(len_roadN,
                laneWidth,
                lanesNum,
                RoadView.Orientation.VERTICAL,
                new Point2D(this.leftUpperCorner.getX(), this.leftUpperCorner.getY() - len_roadN)
        );

        /**
         * Create the road from the EAST
         */
        roadEAST = new RoadView(len_roadE,
                laneWidth,
                lanesNum,
                RoadView.Orientation.HORIZONTAL,
                new Point2D(this.leftUpperCorner.getX() + lanesNum*laneWidth, this.leftUpperCorner.getY())
        );

        /**
         * Create the road from the SOUTH
         */
        roadSOUTH = new RoadView(len_roadS,
                laneWidth,
                lanesNum,
                RoadView.Orientation.VERTICAL,
                new Point2D(this.leftUpperCorner.getX(), this.leftUpperCorner.getY() + lanesNum*laneWidth)
        );

        /**
         * Create the road from the WEST
         */
        roadWEST = new RoadView(len_roadW,
                laneWidth,
                lanesNum,
                RoadView.Orientation.HORIZONTAL,
                new Point2D(this.leftUpperCorner.getX() - len_roadW, this.leftUpperCorner.getY())
        );
    }

    public RoadView getRoadNORTH() {
        return roadNORTH;
    }

    public RoadView getRoadEAST() {
        return roadEAST;
    }

    public RoadView getRoadSOUTH() {
        return roadSOUTH;
    }

    public RoadView getRoadWEST() {
        return roadWEST;
    }

    public Point2D getLeftUpperCorner() {
        return leftUpperCorner;
    }

    public double getCrossroadsWidth() {
        return roadNORTH.getLaneWidth() * roadNORTH.getTotalLanesNum();
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
